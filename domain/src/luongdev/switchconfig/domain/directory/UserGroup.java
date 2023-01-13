package luongdev.switchconfig.domain.directory;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_groups")
public class UserGroup {

    @Id
    private UUID id;

    @Column(name = "user_extension", length = 20, nullable = false)
    private String userExtension;

    @Column(name = "group_extension", length = 20, nullable = false)
    private String groupExtension;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    public UserGroup() {
        this.id = UUID.randomUUID();
    }

    public UserGroup(User user, Group group) {
        this();

        assert user != null && StringUtils.isNotEmpty(user.getExtension());
        assert group != null && StringUtils.isNotEmpty(group.getExtension());

        this.user = user;
        this.group = group;
        this.userExtension = user.getExtension();
        this.groupExtension = group.getExtension();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserExtension() {
        return userExtension;
    }

    public void setUserExtension(String userExtension) {
        this.userExtension = userExtension;
    }

    public String getGroupExtension() {
        return groupExtension;
    }

    public void setGroupExtension(String groupExtension) {
        this.groupExtension = groupExtension;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
