package Main.Java.DataModel;

public class CarDetails {
    public String CarID;
    public String CarName;
    public String CompanyID;
    public String CarTypeID;
    public String EngineTypeID;
    public String Description;

    public CarDetails(String carname, String companyid, String cartypeid, String enginetypeid,
            String des) {
        this.CarName = carname;
        this.CompanyID = companyid;
        this.CarTypeID = cartypeid;
        this.EngineTypeID = enginetypeid;
        this.Description = des;
    }

    public CarDetails() {
    }

}
