package com.syntaxphoenix.syntaxapi.config.xml;

import com.syntaxphoenix.syntaxapi.config.BaseSection;
import com.syntaxphoenix.syntaxapi.utils.config.SectionMap;
import com.thoughtworks.xstream.XStream;

public class XmlConfigSection extends BaseSection {
	
	public XmlConfigSection() {
		super("");
	}

	public XmlConfigSection(String name) {
		super(name);
	}

	@Override
	protected BaseSection initSection(String name) {
		return new XmlConfigSection(name);
	}
	
	/**
	 * @see com.syntaxphoenix.syntaxapi.config.BaseSection#isSectionInstance(com.syntaxphoenix.syntaxapi.config.BaseSection)
	 */
	@Override
	protected boolean isSectionInstance(BaseSection section) {
		return section instanceof XmlConfigSection;
	}
	
	/*
	 * 
	 * FROM XML
	 * 
	 */
	
	public void fromXmlString(String input) {
		fromXmlString(input, XmlDriver.XOM);
	}
	
	public void fromXmlString(String input, XmlDriver driver) {
		fromXmlString(input, driver.newStream());
	}
	
	@SuppressWarnings("unchecked")
	public void fromXmlString(String input, XStream stream) {
		stream.alias("section", SectionMap.class);
		fromMap((SectionMap<String, Object>) stream.fromXML(input));
	}
	
	
	/*
	 * 
	 * TO XML
	 * 
	 */
	
	public String toXmlString() {
		return toXmlString(XmlDriver.XOM);
	}
	
	public String toXmlString(XmlDriver driver) {
		return toXmlString(driver.newStream());
	}
	
	public String toXmlString(XStream stream) {
		SectionMap<String, Object> output = toMap();
		stream.alias("section", SectionMap.class);
		return stream.toXML(output);
	}

}
