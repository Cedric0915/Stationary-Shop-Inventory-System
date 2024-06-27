import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

//This class represents an employee user and extends the User class.
public class Employee extends User implements InfoReader<User>, DataWriter<User>
{
	//Reads data from file and populates an ArrayList of User objects.
	@Override
	public void readFile(String userFilePath, ArrayList<User> dataList)
	{
        try (BufferedReader reader = new BufferedReader(new FileReader(userFilePath))) 
        {
        	String line;
            while ((line = reader.readLine()) != null) 
            {
            	String[] elements = line.split(","); 
            	if (elements.length >= 5) 
            	{
            		String name = elements[0];
                    String ic = elements[1];
                    String username = elements[2];
                    String password = elements[3];
                    User user= new User(name,ic,username,password);
                    dataList.add(user);
            	}
            	else
            		break;
            }
        } 
        
		 // Handle any IOException that may occur during file reading.
        catch (IOException e) 
        {
            System.out.println("Error reading the file: " + userFilePath);
        }
	}
	
	//Writes data from an ArrayList of User objects to file.
	@Override
	public void writeDataToFile(String userFilePath, ArrayList<User> userList) 
	{
		ArrayList<String> dataArray = new ArrayList<>();
		for (User user : userList) 
		{
			dataArray.add(user.toString());
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFilePath))) 
        {
            for (String row : dataArray) 
            {
                writer.write(row);
                writer.newLine();
            }
        } 
        catch (IOException e)
        {
            System.out.println("Error writing to the file: " + e.getMessage());
        }
	}
	
	//Allows a user to register by providing their name, IC number, username, and password.
	public static void registration(ArrayList<User>admArray, ArrayList<User>empArray)
	{
		System.out.println("============================================================");
		System.out.println("                       Registration                         ");
		System.out.println("============================================================");
		
		// Input and validation for user's name
		String name;
		boolean validName = true;
		do
		{
			System.out.println("Enter your name              (Please refer your IC card) : ");
			name = sc.nextLine();
			name = name.toUpperCase();
			for (int i = 0; i < name.length(); i++) 
			{
                 char c = name.charAt(i);
                 
                 // Check if each character is a digit or a dash
                 if (!(Character.isAlphabetic(c) || c == ' '))
                 {
                     validName = false;
                     break;
                 }
                 else
                	 validName = true;
			}
			if(!validName)
            {
            	System.out.println("Name should only consist of alphabet! Please re-enter name!");
            }
		} while(!validName);
		
	    // Input and validation for user's IC number
		String ic;
		boolean existIC = false;
		boolean validIC = true;
		boolean validICLength = true;
		boolean noAccount = true;
		do
		{
			System.out.println("Enter your IC number              (Eg: 012345678901) : ");
			ic = sc.nextLine();
			if (ic.length() != 12)
			{
				System.out.println("Invalid length of IC number! Please re-enter IC number!");
				validICLength = false;
			}
			else
				validICLength = true;
				for (int i = 0; i < ic.length(); i++) 
				{
	                 char c = ic.charAt(i);
	                 
	                 // Check if each character is a digit or a dash
	                 if (!Character.isDigit(c)) 
	                 {
	                     validIC = false;
	                     break;
	                 }
	                 else
	                	 validIC = true;
				}
				if(!validIC)
	             {
	             	System.out.println("IC number should only consist of digit! Please re-enter IC number!");
	             }
				
				// Check if the IC number already exists in the employee list
				for (User employee: empArray)
					if (ic.equals(employee.getIC()))
					{
						String ans;
						do
						{
							System.out.println("Error! Employee already exist! Please enter again!");
							System.out.print("Do you forgot your username or password? [y/y: YES n/N: NO]");
							ans = sc.nextLine();
							ans = ans.toUpperCase();
							if (ans.equals("Y") || ans.equals("N"))
							{
								noAccount = true;
								if (ans.equals("Y"))
								{
									noAccount = false;
									User.forgetPassword(admArray, empArray);
								}
							}
								
							else
							{
								System.out.println("Error! Invalid input! Please enter again!");
							}
						} while (!(ans.equals("Y") || ans.equals("N")));
						
						
						existIC = true;
					}
					else
						existIC = false;
                     
		} while (existIC || !validIC || !validICLength);
		
	    // Create a new username and password for the user
		while (noAccount)
		{
			System.out.println("Create a new username    (Eg: Lim_1212 | no space allow) : ");
			String username = sc.nextLine();
			String password;
			boolean existAccount = false;
			do
			{
				System.out.println("Create a new password (Eg : 0123456789 | no space allow) : ");
				password = sc.nextLine();
				for (User employee: empArray)
					if (username.equals(employee.getUsername()))
						if(password.equals(employee.getPassword()))
						{
							System.out.println("Error! Account already exist! Please re-enter password!");
							existAccount = true;
						}
						else
							existAccount = false;
			} while (existAccount);
			
			// Write the user's information to the employee list and save to file
			DataWriter<User> userWriter = new User();
			User user = new User(name, ic, username, password);
	        empArray.add(user);
	        userWriter.writeDataToFile(empFilePath, empArray);
	        System.out.println("Your information has recorded successfuly...");
	        noAccount = false;
		}
	}
	
	//Displays the employee menu for managing orders and inventory.
	public static boolean employeeMenu(ArrayList<Product> proArray, ArrayList<Vendor> vendorList, ArrayList<Order> transactionRecord, ArrayList<Inventory> inventoryList, int inventoryValue)
	{
		Scanner input = new Scanner(System.in);
		//if can continue to Employee's main menu page
		System.out.println("===========================================================");
		System.out.println("                       Restock alert                       ");
		System.out.println("===========================================================");
		Product.getProductLowerThanMinQuantity(proArray);
		System.out.println("Press enter to proceed");
		boolean validMenuInput = true;
		int empSelect = 0; 
		do
		{
			input.nextLine();
			try
			{
				do
				{
				
					System.out.println("===========================================================");
					System.out.println("                     Employee main menu                    ");
					System.out.println("===========================================================");
					System.out.println("1. Order");
					System.out.println("2. Inventory");
					System.out.println("3. Log out");
					System.out.print("Please enter an option to do modification: ");
					empSelect = input.nextInt();
					
					// Validate the employee main menu choice.
					if (empSelect != 1 && empSelect != 2 && empSelect != 3)
					{
						System.out.println("Invalid option! Please enter again.");
					}
					input.nextLine();
				} while (empSelect != 1 && empSelect != 2 && empSelect != 3);
					switch(empSelect)
					{
					case 1:
						// Order modification menu
						boolean validOrdInput = true;
						int ordSelect;
						do
						{
							input.nextLine();
							try
							{
								do
								{
									System.out.println("-----------------------------------------------------------");
									System.out.println("                   Modification of order                   ");
									System.out.println("-----------------------------------------------------------");
									System.out.println("1. Add transaction");
									System.out.println("2. Search order or transaction");
									System.out.println("3. Update Transaction");
									System.out.println("4. Exit");
									System.out.print("Please enter an option to do modification: ");
									ordSelect = input.nextInt();
									
									// Validate the order modification choice.
									if (ordSelect != 1 && ordSelect != 2 && ordSelect != 3 && ordSelect != 4)
									{
										System.out.println("Invalid option! Please enter again.");
									}
									input.nextLine();
								} while (ordSelect != 1 && ordSelect != 2 && ordSelect != 3 && ordSelect != 4);
								
								if (ordSelect == 1)
								{
									Order.addTransaction(transactionRecord, proArray);
					        		System.out.println("Press enter to proceed");
					        		validOrdInput = false;
								}
								else if (ordSelect == 2)
								{
									Order.searchTransactions(transactionRecord, proArray);
					        		System.out.println("Press enter to proceed");
					        		validOrdInput = false;
								}
								else if (ordSelect == 3)
								{
									Order.updateTransaction(transactionRecord, proArray);
					        		System.out.println("Press enter to proceed");
					        		validOrdInput = false;
								}
								else
								{
									System.out.println("You have successfully exit the modification page!");
									System.out.println("Press enter to proceed");
									validOrdInput = true;
								}
							}
							catch (InputMismatchException e)
	                        {
	                            System.out.println("Error! Invalid input! Please enter again.");
	                            validOrdInput = false;
	                        }
					} while (!validOrdInput);
					break;
					
					case 2:
						// Inventory modification menu
						boolean validIvtInput = true;
						int ivtSelect;
						do
						{
							input.nextLine();
							try
							{
								do
								{
									System.out.println("-----------------------------------------------------------");
									System.out.println("                 Modification of inventory                 ");
									System.out.println("-----------------------------------------------------------");
									System.out.println("1. Add inventory transaction");
									System.out.println("2. Update Stock in quantity");
									System.out.println("3. Exit");
									System.out.print("Please enter an option to do modification: ");
									ivtSelect = input.nextInt();
									// Validate the inventory modification choice.
									if (ivtSelect != 1 && ivtSelect != 2 && ivtSelect != 3)
									{
										System.out.println("Invalid option! Please enter again.");
									}
									input.nextLine();
								} while(ivtSelect != 1 && ivtSelect != 2 && ivtSelect != 3);
								
								if (ivtSelect == 1)
								{
									Inventory.addInventory(inventoryList, inventoryValue, proArray, vendorList);
				                    System.out.println("Press enter to proceed");
				                    validIvtInput = false;
								}
								else if (ivtSelect == 2)
								{
									Inventory.updateStockQuantity(inventoryList, proArray);
				                    System.out.println("Press enter to proceed");
				                    validIvtInput = false;
								}
								else
								{
									System.out.println("You have successfully exit the modification page!");
									System.out.println("Press enter to proceed");
									validIvtInput = true;
								}
							}
							catch (InputMismatchException e)
	                        {
	                            System.out.println("Error! Invalid input! Please enter again.");
	                            validIvtInput = false;
	                        }
						} while (!validIvtInput);
						break;
		
			case 3:
				System.out.println("You have logged out successfully!");
				validMenuInput = true;
				return false;
			default:
				System.out.println("Error! Invalid Input! Please enter again!");
				input.nextLine();
				break;
			}
			}
			catch (InputMismatchException e)
			{
				System.out.println("Invalid input! Please enter again.");
				validMenuInput =false;
			}	
	} while (!validMenuInput || empSelect != 3);
	return true;
	}
}