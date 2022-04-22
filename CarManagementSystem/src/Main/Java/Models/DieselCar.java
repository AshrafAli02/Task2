package Main.Java.Models;

public class DieselCar extends Car {
    private String engineType;
    private String engineCapacity;
    private String carType;

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

    public double CalculatePrice() {
        return 0.00;
    }

    @Override
    public String toString() {
        return "Car Name \t: " + this.carName + "\nCar Type \t:" + this.carType + "\nEngine Type \t: " + this.engineType
                + "\nEngine Capacity \t:" + this.engineCapacity + "\nCompany Name \t:" + this.companyName
                + "\nDescription \t: " + this.description;
    }
}
