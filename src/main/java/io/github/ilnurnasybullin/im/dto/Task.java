package io.github.ilnurnasybullin.im.dto;

import javax.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class Task {

    @XmlElementWrapper
    @XmlElement(name = "constant", type = ConstantDto.class)
    private List<ConstantDto> constants;

    @XmlElementWrapper
    @XmlElement(name = "variable", type = VariableDto.class)
    private List<VariableDto> variables;

    @XmlElementWrapper
    @XmlElement(name = "rule")
    private List<String> rules;

    @XmlElementWrapper
    @XmlElement(name = "unit")
    private List<String> castUnits;

    public List<String> rules() {
        return rules;
    }

    public List<String> castUnits() {
        return castUnits;
    }

    public List<ConstantDto> constants() {
        return this.constants;
    }

    public List<VariableDto> variables() {
        return variables;
    }
}
