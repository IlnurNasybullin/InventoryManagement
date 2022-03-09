package io.github.ilnurnasybullin.im.dto;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "constant")
public class ConstantDto {

    @XmlAttribute
    private String name;

    @XmlValue
    private String value;

    public String name() {
        return this.name;
    }

    public String value() {
        return this.value;
    }
}
