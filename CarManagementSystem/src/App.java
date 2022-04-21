
import java.io.*;
import java.util.*;

import Main.Java.DataModel.*;
import Main.Java.Models.PetrolCar;
import Main.Java.Util.*;

public class App {
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static List<Company> Companies = new ArrayList<>();
    public static List<CarType> CarTypes = new ArrayList<>();
    public static List<EngineType> EngineTypes = new ArrayList<>();
    public static List<Color> Colors = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        try {
            int code = Login();
            DashBoard(code);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void DashBoard(int RoleCode) throws Exception {
        if (RoleCode == 1) {
            System.out.println("Your login as Super Admin ");
            System.out.println("-------------------------");
            SuperAdminFunctions();

        } else if (RoleCode == 2) {
            Companies = utils.GetCompanies();
            CarTypes = utils.GetCarTypes();
            EngineTypes = utils.GetEngineTypes();
            Colors = utils.GetColors();
            AdminActions();
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

    public static void AdminActions() throws Exception {
        System.out.println("Select the Option");
        System.out.println("-----------------");
        System.out.println("1. Create Car");
        System.out.println("2. Update Car");
        System.out.println("3. Remove Car");
        System.out.println("4. Add Restrict Color");
        System.out.println("5. Remove Restrict Color");
        System.out.println("6. Add Available Color");
        System.out.println("7. Remove AvailableColor");

        System.out.print("Enter your Option : ");
        int Number = Integer.parseInt(reader.readLine());
        if (Number == 1) {
            CreateCar();
            AdminActions();
        }
    }

    public static void CreateCar() throws Exception {

        int selCompany = SelectedCompany(Companies);
        Company SelectedCompany = (Company) Companies.get(selCompany - 1);
        int SelCarType = SelectCarType(CarTypes);
        CarType SelectedCarType = (CarType) CarTypes.get(SelCarType - 1);
        int selEngineType = SelectEngineType(EngineTypes);
        EngineType SelectedEngineType = EngineTypes.get(selEngineType - 1);
        if (SelectedEngineType.EngineTypeName.equalsIgnoreCase("petrol")) {
            PetrolCar car = new PetrolCar();
            // carname
            // description
            car.SetCompanyName(SelectedCompany.CompanyName);
            car.SetEngineType(SelectedEngineType.EngineTypeName);
            car.SetEngineCapacity(SelectedEngineType.Capacity);
            car.setCarType(SelectedCarType.CarTypeName);

            System.out.print("\nEnter the Car Name : ");
            String Carname = reader.readLine();
            car.SetCarName(Carname);
            System.out.print("Enter the Car Description : ");
            String carDes = reader.readLine();
            car.SetDescription(carDes);
            System.out.println(car.toString());

            CarDetails carDetail = new CarDetails(Carname, SelectedCompany.CompanyID, SelectedCarType.CarTypeID,
                    SelectedEngineType.EngineTypeID, carDes);
            CarUtil.AddCarDetails(carDetail);

        } else if (SelectedEngineType.EngineTypeName.equalsIgnoreCase("diesel")) {

        } else {

        }

    }

    public static int SelectedCompany(List<Company> companies) throws Exception {
        int SlectedCom = 0;
        int i = 0;
        try {
            System.out.println("Select the Car Company");
            System.out.println("-----------------------");
            for (Company company : companies) {
                System.out.println(++i + " " + company.CompanyName);
            }
            System.out.print("Enter your Option : ");
            int num = Integer.parseInt(reader.readLine());

            if (num <= i && num > 0) {
                return num;
            } else {
                System.out.println("please Select Correct Option");
                SelectedCompany(companies);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return SlectedCom;
    }

    public static int SelectCarType(List<CarType> cartypes) throws Exception {
        int i = 0;
        System.out.println("Select the Car Type");
        System.out.println("-----------------------");
        for (CarType carType : cartypes) {
            System.out.println(++i + " " + carType.CarTypeName);
        }
        System.out.print("Enter your option : ");
        int num = Integer.parseInt(reader.readLine());
        if (num <= i && num > 0) {
            return num;
        } else {
            System.out.println("Please select the Correct Option");
            SelectCarType(cartypes);
        }
        return 0;

    }

    public static int SelectEngineType(List<EngineType> enginetypes) throws Exception {
        int i = 0;
        System.out.println("Select the Car Engine type");
        System.out.println("-----------------------");
        for (EngineType engineType : enginetypes) {
            System.out.println(++i + " ." + engineType.EngineTypeName);
        }
        System.out.print("Enter your option : ");
        int num = Integer.parseInt(reader.readLine());
        if (num <= i && num > 0) {
            return num;
        } else {
            System.out.println("Enter the Correct option");
            SelectEngineType(enginetypes);
        }
        return 0;
    }

    public static int Login() throws Exception {

        try {
            System.out.println("\tLOGIN");
            System.out.println("---------------");
            System.out.print("Enter your UserName \t :");
            String UName = reader.readLine();
            System.out.print("Enter your Password \t :");
            String Password = reader.readLine();
            if (UName.toLowerCase().equals("superadmin") && Password.equals("SuperAdmin123")) {
                return 1;
            } else if (UName.toLowerCase().equals("admin") && Password.equals("Admin123")) {
                return 2;
            }
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
