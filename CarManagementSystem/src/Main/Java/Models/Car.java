package Main.Java.Models;

public abstract class Car {
    String carID;
    String carName;
    String companyName;
    String carColor;
    String description;
    String engineType;
    String engineCapacity;
    String carType;
    double Price;
    double Taxamount;

    public abstract String GetCarID();

    public abstract void SetCarName(String CarName);

    public abstract String GetCarName();

    public abstract void SetCompanyName(String CompanyName);

    public abstract String GetCompanyName();

    public abstract void SetCarColor(String CarColor);

    public abstract String GetCarColor();

    public abstract void SetDescription(String Description);

    public abstract String GetDescription();

    public abstract double CalculatePrice() throws Exception;

    public abstract double GetTaxAmount();

    public abstract double GetPrice();
}
