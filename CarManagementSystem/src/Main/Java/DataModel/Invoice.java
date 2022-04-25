package Main.Java.DataModel;

public class Invoice {
    public String invoiceID;
    public String CustomerID;
    public String CarID;
    public String ColorID;
    public double Price;
    public double TaxAmount;
    public double TotalAmount;
    public double Remark;

    public Invoice(String carID, String colorID, double price, double taxamout, double ColorAmount, String customerID) {
        this.CarID = carID;
        this.ColorID = colorID;
        this.Price = price + ColorAmount;
        this.TaxAmount = taxamout;
        this.TotalAmount = ColorAmount + price + taxamout;
        this.CustomerID = customerID;
    }

}
