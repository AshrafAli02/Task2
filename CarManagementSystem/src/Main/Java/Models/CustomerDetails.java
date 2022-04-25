package Main.Java.Models;

public class CustomerDetails {

    private String customerID;
    private String customerName;
    private String mobileNumber;
    private Address addressDetails;
    private String userName;

    public String GetCustomerID() {
        return this.customerID;

    }

    public void SetCustomerName(String CustomerName) {
        this.customerName = CustomerName;
    }

    public String GetCustomerName() {
        return this.customerName;
    }

    public void SetMobileNumber(String MobileNumber) {
        this.mobileNumber = MobileNumber;
    }

    public String GetMobileNumber() {
        return this.mobileNumber;
    }

    public void SetAddress(Address address) {
        this.addressDetails = address;
    }

    public Address GetAddressDetails() {
        return this.addressDetails;
    }

    public String GetUserName() {
        return this.userName;
    }

    public void SetUserName(String name) {
        this.userName = name;
    }

}
