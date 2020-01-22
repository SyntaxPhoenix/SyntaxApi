package com.syntaxphoenix.syntaxapi.utils.alias;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.syntaxphoenix.syntaxapi.utils.java.Lists;

public class Alias {
	
	private final String name;
	private final List<String> aliases;
	
	private String displayName;
	
	public Alias(String name, String... aliases) {
		this(name, name, aliases);
	}
	
	public Alias(String name, String displayName, String... aliases) {
		this.name = name.toLowerCase();
		this.aliases = ImmutableList.copyOf(Lists.asList(aliases));
		this.displayName = displayName;
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
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	/*
	 * 
	 */
	
	public boolean isName(String name) {
		return this.name.equals(name);
	}
	
	public boolean isAlias(String alias) {
		return this.aliases.contains(alias);
	}
	
	public boolean isLabel(String label) {
		return isName((label = label.toLowerCase())) ? true : isAlias(label);
	}

	public boolean hasAliases() {
		return !this.aliases.isEmpty();
	}

}
