package com.syntaxphoenix.syntaxapi.version;

import com.syntaxphoenix.syntaxapi.utils.java.Strings;

public class DefaultVersion extends Version {

	public DefaultVersion() {
		super();
	}

	public DefaultVersion(int major, int minor, int patch) {
		super(major, minor, patch);
	}

	/*
	 * 
	 */

	@Override
	protected DefaultVersion init(int major, int minor, int patch) {
		return new DefaultVersion(major, minor, patch);
	}

	@Override
	public VersionAnalyzer getAnalyzer() {
		return formatted -> {
			DefaultVersion version = new DefaultVersion();
			String[] parts = formatted.contains(".") ? formatted.split(".") : new String[] { formatted };
			try {
				if (parts.length == 1) {
					version.setMajor(Strings.isNumeric(parts[0]) ? Integer.parseInt(parts[0]) : 0);
				} else if (parts.length == 2) {
					version.setMajor(Strings.isNumeric(parts[0]) ? Integer.parseInt(parts[0]) : 0);
					version.setMinor(Strings.isNumeric(parts[1]) ? Integer.parseInt(parts[1]) : 0);
				} else {
					version.setMajor(Strings.isNumeric(parts[0]) ? Integer.parseInt(parts[0]) : 0);
					version.setMinor(Strings.isNumeric(parts[1]) ? Integer.parseInt(parts[1]) : 0);
					version.setPatch(Strings.isNumeric(parts[2]) ? Integer.parseInt(parts[2]) : 0);
				}
			} catch (NumberFormatException ex) {
				
			}
			return version;
		};
	}

	@Override
	public VersionFormatter getFormatter() {
		return version -> version.getMajor() + "." + version.getMinor() + "." + version.getPatch();
	}

}
