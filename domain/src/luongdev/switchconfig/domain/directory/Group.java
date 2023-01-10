package luongdev.switchconfig.domain.directory;

import luongdev.switchconfig.domain.extension.Extension;
import luongdev.switchconfig.domain.extension.ExtensionType;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "groups")
@DiscriminatorValue("GROUP")
public class Group extends Extension {

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @MapKeyColumn(name = "extension")
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_groups",
            joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Map<String, User> users;

    public Group() {
        super(ExtensionType.GROUP);

        this.users = new HashMap<>();
    }

    public Group(String extension, String domain, String name) {
        super(ExtensionType.GROUP, extension, domain);

        assert StringUtils.isNotEmpty(this.getExtension());
        assert StringUtils.isNotEmpty(this.getDomain());
        assert StringUtils.isNotEmpty(name);

        this.name = name;
    }

    public Group addUser(User user) {
        if (user == null || StringUtils.isEmpty(user.getExtension())) return this;

        if (users.containsKey(user.getExtension())) return this;

        user.join(this);

        users.put(user.getExtension(), user);

        return this;
    }

    public Group rmUser(String extension) {
        if (StringUtils.isEmpty(extension) || !users.containsKey(extension)) return this;

        users.remove(extension);

        return this;
    }

    public Group rmUser(User user) {
        if (user == null) return this;

        return this.rmUser(user.getExtension());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }
}
