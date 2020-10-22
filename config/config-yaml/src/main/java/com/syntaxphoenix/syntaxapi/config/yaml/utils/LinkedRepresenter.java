package com.syntaxphoenix.syntaxapi.config.yaml.utils;

import java.util.LinkedHashMap;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.BaseRepresenter;
import org.yaml.snakeyaml.representer.Representer;

import com.syntaxphoenix.syntaxapi.reflection.AbstractReflect;
import com.syntaxphoenix.syntaxapi.reflection.Reflect;

public class LinkedRepresenter extends Representer {

	public static final AbstractReflect BASE_REPRESENT = new Reflect(BaseRepresenter.class).searchField("objectMap", "representedObjects");

	public LinkedRepresenter() {
		super();
		replaceMap();
	}

	public LinkedRepresenter(DumperOptions options) {
		super(options);
		replaceMap();
	}

	private void replaceMap() {
		BASE_REPRESENT.setFieldValue(this, "objectMap", new LinkedHashMap<Object, Node>());
	}

}
