package Main.Java.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import Main.Java.DataModel.UserDetails;
import Main.Java.Models.CustomerDetails;

public class UserUtil {

    static String ConnectionString = "jdbc:mysql://localhost:3306/";
    static String UserName = "root";
    static String Password = "root";
    static String DBName = "CarManagementSystem";

    public static boolean IsValidUser(String userName, String password) throws Exception {
        boolean Result = true;
        try {
            Connection Sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (Sqlconn != null) {
                Statement Stat = Sqlconn.createStatement();
                String Query = "Select Count(*) from UserDetails where UserName='" + userName + "' and Password='"
                        + password + "'";
                ResultSet set = Stat.executeQuery(Query);
                if (set != null) {
                    while (set.next()) {
                        int count = set.getInt(1);
                        if (count == 0) {
                            throw new Exception("User Name And Password is Incorrect");
                        }
                    }
                }
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return Result;
    }

    public static void AddUser(UserDetails UserData) throws Exception {
        try {
            Connection Sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (Sqlconn != null) {
                Statement stat = Sqlconn.createStatement();
                if (!IsDataAlreadyExist(UserName, UserData.UserName, Sqlconn)
                        && !IsDataAlreadyExist("MobileNumber", UserData.MobileNumber, Sqlconn)
                        && !IsDataAlreadyExist("Email", UserData.Email, Sqlconn)) {
                    int Count = CarUtil.GetRowCount("UserDetails", Sqlconn);
                    String Query = "Insert into UserDetails(RNo,UserID,UserName,Password,Role,MobileNumber,Email) values ('"
                            + Count + "','"
                            + ("U" + Count) + "','" + UserData.UserName + "','" + UserData.Password + "','" + "Customer"
                            + "','" + UserData.MobileNumber + "','" + UserData.Password + "')";
                    stat.executeUpdate(Query);
                }
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public static boolean IsDataAlreadyExist(String FieldName, String Value, Connection sqlConnection) {
        boolean result = false;
        try {
            Connection sqlconn = sqlConnection;
            Statement stat = sqlconn.createStatement();
            String Query = "Select Count('" + FieldName + "') from UserDetails Where UserName='" + Value + "'";
            ResultSet set = stat.executeQuery(Query);
            if (set != null) {
                while (set.next()) {
                    int count = set.getInt(1);
                    if (count != 0) {
                        throw new Exception("This " + FieldName + " Already Exists");
                    }
                }
            }
        } catch (Exception ex) {

        }
        return result;
    }

    public static boolean IsUser(String userName) throws Exception {
        boolean result = true;
        try {
            Connection Sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (Sqlconn != null) {
                Statement stat = Sqlconn.createStatement();
                String Query = "Select Count(*) from CustomerDetails where UserID=(Select UserID from UserDetails where UserName='"
                        + userName + "')";
                ResultSet set = stat.executeQuery(Query);
                if (set != null) {
                    while (set.next()) {
                        int count = set.getInt(1);
                        if (count == 0) {
                            return false;
                        }
                    }
                }
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return result;
    }

    public static String AddCustomerDetails(CustomerDetails cDetails) throws Exception {
        try {

            Connection sqlConn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (sqlConn != null) {
                Statement stat = sqlConn.createStatement();
                int count = CarUtil.GetRowCount("CustomerDetails", sqlConn);
                String USerID = "";
                String Query1 = "Select UserID from UserDetails where UserName='" + cDetails.GetUserName() + "'";
                ResultSet set = stat.executeQuery(Query1);
                if (set != null) {
                    while (set.next()) {
                        USerID = set.getString(1);

                    }
                }
                set.close();
                String Query = "insert into CustomerDetails(RNo,CustomerID,CustomerName,MobileNumber,DoorNo,StreetName,City,District,State,UserID) values ('"
                        + count + "','" + ("CUST" + count) + "','" + cDetails.GetCustomerName() + "','"
                        + cDetails.GetMobileNumber() + "','" + cDetails.GetAddressDetails().DoorNo + "','"
                        + cDetails.GetAddressDetails().StreetName + "','" + cDetails.GetAddressDetails().City + "','"
                        + cDetails.GetAddressDetails().District + "','" + cDetails.GetAddressDetails().State + "','"
                        + USerID + "')";

                stat.executeUpdate(Query);
                return "CUST" + count;
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return "";

    }

    public static String GetCustomerID(String userName) throws Exception {
        String ID = "";
        try {
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (sqlconn != null) {
                Statement stat = sqlconn.createStatement();
                String Query = "Select CustomerID from CustomerDetails Where UserID=(Select UserID from UserDetails where UserName='"
                        + userName + "')";
                ResultSet set = stat.executeQuery(Query);
                if (set != null) {
                    while (set.next()) {
                        ID = set.getString(1);
                    }
                }
                set.close();
                sqlconn.close();
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return ID;

    }

}
