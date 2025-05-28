package data.model;

public class Security {
    private long id;
    private String fullName;
    private String verifiedOtp;
    private Resident resident;
    private Visitor visitor;

    public Security() {
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public String verifiedOtp(String verifiedOtp) {
        return this.verifiedOtp = verifiedOtp;
    }

    public Resident getResident() {
        return resident;
    }

    public Visitor getVisitor() {
        return visitor;
    }

}
