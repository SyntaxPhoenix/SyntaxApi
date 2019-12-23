package com.syntaxphoenix.syntaxapi.nbt.tools;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import com.syntaxphoenix.syntaxapi.nbt.NbtNamedTag;
import com.syntaxphoenix.syntaxapi.utils.io.TextSerializer;

public class MojangsonSerializer implements TextSerializer<NbtNamedTag> {
    
    private final boolean pretty;
    
    /**
     * Constructs a new {@code MojangsonSerializer}.
     *
     * @param pretty whether to "pretty-print", adding whitespace, line breaks and indent
     */
    public MojangsonSerializer(boolean pretty) {
        this.pretty = pretty;
    }
    
    /**
     * Constructs a new {@code MojangsonSerializer} with disabled pretty-printing.
     */
    public MojangsonSerializer() {
        this(false);
    }
    
    @Override
    public void toWriter(NbtNamedTag nbt, Writer writer) throws IOException {
        MojangsonWriter msonWriter = new MojangsonWriter(writer, pretty);
        msonWriter.writeNamedTag(nbt);
        msonWriter.endLn(); // end last line to comply with POSIX standard
        msonWriter.flush();
        msonWriter.close();
    }
    
    @Override
    public String toString(NbtNamedTag nbt) {
        StringWriter stringWriter = new StringWriter();
        try {
            toWriter(nbt, stringWriter);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        return stringWriter.toString();
    }
    
}
