package io.github.ilnurnasybullin.im.dto;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "variable")
@XmlAccessorType(XmlAccessType.FIELD)
public class VariableDto {

    @XmlAttribute
    private String name;

    @XmlAttribute
    private String description;

    @XmlValue
    private String expression;

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public String expression() {
        return expression;
    }
}
