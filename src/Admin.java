import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

//This class represents an admin user and extends the User class.
public class Admin extends User implements InfoReader<User>, DataWriter<User>
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
				if (elements.length == 5) 
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

	//Displays and manages the admin menu for modifying products, vendors, orders, and inventory.
	public static boolean adminMenu(ArrayList<Product> proArray, int value, ArrayList<Vendor> vendorList, int vendorValue, ArrayList<Order> transactionRecord, ArrayList<Inventory> inventoryList)
	{
		Scanner input = new Scanner(System.in);
		//if can continue to Admin's main menu page
		System.out.println("===========================================================");
		System.out.println("                       Restock alert                       ");
		System.out.println("===========================================================");
		Product.getProductLowerThanMinQuantity(proArray);
		System.out.println("Press enter to proceed");
		boolean validMenuInput = true;
		int admSelect = 0; 
		do
		{
			input.nextLine();
			try
			{
				do
				{
					// Display the admin main menu options.
					System.out.println("===========================================================");
					System.out.println("                       Admin main menu                     ");
					System.out.println("===========================================================");
					System.out.println("1. Product");
					System.out.println("2. Vendor");
					System.out.println("3. Order");
					System.out.println("4. Inventory");
					System.out.println("5. Log out");
					System.out.print("Please enter an option to do modification: ");
					admSelect = input.nextInt();
					
					// Validate the admin's menu choice.
					if (admSelect != 1 && admSelect != 2 && admSelect != 3 && admSelect != 4 && admSelect != 5)
					{
						System.out.println("Invalid option! Please enter again.");
					}
				} while(admSelect != 1 && admSelect != 2 && admSelect != 3 && admSelect != 4 && admSelect != 5);
				switch(admSelect)
				{
				case 1:

					// Product modification menu.
					boolean validProInput = true;
					int proSelect;
					do
					{
						input.nextLine();
						try
						{

							do
							{
								System.out.println("-----------------------------------------------------------");
								System.out.println("                  Modification of product                  ");
								System.out.println("-----------------------------------------------------------");
								System.out.println("1. Add new product");
								System.out.println("2. Update product price");
								System.out.println("3. Delete product");
								System.out.println("4. Set minimum stock quantity for a product");
								System.out.println("5. Print product list");
								System.out.println("6. Exit");
								System.out.print("Please enter an option to do modification: ");
								proSelect = input.nextInt();
								
								// Validate the product modification choice.
								if (proSelect != 1 && proSelect != 2 && proSelect != 3 && proSelect != 4 && proSelect != 5 && proSelect != 6)
								{
									System.out.println("Invalid option! Please enter again.");
								}
								input.nextLine();
							} while(proSelect != 1 && proSelect != 2 && proSelect != 3 && proSelect != 4 && proSelect != 5 && proSelect != 6);

							if (proSelect == 1)
							{
								Product.addNewProduct(proArray, value);
								System.out.println("Press enter to proceed");
								validProInput = false;
							}
							else if (proSelect == 2)
							{
								Product.updateProduct(proArray);
								System.out.println("Press enter to proceed");
								validProInput = false;
							}
							else if (proSelect == 3)
							{
								Product.deleteProduct(proArray);
								System.out.println("Press enter to proceed");
								validProInput = false;
							}
							else if (proSelect == 4)
							{
								Product.setMinimumQuantity(proArray);
								System.out.println("Press enter to proceed");
								validProInput = false;
							}
							else if (proSelect == 5)
							{
								Product.printProductList(proArray);
								System.out.println("Press enter to proceed");
								validProInput = false;
							}
							else
							{
								System.out.println("You have successfully exit the modification page!");
								System.out.println("Press enter to proceed");
								validProInput = true;
							}
						}
						catch (InputMismatchException e)
						{
							System.out.println("Error! Invalid input! Please enter again.");
							validProInput = false;
						}
					}while (!validProInput);
					break;
					
				case 2:
					// Vendor modification menu.
					boolean validVdrInput = true;
					int vdrSelect;
					do
					{
						input.nextLine();
						try
						{
							do
							{
								System.out.println("-----------------------------------------------------------");
								System.out.println("                   Modification of vendor                  ");
								System.out.println("-----------------------------------------------------------");
								System.out.println("1. Add new vendor");
								System.out.println("2. Update vendor information");
								System.out.println("3. Remove vendor");
								System.out.println("4. Exit");
								System.out.print("Please enter an option to do modification: ");
								vdrSelect = input.nextInt();
								
								// Validate the vendor modification choice.
								if (vdrSelect != 1 && vdrSelect != 2 && vdrSelect != 3 && vdrSelect != 4)
								{
									System.out.println("Invalid option! Please enter again.");
								}
								input.nextLine();
							} while (vdrSelect != 1 && vdrSelect != 2 && vdrSelect != 3 && vdrSelect != 4);

							if (vdrSelect == 1)
							{
								Vendor.addVendor(vendorList, vendorValue);
								System.out.println("Press enter to proceed");
								validVdrInput = false;
							}
							else if (vdrSelect == 2)
							{
								Vendor.updateVendor(vendorList);
								System.out.println("Press enter to proceed");
								validVdrInput = false;
							}
							else if (vdrSelect == 3)
							{
								Vendor.removeVendor(vendorList);
								System.out.println("Press enter to proceed");
								validVdrInput = false;
							}
							else
							{
								System.out.println("You have successfully exit the modification page!");
								System.out.println("Press enter to proceed");
								validVdrInput = true;
							}
						}
						catch (InputMismatchException e)
						{
							System.out.println("Error! Invalid input! Please enter again.");
							validVdrInput = false;
						}
					} while (!validVdrInput);
					break;

				case 3:
					// Order modification menu.
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
								System.out.println("1. Search order or transaction");
								System.out.println("2. Update transaction");
								System.out.println("3. Exit");
								System.out.print("Please enter an option to do modification: ");
								ordSelect = input.nextInt();
								
								// Validate the order modification choice.
								if (ordSelect != 1 && ordSelect != 2 && ordSelect != 3)
								{
									System.out.println("Invalid option! Please enter again.");
								}
								input.nextLine();
							} while (ordSelect != 1 && ordSelect != 2 && ordSelect != 3);

							if (ordSelect == 1)
							{
								Order.searchTransactions(transactionRecord,proArray);
								System.out.println("Press enter to proceed");
								validOrdInput = false;
							}
							else if (ordSelect == 2)
							{
								Order.updateTransaction(transactionRecord, proArray);
								System.out.println("Press enter to proceed");
								validOrdInput = false;
							}
							else
							{
								System.out.println("You have successfully exit the modification page!");
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
					
				case 4:
					// Inventory modification menu.
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
								System.out.println("1. Display inventory transaction");
								System.out.println("2. Search inventory record");
								System.out.println("3. Update Stock in quantity");
								System.out.println("4. Exit");
								System.out.print("Please enter an option to do modification: ");
								ivtSelect = input.nextInt();
								
                                // Validate the inventory modification choice.
								if (ivtSelect != 1 && ivtSelect != 2 && ivtSelect != 3 && ivtSelect != 4)
								{
									System.out.println("Invalid option! Please enter again.");
								}
								input.nextLine();
							} while (ivtSelect != 1 && ivtSelect != 2 && ivtSelect != 3 && ivtSelect != 4);

							if (ivtSelect == 1)
							{
								Inventory.displayInventory(inventoryList);
								System.out.println("Press enter to proceed");
								validIvtInput = false;
							}
							else if (ivtSelect == 2)
							{
								Inventory.searchInventory(inventoryList);						
								System.out.println("Press enter to proceed");
								validIvtInput = false;
							}
							else if (ivtSelect == 3)
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
				case 5:
					System.out.println("You have logged out successfully!");
					return false;
				default:
					System.out.println("Error! Invalid Option! Please enter again!");
					input.nextLine();
					break;
				}
			}
			
			// If an InputMismatchException occurs, notify the user and prompt to re-enter
			catch (InputMismatchException e)
			{
				System.out.println("Error! Invalid input! Please enter again.");
				validMenuInput =false;
			}
		} while (!validMenuInput || admSelect != 5);
		return true;
	}
}