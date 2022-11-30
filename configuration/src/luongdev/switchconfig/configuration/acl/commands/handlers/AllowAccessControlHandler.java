package luongdev.switchconfig.configuration.acl.commands.handlers;

import luongdev.switchconfig.common.cqrs.EventRequestHandler;
import luongdev.switchconfig.configuration.acl.AccessControl;
import luongdev.switchconfig.configuration.acl.AccessControls;
import luongdev.switchconfig.configuration.acl.commands.AllowAccessControlCommand;
import luongdev.switchconfig.configuration.acl.events.AccessControlPersistedEvent;
import luongdev.switchconfig.configuration.acl.exceptions.AccessControlNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AllowAccessControlHandler extends EventRequestHandler<AccessControl, AllowAccessControlCommand> {

    private final AccessControls accessControls;

    public AllowAccessControlHandler(AccessControls accessControls) {
        this.accessControls = accessControls;
    }

    @Override
    @Transactional
    public AccessControl handle(AllowAccessControlCommand cmd) {
        var accessControl = accessControls
                .findById(cmd.getName())
                .orElseThrow(() -> new AccessControlNotFoundException(cmd.getName()));

        for (var node : cmd.getAccessNodes()) {
            accessControl.addDetail(node.getCidr(), node.getDomain(), true, node.getDescription());
        }

        accessControl = accessControls.save(accessControl);

        return withEvent(accessControl, new AccessControlPersistedEvent(this, accessControl));
    }
}
