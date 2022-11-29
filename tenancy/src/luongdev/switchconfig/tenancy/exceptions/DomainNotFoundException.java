package luongdev.switchconfig.tenancy.exceptions;

import luongdev.switchconfig.common.exception.BadRequestException;

public class DomainNotFoundException extends BadRequestException {

    public DomainNotFoundException(String domain) {
        super(String.format("Cannot found domain with name [%s]", domain));
    }

}
