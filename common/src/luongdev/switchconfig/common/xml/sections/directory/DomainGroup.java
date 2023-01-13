package luongdev.switchconfig.common.xml.sections.directory;

import luongdev.switchconfig.common.xml.Domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "domain")
@XmlAccessorType(XmlAccessType.NONE)
public class DomainGroup extends Domain {

    @XmlElementRef
    private Group group;

    public DomainGroup() {
        super(null);
    }

    public DomainGroup(String name, Group group) {
        super(name);

        assert group != null;

        this.group = group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
