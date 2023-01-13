package luongdev.switchconfig.domain.directory.builder;

import luongdev.switchconfig.common.util.Utils;
import luongdev.switchconfig.domain.directory.Group;
import luongdev.switchconfig.domain.directory.User;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class UserBuilder {

    private UserBuilder() {
        this.callTimeout = 20;
        this.limitMax = 0;
        this.limitDestination = "error/user_busy";
        this.stereoRecord = true;
    }

    private String extension;
    private String domain;
    private String password;
    private String record;
    private String name;
    private boolean stereoRecord;
    private int callTimeout;
    private int limitMax;
    private String limitDestination;

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public UserBuilder extension(String extension) {
        this.extension = extension;

        return this;
    }

    public UserBuilder domain(String domain) {
        this.domain = domain;

        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;

        return this;
    }

    public UserBuilder record(String record) {
        this.record = record;

        return this;
    }

    public UserBuilder name(String name) {
        this.name = name;

        return this;
    }

    public UserBuilder stereoRecord(boolean stereoRecord) {
        this.stereoRecord = stereoRecord;

        return this;
    }

    public UserBuilder callTimeout(int callTimeout) {
        this.callTimeout = callTimeout;

        return this;
    }

    public UserBuilder limitMax(int limitMax) {
        this.limitMax = limitMax;

        return this;
    }

    public UserBuilder limitDestination(String limitDestination) {
        this.limitDestination = limitDestination;

        return this;
    }

    public User build() {
        if (StringUtils.isEmpty(extension)) throw new IllegalArgumentException("extension required!");
        if (StringUtils.isEmpty(domain)) throw new IllegalArgumentException("domain required!");
        if (StringUtils.isEmpty(name)) throw new IllegalArgumentException("name required!");

        var domainExtension = String.format("%s@%s", extension, domain);
        var user = new User(extension, domain, name)
                .variable("extension", extension)
                .variable("caller_id_number", extension)
                .variable("presence_id", domainExtension)
                .variable("export_vars", "domain_name")
                .variable("user_context", domain)
                .variable("domain_name", domain);

        if (StringUtils.isNotEmpty(record)) {
            user.variable("user_record", record);
        }

        if (StringUtils.isEmpty(password)) {
            password = Utils.randomPassword(15);
        }

        user.setPassword(password);
        user.param("password", password);

        if (StringUtils.isEmpty(name)) name = domainExtension;

        user.variable("caller_id_name", name);
        user.setName(name);

        if (callTimeout < 1 || callTimeout > 120) callTimeout = 20;

        user.variable("call_timeout", callTimeout);

        if (limitMax < 1) limitMax = 0;

        user.variable("limit_max", limitMax);

        if (StringUtils.isEmpty(limitDestination)) limitDestination = "error/user_busy";

        user.variable("limit_destination", limitDestination);

        user.param(
                "dial-string",
                String.format("{sip_invite_domain=%s,leg_timeout=%s,presence_id=%s}${sofia_contact(*/%s)}",
                    domain,
                    callTimeout,
                    domainExtension,
                    domainExtension
                )
        );

        return user;
    }
}
