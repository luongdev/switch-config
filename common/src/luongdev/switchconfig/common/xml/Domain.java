package luongdev.switchconfig.common.xml;

import luongdev.switchconfig.common.xml.sections.directory.DomainGroup;
import luongdev.switchconfig.common.xml.sections.directory.DomainUser;
import luongdev.switchconfig.common.xml.shared.ParamList;
import luongdev.switchconfig.common.xml.shared.VariableList;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({ DomainUser.class, DomainGroup.class })
public abstract class Domain {

    @XmlAttribute(required = true)
    private String name;

    @XmlAttribute
    private Boolean alias;

    @XmlElementRef(name = "params")
    private ParamList params;

    @XmlElementRef(name = "variables")
    private VariableList variables;

    protected Domain(String name, Boolean alias, ParamList params, VariableList variables) {
        assert StringUtils.isNotEmpty(name);

        this.name = name;
        this.alias = alias;
        this.params = params;
        this.variables = variables;
    }

    protected Domain(String name, Boolean alias) {
        this(name, alias, null, null);
    }

    protected Domain(String name) {
        this(name, null);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlias(Boolean alias) {
        this.alias = alias;
    }

    public void setParams(ParamList params) {
        this.params = params;
    }

    public void setVariables(VariableList variables) {
        this.variables = variables;
    }
}
