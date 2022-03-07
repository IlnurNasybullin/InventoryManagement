package io.github.ilnurnasybullin.im.dto;

import javax.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@XmlRootElement(name = "parameters")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Parameters {
    private String K;
    private String v;
    private String T;
    private String s;
    private String tOrd;
    private String C;

    @XmlElementWrapper
    @XmlElement(name = "discount", type = DiscountDto.class)
    private List<DiscountDto> discounts = new ArrayList<>();

    public String K() {
        return K;
    }

    public void setK(String K) {
        this.K = K;
    }

    public String V() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String T() {
        return T;
    }

    public void setT(String T) {this.T = T;}

    public String s() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String tOrd() {
        return tOrd;
    }

    public void settOrd(String tOrd) {
        this.tOrd = tOrd;
    }

    public String C() {
        return C;
    }

    public void setC(String C) {
        this.C = C;
    }

    public List<DiscountDto> discounts() {
        return discounts;
    }

    public void setDiscounts(List<DiscountDto> discounts) {
        this.discounts = discounts;
    }
}
