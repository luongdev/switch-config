package luongdev.switchconfig.configuration.acl.commands.handlers;

import luongdev.switchconfig.common.cqrs.EventRequestHandler;
import luongdev.switchconfig.configuration.acl.AccessControl;
import luongdev.switchconfig.configuration.acl.AccessControls;
import luongdev.switchconfig.configuration.acl.commands.ToggleAccessControlCommand;

import luongdev.switchconfig.configuration.acl.events.AccessControlPersistedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Component
public class ToggleAccessControlHandler extends EventRequestHandler<AccessControl, ToggleAccessControlCommand> {

    private final AccessControls accessControls;

    public ToggleAccessControlHandler(AccessControls accessControls) {
        this.accessControls = accessControls;
    }

    @Override
    @Transactional
    public AccessControl handle(ToggleAccessControlCommand cmd) {
        var accessControl = accessControls.findIncludeDetails(cmd.getId());
        if (accessControl == null) throw new EntityNotFoundException("AccessControl[" + cmd.getId() + "]");

        accessControl.setAllow(!accessControl.isAllow());
        accessControl.getAccessControlDetails().forEach((k, v) -> v.setAllow(!v.isAllow()));

        accessControl = accessControls.save(accessControl);

        return withEvent(accessControl, new AccessControlPersistedEvent(this, accessControl));
    }
}
