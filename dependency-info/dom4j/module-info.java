module dom4j {
    requires transitive java.desktop;
    requires transitive java.xml;
    requires transitive java.xml.bind;

    exports org.dom4j;
    exports org.dom4j.bean;
    exports org.dom4j.datatype;
    exports org.dom4j.dom;
    exports org.dom4j.dtd;
    exports org.dom4j.io;
    exports org.dom4j.jaxb;
    exports org.dom4j.rule;
    exports org.dom4j.rule.pattern;
    exports org.dom4j.swing;
    exports org.dom4j.tree;
    exports org.dom4j.util;
    exports org.dom4j.xpath;
    exports org.dom4j.xpp;

}
