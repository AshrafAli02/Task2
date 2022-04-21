package Main.Java.Util;

import java.sql.*;

import Main.Java.DataModel.*;

public class utils {

    static String ConnectionString = "jdbc:mysql://localhost:3306/";
    static String UserName = "root";
    static String Password = "root";
    static String DBName = "CarManagementSystem";

    public static int AddCompany(String CompanyName) throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (sqlconn != null) {
                Statement stat = (Statement) sqlconn.createStatement();
                boolean res = IsCompanyAlreadyExists(CompanyName, sqlconn);
                if (!res) {
                    String Query = "Select Count(*) as Cnt From Company";
                    ResultSet set = stat.executeQuery(Query);
                    int Count = 0;
                    if (set != null) {
                        while (set.next()) {
                            Count = set.getInt("Cnt");
                        }
                        set.close();
                    }

                    String Query1 = "insert into Company Values('" + ("COM" + (Count + 1)) + "','" + CompanyName + "')";
                    stat.executeUpdate(Query1);

                } else {
                    sqlconn.close();
                    throw new Exception("This Company Name Already Exists!...");
                }
                sqlconn.close();

            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return 0;
    }

    static boolean IsCompanyAlreadyExists(String CompanyName, Connection sqlconn) throws Exception {
        try {
            Connection conn = sqlconn;
            Statement sta = conn.createStatement();
            String Query = "select Count(*) from Company Where CompanyName='" + CompanyName + "'";
            ResultSet resultset = sta.executeQuery(Query);
            if (resultset.next()) {
                int res = resultset.getInt(1);
                if (res != 0) {
                    return true;
                }

            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return false;
    }

    public static void AddColor(Color color) throws Exception {
        try {
            Connection Sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (Sqlconn != null) {
                Statement stat = Sqlconn.createStatement();
                if (CheckColorExists(color.ColorName, Sqlconn) == false) {
                    int Count = GetTableDataCount("Color", Sqlconn);
                    String Query = "insert into Color Values('" + ("Col" + (Count + 1)) + "','" + color.ColorName
                            + "','" + color.Amount + "')";
                    stat.executeUpdate(Query);
                    Sqlconn.close();
                } else {
                    throw new Exception("This Color Already Exists");
                }

            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    static boolean CheckColorExists(String ColorName, Connection sqlconn) throws Exception {
        boolean res = false;
        try {
            Connection Sqlconn = sqlconn;
            Statement stat = Sqlconn.createStatement();
            String Query = "Select Count(*) as Cnt from Color where ColorName='" + ColorName + "'";
            ResultSet set = stat.executeQuery(Query);
            if (set != null) {
                while (set.next()) {
                    int count = set.getInt("cnt");
                    if (count != 0) {
                        return true;
                    }
                }
            }
            set.close();

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return res;

    }

    static int GetTableDataCount(String TableName, Connection sqlconn) throws Exception {
        int Count = 0;
        try {
            Statement stat = sqlconn.createStatement();
            String Query = "Select Count(*) as cnt from " + TableName;
            ResultSet res = stat.executeQuery(Query);
            if (res != null) {
                while (res.next()) {
                    Count = res.getInt("cnt");
                }
            }

        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
        return Count;

    }

    public static void AddEngineType(EngineType engineType) throws Exception {
        try {
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            Statement stat = sqlconn.createStatement();
            int Count = GetTableDataCount("EngineType", sqlconn);
            engineType.EngineTypeID = "ET" + (Count + 1);
            String Query = "insert into EngineType(EngineTypeID,EngineTypeName,Capacity,Amount) values ('"
                    + engineType.EngineTypeID + "','" + engineType.EngineTypeName + "','" + engineType.Capacity + "','"
                    + engineType.Amount + "')";
            stat.executeUpdate(Query);
            sqlconn.close();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }

    }

    public static void UpdateEngineType(EngineType engineType) throws Exception {
        try {
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            Statement stat = sqlconn.createStatement();
            String Query = "update EngineType set EngineTypeName='" + engineType.EngineTypeName + "' and Capacity='"
                    + engineType.Capacity + "' and Amount='"
                    + engineType.Amount + "' where EngineTypeID='"
                    + engineType.EngineTypeID + "'";
            stat.executeUpdate(Query);
            sqlconn.close();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }

    }

    public static void RemoveEngineType(String EngineTypeID) {

    }

    public static void AddCarType(CarType carType) throws Exception {
        try {
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            Statement stat = sqlconn.createStatement();
            int Count = GetTableDataCount("CarType", sqlconn);
            carType.CarTypeID = "CT" + (Count + 1);
            String Query = "insert into CarType(CarTypeID,TypeName,BasePrice,GST,CESS,RegistrationCharge,TransportCharge) values('"
                    + carType.CarTypeID + "','" + carType.CarTypeName.toUpperCase() + "','" + carType.BasePrice + "','"
                    + carType.Gst
                    + "','" + carType.CESS + "','" + carType.RegistrationCharge + "','" + carType.TransportCharge
                    + "')";
            stat.executeUpdate(Query);

        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
    }

    public static void UpdateCarType(CarType carType) throws Exception {
        try {
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            Statement stat = sqlconn.createStatement();
            String Query = "update CarType set TypeName='" + carType.CarTypeName.toUpperCase() + "' and BasePrice='"
                    + carType.BasePrice + "' and GST='"
                    + carType.Gst + "' and CESS= '" + carType.CESS + "' and RegistrationCharge='"
                    + carType.RegistrationCharge + "' and TransportCharge= '"
                    + carType.TransportCharge
                    + "' where CarTypeID='"
                    + carType.CarTypeID + "'";
            stat.executeUpdate(Query);

        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
    }

    public static void RemoveCarType(String CarTypeID) {

    }

}
