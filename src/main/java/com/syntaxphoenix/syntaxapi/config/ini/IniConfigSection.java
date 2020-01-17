package com.syntaxphoenix.syntaxapi.config.ini;

import com.syntaxphoenix.syntaxapi.config.BaseSection;

public class IniConfigSection extends BaseSection {

	public IniConfigSection() {
		super("");
	}

	public IniConfigSection(String name) {
		super(name);
	}

	@Override
	protected BaseSection initSection(String name) {
		return new IniConfigSection(name);
	}

	@Override
	protected boolean isSectionInstance(BaseSection section) {
		return section instanceof IniConfigSection;
	}

}
