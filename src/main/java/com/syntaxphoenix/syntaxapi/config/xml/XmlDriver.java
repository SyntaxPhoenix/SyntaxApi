package com.syntaxphoenix.syntaxapi.config.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XomDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;
import com.thoughtworks.xstream.io.xml.JDom2Driver;
import com.thoughtworks.xstream.io.xml.JDomDriver;
import com.thoughtworks.xstream.io.xml.KXml2DomDriver;
import com.thoughtworks.xstream.io.xml.KXml2Driver;
import com.thoughtworks.xstream.io.xml.StandardStaxDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.io.xml.WstxDriver;
import com.thoughtworks.xstream.io.xml.Xpp3DomDriver;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;
import com.thoughtworks.xstream.io.xml.XppDomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

public enum XmlDriver {

	XOM(new XomDriver()), DOM(new DomDriver()), DOM4J(new Dom4JDriver()), JDOM2(new JDom2Driver()),
	JDOM(new JDomDriver()), KXML2_DOM(new KXml2DomDriver()), KXML2(new KXml2Driver()),
	STANDART_STAX(new StandardStaxDriver()), STAX(new StaxDriver()), WSTX(new WstxDriver()),
	XPP3_DOM(new Xpp3DomDriver()), XPP3(new Xpp3Driver()), XPP_DOM(new XppDomDriver()), XPP(new XppDriver());

	private final HierarchicalStreamDriver driver;

	private XmlDriver(HierarchicalStreamDriver driver) {
		this.driver = driver;
	}

	public HierarchicalStreamDriver getDriver() {
		return driver;
	}

	public XStream newStream() {
		return new XStream(driver);
	}

}
