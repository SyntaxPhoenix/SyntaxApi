package com.syntaxphoenix.syntaxapi.version;

/**
 * 
 * @author Lauriichan
 *
 */
public abstract class Version implements Comparable<Version> {
	
	private int major;
	private int minor;
	private int patch;
	
	/*
	 * 
	 */
	
	public Version() {
		major = 0;
		minor = 0;
		patch = 0;
	}
	
	public Version(int major, int minor, int patch) {
		this.major = major;
		this.minor = minor;
		this.patch = patch;
	}
	
	/*
	 * 
	 */
	
	public int getMajor() {
		return major;
	}
	
	public int getMinor() {
		return minor;
	}
	
	public int getPatch() {
		return patch;
	}
	
	/*
	 * 
	 */
	
	protected Version setMajor(int major) {
		this.major = major;
		return this;
	}
	
	protected Version setMinor(int minor) {
		this.minor = minor;
		return this;
	}

	protected Version setPatch(int patch) {
		this.patch = patch;
		return this;
	}
	
	/*
	 * 
	 */
	
	// 1.5.3 (isHigher) 0.6.0 FALSCH
	// 					f t f -> true
	//				==> f - - -> false
	//
	// 1.5.3 (isHigher) 0.2.5 FALSCH
	//					f f t -> true
	//				==> f - - -> false
	//
	// 1.5.3 (isHigher) 1.5.4 RICHTIG
	//					f f t -> true
	//				==> f f t -> true
	
	public boolean isHigher(Version version) { 
		if(major > version.major)
			return true;
		if(major < version.major)
			return false;
		if(minor > version.minor)
			return true;
		if(minor < version.minor)
			return false;
		if(patch > version.patch)
			return true;
		return false;
	}
	
	public boolean isSimilar(Version version) {
		return major == version.major && minor == version.minor && patch == version.patch;
	}
	
	public boolean isLower(Version version) {
		if(major < version.major)
			return true;
		if(major > version.major)
			return false;
		if(minor < version.minor)
			return true;
		if(minor > version.minor)
			return false;
		if(patch < version.patch)
			return true;
		return false;
	}
	
	/*
	 * 
	 */
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Version) {
			return isSimilar((Version) obj);
		}
		return false;
	}
	
	/*
	 * 
	 */
	
	@Override
	public Version clone() {
		return init(major, minor, patch);
	}
	
	public Version update(int major, int minor, int patch) {
		return init(this.major + major, this.minor + minor, this.patch + patch);
	}
	
	protected abstract Version init(int major, int minor, int patch);
	
	/*
	 * 
	 */
	
	public abstract VersionAnalyzer getAnalyzer();
	
	public abstract VersionFormatter getFormatter();
	
	/*
	 * 
	 */
	
	@Override
	public String toString() {
		return getFormatter().format(this);
	}
	
	/*
	 * 
	 */
	
	@Override
	public int compareTo(Version version) {
		if(isLower(version))
			return -1;
		if(isHigher(version))
			return 1;
		return 0;
	}
	
}
