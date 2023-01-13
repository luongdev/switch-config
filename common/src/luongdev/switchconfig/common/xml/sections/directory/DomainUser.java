package luongdev.switchconfig.common.xml.sections.directory;

import luongdev.switchconfig.common.xml.Domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "domain")
@XmlAccessorType(XmlAccessType.NONE)
public class DomainUser extends Domain {

    @XmlElementRef
    private XmlUser user;

    public DomainUser() {
        super(null);
    }

    public DomainUser(String name, XmlUser user) {
        super(name);

        assert user != null;

        this.user = user;
    }

    public void setUser(XmlUser user) {
        this.user = user;
    }
}
