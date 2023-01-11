package luongdev.switchconfig.common.xml.sections.directory;

import luongdev.switchconfig.common.xml.shared.ParamList;
import luongdev.switchconfig.common.xml.shared.VariableList;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class UserXml extends UserBase {

    @XmlElementRef(name = "params", required = false)
    private ParamList params;

    @XmlElementRef(name = "variables", required = false)
    private VariableList variables;
    public UserXml() {
        super(null);
    }

    public UserXml param(String key, Object value) {
        if (StringUtils.isEmpty(key) || value == null) return this;

        if (params == null) params = new ParamList();

        params.add(key, value);

        return this;
    }

    public UserXml variable(String key, Object value) {
        if (StringUtils.isEmpty(key) || value == null) return this;

        if (variables == null) variables = new VariableList();

        variables.add(key, value);

        return this;
    }
}
