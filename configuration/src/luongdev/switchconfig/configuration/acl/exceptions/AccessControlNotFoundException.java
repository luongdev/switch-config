package luongdev.switchconfig.configuration.acl.exceptions;

import luongdev.switchconfig.common.exception.BadRequestException;

public class AccessControlNotFoundException extends BadRequestException {

    public AccessControlNotFoundException(String name) {
        super(String.format("Cannot found access control with name [%s]", name));
    }

}
