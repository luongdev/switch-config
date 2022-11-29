package luongdev.switchconfig.common.exception;

public class BadRequestException extends BusinessException {

    public BadRequestException(String message) {
        super(400, message);
    }

    public BadRequestException(Exception e) {
        super(400, e);
    }

    public BadRequestException(Throwable e) {
        super(400, e);
    }
}
