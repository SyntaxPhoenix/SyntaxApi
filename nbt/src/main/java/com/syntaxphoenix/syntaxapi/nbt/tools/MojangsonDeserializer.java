package com.syntaxphoenix.syntaxapi.nbt.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.stream.Collectors;

import com.syntaxphoenix.syntaxapi.nbt.NbtNamedTag;
import com.syntaxphoenix.syntaxapi.utils.io.TextDeserializer;

public class MojangsonDeserializer implements TextDeserializer<NbtNamedTag> {
    
    @Override
    public NbtNamedTag fromReader(Reader reader) throws IOException {
        BufferedReader buffReader = reader instanceof BufferedReader?
            (BufferedReader) reader :
            new BufferedReader(reader);
        
        String mson = buffReader.lines().collect(Collectors.joining());
        
        return MojangsonParser.parse(mson);
    }
    
}
