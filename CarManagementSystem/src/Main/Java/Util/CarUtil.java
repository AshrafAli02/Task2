package Main.Java.Util;

import java.sql.*;

import Main.Java.DataModel.CarDetails;

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
                String Query = "insert into CarDetails(CarID,CarName,CompanyID,CarTypeID,EngineTypeID,Description) values ('"
                        + "CAR" + (maxvalue + 1) + "','" + cardetails.CarName + "','" + cardetails.CompanyID + "','"
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

}
