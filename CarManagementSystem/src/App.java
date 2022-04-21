import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Main.Java.DataModel.CarType;
import Main.Java.DataModel.Color;
import Main.Java.DataModel.EngineType;
import Main.Java.Util.utils;

public class App {
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {
        try {
            int roleCode = Login();
            DashBoard(roleCode);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void DashBoard(int RoleCode) throws Exception {
        if (RoleCode == 1) {
            System.out.println("Your login as Super Admin ");
            System.out.println("-------------------------");
            SuperAdminFunctions();

        }
    }

    public static void SuperAdminFunctions() throws Exception {
        System.out.println("\n\nSelect the option");
        System.out.println("-----------------");
        System.out.println("1. Add Company");
        System.out.println("2. Add EngineType");
        System.out.println("3. Add CarType");
        System.out.println("4. Add Color");
        System.out.print("Enter your option : ");
        int Number = Integer.parseInt(reader.readLine());
        if (Number == 1) {
            String CompanyName = GetCompanyName();
            utils.AddCompany(CompanyName);
            SuperAdminFunctions();
        } else if (Number == 2) {
            EngineType enginetype = GetEngineType();
            utils.AddEngineType(enginetype);
            SuperAdminFunctions();
        } else if (Number == 3) {
            CarType cartype = GetCarType();
            utils.AddCarType(cartype);
            SuperAdminFunctions();
        } else if (Number == 4) {
            Color color = GetColor();
            utils.AddColor(color);
            SuperAdminFunctions();
        } else {
            System.out.println("Your option is incorrect, please select correct option");
            SuperAdminFunctions();
        }

    }

    public static int Login() throws Exception {

        try {
            System.out.println("\tLOGIN");
            System.out.println("---------------");
            System.out.print("Enter your UserName \t : ");
            String UName = reader.readLine();
            System.out.print("Enter your Password \t :");
            String Password = reader.readLine();
            if (UName.toLowerCase().equals("superadmin") && Password.equals("SuperAdmin123")) {
                return 1;
            }
            // Admin
            // user
            else {
                throw new Exception("User Name and Password Incorrect");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

    }

    public static EngineType GetEngineType() throws IOException {
        EngineType ET = new EngineType();
        System.out.println("Enter the Engine Details");
        System.out.println("------------------------");
        System.out.print("Enter the Engine Type :");
        ET.EngineTypeName = reader.readLine();
        System.out.print("Enter the Capacity : ");
        ET.Capacity = reader.readLine();
        System.out.print("Enter the Amount : ");
        ET.Amount = Double.parseDouble(reader.readLine());
        return ET;
    }

    public static CarType GetCarType() throws Exception {
        CarType CT = new CarType();
        System.out.println("Enter the CarType Details");
        System.out.println("-------------------------");
        System.out.print("Enter the Car Type Name : ");
        CT.CarTypeName = reader.readLine();
        System.out.print("Enter the Base Price : ");
        CT.BasePrice = Double.parseDouble(reader.readLine());
        System.out.print("Enter the GsT percentage : ");
        CT.Gst = Double.parseDouble(reader.readLine());
        System.out.print("Enter the CESS Charge : ");
        CT.CESS = Double.parseDouble(reader.readLine());
        System.out.print("Enter the Registration Charge : ");
        CT.RegistrationCharge = Double.parseDouble(reader.readLine());
        System.out.print("Enter the Transport Charge : ");
        CT.TransportCharge = Double.parseDouble(reader.readLine());

        return CT;
    }

    public static String GetCompanyName() throws IOException {
        System.out.print("Enter the Company Name :");
        String Companyname = reader.readLine();
        return Companyname;
    }

    public static Color GetColor() throws Exception {
        Color color = new Color();
        System.out.println("Enter the Color Details");
        System.out.println("------------------------");
        System.out.print("Enter the Color Name : ");
        color.ColorName = reader.readLine();
        System.out.print("Enter the Amount : ");
        color.Amount = Double.parseDouble(reader.readLine());
        return color;
    }

}
