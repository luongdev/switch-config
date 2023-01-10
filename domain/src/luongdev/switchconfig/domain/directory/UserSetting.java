package luongdev.switchconfig.domain.directory;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_settings")
public class UserSetting {

    @Id
    private UUID id;

    @Column(name = "key", nullable = false, updatable = false, length = 64)
    private String key;

    @Column(name = "value", length = 510)
    private String value;

    @Column(name = "is_variable", nullable = false, updatable = false)
    private boolean variable;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "extension_id", updatable = false, nullable = false)
    private User user;

    public UserSetting() {
        this.id = UUID.randomUUID();
        this.enabled = true;
        this.variable = true;
    }

    public UserSetting(User user, String key, String value, boolean variable) {
        this();
        this.user = user;
        this.key = key;
        this.value = value;
        this.variable = variable;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isVariable() {
        return variable;
    }

    public void setVariable(boolean variable) {
        this.variable = variable;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
