package com.syntaxphoenix.syntaxapi.nbt.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.syntaxphoenix.syntaxapi.nbt.NbtBigInt;
import com.syntaxphoenix.syntaxapi.nbt.NbtCompound;
import com.syntaxphoenix.syntaxapi.nbt.NbtList;
import com.syntaxphoenix.syntaxapi.nbt.NbtNamedTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtNumber;
import com.syntaxphoenix.syntaxapi.nbt.NbtString;
import com.syntaxphoenix.syntaxapi.nbt.NbtTag;
import com.syntaxphoenix.syntaxapi.nbt.NbtType;
import com.syntaxphoenix.syntaxapi.utils.java.Strings;

public class NbtParser {
	
	public static final MojangsonDeserializer DESERIALIZER = new MojangsonDeserializer();
	public static final MojangsonSerializer SERIALIZER = new MojangsonSerializer(true);
	
	public static String toPrettyMson(NbtNamedTag tag) {
		return SERIALIZER.toString(tag);
	}
	
	public static NbtNamedTag fromPrettyMson(String mson) {
		try {
			return DESERIALIZER.fromString(mson);
		} catch (IOException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static NbtTag fromObject(Object raw) {
		if(raw instanceof Number) {
			return NbtNumber.fromNumber((Number) raw);
		} else if(raw instanceof String) {
			String input = (String) raw;
			if(Strings.isNumeric(input)) {
				return new NbtBigInt(input);
			}
			return new NbtString(input);
		} else if(raw instanceof List) {
			List<NbtList<?>> list = fromList((List<Object>) raw);
			if(list.size() == 1) {
				return list.get(0);
			}
			return listsToList(list);
		} else if(raw instanceof Map) {
			return fromMap((Map<Object, Object>) raw);
		} else if(raw instanceof NbtTag) {
			return (NbtTag) raw;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static List<NbtList<?>> fromList(List<Object> list) {
		List<NbtList<?>> output = new ArrayList<>();
		if(list == null || list.isEmpty()) {
			output.add(new NbtList<>());
			return output;
		}
		HashMap<NbtType, NbtList<NbtTag>> sort = new HashMap<>();
		for(Object obj : list) {
			NbtTag tag = fromObject(obj);
			NbtType type = tag.getType();
			if(sort.containsKey(type)) {
				sort.get(type).add(tag);
			} else {
				NbtList<NbtTag> nbt = (NbtList<NbtTag>) NbtList.createFromType(type);  
				nbt.add(tag);
				sort.put(type, nbt);
			}
		}
		output.addAll(sort.values());
		return output;
	}
	
	public static NbtCompound fromMap(Map<Object, Object> map) {
		NbtCompound compound = new NbtCompound();
		if(map == null || map.isEmpty()) {
			return compound;
		}
		Set<Entry<Object, Object>> set = map.entrySet();
		for(Entry<Object, Object> entry : set) {
			Object raw = entry.getKey();
			if(!(raw instanceof String)) {
				continue;
			}
			String key = (String) raw;
			NbtTag input = fromObject(entry.getValue());
			if(input != null) {
				compound.set(key, input);
			}
		}
		return compound;
	}
	
	public static NbtList<NbtList<?>> listsToList(List<NbtList<?>> lists) {
		NbtList<NbtList<?>> list = new NbtList<>();
		if(lists == null || lists.isEmpty()) {
			return list;
		}
		for(NbtList<?> input : lists) {
			list.add(input);
		}
		return list;
	}

}
