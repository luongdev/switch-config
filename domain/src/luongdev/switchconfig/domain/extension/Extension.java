package luongdev.switchconfig.domain.extension;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "extensions")
public class Extension {

    @Id
    @Column(name = "extension", length = 20)
    private String extension;

    @Column(name = "password")
    private String password;

    @Column(name = "account_code")
    private String accountCode;

    @Column(name = "limit_max", nullable = false)
    private int limitMax;

    @Column(name = "limit_destination")
    private String limitDestination;

    @Column(name = "call_timeout", nullable = false)
    private int callTimeout;

    @Column(name = "force_ping", nullable = false)
    private boolean forcePing;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "domain", nullable = false)
    private String domain;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<ExtensionGroup> groups;

    public Extension() {
        this.enabled = true;
        this.forcePing = false;
    }

    public Extension(String extension, String domain) {
        this();
        this.extension = extension;
        this.domain = domain;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public int getLimitMax() {
        return limitMax;
    }

    public void setLimitMax(int limitMax) {
        this.limitMax = limitMax;
    }

    public String getLimitDestination() {
        return limitDestination;
    }

    public void setLimitDestination(String limitDestination) {
        this.limitDestination = limitDestination;
    }

    public int getCallTimeout() {
        return callTimeout;
    }

    public void setCallTimeout(int callTimeout) {
        this.callTimeout = callTimeout;
    }

    public boolean isForcePing() {
        return forcePing;
    }

    public void setForcePing(boolean forcePing) {
        this.forcePing = forcePing;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
