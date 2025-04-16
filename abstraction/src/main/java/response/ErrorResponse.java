package response;

public class ErrorResponse extends ApiResponse {
    private String errorCode;

    public ErrorResponse(String message, String errorCode) {
        super("error", message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}