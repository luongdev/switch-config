package luongdev.switchconfig.configuration.acl.commands.handlers;

import luongdev.switchconfig.common.cqrs.EventRequestHandler;
import luongdev.switchconfig.configuration.acl.AccessControl;
import luongdev.switchconfig.configuration.acl.AccessControls;
import luongdev.switchconfig.configuration.acl.commands.DenyAccessControlCommand;
import luongdev.switchconfig.configuration.acl.events.AccessControlPersistedEvent;
import luongdev.switchconfig.configuration.acl.exceptions.AccessControlNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DenyAccessControlHandler extends EventRequestHandler<AccessControl, DenyAccessControlCommand> {

    private final AccessControls accessControls;

    public DenyAccessControlHandler(AccessControls accessControls) {
        this.accessControls = accessControls;
    }

    @Override
    @Transactional
    public AccessControl handle(DenyAccessControlCommand cmd) {
        var accessControl = accessControls
                .findById(cmd.getName())
                .orElseThrow(() -> new AccessControlNotFoundException(cmd.getName()));

        for (var node : cmd.getAccessNodes()) {
            accessControl.addDetail(node.getCidr(), node.getDomain(), false, node.getDescription());
        }

        accessControl = accessControls.save(accessControl);

        return withEvent(accessControl, new AccessControlPersistedEvent(this, accessControl));
    }
}
