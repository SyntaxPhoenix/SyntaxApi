package com.syntaxphoenix.syntaxapi.config.yaml.utils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;

import com.syntaxphoenix.syntaxapi.config.yaml.exceptions.YamlException;
import com.syntaxphoenix.syntaxapi.reflection.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public class LinkedConstructor extends SafeConstructor {
	
	public static final AbstractReflect SAFE_CONSTRUCT = new Reflect(SafeConstructor.class).searchMethod("merge", "mergeNode", MappingNode.class, boolean.class, Map.class, List.class);

	public LinkedConstructor() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void flattenMapping(MappingNode node) {
		this.processDuplicateKeys(node);
		if (node.isMerged()) {
			node.setValue((List<NodeTuple>) SAFE_CONSTRUCT.run(this, "merge", node, true, new LinkedHashMap<>(), new LinkedList<>()));
		}

	}

	@Override
	protected void processDuplicateKeys(MappingNode node) {
		List<NodeTuple> nodeValue = node.getValue();
		Map<Object, Integer> keys = new LinkedHashMap<>(nodeValue.size());
		TreeSet<Integer> toRemove = new TreeSet<>();
		int i = 0;

		Iterator<NodeTuple> indicies2remove;
		for (indicies2remove = nodeValue.iterator(); indicies2remove.hasNext(); ++i) {
			NodeTuple tuple = indicies2remove.next();
			Node keyNode = tuple.getKeyNode();
			if (!keyNode.getTag().equals(Tag.MERGE)) {
				Object key = this.constructObject(keyNode);
				if (key != null) {
					try {
						key.hashCode();
					} catch (Exception var11) {
						throw new YamlException("while constructing a mapping // " + node.getStartMark().toString()
								+ " // found unacceptable key " + key + " // "
								+ tuple.getKeyNode().getStartMark().toString(), var11);
					}
				}

				Integer prevIndex = keys.put(key, i);
				if (prevIndex != null) {
					if (!this.isAllowDuplicateKeys()) {
						throw new YamlException(node.getStartMark().toString() + " // " + key + " // " + tuple.getKeyNode().getStartMark());
					}

					toRemove.add(prevIndex);
				}
			}
		}
		
		
		Iterator<Integer> indicies2remove2 = toRemove.descendingIterator();

		while (indicies2remove2.hasNext()) {
			nodeValue.remove(indicies2remove2.next().intValue());
		}

	}

}
