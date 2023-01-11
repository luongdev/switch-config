package luongdev.switchconfig.common.xml.sections.directory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.NONE)
public abstract class UserBase {

    @XmlAttribute(name = "id", required = true)
    protected String id;

    @XmlAttribute(name = "type")
    protected String type;

    private UserBase() {}

    protected UserBase(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
