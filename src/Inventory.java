//Inventory class represents information about the transaction details during product stock-in.
//It includes methods for reading and writing inventory data to files, adding and updating inventory transactions,
//searching for inventory records and display inventory and product information.

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.text.ParseException;
import java.util.ArrayList;

public class Inventory implements DataReader<Inventory>, DataWriter<Inventory>
{
	// Defines necessary input and file paths for the Stationary Inventory Management System.
	static Scanner input = new Scanner(System.in);
	static String inventoryFilePath = "Inventory.txt";	//file stored inventory transaction data.
	static String productFilePath = "Product.txt";		//file stored product data.
	static String vendorFilePath = "Vendor.txt";		//file stored vendor data.

	// instance variables
	private String inventoryID;
	private String productID;
	private double pricePerProduct;
	private int stockInQuantity;
	private double totalPrice;
	private String vendorID;
	private String stockInDate;
	
	// accessors
	public String getInventoryID()
	{
		return inventoryID;
	}
	
	public String getProductID()
	{
		return productID;
	}
	
	public double getPricePerProduct()
	{
		return pricePerProduct;
	}
	
	public int getStockInQuantity()
	{
		return stockInQuantity;
	}
	
	public double getTotalPrice()
	{
		return stockInQuantity*pricePerProduct;
	}
	
	public String getVendorID()
	{
		return vendorID;
	}
	
	public String getStockInDate()
	{
		return stockInDate;
	}
	
	// mutators
	public void setInventoryID(String theInventoryID)
	{
		inventoryID=theInventoryID;
	}
	
	public void setPricePerProduct(double thePricePerProduct)
	{
		pricePerProduct=thePricePerProduct;
	}
	
	public void setStockInQuantity(int theStockInQuantity)
	{
		stockInQuantity=theStockInQuantity;
	}
	
	public void setTotalPrice(double theTotalPrice)
	{
		totalPrice=theTotalPrice;
	}
	
	// Default constructor for the Inventory class.
	// Initializes an empty Inventory object with default values.
	public Inventory()
	{
		
	}
	
	//Overloaded constructor for the Inventory class.
	//Initializes an Inventory object with specific attributes.
	public Inventory(String theInventoryID,String theProductID,double thePricePerProduct,int theStockInQuantity,double theTotalPrice,String theVendorID, String theStockInDate)
	{
		inventoryID=theInventoryID;
		productID=theProductID;
		pricePerProduct=thePricePerProduct;
		stockInQuantity=theStockInQuantity;
		totalPrice=theTotalPrice;
		vendorID=theVendorID;
		stockInDate=theStockInDate;
	}
	
	// instance methods
	
	// Overrides toString method to provide a formatted string representation of the Inventory object.
	@Override
	public String toString() 
	{
        return inventoryID + "," + productID + "," + pricePerProduct + "," + stockInQuantity + "," + totalPrice + "," + vendorID + "," + stockInDate;
    }
	
	// Reads data from a file and populates an ArrayList of Inventory objects.
	@Override
	public int readFile(String filePath, ArrayList<Inventory> dataList)
	{
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
        	String line;
        	int count = 1;
            while ((line = reader.readLine()) != null) 
            {
            	String[] elements = line.split(","); 
            	if (elements.length >= 7) 
            	{
            		String inventoryID = elements[0];
                    String productID = elements[1];
                    double pricePerProduct = Double.parseDouble(elements[2]);
                    int stockInQuantity = Integer.parseInt(elements[3]);
                    double totalPrice = Double.parseDouble(elements[4]);
                    String vendorID = elements[5];
                    String stockInDate = elements[6];
                    
                    // Create a new Inventory object and add it to the ArrayList
                    Inventory inventory= new Inventory(inventoryID, productID, pricePerProduct, stockInQuantity, totalPrice, vendorID, stockInDate);
                    dataList.add(inventory);
            	}
            	count++;
            }
            return count;
        } 
        catch (IOException e) 
        {
            // Handle any IOException during file reading
            System.out.println("Error reading the file: " + filePath);
        }
        return -1;
	}
	
	// Writes data from an ArrayList of Inventory objects to a file.
	@Override
	public void writeDataToFile(String filePath, ArrayList<Inventory> inventoryList) 
	{
		// Create an ArrayList to store the data as strings
		ArrayList<String> dataArray = new ArrayList<>();
		
		// Convert each Inventory object to string representation and add it to the dataArray
		for (Inventory inventory : inventoryList) 
		{
			dataArray.add(inventory.toString());
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) 
        {
        	// Write each data row to the file, one row per line
            for (String row : dataArray) 
            {
                writer.write(row);
                writer.newLine();
            }
        } 
        catch (IOException e)
        {
        	// Handle any IO exception during file writing
            System.out.println("Error writing to the file: " + e.getMessage());
        }
    }
	
	//Add new inventory transaction to the system.
	public static void addInventory(ArrayList<Inventory> inventoryList, int inventoryValue, ArrayList<Product> proArray, ArrayList<Vendor> vendorList)
	{
		// Initialize variables to manage user input and validation
		boolean validInput = true;
        boolean validPriceInput = true;
        boolean validStockInput = true;
        boolean foundProduct = false;
        boolean foundVendor = false;
        boolean Exit = false;
        String productID, vendorID, stockInDate;
        boolean isValid;
        double pricePerProduct = 0;
        double totalPrice = 0;
        int stockInQuantity = 0;
        
        do
        {
        	// Handle potential exceptions that may occur during user input and processing
        	try
        	{
    			System.out.println("----------------------------------------");
				System.out.println("Add Inventory  Transaction ");
				System.out.println("----------------------------------------");
				
				do 
	            {
					System.out.println("Enter e/E exit if do not have inventory transaction to add");
					System.out.print("Product ID: S00");
					productID = input.nextLine();
					
					if (productID.equalsIgnoreCase("E"))
					{
						Exit = true;
						break;
					}
					
					else
					{
						foundProduct = false;
						
						//Go through product by product  
		                for (Product product : proArray) 
		                {
		                    if (product.getProductID().equals(productID)) //Check whether match user input's product ID
		                    {
		                        foundProduct = true;
		                        break;
		                    }
		                }
		                
	                	//Display statement if not match user input's product ID
		                if (!foundProduct) 
		                {
		                    System.out.println("Product ID not found! Please re-enter");
		                }
					}
					
	            } while (!foundProduct); // Continue the loop until a valid product ID is entered
				
				if (Exit)
				{
					System.out.println("Exit Successfully!");
					break;
				}
				
				do
				{
					System.out.print("Price per product: ");
					String priceInput = input.nextLine();
					
					try 
					{
				        pricePerProduct = Double.parseDouble(priceInput);
				        validPriceInput = true;
				    } 
					catch (NumberFormatException e) 
					{
				        System.out.println("Invalid input! Please enter a valid price.");
				        validPriceInput = false;
				    }
				}while(!validPriceInput);
				
				do
				{
					System.out.print("Stock in Quantity: ");
					String stockInInput = input.nextLine();
					
					try 
					{
						stockInQuantity = Integer.parseInt(stockInInput);
				        validStockInput = true;
				        
						totalPrice = stockInQuantity*pricePerProduct;
						System.out.println("Total Price: " + totalPrice);
				    } 
					catch (NumberFormatException e) 
					{
				        System.out.println("Invalid input! Please enter a valid stock.");
				        validStockInput = false;
					}
				}while(!validStockInput);
				
				do 
	            {
					System.out.print("Vendor ID: VD00");
					vendorID = input.nextLine();
	               	
					//Go through vendor by vendor 
	                for (Vendor vendor : vendorList) 
	                {
	                    if (vendor.getID().equals(vendorID)) //Check whether match user input's vendor ID
	                    {
	                    	foundVendor = true;
	                        break;
	                    }
	                }
	                
                	//Display statement if not match user input's vendor ID
	                if (!foundVendor) 
	                {
	                    System.out.println("Vendor ID not found! Please re-enter");
	                }
	            } while (!foundVendor); // Continue the loop until a valid vendor ID is entered
				
				
				do
				{
					System.out.print("Stock in  (DD-MM-YYYY): ");
    				stockInDate = input.nextLine();
    				
					isValid = Inventory.validateJavaDate(stockInDate);  // Validate the entered date format
					
					if (isValid) 
	    	        {
	    	        	System.out.println("Inventory transaction successfully added!");
        				Inventory inventory = new Inventory(Integer.toString(inventoryValue), productID, pricePerProduct, stockInQuantity, totalPrice, vendorID, stockInDate);
        				
        				// Generate a unique identifier for the new inventory transaction
        				String amount = Integer.toString(inventoryList.size()+1);
        				inventory.setInventoryID(amount);
        				inventoryList.add(inventory);
        	           
        				// Write the new inventory transaction into "Inventory.txt"
        				DataWriter<Inventory> inventoryWriter = new Inventory();
        	            inventoryWriter.writeDataToFile(inventoryFilePath, inventoryList);
    	    	        
        	            // Update the stock quantity for the associated product
        	            int currentStockQuantity=Inventory.searchStockQuantity(productID,proArray);
        				System.out.println("Current stock quantity: " + currentStockQuantity);
        				int newCurrentQuantity = currentStockQuantity+stockInQuantity;
        				System.out.println("New Stock Quantity: "+newCurrentQuantity);
        				
        				// Update the product's quantity in the product list
    	   				for (Product product : proArray) 
    	   				{
    	   				    if (product.getProductID().equals(productID)) 
    	   				    {
    	   				        product.setProductQuantity(newCurrentQuantity);
    	   				        break; // No need to continue searching once found
    	   				    }
    	   				}
    	    			
    	   				// Write the updated product list to "Product.txt"
        				DataWriter<Product> productWriter = new Product();
   	    				productWriter.writeDataToFile(productFilePath, proArray);
    	    				
   	    	            inventoryValue++; // Increment the identifier for the next inventory transaction
    	            } 
    				else 
    				{
    	                System.out.println("Invalid date format! Please enter the date in the format DD-MM-YYYY.");
    	            }
				}while(!isValid); // Continue the loop until a valid date is entered
        	}
        	
        	// If an InputMismatchException occurs, notify the user and prompt to re-enter
        	catch (InputMismatchException e)
            {
                System.out.println("Invalid input! Please enter again.");
                validInput =false;
            }
        	
            // If any other unexpected exception occurs, prompt user to retry
            catch (Exception e)
            {
                System.out.println("Some error occur! Please retry.");
                validInput =false;
            }
        }while (!validInput);
		
	}
	
	//Validate the stock-in date enter by user
	public static boolean validateJavaDate(String strDate)
	{
		//Check if date is 'null' 
		if (strDate.trim().equals(""))
		{
		    return true;
		}
		// Date is not 'null' 
		else
		{
		    //Set preferred date format
		    SimpleDateFormat stdDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		    stdDateFormat.setLenient(false); // Set leniency to false
		    
		    try
		    {
		    	// Parse the input date string using the specified date format
		        Date date = stdDateFormat.parse(strDate); 
		        
		        // Extract the year from the parsed date
		        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
		        
		        // Check if the extracted year is less than or equal to 2023
		        if (year <= 2023) 
		        {
	                return true;
	            } 
		        else 
	            {
	                return false;
	            }
		    }
		    
		    // Date format is invalid 
		    catch (ParseException e)
		    {
		        return false;
		    }
		}
	 }
	
	//Update the stock-in quantity of a specific inventory transaction.
	public static void updateStockQuantity(ArrayList<Inventory> inventoryList, ArrayList<Product> proArray)
	{
		boolean validInput = true;
		boolean found = false;
		boolean validNewStockInput = true;
    	int newStockInQuantity = 0;

        do
        {
        	String inventoryIDToUpdate;
        	try
        	{
        		System.out.println("---------------------------------------------------------");
				System.out.println("Update Inventory  Transaction's Stock In Quantity");
				System.out.println("---------------------------------------------------------");
				
				do 
	            {
					System.out.println("Enter e/E exit if do not have inventory transaction to update");
					System.out.print("Type Inventory ID to update : I00");
					inventoryIDToUpdate = input.nextLine();
	               	
					
					for (Inventory inventory : inventoryList) 
					{
						if (inventory.getInventoryID().equals(inventoryIDToUpdate)) 
						{
							found = true;

							// Display the details of the specific inventory transaction
							System.out.println("----------------------------------------");
							System.out.println("Inventory  Transaction ");
							System.out.println("----------------------------------------");
							System.out.println("Inventory ID: I00" + inventory.getInventoryID());
							System.out.println("Product ID: s00" + inventory.getProductID());
							System.out.println("Price per product: " + inventory.getPricePerProduct());
							System.out.println("Stock-in Quantity: " + inventory.getStockInQuantity());
							System.out.println("Total Price: " + inventory.getTotalPrice());
							System.out.println("Vendor ID: VD00" + inventory.getVendorID());
							System.out.println("Stock-in Date: " + inventory.getStockInDate());
							break;
						}
					}

					if (!found) 
					{
						System.out.println("Inventory Transaction ID not found!");
					}
				}while (!found);
	                
	          
				
				do
				{
					System.out.print("Enter new stock-in quantity: ");
					String newStockInInput = input.nextLine();
			        
					try 
					{
						newStockInQuantity = Integer.parseInt(newStockInInput);
				        validNewStockInput = true;
				    } 
					catch (NumberFormatException e) 
					{
				        System.out.println("Invalid input! Please enter a valid stock.");
				        validNewStockInput = false;
					}
				}while(!validNewStockInput);
				
		        
				for (Inventory inventory : inventoryList) 
				{
					if (inventory.getInventoryID().equals(inventoryIDToUpdate)) 
					{
						// Calculate the incorrect stock change
						int incorrectStockInQuantity = inventory.getStockInQuantity();
						int stockChange = newStockInQuantity - incorrectStockInQuantity;

				        // Update the incorrect transaction
				        inventory.setStockInQuantity(newStockInQuantity);

				        // Update the current stock quantity in Product.txt
				        String productID = inventory.getProductID();
				        for (Product product : proArray) 
			            {
			                if (product.getProductID().equals(productID)) 
			                {
			                    int currentStockQuantity = product.getProductQuantity();
				                int newStockQuantity = currentStockQuantity + stockChange;
				                product.setProductQuantity(newStockQuantity);

				                // Write the updated data back to "Product.txt"
								DataWriter<Product> productWriter = new Product();
				                productWriter.writeDataToFile(productFilePath, proArray);

				                break; // No need to continue searching once found
				            }
			            }
				        break;
					}
				}
				              
				int num;
				Inventory inventory = null;
				
				// Search for the Inventory transaction by its ID and get its index.
				num = searchID(inventoryIDToUpdate, inventoryList);
				if (num == -1)
					System.out.println("Inventory is not found!");
				else 
				{
				    inventory = inventoryList.get(num);
				    System.out.println("Inventory transaction information has been updated!");
				    
				    //Write the updated stock quantity back to "Inventory.txt"
				    DataWriter<Inventory> inventoryWriter = new Inventory();
		            inventoryWriter.writeDataToFile(inventoryFilePath, inventoryList);
				}
        	}
        	catch (InputMismatchException e)
            {
                System.out.println("Invalid input! Please enter again.");
                validInput =false;
            }
            catch (Exception e)
            {
                System.out.println("Some error occur! Please retry.");
                validInput =false;
            }
        }while (!validInput);
	}
	
	//Searches for the stock quantity of a product by its ID in proArray list
	public static int searchStockQuantity(String theProductID, ArrayList<Product> proArray)
	{
		int stockQuantityOfProduct=0;
		for (int i = 0; i < proArray.size(); i++)
		{
			Product products = proArray.get(i);
			if (theProductID.equals(products.getProductID()))
				stockQuantityOfProduct=products.getProductQuantity();
		}
		return stockQuantityOfProduct;
	}
	
	//Searches for an Inventory object by its ID in the inventoryList
	public static Inventory searchByID(String theID, ArrayList<Inventory> inventoryList)  
	{
		for (int i = 0; i < inventoryList.size(); i++)
		{
			Inventory inventory = inventoryList.get(i);
			if (theID.equals(inventory.getInventoryID()))
				return inventory;
		}
		return null;
	}
	
	//Searches for the index of an Inventory object by its ID in the given list of inventory items.
	public static int searchID(String theID, ArrayList<Inventory> inventoryList)  
	{
		for (int i = 0; i < inventoryList.size(); i++)
		{
			Inventory inventory = inventoryList.get(i);
			if (theID.equals(inventory.getInventoryID()))
				return i;
		}
		return -1;
	}
	
	//Searches and displays inventory transactions for a specific product ID in inventoryList
	public static void searchByProductID(String productID, ArrayList<Inventory> inventoryList) 
	{
	    System.out.println("Inventory transactions for Product ID: " + productID);
	    for (Inventory inventory : inventoryList) {
	        if (inventory.getProductID().equals("S00" + productID)) {
	            System.out.println("Inventory ID: I00" + inventory.getInventoryID());
	            System.out.println("Price Per Product: RM" + inventory.getPricePerProduct());
	            System.out.println("Stock In Quantity: " + inventory.getStockInQuantity());
	            System.out.println("Total Price: RM" + inventory.getTotalPrice());
	            System.out.println("Vendor ID: VD00" + inventory.getVendorID());
	            System.out.println("Stock In Date: " + inventory.getStockInDate());
	            System.out.println("----------------------------------------");
	        }
	    }
	}
	
	//Displays a list of inventory transactions from the given list of inventory items.
	public static void displayInventory(ArrayList<Inventory> inventoryList)
	{
		boolean validInput = true;
        do
        {
        	try
        	{
        		System.out.println("----------------------------------------");
				System.out.println("Inventory  Transaction ");
				System.out.println("----------------------------------------");
				for (Inventory inventory : inventoryList) 
				{
					String formatProductPrice = String.format("%.2f", inventory.getPricePerProduct());
					String formatTotalPrice = String.format("%.2f", inventory.getTotalPrice());
					System.out.println("Inventory ID : I00" + inventory.getInventoryID() + "\tProduct ID: S00" + inventory.getProductID() + "\tPrice Per Product: RM" + formatProductPrice + "\tStock in quantity: " + inventory.getStockInQuantity()+ "\tTotal price : RM" + formatTotalPrice + "\tVendor ID : VD00" + inventory.getVendorID() + "\t Stock-in Date : " + inventory.getStockInDate());		
				}
                validInput = true;
        	}
        	catch (Exception e)
            {
                System.out.println("Some error occur! Please retry.");
        		input.nextLine();
                validInput =false;
            }
        }while (!validInput);
	}
	
	//Searches and displays inventory transactions based on user-selected criteria in the given option
	public static void searchInventory(ArrayList<Inventory> inventoryList)
	{
		boolean validInput = true;
        do
        {
            try
            {
                int select = 0;
                String IDtoSearch;
                do
                {
                    // Display search options menu
                	System.out.println("----------------------------------------");
    				System.out.println("Search List ");
    				System.out.println("----------------------------------------");
                    System.out.println("Options: ");
                    System.out.println("1. Search by Inventory Transaction ID");
                    System.out.println("2. Search by Product ID");
                    System.out.println("3. Search by Vendor ID");
                    System.out.print("Please enter an option: ");

                    if (input.hasNextInt()) 
                    { 
                    	// Check if the next input is an integer
                        select = input.nextInt();
                        if (select != 1 && select != 2 && select!=3)
                        {
                            System.out.println("Invalid option! Please enter again.");
                            input.nextLine();
                        }
                    }
                    else
                    {
                        System.out.println("Invalid input! Please enter again.");
                        input.nextLine();
                    }
                } while (select != 1 && select != 2 && select!=3);
                
                // Clear the input buffer
                input.nextLine();
                
                boolean found = false;
                
                if(select == 1)
                {
                	// Search by inventory transaction ID
                	System.out.print("Please enter the inventory ID: I00");
                    IDtoSearch = input.nextLine();
                	for (int i = 0; i < inventoryList.size(); i++)
            		{
            			Inventory inventory = inventoryList.get(i);
            			if (IDtoSearch.equals(inventory.getInventoryID()))
            			{
            				found = true;
                         	System.out.println("Inventory ID : I00" + IDtoSearch + "\tProduct ID: S00" + inventory.getProductID() + "\tPrice Per Product: RM" + inventory.getPricePerProduct() + "\tStock in quantity: " + inventory.getStockInQuantity()+ "\tTotal price : RM" + inventory.getTotalPrice()+ "\tVendor ID : VD00" + inventory.getVendorID() + "\t Stock-in Date : " + inventory.getStockInDate());		
            			}
                    }
                	if (!found)
                        System.out.println("No inventory transactions found");
                }
                else if (select == 2)
                {
                	// Search by product ID
                	System.out.print("Please enter the product ID: S00");
                    IDtoSearch = input.nextLine();
                    for (Inventory inventory : inventoryList) 
                    {
                        if (inventory.getProductID().equals(IDtoSearch)) 
                        {
                            found = true;
                            System.out.println("Inventory ID : I00" + inventory.getInventoryID() + "\tPrice Per Product: RM" + inventory.getPricePerProduct() + "\tStock in quantity: " + inventory.getStockInQuantity() + "\tTotal price : RM" + inventory.getTotalPrice() + "\tVendor ID : VD00" + inventory.getVendorID() + "\t Stock-in Date : " + inventory.getStockInDate());
                        }
                    }
                }
                else if (select == 3)
                {
                	// Search by Vendor ID
                	System.out.print("Please enter the vendor ID: VD00");
                    IDtoSearch = input.nextLine();
                    for (Inventory inventory : inventoryList) 
                    {
                        if (inventory.getVendorID().equals(IDtoSearch)) 
                        {
                            found = true;
                            System.out.println("Inventory ID : I00" + inventory.getInventoryID() + "\tProduct ID: S00" + inventory.getProductID() + "\tPrice Per Product: RM" + inventory.getPricePerProduct() + "\tStock in quantity: " + inventory.getStockInQuantity() + "\tTotal price : RM" + inventory.getTotalPrice() + "\t Stock-in Date : " + inventory.getStockInDate());
                        }
                    }
                    if (!found)
                        System.out.println("No inventory transactions found for Vendor ID: VD00" + IDtoSearch);
                }
                else
                {
                    System.out.println("No inventory transactions found.");
                }
                
                validInput = true;
            }
            catch (InputMismatchException e)
            {
                System.out.println("Invalid input! Please enter again.");
                validInput =false;
            }
            catch (Exception e)
            {
                System.out.println("Some error occur! Please retry.");
                validInput =false;
            }
        }while (!validInput);
	}
}