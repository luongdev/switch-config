package luongdev.switchconfig.domain.extension.commands;

import luongdev.switchconfig.common.util.Utils;
import luongdev.switchconfig.domain.extension.Extension;
import luongld.cqrs.Request;
import org.apache.commons.lang3.StringUtils;

public class CreateExtensionCommand implements Request<Extension> {

    private String extension;
    private String domain;
    private String password;
    private String accountCode;
    private int limitMax = 0;
    private int callTimeout = 20;
    private boolean forcePing = false;

    private CreateExtensionCommand() {}

    public CreateExtensionCommand(String extension, String domain) {
        assert StringUtils.isNotEmpty(extension) && StringUtils.isNotEmpty(domain);
        assert extension.matches("^\\d+$");

        this.extension = extension;
        this.domain = domain;
        this.password = Utils.randomPassword();
    }

    public CreateExtensionCommand password(String password) {
        assert StringUtils.isNotEmpty(password) && password.length() >= 6;
        this.password = password;

        return this;
    }

    public CreateExtensionCommand accountCode(String accountCode) {
        this.accountCode = accountCode;

        return this;
    }

    public CreateExtensionCommand limitMax(int limitMax) {
        assert limitMax >= 0;
        this.limitMax = limitMax;

        return this;
    }

    public CreateExtensionCommand callTimeout(int callTimeout) {
        assert callTimeout > 5;
        this.callTimeout = callTimeout;

        return this;
    }

    public CreateExtensionCommand forcePing(boolean forcePing) {
        this.forcePing = forcePing;

        return this;
    }

    public String getExtension() {
        return extension;
    }

    public String getDomain() {
        return domain;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public int getLimitMax() {
        return limitMax;
    }

    public int getCallTimeout() {
        return callTimeout;
    }

    public boolean isForcePing() {
        return forcePing;
    }

    //    static Builder builder() {
//        return new Builder();
//    }
//
//    public static class Builder {
//
//        String extension;
//        String domain;
//        String password;
//        String accountCode;
//        int limitMax = 0;
//        int callTimeout = 20;
//        String dialString;
//        boolean forcePing = false;
//
//        Builder() {}
//
//        public Builder extension(String extension) {
//            this.extension = extension;
//            return this;
//        }
//
//        public Builder domain(String domain) {
//            this.domain = domain;
//            return this;
//        }
//
//        public Builder password(String password) {
//            this.password = password;
//            return this;
//        }
//
//        public Builder accountCode(String accountCode) {
//            this.accountCode = accountCode;
//            return this;
//        }
//
//        public Builder limitMax(int limitMax) {
//            this.limitMax = limitMax;
//            return this;
//        }
//
//        public Builder dialString(String dialString) {
//            this.dialString = dialString;
//            return this;
//        }
//
//        public Builder forcePing(boolean forcePing) {
//            this.forcePing = forcePing;
//            return this;
//        }
//
//        public CreateExtensionCommand build() {
//            var cmd = new CreateExtensionCommand(extension, domain);
//
//        }
//    }
}
