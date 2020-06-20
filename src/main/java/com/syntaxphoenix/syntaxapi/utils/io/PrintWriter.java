package com.syntaxphoenix.syntaxapi.utils.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

public class PrintWriter extends PrintStream {

	private final PrintStream stream;
	private boolean file = false;

	public PrintWriter(File file, PrintStream stream) throws FileNotFoundException {
		super(file);
		this.stream = stream;
	}

	public void writeFile(boolean state) {
		file = state;
	}

	public boolean writeFile() {
		return file;
	}

	@Override
	public void write(byte[] b) throws IOException {
		stream.write(b);
		if (file)
			super.write(b);
	}

	@Override
	public void write(byte[] buf, int off, int len) {
		stream.write(buf, off, len);
		if (file)
			super.write(buf, off, len);
	}

	@Override
	public void write(int b) {
		stream.write(b);
		if (file)
			super.write(b);
	}

}
