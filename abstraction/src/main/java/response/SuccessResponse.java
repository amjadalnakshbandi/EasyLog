package response;

public class SuccessResponse<T> extends ApiResponse {
    private T data;

    public SuccessResponse(String message, T data) {
        super("success", message);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}