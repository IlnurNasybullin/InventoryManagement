package io.github.ilnurnasybullin.im.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "discount")
@XmlAccessorType(XmlAccessType.FIELD)
public class DiscountDto {

    private String Q;
    private double value;

    public String Q() {
        return Q;
    }

    public void setQ(String Q) {
        this.Q = Q;
    }

    public double value() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
