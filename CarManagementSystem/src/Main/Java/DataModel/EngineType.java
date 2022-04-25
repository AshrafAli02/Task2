package Main.Java.DataModel;

public class EngineType {
    public String EngineTypeID;
    public String EngineTypeName;
    public String Capacity;
    public double Amount;

    @Override
    public String toString() {
        String value = "Engine Type \t : " + this.EngineTypeName + "\nEngine Capacity \t : " + this.Capacity
                + "\nEngine Price \t:" + this.Amount;
        return value;
    }

}
