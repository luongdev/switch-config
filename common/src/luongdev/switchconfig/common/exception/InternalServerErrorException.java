package luongdev.switchconfig.common.exception;

public class InternalServerErrorException extends BusinessException {

    public InternalServerErrorException(String message) {
        super(500, message);
    }

    public InternalServerErrorException(Exception e) {
        super(500, e);
    }

    public InternalServerErrorException(Throwable e) {
        super(500, e);
    }
}
