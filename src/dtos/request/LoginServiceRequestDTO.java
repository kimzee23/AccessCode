package dtos.request;

public class LoginServiceRequestDTO {

    private long id;
    private String phoneNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPhoneNumber(String email) {
        this.phoneNumber = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
