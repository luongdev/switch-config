package luongdev.switchconfig.domain.extension;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "extensions")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Extension {

    @Id
    private UUID id;

    @Column(name = "extension", length = 20, unique = true, nullable = false)
    private String extension;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 36)
    private ExtensionType type;

    @Column(name = "xml", length = 2040)
    private String xml;

    @Column(name = "domain", nullable = false)
    private String domain;

    public Extension() {
        this.id = UUID.randomUUID();
    }

    protected Extension(ExtensionType type) {
        this();
        this.type = type;
    }

    protected Extension(ExtensionType type, String extension, String domain) {
        this(type);
        this.domain = domain;
        this.extension = extension;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public ExtensionType getType() {
        return type;
    }

    public void setType(ExtensionType type) {
        this.type = type;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
