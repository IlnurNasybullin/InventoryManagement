module InventoryManagement {
    requires org.jgrapht.core;
    requires spring.boot;
    requires spring.core;
    requires spring.beans;
    requires spring.context;
    requires spring.boot.autoconfigure;
    requires spring.oxm;
    requires java.xml.bind;
    requires com.sun.xml.bind;
    requires jcommander;

    exports io.github.ilnurnasybullin.im to spring.beans, spring.context;
    exports io.github.ilnurnasybullin.im.service to spring.beans;
    exports io.github.ilnurnasybullin.im.config to spring.beans, spring.context;
    opens io.github.ilnurnasybullin.im to spring.core;
    opens io.github.ilnurnasybullin.im.config to spring.core;
    opens io.github.ilnurnasybullin.im.dto to java.xml.bind;
    exports io.github.ilnurnasybullin.im.component to spring.beans;
    opens io.github.ilnurnasybullin.im.component to jcommander;
}