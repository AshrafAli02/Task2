package Main.Java.DataModel;

public class CarType {
    public String CarTypeID;
    public String CarTypeName;
    public double BasePrice;
    public double Gst;
    public double CESS;
    public double RegistrationCharge;
    public double TransportCharge;

    @Override
    public String toString() {
        String Value = "Car Type Name \t :" + this.CarTypeName + "\nBase Price \t : " + this.BasePrice + "\nGST \t : "
                + this.Gst + "%\nCESS \t :" + this.CESS + "%\nReg. Charge \t : " + this.RegistrationCharge
                + "\nTran. Charge \t: " + this.TransportCharge;
        return Value;
    }

}
