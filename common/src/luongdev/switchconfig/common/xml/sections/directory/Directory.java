package luongdev.switchconfig.common.xml.sections.directory;


import luongdev.switchconfig.common.xml.shared.ParamList;
import luongdev.switchconfig.common.xml.shared.VariableList;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.NONE)
public class Directory {

    @XmlAttribute
    private String id;

    @XmlAttribute
    private String type;

    @XmlElementRef(name = "params")
    private final ParamList params;

    @XmlElementRef(name = "variables")
    private final VariableList variables;

    private Directory() {
        this.params = new ParamList();
        this.variables = new VariableList();

        variable("record_stereo", true);
        variable("limit_destination", "error/user_busy");
        variable("export_vars", "domain_name");

        param("vm-enabled", "true");
        param("dial-string", "{sip_invite_domain=${domain_name},leg_timeout=${call_timeout},presence_id=${dialed_user}@${dialed_domain}}${sofia_contact(*/${dialed_user}@${dialed_domain})}");
    }

    public Directory(String id, String type) {
        this();

        assert StringUtils.isNotEmpty(id);

        this.id = id;
        this.type = type;

        variable("extension", id);
    }

    public Directory(String id) {
        this(id, null);
    }

    public Directory password(String password) {
        return param("password", password);
    }

    public Directory dialString(String dialString) {
        return param("dial-string", dialString);
    }

    public Directory callTimeout(int callTimeout) {
        return variable("call_timeout", callTimeout);
    }

    public Directory callerIdName(String callerIdName) {
        return variable("caller_id_name", callerIdName);
    }

    public Directory extension(String extension, String domain) {
        var domainExtension = String.format("%s@%s", extension, domain);

        variable("presence_id", domainExtension);
        variable("user_context", domain);
        variable("domain_name", domain);

        return this;
    }

    public Directory limitMax(int limitMax) {
        return variable("limit_max", limitMax);
    }

    public Directory callerIdNumber(String callerIdNumber) {
        return variable("caller_id_number", callerIdNumber);
    }

    public Directory param(String name, Object value) {
        this.params.add(name, value);

        return this;
    }

    public Directory variable(String name, Object value) {
        this.variables.add(name, value);

        return this;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public ParamList getParams() {
        return params;
    }

    public VariableList getVariables() {
        return variables;
    }

    @XmlRootElement(name = "users")
    @XmlAccessorType(XmlAccessType.NONE)
    public static class DirectoryList {

        @XmlElementRef(name = "user")
        private final Set<Directory> directories;

        public DirectoryList() {
            this.directories = new HashSet<>();
        }

        public DirectoryList(Collection<Directory> directories) {
            this();

            if (directories != null && !directories.isEmpty()) this.directories.addAll(directories);
        }

        public DirectoryList add(Directory directory) {
            assert directory != null;

            this.directories.add(directory);

            return this;
        }

        public Set<Directory> getDirectories() {
            return directories;
        }
    }
}
