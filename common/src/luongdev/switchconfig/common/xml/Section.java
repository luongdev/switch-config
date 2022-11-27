package luongdev.switchconfig.common.xml;

import luongdev.switchconfig.common.xml.sections.ConfigurationSection;
import luongdev.switchconfig.common.xml.sections.DirectorySection;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({
        ConfigurationSection.class,
        DirectorySection.class,
})
public abstract class Section {

    @XmlAttribute(name = "name")
    private String name;

    private Section() {}

    protected Section(String name) {
        this();
        assert StringUtils.isNotEmpty(name);

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
