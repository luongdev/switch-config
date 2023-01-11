package luongdev.switchconfig.domain.directory;

import luongdev.switchconfig.domain.extension.Extension;
import luongdev.switchconfig.domain.extension.ExtensionType;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "users")
@DiscriminatorValue("DIRECTORY")
public class User extends Extension {

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    @MapKeyColumn(name = "key")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private Map<String, UserSetting> settings;

    @MapKeyColumn(name = "group_extension")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Map<String, UserGroup> groups;

    public User() {
        super(ExtensionType.DIRECTORY);

        this.enabled = true;
        this.settings = new HashMap<>();
        this.groups = new HashMap<>();
    }

    @Override
    protected String generateXml() {
        return null;
    }

    public User(String extension, String domain, String name) {
        this();

        assert StringUtils.isNotEmpty(extension);
        assert StringUtils.isNotEmpty(domain);
        assert StringUtils.isNotEmpty(name);

        this.name = name;
        this.setExtension(extension);
        this.setDomain(domain);
    }

    public User config(String key, Object value, boolean variable) {
        if (StringUtils.isEmpty(key)) return this;

        var setting = settings.get(key);
        if (setting == null) {
            setting = new UserSetting(this, key, String.valueOf(value), variable);
            settings.put(key, setting);
        } else {
            setting.setValue(String.valueOf(value));
        }

        return this;
    }

    public String getConfig(String key) {
        if (StringUtils.isEmpty(key)) return null;

        var setting = settings.get(key);

        return setting == null ? null : setting.getKey();
    }

    public User join(Group group) {
        if (group == null || StringUtils.isEmpty(group.getExtension())) return this;

        if (this.groups.containsKey(group.getExtension())) return this;

        this.groups.put(group.getExtension(), new UserGroup(this, group));

        return this;
    }

    public User variable(String key, Object value) {
        return this.config(key, value, true);
    }

    public User param(String key, String value) {
        return this.config(key, value, false);
    }

    public User rmConfig(String key) {
        if (StringUtils.isEmpty(key)) return this;

        settings.remove(key);

        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Map<String, UserSetting> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, UserSetting> settings) {
        this.settings = settings;
    }

    public Map<String, UserGroup> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, UserGroup> groups) {
        this.groups = groups;
    }
}