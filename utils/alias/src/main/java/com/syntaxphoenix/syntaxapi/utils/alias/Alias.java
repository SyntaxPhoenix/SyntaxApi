package com.syntaxphoenix.syntaxapi.utils.alias;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Alias {

	private final String name;
	private final List<String> aliases;

	private String displayName;
	private String description;

	public Alias(String name, String... aliases) {
		this.name = name.toLowerCase();
		this.aliases = Collections.unmodifiableList(Arrays.asList(aliases));
	}

	public String[] getAliases() {
		return aliases.toArray(new String[0]);
	}

	public String getName() {
		return name;
	}

	/*
	 * 
	 */

	public String getDisplayName() {
		return displayName == null ? name : displayName;
	}

	public Alias setDisplayName(String displayName) {
		this.displayName = displayName;
		return this;
	}

	/*
	 * 
	 */

	public String getDescription() {
		return description == null ? "" : description;
	}

	public Alias setDescription(String description) {
		this.description = description;
		return this;
	}

	/*
	 * 
	 */

	public boolean isName(String name) {
		return this.name.equals(name);
	}

	public boolean isAlias(String alias) {
		return aliases.contains(alias);
	}

	public boolean isLabel(String label) {
		return isName((label = label.toLowerCase())) ? true : isAlias(label);
	}

	/*
	 * 
	 */

	public boolean hasAliases() {
		return !aliases.isEmpty();
	}

	public boolean hasDisplayName() {
		return displayName != null && !displayName.isEmpty();
	}

	public boolean hasDescription() {
		return description != null && !description.isEmpty();
	}

	/*
	 * 
	 */

	public Alias removeConflicts(List<String> conflicts) {
		ArrayList<String> list = new ArrayList<>(aliases);
		list.removeAll(conflicts);
		return new Alias(name, list.toArray(new String[0])).setDisplayName(displayName).setDescription(description);
	}

	/*
	 * 
	 */

	public static Alias create(String name) {
		return new Alias(name);
	}

	public static Alias create(String name, String... aliases) {
		return new Alias(name, aliases);
	}

}
