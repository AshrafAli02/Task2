package Main.Java.Models;

import Main.Java.Util.CarUtil;

public class ElectricCar extends Car {
    private String engineType;
    private String engineCapacity;
    private String carType;

    public ElectricCar(String CarID, String CarName, String CompanyName, String CarType, String EngineType,
            String Description, String EngineCapacity) {
        this.carID = CarID;
        this.carName = CarName;
        this.companyName = CompanyName;
        this.carType = CarType;
        this.engineType = EngineType;
        this.description = Description;
        this.engineCapacity = EngineCapacity;

    }

    public ElectricCar() {
    }

    public String GetCarID() {
        return this.carID;
    }

    public void SetCarName(String CarName) {
        this.carName = CarName;
    }

    public String GetCarName() {
        return this.carName;
    }

    public void SetCompanyName(String CompanyName) {
        this.companyName = CompanyName;
    }

    public String GetCompanyName() {
        return this.companyName;
    }

    public void SetCarColor(String CarColor) {
        this.carColor = CarColor;
    }

    public String GetCarColor() {
        return this.carColor;
    }

    public void SetDescription(String Description) {
        this.description = Description;
    }

    public String GetDescription() {
        return this.description;
    }

    public void SetEngineType(String EngineType) {
        this.engineType = EngineType;
    }

    public String GetEngineType() {
        return this.engineType;
    }

    public void SetEngineCapacity(String EngineCapacity) {
        this.engineCapacity = EngineCapacity;
    }

    public String GetEngineCapacity() {
        return this.engineCapacity;
    }

    public void setCarType(String CarType) {
        this.carType = CarType;
    }

    public String GetCarType() {
        return this.carType;
    }

    public double CalculatePrice() throws Exception {
        double tax = CarUtil.GetTaxAmount(this.carID);
        this.Taxamount = tax;
        double Price = CarUtil.GetCarPrice(this.carID);
        this.Price = Price;

        return tax + Price;
    }

    public double GetTaxAmount() {

        return this.Taxamount;
    }

    public double GetPrice() {

        return this.Price;
    }

    @Override
    public String toString() {
        return "Car Name \t: " + this.carName + "\nCar Type \t:" + this.carType + "\nEngine Type \t: " + this.engineType
                + "\nEngine Capacity :" + this.engineCapacity + "\nCompany Name \t:" + this.companyName
                + "\nDescription \t: " + this.description;
    }
}
