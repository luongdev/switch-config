package luongdev.switchconfig.common.exception;

public abstract class BusinessException extends RuntimeException {

    private final int statusCode;

    public BusinessException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public BusinessException(int statusCode, Exception e) {
        super(e);
        this.statusCode = statusCode;
    }

    public BusinessException(int statusCode, Throwable e) {
        super(e);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
