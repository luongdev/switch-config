package luongdev.switchconfig.common.xml.sections;

import luongdev.switchconfig.common.xml.Section;
import luongdev.switchconfig.common.xml.Domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "section")
@XmlAccessorType(XmlAccessType.NONE)
public final class DirectorySection extends Section {

    @XmlElementRef(name = "domain")
    private Domain domain;

    private DirectorySection() {
        super("directory");
    }

    public DirectorySection(Domain domain) {
        this();
        assert domain != null;

        this.domain = domain;
    }


    public Domain getDomain() {
        return domain;
    }

}
