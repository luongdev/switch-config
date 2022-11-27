package luongdev.switchconfig.common.xml.sections;

import luongdev.switchconfig.common.xml.Section;
import luongdev.switchconfig.common.xml.sections.directory.Directory;
import luongdev.switchconfig.common.xml.sections.directory.DirectoryDomain;
import luongdev.switchconfig.common.xml.sections.directory.DirectoryGroup;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "section")
@XmlAccessorType(XmlAccessType.NONE)
public final class DirectorySection extends Section {

    @XmlElementRef(name = "domain")
    private DirectoryDomain domain;

    @XmlElementRef(name = "groups")
    private DirectoryGroup groups;

    private DirectorySection() {
        super("directory");
    }

    public DirectorySection(DirectoryDomain domain, Collection<Directory> directories) {
        this();
        assert domain != null;

        this.domain = domain;
        this.groups = new DirectoryGroup(new DirectoryGroup.Group(directories));
    }

    public DirectorySection(DirectoryDomain domain, Directory directory) {
        this();
        assert domain != null;
        assert directory != null;

        this.domain = domain;
        this.groups = new DirectoryGroup(new DirectoryGroup.Group(directory));
    }


    public DirectoryDomain getDomain() {
        return domain;
    }

    public DirectoryGroup getGroups() {
        return groups;
    }
}
