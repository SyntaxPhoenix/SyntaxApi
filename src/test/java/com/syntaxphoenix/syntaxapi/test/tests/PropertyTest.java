package com.syntaxphoenix.syntaxapi.test.tests;

import java.io.IOException;
import java.util.function.Consumer;

import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.nbt.tools.MojangsonDeserializer;
import com.syntaxphoenix.syntaxapi.test.utils.Printer;
import com.syntaxphoenix.syntaxapi.utils.data.Properties;
import com.syntaxphoenix.syntaxapi.utils.data.Property;

public class PropertyTest implements Consumer<String[]>, Printer {

	@Override
	public void accept(String[] args) {
		
		String mson = "{\"count\":1}";
		
		print(mson);
		
		NbtCompound comp = null;
		try {
			comp = (NbtCompound) new MojangsonDeserializer().fromString(mson).getTag();
		} catch (IOException e) {
			print(e);
		}

		StringBuilder builder = new StringBuilder();
		for(NbtTag tag : comp.getValue().values()) {
			builder.append(tag.getType());
			builder.append(':');
			builder.append(tag.getValue());
			builder.append(", ");
		}
		String built = builder.toString();
		
		Properties properties = new Properties();
		properties.fromNbt(comp);
		
		StringBuilder builder2 = new StringBuilder();
		for(Property<?> property : properties.getProperties()) {
			builder2.append(property.getValueOwner());
			builder2.append(':');
			builder2.append(property.getValue());
			builder2.append(", ");
		}
		String built2 = builder2.toString();
		
		print(comp.toMSONString());
		print(built.substring(0, built.length() - 2));
		print(built2.substring(0, built2.length() - 2));
		
		print(properties.findProperty("count").tryParse(int.class).getValue());
		print(properties.findProperty("count").tryParse(Integer.class).getValue());
		
		properties.clearProperties();
		
		properties.addProperty(Property.create("t1", (Integer) 1));
		properties.addProperty(Property.create("t2", (int) 1));
		
		print(properties.findProperty("t1").tryParse(int.class).getValue());
		print(properties.findProperty("t1").tryParse(Integer.class).getValue());
		
		print(properties.findProperty("t2").tryParse(int.class).getValue());
		print(properties.findProperty("t2").tryParse(Integer.class).getValue());
		
	}

}
