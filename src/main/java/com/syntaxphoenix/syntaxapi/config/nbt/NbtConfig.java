package com.syntaxphoenix.syntaxapi.config.nbt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.syntaxphoenix.syntaxapi.config.BaseConfig;
import com.syntaxphoenix.syntaxapi.nbt.NbtNamedTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtType;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.tools.NbtDeserializer;
import com.syntaxphoenix.syntaxapi.nbt.tools.NbtSerializer;

public class NbtConfig extends NbtConfigSection implements BaseConfig {
	
	private final NbtSerializer serializer;
	private final NbtDeserializer deserializer;
	
	public NbtConfig() {
		this(true);
	}
	
	public NbtConfig(boolean compressed) {
		serializer = new NbtSerializer(compressed);
		deserializer = new NbtDeserializer(compressed);
	}

	@Override
	public void load(File file) throws FileNotFoundException, IOException {
		
		if(!file.exists()) {
			return;
		}
		FileInputStream stream = new FileInputStream(file);
		
		NbtNamedTag tag = deserializer.fromStream(stream);
		
		stream.close();
	
		NbtTag nbt = tag.getTag();
		if(nbt.getType() != NbtType.COMPOUND) {
			return;
		}
		
		fromNbt((NbtCompound) nbt);
		
	}

	@Override
	public void save(File file) throws FileNotFoundException, IOException {
		
		if(!file.exists()) {
			String parentPath = file.getParent();
			if(parentPath != null && !parentPath.isEmpty()) {
				File parent = file.getParentFile();
				if(parent.exists()) {
					if(!parent.isDirectory()) {
						parent.delete();
						parent.mkdirs();
					}
				} else {
					parent.mkdirs();
				}
			}
			file.createNewFile();
		}
		
		FileOutputStream stream = new FileOutputStream(file);
		
		serializer.toStream(new NbtNamedTag("root", asNbt()), stream);
		
		stream.close();
		
	}

}
