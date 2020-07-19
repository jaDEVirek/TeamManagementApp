package src.main.java.com.devirek.dashboardapp.security.payload.response;


public class PayloadResponse {
    private String message;

    public PayloadResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
