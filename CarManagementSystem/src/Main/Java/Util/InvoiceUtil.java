package Main.Java.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import Main.Java.DataModel.Invoice;

public class InvoiceUtil {
    static String ConnectionString = "jdbc:mysql://localhost:3306/";
    static String UserName = "root";
    static String Password = "root";
    static String DBName = "CarManagementSystem";

    public static void AddInvoice(Invoice invoice) throws Exception {
        try {
            Connection sqlconn = DriverManager.getConnection(ConnectionString + DBName, UserName, Password);
            if (sqlconn != null) {
                Statement stat = sqlconn.createStatement();
                int Count = CarUtil.GetRowCount("Invoice", sqlconn);
                String Query = "insert into invoice(RNO,InvoiceID,CustomerID,CarID,ColorID,Price,TaxAmount,TotalAmount,Remark) values('"
                        + Count + "','" + ("INV" + Count) + "','" + invoice.CustomerID + "','" + invoice.CarID + "','"
                        + invoice.ColorID + "','" + invoice.Price + "','" + invoice.TaxAmount + "','"
                        + invoice.TotalAmount + "','')";
                stat.executeUpdate(Query);
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

}
