module org.hibernate.commons.annotations {
    requires transitive org.jboss.logging;

    exports org.hibernate.annotations.common;
    exports org.hibernate.annotations.common.annotationfactory;
    exports org.hibernate.annotations.common.reflection;
    exports org.hibernate.annotations.common.reflection.java;
    exports org.hibernate.annotations.common.reflection.java.generics;
    exports org.hibernate.annotations.common.util;
    exports org.hibernate.annotations.common.util.impl;

    opens org.hibernate.annotations.common.reflection.java;
}
