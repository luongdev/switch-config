package luongdev.switchconfig.common.xml.sections.directory;

import javax.xml.bind.annotation.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "group")
@XmlAccessorType(XmlAccessType.NONE)
public class Group {

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlElementRef(name = "users")
    private final Set<PointerUser> users;

    private Group() {
        this.name = null;
        this.users = new HashSet<>();
    }

    public Group(String name, Collection<? extends User> users) {
        this();
        this.name = name;

        if (users == null || users.isEmpty()) return;

        for (var user : users) {
            if (user instanceof PointerUser pointer) {
                this.users.add(pointer);
            } else if (user instanceof XmlUser xml) {
                this.users.add(new PointerUser(xml.getId()));
            }
        }
    }

    public Group(String name, User...users) {
        this(name, Arrays.asList(users));
    }

    public String getName() {
        return name;
    }

    public Set<PointerUser> getUsers() {
        return users;
    }
}
