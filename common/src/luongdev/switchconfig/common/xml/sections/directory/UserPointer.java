package luongdev.switchconfig.common.xml.sections.directory;

import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.NONE)
public class UserPointer extends UserBase {

    public UserPointer() {
        super("pointer");
    }

    public UserPointer(String id) {
        this();

        assert StringUtils.isNotEmpty(id);
        this.id = id;
    }

}
