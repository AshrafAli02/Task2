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
}
