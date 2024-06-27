//This is a main menu class
//It allows us to run for the whole program as one which is a completed program structures
//It contains a login page to user(login as admin or employee) or non-user(register as a new user which is employee)
//After login, user are allow to perform several tasks related to its role.
import java.util.*;

public class MainMenu 
{
	public static void main(String[]args)
	{
		//Read product text file
		ArrayList<Product> proArray = new ArrayList<>();
		Scanner input = new Scanner(System.in);
		String productFilePath = "Product.txt";
		DataReader<Product> productFileReader = new Product();
		int value = productFileReader.readFile(productFilePath, proArray);
		
		//Read vendor text file
		ArrayList<Vendor> vendorList = new ArrayList<>();
		String vendorFilePath= "Vendor.txt";
		DataReader<Vendor> vendorFileReader = new Vendor();
		int vendorValue = vendorFileReader.readFile(vendorFilePath, vendorList);
		
		//Read order text file
		ArrayList<Order> transactionRecord = new ArrayList<>();
		String orderFilePath = "Order.txt";
		InfoReader<Order> orderFileReader = new Order();
        orderFileReader.readFile(orderFilePath, transactionRecord);
        
        //Read inventory text file
        ArrayList<Inventory> inventoryList = new ArrayList<>();
        String inventoryFilePath= "Inventory.txt";
		DataReader<Inventory> inventoryFileReader = new Inventory();
		int inventoryValue = inventoryFileReader.readFile(inventoryFilePath, inventoryList);
		
		//Read admin text file
		ArrayList<User> adminArray = new ArrayList<>();
		String adminFilePath = "Admin.txt";
		InfoReader<User>userFileReader1 = new User();
		userFileReader1.readFile(adminFilePath,adminArray);
		
		//Read employee text file
		ArrayList<User> empArray = new ArrayList<>();
		String empFilePath = "Employee.txt";
		InfoReader<User>userFileReader2 = new User();
		userFileReader2.readFile(empFilePath,empArray);
		
		boolean useSystem = true;
		do
		{
			boolean validLogInput = true;
			do 
			{
			try
			{
			//Display Main Page
			System.out.println("===========================================================");
			System.out.println("          Welcome to Stationary Inventory System!          ");
			System.out.println("===========================================================");
			System.out.println("1. Login as Admin");
			System.out.println("2. Login as Employee");
			System.out.println("3. Register");
			System.out.println("4. Forget username or/and passsword");
			System.out.print("Please enter an option: ");
			String option = input.nextLine();
			boolean validInput = true;
			do
			{
				try
				{
					
					switch(option)
					{
					case "1":
						//Allow admin to login
						boolean validAdmLogin = true;
						//validate can access or not
						validAdmLogin = User.login(option, adminArray, empArray);
						if (!validAdmLogin)
						{
							//if not valid
							useSystem = false;
						}
						else
						{
							//Allow for admin to access for these methods
							useSystem = Admin.adminMenu(proArray, value, vendorList, vendorValue, transactionRecord, inventoryList);
						}
						break;

					case "2":
						//Allow employee to login
						boolean validEmpLogin = true;
						//validate can access or not
						validEmpLogin = User.login(option, adminArray, empArray);
						if (!validEmpLogin)
						{
							//if not valid
							useSystem = false;
						}
						else
						{
							//Allow for employee to access for these methods
							useSystem = Employee.employeeMenu(proArray, vendorList, transactionRecord, inventoryList, inventoryValue);
						}
						break;
					case "3":
						//Allow new user to register 
						Employee.registration(adminArray,empArray);
						break;
					case "4":
						//Allow existed users to change their username and/or password
						useSystem = User.forgetPassword(adminArray, empArray);
						break;
					default:
						//Error message will be display when not 1 to 4 is inserted
						System.out.println("Error! Invalid Input! Please enter again!");
						break;
					}
					validInput = true;
				}
				catch (Exception e)
				{
					// Handle any IO exception during file writing
					System.out.println("Some error occur! Please retry.");
					validInput =false;
				}
			} while (!validInput);
			}
			catch (InputMismatchException e)
			{
				System.out.println("Invalid input! Please enter again.");
				validLogInput =false;
			}
		}while (!validLogInput);
		} while(useSystem);
		//display after the program is exit
		System.out.println("The system will be closed.");
	}
}