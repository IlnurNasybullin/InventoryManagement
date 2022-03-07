package io.github.ilnurnasybullin.im.dto;

import javax.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class Task {
    private Parameters parameters;

    @XmlElementWrapper
    @XmlElement(name = "rule")
    private List<String> rules;

    @XmlElementWrapper
    @XmlElement(name = "unit")
    private List<String> castUnits;

    public Parameters parameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public List<String> rules() {
        return rules;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }

    public List<String> castUnits() {
        return castUnits;
    }

    public void setCastUnits(List<String> castUnits) {
        this.castUnits = castUnits;
    }
}
