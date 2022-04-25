
import java.io.*;
import java.util.*;

import Main.Java.DataModel.*;
import Main.Java.Models.*;

import Main.Java.Util.*;

public class App {
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static List<Company> Companies = new ArrayList<>();
    public static List<CarType> CarTypes = new ArrayList<>();
    public static List<EngineType> EngineTypes = new ArrayList<>();
    public static List<Color> Colors = new ArrayList<>();
    public static String User_Name = "";

    public static void main(String[] args) throws Exception {
        try {
            InitialFunction();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void InitialFunction() throws Exception {
        System.out.println("Select Your Option");
        System.out.println("-------------------");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Enter your Option : ");
        int option = Integer.parseInt(reader.readLine());

        if (option == 1) {
            int code = Login();
            DashBoard(code);
        } else if (option == 2) {
            Registration();
            InitialFunction();
        } else {
            System.out.println("Enter Correct Option");
            InitialFunction();
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
        } else if (RoleCode == 3) {
            Companies = utils.GetCompanies();
            CarTypes = utils.GetCarTypes();
            EngineTypes = utils.GetEngineTypes();
            Colors = utils.GetColors();
            CustomerFuctions();

        }
    }

    public static void CustomerFuctions() throws Exception {

        System.out.println("Select Company");
        int SelCompany = SelectedCompany(Companies);
        Company SelectedCompany = Companies.get(SelCompany - 1);
        int SelCarType = SelectCarType(CarTypes);
        CarType SelectedCarType = CarTypes.get(SelCarType - 1);
        int SelEngineType = SelectEngineType(EngineTypes);
        EngineType SelectedEngineType = EngineTypes.get(SelEngineType - 1);

        List<CarDetails> cardetails = CarUtil.GetCarDetails(SelectedCompany.CompanyID, SelectedEngineType.EngineTypeID,
                SelectedCarType.CarTypeID);

        List<Car> cars = GetCarDatas(cardetails);

        System.out.println("\n\n\nAvailable Cars");
        int i = 0;
        for (Car car : cars) {
            System.out.println("--------------------------------------------------------");
            System.out.println("Car NO : " + (++i));
            System.out.println("--------------------------------------------------------");
            System.out.println(car.toString());
            System.out.println("The Price of the Car is\t : " + car.CalculatePrice());
            System.out.println("--------------------------------------------------------");

        }
        System.out.print("Enter Car No You want to Purchase : ");
        int option = Integer.parseInt(reader.readLine());
        if (option <= i && option > 0) {
            System.out.println("Available Car Colors");
            Car selectedCar = cars.get(option - 1);
            Double amt = selectedCar.CalculatePrice();
            List<String> carcol = CarUtil.GetAvailableCaolors(cars.get(option - 1).GetCarID());
            int j = 0;
            List<Integer> avaind = new ArrayList<>();
            for (Color col : Colors) {
                ++j;
                if (carcol.contains(col.ColorID)) {
                    System.out.println(j + " . " + col.ColorName + "\t Total: " + (amt + col.Amount) + "\t");
                    avaind.add(j);

                }

            }
            System.out.print("Select Color : ");
            int Coloption = Integer.parseInt(reader.readLine());
            if (Coloption <= j && Coloption > 0 && avaind.contains(Coloption)) {
                selectedCar.SetCarColor(Colors.get(Coloption - 1).ColorName);
                Color selectedColor = Colors.get(Coloption - 1);

                System.out.println("Are you Sure You want to Buy this Car");
                System.out.print("If yes Type 'y'  : ");
                String res = reader.readLine();
                if (res.equalsIgnoreCase("y")) {
                    boolean isUser = UserUtil.IsUser(User_Name);
                    if (!isUser) {
                        System.out.println("Customer details Not Found Please Enter the Details");
                        CustomerDetails customer = GetCustomerDetails();
                        String CID = UserUtil.AddCustomerDetails(customer);

                        System.out.println("Customer Added SuccessFully");
                        Invoice invoice = new Invoice(selectedCar.GetCarID(), selectedColor.ColorID,
                                selectedCar.GetPrice(), selectedCar.GetTaxAmount(), selectedColor.Amount, CID);
                        InvoiceUtil.AddInvoice(invoice);
                        System.out.println("invoice generated SuccessFully");
                        GenerateInvoice(invoice);

                    } else {
                        String CID = UserUtil.GetCustomerID(User_Name);
                        Invoice invoice = new Invoice(selectedCar.GetCarID(), selectedColor.ColorID,
                                selectedCar.GetPrice(), selectedCar.GetTaxAmount(), selectedColor.Amount, CID);
                        InvoiceUtil.AddInvoice(invoice);
                        System.out.println(" invoice Generated SuccessFully");
                        GenerateInvoice(invoice);

                    }

                }
            }
        }

    }

    public static void Registration() throws Exception {
        System.out.println("Welcome");
        System.out.println("--------");
        UserDetails user = new UserDetails();
        System.out.print("Enter the User Name : ");
        user.UserName = reader.readLine();
        System.out.print("Enter the Password : ");
        user.Password = reader.readLine();
        System.out.print("Enter the Mobile Number : ");
        user.MobileNumber = reader.readLine();
        System.out.print("Enter the Email : ");
        user.Email = reader.readLine();

        UserUtil.AddUser(user);

    }

    public static void GenerateInvoice(Invoice invoice) throws Exception {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Invoice Detail");
        System.out.println("----------------------------------------------------------------");
        CarDetails cardetails = CarUtil.GetCarDetail(invoice.CarID);
        EngineType enginetype = CarEngineType(cardetails.EngineTypeID);
        CarType cartypee = CarType_Get(cardetails.CarTypeID);
        Company company = Company_Get(cardetails.CompanyID);
        Color col = GetColor(invoice.ColorID);
        System.out.println("Car Name \t\t:" + cardetails.CarName);
        System.out.println("Description \t\t:" + cardetails.Description);
        System.out.println("Company \t\t:" + company.CompanyName);
        System.out.println("Engine Details");
        System.out.println(enginetype.toString());
        System.out.println("Car Type Details");
        System.out.println(cartypee.toString());
        System.out.println("Car Color Details");
        System.out.println(col.toString());
        System.out.println("----------------------------------------------------------------");
        System.out.println("Price                                           : " + invoice.Price);
        System.out.println("TAx                                             : " + invoice.TaxAmount);
        System.out.println("----------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------");
        System.out.println("Price                                           : " + invoice.TotalAmount);
        System.out.println("----------------------------------------------------------------");

    }

    // Super Admin Functions

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

    // Admin Actions
    public static void AdminActions() throws Exception {
        System.out.println("Select the Option");
        System.out.println("-----------------");
        System.out.println("1. Create Car");
        System.out.println("2. Update Car");
        System.out.println("3. Remove Car");
        System.out.println("4. Add Restrict Color");
        System.out.println("5. Remove Restrict Color");
        System.out.println("6. Add Available Color");
        System.out.println("7. Remove Available Color");

        System.out.print("Enter your Option : ");
        int Number = Integer.parseInt(reader.readLine());
        if (Number == 1) {
            CreateCar();
            AdminActions();
        } else if (Number == 6) {
            AddAvailableColor();
            System.out.println("Color Added Successfully");
            AdminActions();
        } else if (Number == 4) {
            AddRestrictedColor();
            System.out.println("Color Added Successfully\n\n");
            AdminActions();
        } else if (Number == 5) {
            RemoveRestrictColor();
            System.out.println("Color Removed Successfully");
            AdminActions();
        } else if (Number == 7) {
            RemoveAvailableColor();
            System.out.println("\n\n\n");
            AdminActions();
        } else if (Number == 2) {
            UpdateCarDetails();
            System.out.println("\n\n\n");
            System.out.println("Car Details Updated Successfully");
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
            System.out.println("Successfully Added Car Details\n\n");

        } else if (SelectedEngineType.EngineTypeName.equalsIgnoreCase("diesel")) {
            DieselCar car = new DieselCar();

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
            System.out.println("Successfully Added Car Details\n\n");
        } else {
            ElectricCar car = new ElectricCar();

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
            System.out.println("Successfully Added Car Details\n\n");
        }

    }

    public static void AddAvailableColor() throws Exception {
        List<CarMeta> CarData = CarUtil.GetCarMetaDetails();
        int i = 0;
        System.out.println("Select Car");
        System.out.println("-----------");
        for (CarMeta carMeta : CarData) {
            System.out.println(++i + " . " + carMeta.CarName);
        }
        System.out.print("Enter you Option : ");
        int number = Integer.parseInt(reader.readLine());
        if (number <= i && number > 0) {
            CarMeta SelectedCar = CarData.get(number - 1);
            List<String> availableColors = CarUtil.GetAvailableCaolors(SelectedCar.CarID);
            List<String> Restrictedcolors = CarUtil.GetRestrictedCaolors(SelectedCar.CarID);

            // print Car Available Colors
            String selectedColor = PrintCarAvailableColors(SelectedCar.CarName, availableColors, Restrictedcolors);
            CarUtil.AddAvailableColor(SelectedCar.CarID, selectedColor);

        } else {
            System.out.println("Enter Correct Option\n\n");
            AddAvailableColor();
        }

    }

    public static void AddRestrictedColor() throws Exception {
        List<CarMeta> CarData = CarUtil.GetCarMetaDetails();
        int i = 0;
        System.out.println("Select Car");
        System.out.println("-----------");
        for (CarMeta carMeta : CarData) {
            System.out.println(++i + " . " + carMeta.CarName);
        }
        System.out.print("Enter you Option : ");
        int number = Integer.parseInt(reader.readLine());
        if (number <= i && number > 0) {
            CarMeta SelectedCar = CarData.get(number - 1);
            List<String> availableColors = CarUtil.GetAvailableCaolors(SelectedCar.CarID);
            List<String> Restrictedcolors = CarUtil.GetRestrictedCaolors(SelectedCar.CarID);

            // print Car Available Colors
            String selectedColor = PrintCarRestrictedColors(SelectedCar.CarName, availableColors, Restrictedcolors);
            CarUtil.AddRestrictedColor(SelectedCar.CarID, selectedColor);

        } else {
            System.out.println("Enter Correct Option\n\n");
            AddAvailableColor();
        }

    }

    public static String PrintCarAvailableColors(String CarName, List<String> CarColors, List<String> restrictedColors)
            throws Exception {
        String SelectedColor = "";
        System.out.println("Car Name \t: " + CarName);
        System.out.println("Available Colors");
        System.out.println("------------------");
        for (Color col : Colors) {
            if (CarColors.contains(col.ColorID)) {
                System.out.println(col.ColorName);
            }
        }
        System.out.println("No of Available Colors : " + CarColors.size());
        int i = 0;
        List<Integer> avaind = new ArrayList<>();
        System.out.println("\n\n Select Color To Add");
        for (Color col : Colors) {
            ++i;
            if (!CarColors.contains(col.ColorID) && !restrictedColors.contains(col.ColorID)) {
                System.out.println(i + " . " + col.ColorName);
                avaind.add(i);
            }

        }
        System.out.println("Enter your option : ");
        int number = Integer.parseInt(reader.readLine());
        if (number <= i && number > 0 && avaind.contains(number)) {
            SelectedColor = Colors.get(number - 1).ColorID;
        } else {
            System.out.println("please select Correct option \n\n");
            PrintCarAvailableColors(CarName, CarColors, restrictedColors);
        }
        return SelectedColor;
    }

    public static String PrintCarRestrictedColors(String CarName, List<String> CarColors, List<String> restrictedColors)
            throws Exception {
        String SelectedColor = "";
        System.out.println("Car Name \t: " + CarName);
        System.out.println("Restricted Colors");
        System.out.println("------------------");
        for (Color col : Colors) {
            if (restrictedColors.contains(col.ColorID)) {
                System.out.println(col.ColorName);
            }
        }
        System.out.println("No of Restricted Colors : " + restrictedColors.size());
        int i = 0;
        List<Integer> avaind = new ArrayList<>();
        System.out.println("\n\n Select Color To Add RestrictedColor");
        for (Color col : Colors) {
            ++i;
            if (!CarColors.contains(col.ColorID) && !restrictedColors.contains(col.ColorID)) {
                System.out.println(i + " . " + col.ColorName);
                avaind.add(i);
            }

        }
        System.out.println("Enter your option : ");
        int number = Integer.parseInt(reader.readLine());
        if (number <= i && number > 0 && avaind.contains(number)) {
            SelectedColor = Colors.get(number - 1).ColorID;
        } else {
            System.out.println("please select Correct option \n\n");
            PrintCarRestrictedColors(CarName, CarColors, restrictedColors);
        }
        return SelectedColor;
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
            if (UName != null || Password != null) {
                if (UName.toLowerCase().equals("superadmin") && Password.equals("SuperAdmin123")) {
                    return 1;
                } else if (UName.toLowerCase().equals("admin") && Password.equals("Admin123")) {
                    return 2;
                } else {
                    boolean res = UserUtil.IsValidUser(UName, Password);
                    if (res) {
                        User_Name = UName;
                        return 3;
                    }
                }
            } else {
                throw new Exception("User Name and Password Incorrect");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return 0;

    }

    public static void RemoveRestrictColor() throws Exception {
        List<CarMeta> CarData = CarUtil.GetCarMetaDetails();
        int i = 0;
        System.out.println("Select Car");
        System.out.println("-----------");
        for (CarMeta carMeta : CarData) {
            System.out.println(++i + " . " + carMeta.CarName);
        }
        System.out.print("Enter you Option : ");
        int number = Integer.parseInt(reader.readLine());
        if (number <= i && number > 0) {
            CarMeta SelectedCar = CarData.get(number - 1);
            List<String> Restrictedcolors = CarUtil.GetRestrictedCaolors(SelectedCar.CarID);
            if (Restrictedcolors.size() != 0) {
                int selectedColor = SelectedRestrictedColor(SelectedCar.CarName, Restrictedcolors);
                CarUtil.RemoveRestrictedColor(SelectedCar.CarID, Restrictedcolors.get(selectedColor - 1));
            } else {
                System.out.println("There is no restricted colors in Selected Car");
            }
            // print Car Available Colors

        } else {
            System.out.println("Enter Correct Option\n\n");
            RemoveRestrictColor();

        }
    }

    public static int SelectedRestrictedColor(String CarName, List<String> RestrictedColors) throws Exception {
        int SelectedColor = 0;
        int i = 0;
        for (Color color : Colors) {
            if (RestrictedColors.contains(color.ColorID)) {
                System.out.println(++i + " . " + color.ColorName);
            }

        }
        System.out.print("Enter your Option : ");
        int number = Integer.parseInt(reader.readLine());
        if (number <= i && number > 0) {
            SelectedColor = number;
        } else {
            System.out.println("Enter correct option\n\n");
            SelectedRestrictedColor(CarName, RestrictedColors);
        }
        return SelectedColor;
    }

    public static int SelectedAvailableColor(String CarName, List<String> AvailableColor) throws Exception {
        int SelectedColor = 0;
        int i = 0;
        for (Color color : Colors) {
            if (AvailableColor.contains(color.ColorID)) {
                System.out.println(++i + " . " + color.ColorName);
            }

        }
        System.out.print("Enter your Option : ");
        int number = Integer.parseInt(reader.readLine());
        if (number <= i && number > 0) {
            SelectedColor = number;
        } else {
            System.out.println("Enter correct option\n\n");
            SelectedRestrictedColor(CarName, AvailableColor);
        }
        return SelectedColor;
    }

    public static void RemoveAvailableColor() throws Exception {
        List<CarMeta> CarData = CarUtil.GetCarMetaDetails();
        int i = 0;
        System.out.println("Select Car");
        System.out.println("-----------");
        for (CarMeta carMeta : CarData) {
            System.out.println(++i + " . " + carMeta.CarName);
        }
        System.out.print("Enter you Option : ");
        int number = Integer.parseInt(reader.readLine());
        if (number <= i && number > 0) {
            CarMeta SelectedCar = CarData.get(number - 1);
            List<String> AvailableColors = CarUtil.GetAvailableCaolors(SelectedCar.CarID);
            if (AvailableColors.size() != 0) {
                int selectedColor = SelectedAvailableColor(SelectedCar.CarName, AvailableColors);
                CarUtil.RemoveAvailableColor(SelectedCar.CarID, AvailableColors.get(selectedColor - 1));
            } else {
                System.out.println("There is no Available colors in Selected Car");
            }

        } else {
            System.out.println("Enter Correct Option\n\n");
            RemoveRestrictColor();

        }
    }

    public static void UpdateCarDetails() throws Exception {
        List<CarMeta> CarData = CarUtil.GetCarMetaDetails();
        int i = 0;
        System.out.println("Select Car");
        System.out.println("-----------");
        for (CarMeta carMeta : CarData) {
            System.out.println(++i + " . " + carMeta.CarName);
        }
        System.out.print("Enter you Option : ");
        int number = Integer.parseInt(reader.readLine());
        if (number <= i && number > 0) {
            CarMeta SelectedCar = CarData.get(number - 1);
            CarDetails cardetail = CarUtil.GetCarDetail(SelectedCar.CarID);
            EngineType enginetype = CarEngineType(cardetail.EngineTypeID);
            CarType cartype = CarType_Get(cardetail.CarTypeID);
            Company carcompany = Company_Get(cardetail.CompanyID);
            if (enginetype.EngineTypeName.equalsIgnoreCase("petrol")) {
                PetrolCar petrolcar = new PetrolCar();
                petrolcar.SetCompanyName(carcompany.CompanyName);
                petrolcar.SetEngineType(enginetype.EngineTypeName);
                petrolcar.SetEngineCapacity(enginetype.Capacity);
                petrolcar.setCarType(cartype.CarTypeName);
                petrolcar.SetCarName(cardetail.CarName);
                petrolcar.SetDescription(cardetail.Description);

                System.out.println("Current Details of Car");
                System.out.println(petrolcar.toString());
                System.out.println("you want to change the Company details");
                System.out.print("\nPlease select  'y' - yes 'n'- No : ");
                String opt1 = reader.readLine();
                if (opt1.equalsIgnoreCase("y")) {
                    int selCompany = SelectedCompany(Companies);
                    carcompany = Companies.get(selCompany - 1);
                    cardetail.CompanyID = carcompany.CompanyID;
                    petrolcar.SetCompanyName(carcompany.CompanyName);
                }
                System.out.println("you want to change the Car Type ");
                System.out.print("\nPlease select  'y' - yes 'n'- No : ");
                String opt2 = reader.readLine();
                if (opt2.equalsIgnoreCase("y")) {
                    int selcartype = SelectCarType(CarTypes);
                    cartype = CarTypes.get(selcartype - 1);
                    cardetail.CarTypeID = cartype.CarTypeID;
                    petrolcar.setCarType(cartype.CarTypeName);
                }
                System.out.println("you want to change the Car Engine Type ");
                System.out.print("\nPlease select  'y' - yes 'n'- No : ");
                String opt3 = reader.readLine();
                if (opt3.equalsIgnoreCase("y")) {
                    int selenginetype = SelectEngineType(EngineTypes);
                    enginetype = EngineTypes.get(selenginetype);
                    cardetail.EngineTypeID = enginetype.EngineTypeID;
                    petrolcar.SetEngineType(enginetype.EngineTypeName);
                    petrolcar.SetEngineCapacity(enginetype.Capacity);
                }
                System.out.println("you want to change the Car Name ");
                System.out.print("\nPlease select  'y' - yes 'n'- No : ");
                String opt4 = reader.readLine();
                if (opt4.equalsIgnoreCase("y")) {
                    System.out.print("Enter new Car Name : ");
                    String Newname = reader.readLine();
                    cardetail.CarName = Newname;
                    petrolcar.SetCarName(Newname);
                }
                System.out.println("updated Car Details");
                System.out.println(petrolcar.toString());

                CarUtil.UpdateCarDetails(cardetail);

            } else if (enginetype.EngineTypeName.equalsIgnoreCase("diesel")) {
                DieselCar dieselcar = new DieselCar();
                dieselcar.SetCompanyName(carcompany.CompanyName);
                dieselcar.SetEngineType(enginetype.EngineTypeName);
                dieselcar.SetEngineCapacity(enginetype.Capacity);
                dieselcar.setCarType(cartype.CarTypeName);
                dieselcar.SetCarName(cardetail.CarName);
                dieselcar.SetDescription(cardetail.Description);

                System.out.println("Current Details of Car");
                System.out.println(dieselcar.toString());
                System.out.println("you want to change the Company details");
                System.out.print("\nPlease select  'y' - yes 'n'- No : ");
                String opt1 = reader.readLine();
                if (opt1.equalsIgnoreCase("y")) {
                    int selCompany = SelectedCompany(Companies);
                    carcompany = Companies.get(selCompany - 1);
                    cardetail.CompanyID = carcompany.CompanyID;
                    dieselcar.SetCompanyName(carcompany.CompanyName);
                }
                System.out.println("you want to change the Car Type ");
                System.out.print("\nPlease select  'y' - yes 'n'- No : ");
                String opt2 = reader.readLine();
                if (opt2.equalsIgnoreCase("y")) {
                    int selcartype = SelectCarType(CarTypes);
                    cartype = CarTypes.get(selcartype - 1);
                    cardetail.CarTypeID = cartype.CarTypeID;
                    dieselcar.setCarType(cartype.CarTypeName);
                }
                System.out.println("you want to change the Car Engine Type ");
                System.out.print("\nPlease select  'y' - yes 'n'- No : ");
                String opt3 = reader.readLine();
                if (opt3.equalsIgnoreCase("y")) {
                    int selenginetype = SelectEngineType(EngineTypes);
                    enginetype = EngineTypes.get(selenginetype);
                    cardetail.EngineTypeID = enginetype.EngineTypeID;
                    dieselcar.SetEngineType(enginetype.EngineTypeName);
                    dieselcar.SetEngineCapacity(enginetype.Capacity);
                }
                System.out.println("you want to change the Car Name ");
                System.out.print("\nPlease select  'y' - yes 'n'- No : ");
                String opt4 = reader.readLine();
                if (opt4.equalsIgnoreCase("y")) {
                    System.out.print("Enter new Car Name : ");
                    String Newname = reader.readLine();
                    cardetail.CarName = Newname;
                    dieselcar.SetCarName(Newname);
                }
                System.out.println("updated Car Details");
                System.out.println(dieselcar.toString());

                CarUtil.UpdateCarDetails(cardetail);
            } else {

            }

        } else {
            System.out.println("Enter Correct Option\n\n");

        }
    }

    public static EngineType CarEngineType(String EngineTypeID) {
        EngineType engineType = new EngineType();
        for (EngineType et : EngineTypes) {
            if (et.EngineTypeID.equals(EngineTypeID)) {
                engineType = et;
                break;
            }
        }
        return engineType;
    }

    public static CarType CarType_Get(String CarTypeID) {
        CarType carType = new CarType();
        for (CarType CT : CarTypes) {
            if (CT.CarTypeID.equalsIgnoreCase(CarTypeID)) {
                carType = CT;
                break;
            }
        }
        return carType;
    }

    public static Company Company_Get(String CompanyID) {
        Company company = new Company();
        for (Company com : Companies) {
            if (com.CompanyID.equalsIgnoreCase(CompanyID)) {
                company = com;
                break;
            }

        }
        return company;
    }

    public static Color GetColor(String Colorid) {
        for (Color col : Colors) {
            if (col.ColorID.equals(Colorid)) {
                return col;
            }

        }
        return new Color();
    }

    public static List<Car> GetCarDatas(List<CarDetails> Cardetails) {
        List<Car> cardata = new ArrayList<>();
        for (CarDetails car : Cardetails) {
            EngineType enginetype = CarEngineType(car.EngineTypeID);
            CarType cartype = CarType_Get(car.CarTypeID);
            Company carcompany = Company_Get(car.CompanyID);
            if (enginetype.EngineTypeName.equalsIgnoreCase("Petrol")) {
                PetrolCar ptcar = new PetrolCar(car.CarID, car.CarName, carcompany.CompanyName, cartype.CarTypeName,
                        enginetype.EngineTypeName, car.Description, enginetype.Capacity);
                cardata.add(ptcar);
            } else if (enginetype.EngineTypeName.equalsIgnoreCase("diesel")) {
                DieselCar descar = new DieselCar(car.CarID, car.CarName, carcompany.CompanyName, cartype.CarTypeName,
                        enginetype.EngineTypeName, car.Description, enginetype.Capacity);
                cardata.add(descar);
            } else {
                ElectricCar ev = new ElectricCar(car.CarID, car.CarName, carcompany.CompanyName, cartype.CarTypeName,
                        enginetype.EngineTypeName, car.Description, enginetype.Capacity);
                cardata.add(ev);
            }

        }
        return cardata;
    }

    public static CustomerDetails GetCustomerDetails() throws Exception {
        CustomerDetails customer = new CustomerDetails();
        System.out.println("Enter the Customer Details");
        System.out.println("--------------------------------------------------");
        System.out.print("Enter the Name \t: ");
        customer.SetCustomerName(reader.readLine());
        System.out.print("Enter the Mobile NO \t: ");
        customer.SetMobileNumber(reader.readLine());

        Address address = new Address();
        System.out.print("Enter Door No \t: ");
        address.DoorNo = reader.readLine();
        System.out.print("Enter Street Name \t: ");
        address.StreetName = reader.readLine();
        System.out.print("Enter City Name \t: ");
        address.City = reader.readLine();
        System.out.print("Enter District \t: ");
        address.District = reader.readLine();
        System.out.print("Enter State \t: ");
        address.State = reader.readLine();
        customer.SetAddress(address);
        customer.SetUserName(User_Name);

        return customer;

    }
}
