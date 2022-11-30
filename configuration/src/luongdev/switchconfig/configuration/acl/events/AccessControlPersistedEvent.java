package luongdev.switchconfig.configuration.acl.events;

import luongdev.switchconfig.configuration.acl.AccessControl;
import org.springframework.context.ApplicationEvent;

public class AccessControlPersistedEvent extends ApplicationEvent {

    private final AccessControl accessControl;

    public AccessControlPersistedEvent(Object source, AccessControl accessControl) {
        super(source);
        this.accessControl = accessControl;
    }

    public AccessControl getAccessControl() {
        return accessControl;
    }
}
