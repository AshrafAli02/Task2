package Main.Java.Util;

import java.sql.*;
import java.util.ArrayList;

import java.util.List;

import Main.Java.DataModel.CarColor;
import Main.Java.DataModel.CarDetails;
import Main.Java.DataModel.CarMeta;

public class CarUtil {

    static String ConnectionString = "jdbc:mysql://localhost:3306/";
    static String UserName = "root";
    static String Password = "root";
    static String DBName = "CarManagementSystem";

    public static void AddCarDetails(CarDetails cardetails) throws Exception {
        try {
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (sqlconn != null) {

                int maxvalue = GetRowCount("CarDetails", sqlconn);
                Statement stat = sqlconn.createStatement();
                String Query = "insert into CarDetails(RNO,CarID,CarName,CompanyID,CarTypeID,EngineTypeID,Description) values ('"
                        + maxvalue + "','"
                        + "CAR" + (maxvalue) + "','" + cardetails.CarName + "','" + cardetails.CompanyID + "','"
                        + cardetails.CarTypeID + "','" + cardetails.EngineTypeID + "','" + cardetails.Description
                        + "')";
                stat.executeUpdate(Query);
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public static int GetRowCount(String TableName, Connection sqlconn) throws Exception {
        int num = 1;
        try {

            Statement stat = sqlconn.createStatement();

            String Query1 = "Select Count(*) from CarDetails";
            ResultSet set1 = stat.executeQuery(Query1);
            int Count = 0;
            if (set1 != null) {
                while (set1.next()) {
                    Count = set1.getInt(1);
                }
            }
            set1.close();
            if (Count != 0) {
                String Query = "Select max(RNO) from " + TableName;
                ResultSet set = stat.executeQuery(Query);

                if (set != null) {
                    while (set.next()) {
                        int res = set.getInt(1);
                        num += res;
                    }
                }
                set.close();
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return num;

    }

    public static List<CarMeta> GetCarMetaDetails() throws Exception {
        List<CarMeta> CarDatas = new ArrayList<>();
        try {
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (sqlconn != null) {
                Statement stat = sqlconn.createStatement();
                String Query = "Select CarID,CarName from CarDetails";
                ResultSet set = stat.executeQuery(Query);
                if (set != null) {
                    while (set.next()) {
                        String id = set.getString(1);
                        String name = set.getString(2);
                        CarDatas.add(new CarMeta(id, name));

                    }
                }
                set.close();
                sqlconn.close();
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return CarDatas;
    }

    public static List<String> GetAvailableCaolors(String CarID) throws Exception {
        List<String> Colors = new ArrayList<>();
        try {
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (sqlconn != null) {
                Statement stat = sqlconn.createStatement();
                String Query = "Select ColorID from CarColors where CarID='" + CarID + "' and IsRestrictedColor=0";
                ResultSet set = stat.executeQuery(Query);
                if (set != null) {
                    while (set.next()) {
                        String id = set.getString(1);
                        Colors.add(id);
                    }
                }
                set.close();
                sqlconn.close();

            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return Colors;

    }

    public static List<String> GetRestrictedCaolors(String CarID) throws Exception {
        List<String> Colors = new ArrayList<>();
        try {
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (sqlconn != null) {
                Statement stat = sqlconn.createStatement();
                String Query = "Select ColorID from CarColors where CarID='" + CarID + "' and IsRestrictedColor=1";
                ResultSet set = stat.executeQuery(Query);
                if (set != null) {
                    while (set.next()) {
                        String id = set.getString(1);
                        Colors.add(id);
                    }
                }
                set.close();
                sqlconn.close();

            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return Colors;

    }

    public static List<CarColor> GetCarColors(String CarID) throws Exception {
        List<CarColor> Colors = new ArrayList<>();
        try {
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (sqlconn != null) {
                Statement stat = sqlconn.createStatement();
                String Query = "Select ColorID,IsRestrictedColor from CarColors where CarID='" + CarID + "'";
                ResultSet set = stat.executeQuery(Query);
                if (set != null) {
                    while (set.next()) {
                        String id = set.getString(1);
                        Boolean isrestricted = set.getBoolean(2);
                        Colors.add(new CarColor(id, isrestricted));
                    }
                }
                set.close();
                sqlconn.close();

            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return Colors;
    }

    public static void AddAvailableColor(String CarID, String ColorID) throws Exception {
        try {
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (sqlconn != null) {
                Statement stat = sqlconn.createStatement();
                String Query = "insert into CarColors values('" + CarID + "','" + ColorID + "',0)";
                stat.executeUpdate(Query);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public static void AddRestrictedColor(String CarID, String ColorID) throws Exception {
        try {
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (sqlconn != null) {
                Statement stat = sqlconn.createStatement();
                String Query = "insert into CarColors values('" + CarID + "','" + ColorID + "',1)";
                stat.executeUpdate(Query);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public static void RemoveRestrictedColor(String CarID, String ColorID) throws Exception {
        try {
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (sqlconn != null) {
                Statement stat = sqlconn.createStatement();
                String Query = "delete from CarColors where CarID='" + CarID + "' and ColorID='" + ColorID + "'";
                stat.executeUpdate(Query);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public static void RemoveAvailableColor(String CarID, String ColorID) throws Exception {
        try {
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (sqlconn != null) {
                Statement stat = sqlconn.createStatement();
                String Query = "delete from CarColors where CarID='" + CarID + "' and ColorID='" + ColorID + "'";
                stat.executeUpdate(Query);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public static CarDetails GetCarDetail(String CarID) throws Exception {
        CarDetails car = new CarDetails();
        try {
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (sqlconn != null) {
                Statement stat = sqlconn.createStatement();
                String Query = "Select CarID,CarName,CompanyID,CarTypeID,EngineTypeID,Description from Cardetails where CarID='"
                        + CarID + "'";
                ResultSet set = stat.executeQuery(Query);
                if (set != null) {
                    while (set.next()) {
                        car.CarID = set.getString(1);
                        car.CarName = set.getString(2);
                        car.CompanyID = set.getString(3);
                        car.CarTypeID = set.getString(4);
                        car.EngineTypeID = set.getString(5);
                        car.Description = set.getString(6);
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return car;
    }

    public static void UpdateCarDetails(CarDetails carDetail) throws Exception {
        try {
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (sqlconn != null) {
                Statement stat = sqlconn.createStatement();
                String Query = "update CarDetails set CarName='" + carDetail.CarName + "' , CompanyID='"
                        + carDetail.CompanyID + "' , CarTypeID='" + carDetail.CarTypeID + "' , EngineTypeID='"
                        + carDetail.EngineTypeID + "' , Description='" + carDetail.Description + "' where CarID='"
                        + carDetail.CarID + "'";
                stat.executeUpdate(Query);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public static List<CarDetails> GetCarDetails(String CompanyID, String EngineTypeID, String CarTypeID)
            throws Exception {
        List<CarDetails> carDetail = new ArrayList<>();
        try {
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (sqlconn != null) {
                Statement stat = sqlconn.createStatement();
                String Query = "Select CarID,CarName,CompanyID,CarTypeID,EngineTypeID,Description from CarDetails where CompanyID='"
                        + CompanyID + "' and EngineTypeID='" + EngineTypeID + "' and CarTypeID= '" + CarTypeID + "'";
                ResultSet set = stat.executeQuery(Query);
                if (set != null) {
                    while (set.next()) {
                        CarDetails Car = new CarDetails();
                        Car.CarID = set.getString(1);
                        Car.CarName = set.getString(2);
                        Car.CompanyID = set.getString(3);
                        Car.CarTypeID = set.getString(4);
                        Car.EngineTypeID = set.getString(5);
                        Car.Description = set.getString(6);

                        carDetail.add(Car);
                    }
                }
                set.close();
                sqlconn.close();
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return carDetail;
    }

    public static List<CarDetails> GetCarDetails(String CompanyID, String CarTypeID) throws Exception {
        List<CarDetails> carDetail = new ArrayList<>();
        try {
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (sqlconn != null) {
                Statement stat = sqlconn.createStatement();
                String Query = "Select CarID,CarName,CompanyID,CarTypeID,EngineTypeID,Description from CarDetails where CompanyID='"
                        + CompanyID + "' and CarTypeID='" + CarTypeID + "'";
                ResultSet set = stat.executeQuery(Query);
                if (set != null) {
                    while (set.next()) {
                        CarDetails Car = new CarDetails();
                        Car.CarID = set.getString(1);
                        Car.CarName = set.getString(2);
                        Car.CompanyID = set.getString(3);
                        Car.CarTypeID = set.getString(4);
                        Car.EngineTypeID = set.getString(5);
                        Car.Description = set.getString(6);

                        carDetail.add(Car);
                    }
                }
                set.close();
                sqlconn.close();
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return carDetail;
    }

    public static List<CarDetails> GetCarDetails(String CompanyID) throws Exception {
        List<CarDetails> carDetail = new ArrayList<>();
        try {
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (sqlconn != null) {
                Statement stat = sqlconn.createStatement();
                String Query = "Select CarID,CarName,CompanyID,CarTypeID,EngineTypeID,Description from CarDetails where CompanyID='"
                        + CompanyID + "'";
                ResultSet set = stat.executeQuery(Query);
                if (set != null) {
                    while (set.next()) {
                        CarDetails Car = new CarDetails();
                        Car.CarID = set.getString(1);
                        Car.CarName = set.getString(2);
                        Car.CompanyID = set.getString(3);
                        Car.CarTypeID = set.getString(4);
                        Car.EngineTypeID = set.getString(5);
                        Car.Description = set.getString(6);

                        carDetail.add(Car);
                    }
                }
                set.close();
                sqlconn.close();
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return carDetail;
    }

    public static Double GetTaxAmount(String CarID) throws Exception {
        double tax = 0;
        double BasePrice = 0, EnginePrice = 0, GST = 0, CESS = 0;
        try {
            Connection Sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (Sqlconn != null) {
                Statement stat = Sqlconn.createStatement();
                String Query = "Select CarType.BasePrice,CarType.GST,CarType.CESS,EngineType.Amount from CarType,EngineType,CarDetails where CarType.CarTypeID=CarDetails.CarTypeID and EngineType.EngineTypeID=CarDetails.EngineTypeID and CarDetails.CarID='"
                        + CarID + "'";
                ResultSet set = stat.executeQuery(Query);
                if (set != null) {
                    while (set.next()) {
                        BasePrice = set.getDouble(1);
                        GST = set.getDouble(2);
                        CESS = set.getDouble(3);
                        EnginePrice = set.getDouble(4);
                    }
                }
                set.close();
                Sqlconn.close();

            }

            double calGst = (BasePrice + EnginePrice) * (GST / 100);
            double calcess = (BasePrice + EnginePrice) * (CESS / 100);
            tax = calGst + calcess;

        } catch (Exception ex) {
            throw new Exception();
        }

        return tax;
    }

    public static Double GetCarPrice(String CarID) throws Exception {
        double BasePrice = 0, EnginePrice = 0, regcharge = 0, trascharge = 0;
        try {
            Connection Sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (Sqlconn != null) {
                Statement stat = Sqlconn.createStatement();
                String Query = "Select CarType.BasePrice,CarType.RegistrationCharge,CarType.TransportCharge,EngineType.Amount from CarType,EngineType,CarDetails where CarType.CarTypeID=CarDetails.CarTypeID and EngineType.EngineTypeID=CarDetails.EngineTypeID and CarDetails.CarID='"
                        + CarID + "'";
                ResultSet set = stat.executeQuery(Query);
                if (set != null) {
                    while (set.next()) {
                        BasePrice = set.getDouble(1);
                        regcharge = set.getDouble(2);
                        trascharge = set.getDouble(3);
                        EnginePrice = set.getDouble(2);
                    }
                }
                set.close();
                Sqlconn.close();
            }
        } catch (Exception ex) {
            throw new Exception();
        }
        return (BasePrice + EnginePrice + regcharge + trascharge);
    }
}
