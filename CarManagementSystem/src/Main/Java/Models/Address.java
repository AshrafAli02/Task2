package Main.Java.Models;

public class Address {

    public String DoorNo;
    public String StreetName;
    public String City;
    public String District;
    public String State;

    public Address(String doorno, String streetname, String city, String district, String state) {
        this.DoorNo = doorno;
        this.StreetName = streetname;
        this.City = city;
        this.District = district;
        this.State = state;

    }

    public Address() {
    }

}
