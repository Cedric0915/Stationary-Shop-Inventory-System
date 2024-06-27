//Product class represents products in this stationary inventory management system.
//It includes methods that allow admin for reading and writing product data to files, adding and updating product details,
//searching for product, delete product, set minimum stock quantity of a product and display product information.

import java.io.BufferedReader; //Reads text from a character-input stream
import java.io.FileReader; //reading streams of characters
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Product implements DataReader<Product>, DataWriter<Product>
{
	//Entering and modifying data
	static Scanner input = new Scanner(System.in);
	static String filePath = "Product.txt";
	
	//instance variables
	
	private String productID;
	private String productName;
	private double productPrice;
	private int productQuantity;
	private int productMinQuantity;
	private String productType;
	private String productStatus;

	//accessors

	public String getProductID()
	{
		return productID;
	}

	public String getProductName()
	{
		return productName;
	}

	public double getProductPrice()
	{
		return productPrice;
	}

	public int getProductQuantity()
	{
		return productQuantity;
	}

	public int getProductMinQuantity()
	{
		return productMinQuantity;
	}

	public String getProductType()
	{
		return productType;
	}

	public String getProductStatus()
	{
		return productStatus;
	}

	//mutators [assume will not key in wrong name and type] [ID can do auto-generation] done!!
	public void setProductID(String theProductID)
	{
		productID = theProductID;
	}

	public void setProductPrice(double price)
	{
		productPrice = price;
	}

	public void setProductQuantity(int quantity)
	{
		productQuantity = quantity;
	}

	public void setProductMinQuantity(int minQuantity)
	{
		productMinQuantity = minQuantity;
	}

	public void setProductStatus(String status)
	{
		productStatus = status;
	}

	//Default constructor for the Product class.
	//Initializes an empty Product object with default values.
	public Product() 
	{

	}

	//Overloaded constructors for the Product class.
	//Initializes an Product object with specific attributes.
	public Product(String theProductID, String theProductName, double theProductPrice, int theProductQuantity, int theproductMinQuantity, String theProductType, String theStatus)
	{
		productID = theProductID;
		productName = theProductName;
		productPrice = theProductPrice;
		productQuantity = theProductQuantity;
		productMinQuantity = theproductMinQuantity;
		productType = theProductType;
		productStatus = theStatus;
	}

	public Product(String theProductName, double theProductPrice, int theProductQuantity, int theproductMinQuantity, String theProductType, String theStatus)
	{
		productName = theProductName;
		productPrice = theProductPrice;
		productQuantity = theProductQuantity;
		productMinQuantity = theproductMinQuantity;
		productType = theProductType;
		productStatus = theStatus;
	}

	public Product(String theProductName, double theProductPrice, String theProductType, String theStatus)
	{
		productName = theProductName;
		productPrice = theProductPrice;
		productType = theProductType;
		productStatus = theStatus;
	}

	public Product(String theProductName, int theProductQuantity, String theProductType, String theStatus)
	{
		productName = theProductName;
		productQuantity = theProductQuantity;
		productType = theProductType;
		productStatus = theStatus;
	}

	public Product(String theProductName, String theProductType, String theStatus)
	{
		productName = theProductName;
		productType = theProductType;
		productStatus = theStatus;
	}

	public Product(String theProductName)
	{
		productName = theProductName;
	}

	public Product(double theProductPrice)
	{
		productPrice = theProductPrice;
	}

	//Overrides toString method to provide a formatted string representation of the Product object.
	@Override
	public String toString() 
	{
		String formatPrice = String.format("%.2f", productPrice);
		return productID + "," + productName + "," + formatPrice + "," + productQuantity + "," + productMinQuantity + "," + productType + "," + productStatus;
	}

	//Reads data from product text file and populates an ArrayList of Product objects.
	@Override
	public int readFile(String filePath, ArrayList<Product> dataList)
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
					String productID = elements[0];
					String productName = elements[1];
					double productPrice = Double.parseDouble(elements[2]);
					int productQuantity = Integer.parseInt(elements[3]);
					int productMinQuantity = Integer.parseInt(elements[4]);
					String productType = elements[5];
					String productStatus = elements[6];
					Product product = new Product(productID, productName, productPrice, productQuantity, productMinQuantity, productType, productStatus);
					dataList.add(product);
				}
				count++;
			}
			return count;
		} 
		catch (IOException e) 
		{
			System.out.println("Error reading the file: " + filePath);
		}
		return -1;
	}

	//Writes data from an ArrayList of Inventory objects to a file.
	@Override
	public void writeDataToFile(String filePath, ArrayList<Product> productList) 
	{
		ArrayList<String> dataArray = new ArrayList<>();
		for (Product product : productList) 
		{
			dataArray.add(product.toString());
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) 
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

	//Add new product to the system.
	public static void addNewProduct(ArrayList<Product> proArray, int value)
	{
		//Initialize variables to manage validation
		boolean validInput = true;
		boolean Exit = true;
		do
		{
			//Handle potential exceptions that may occur during user input and processing
			try
			{
				int availability = 0;
				do 
				{
					System.out.println("[e/E:exit if do not have product to add]");
					System.out.println("Enter product name: ");
					String name = input.nextLine();
					name = name.toUpperCase();
					
					//Check if the user wants to exit.
					if (name.equals("E"))
					{
						Exit = false;
						break;
					}
					else
					{
						//Check if the product name already exists in the product list.
						availability = Product.searchName(name, proArray);
						if (availability == -1)
						{
							//If the product name is unique, prompt the user to enter additional details
							System.out.println("Enter product price: ");
							double price = input.nextDouble();
							System.out.println("Enter product quantity: ");
							int quantity = input.nextInt();
							System.out.println("Enter product minimum quantity: ");
							int minquantity = input.nextInt();
							input.nextLine();
							boolean validCategory = true;
							String category;
							
							//Validate the product category, ensuring it contains only alphabetic characters or spaces.
							do
							{
								System.out.print("Enter product category: ");
								category = input.nextLine();
								category = category.toUpperCase();
								for (int i = 0; i < category.length(); i++) 
								{
									char c = category.charAt(i);

									//Check if each character is a letter (alphabet) or a space.
									if (!(Character.isAlphabetic(c) || c == ' '))
									{
										validCategory = false;
										break;
									}
									else
										validCategory = true;
								}
								if(!validCategory)
								{
									System.out.println("Category should only consist of alphabet! Please re-enter category!");
								}
							}while(!validCategory);

				            //Create a new 'Product' object with the entered details.
							String status = "ACTIVE";
							Product p = new Product(Integer.toString(value - 1), name, price, quantity, minquantity, category, status);
							
							//Generate a unique product ID and set it for the new product.
							String amount = Integer.toString(proArray.size() + 1);
							p.setProductID(amount);
							proArray.add(p);
							System.out.println("New product is added successfully!");
							
							//Write the updated product list to the file.
							DataWriter<Product> productFileWriter = new Product();
							productFileWriter.writeDataToFile(filePath, proArray);
							value++;
							validInput = true;
						}
						else
							System.out.println("Product already exist! Please enter a new product!");
					}
				} while (availability != -1); 
			}
			
			// If an InputMismatchException occurs, notify the user and prompt to re-enter
			catch (InputMismatchException e)
			{
				System.out.println("Invalid input! Please enter again.");
				input.nextLine();
				validInput = false;
			}
			
			// If any other unexpected exception occurs, prompt user to retry
			catch (Exception e)
			{
				System.out.println("Some error occur! Please retry.");
				validInput =false;
			}
		} while (!validInput || !Exit);
	}

	//Searches for a product in the product list by its unique Product ID and returns the matching Product object.
	public static Product searchByID(String theProductID, ArrayList<Product> proArray)  
	{
		for (int i = 0; i < proArray.size(); i++)
		{
			Product products = proArray.get(i);
			if (theProductID.equals(products.getProductID()))
				return products;
		}
		return null;
	}

	//Searches for a product in the product list by its name and returns the matching Product object.
	public static Product searchByName(String theProductName, ArrayList<Product> proArray)  
	{
		for (int i = 0; i < proArray.size(); i++)
		{
			Product products = proArray.get(i);
			if (theProductName.equals(products.getProductName()))
				return products;
		}
		return null;
	}
	
	//Searches for a product in the product list by its unique Product ID and returns the index of the matching product.
	public static int searchID(String theProductID, ArrayList<Product> proArray)  
	{
		for (int i = 0; i < proArray.size(); i++)
		{
			Product products = proArray.get(i);
			if (theProductID.equals(products.getProductID()))
				return i;
		}
		return -1;
	}

	//Searches for a product in the product list by its name and returns the index of the matching product.
	public static int searchName(String theProductName, ArrayList<Product> proArray)  
	{
		for (int i = 0; i < proArray.size(); i++)
		{
			Product products = proArray.get(i);
			if (theProductName.equals(products.getProductName()))
				return i;
		}
		return -1;
	}

	//Searches for a product in the product list by its stock quantity and returns the matching Product object.
	public Product searchByStockQty(int theProductStockQty, ArrayList<Product> proArray)  
	{
		for (int i = 0; i < proArray.size(); i++)
		{
			Product products = proArray.get(i);
			if (theProductStockQty == products.getProductQuantity())
				return products;
		}
		return null;
	}
	
	//Allows admin to update the price of a product by searching for it either by Product Name or Product ID.
	public static void updateProduct(ArrayList<Product> proArray)
	{
		boolean validInput = true;
		do
		{
			try
			{
				int select = 0;
				String information1;
				do
				{
					//Display search option
					System.out.println("Options: ");
					System.out.println("1. Search by Product Name");
					System.out.println("2. Search by Product ID");
					System.out.println("Please enter an option: ");
					if (input.hasNextInt()) 
					{ 
						// Check if the next input is an integer
						select = input.nextInt();
						if (select != 1 && select != 2)
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
				} while (select != 1 && select != 2);
				System.out.println("Please enter the information of product: ");
				input.nextLine();
				information1 = input.nextLine();
				information1 = information1.toUpperCase();

				int num;
				Product products = null;
				
				//Search for the product based on the selected option
				switch(select)
				{
				case 1:
					num = searchName(information1, proArray);
					products = searchByName(information1, proArray);
					break;
				case 2:
					num = searchID(information1, proArray);
					products = searchByID(information1, proArray);
					break;
				default:
					System.out.println("Error! Invalid option!");
					num = -1;
					break;
				}
				if (num == -1)
					System.out.println("Product is not found!");
				else
				{
					// Prompt the user for the new price
					System.out.println("Please enter the new price of product: ");
					double newPrice = input.nextDouble();
					products.setProductPrice(newPrice);
					proArray.set(num, products);
					System.out.println("Product price has been updated!");
					
					// Write the updated data to the data file
					DataWriter<Product> productFileWriter = new Product();
					productFileWriter.writeDataToFile(filePath, proArray);
				}
				validInput = true;
			}
			catch (InputMismatchException e)
			{
				System.out.println("Invalid input! Please enter again.");
				input.nextLine();
				validInput =false;
			}
			catch (Exception e)
			{
				System.out.println("Some error occur! Please retry.");
				validInput =false;
			}
		} while (!validInput);

	}

	//Deletes a product from the proArray based on admin input.
	public static void deleteProduct(ArrayList<Product> proArray)
	{
		boolean validInput = true;
		do
		{
			try
			{
				int select = 0;
				String information2;
				do
				{
					System.out.println("Options: ");
					System.out.println("1. Search by Product Name");
					System.out.println("2. Search by Product ID");
					System.out.println("Please enter an option: ");
					if (input.hasNextInt()) 
					{ // Check if the next input is an integer
						select = input.nextInt();
						if (select != 1 && select != 2)
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
				} while (select != 1 && select != 2);
				System.out.println("Please enter the information of product: ");
				input.nextLine();
				information2 = input.nextLine();
				int num;
				switch(select)
				{
				case 1:
					num = searchName(information2, proArray);
					break;
				case 2:
					num = searchID(information2, proArray);
					break;
				default:
					System.out.println("Error! Invalid option!");
					num = -1;
					break;
				}
				if (num == -1)
					System.out.println("Product is not found!");
				else
				{
					for (int i = 0; i < proArray.size(); i++)
					{
						if (i == num)
						{
							Product product = proArray.get(i);
							product.setProductStatus("INACTIVE");
							break;
						}
					}
					System.out.println("Product is successfully deleted!");
					DataWriter<Product> productFileWriter = new Product();
					productFileWriter.writeDataToFile(filePath, proArray);
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
		} while (!validInput);
	}

	//Retrieves a product from the provided ArrayList based on the user's choice and information.
	public static Product getProduct(int choice, String information, ArrayList<Product> proArray)
	{
		Product products;
		switch(choice)
		{
		case 1:
			products = searchByName(information, proArray);
			break;
		case 2:
			products = searchByID(information, proArray);
			break;
		default:
			System.out.println("Error! Invalid option!");
			products = null;
			break;
		}
		return products;
	}

	//Prints the list of active products with their details to the console.
	public static void printProductList(ArrayList<Product> proArray)
	{
		boolean validInput = true;
		do
		{
			try
			{
				System.out.println("-----------------------------------------------------------");
				System.out.println("                       Product List                        ");
				System.out.println("-----------------------------------------------------------");
				for (Product product : proArray) 
				{
					// Check if the product is active before printing its details.
					if (product.getProductStatus().equals("ACTIVE"))
					{
						System.out.printf("ID : S00%-4sName: %-59sPrice: %3.2f\tQuantity: %3d\tMinimum Quantity: %3d\tType: %-15s", product.getProductID(), product.getProductName(), product.getProductPrice(), product.getProductQuantity(), product.getProductMinQuantity(), product.getProductType());
						System.out.println("");
					}
				}
				validInput = true; 
			}
			catch (Exception e)
			{
				System.out.println("Some error occur! Please retry.");
				validInput =false; 
			}
		} while (!validInput);
	}

	//Retrieves and prints products with quantities lower than their minimum stock quantity.
	public static void getProductLowerThanMinQuantity(ArrayList<Product> proArray)
	{
		boolean validInput = true;
		do
		{
			try
			{
				ArrayList<Product> proLowStock = new ArrayList<Product>();
				for (int i = 0; i < proArray.size(); i++)
				{
					Product products = proArray.get(i);
					
					// Check if the product quantity is lower than the minimum stock quantity and if it's active.
					if(products.getProductQuantity() < products.getProductMinQuantity())
						if(products.getProductStatus().equals("ACTIVE"))
							proLowStock.add(products);
				}
				System.out.println("Products that have lower quantity than minimum stock: ");
				
				// Iterate through the low-stock products and print their details.
				for (Product product : proLowStock) 
				{
					System.out.printf("ID : S00%-4sName: %-59sPrice: %3.2f\tQuantity: %3d\tMinimum Quantity: %3d\tType: %-15s", product.getProductID(), product.getProductName(), product.getProductPrice(), product.getProductQuantity(), product.getProductMinQuantity(), product.getProductType());
					System.out.println("");
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
		} while (!validInput);
	}

	//Allows admin to set a new minimum stock level for a product based on their choice and input.
	public static void setMinimumQuantity(ArrayList<Product> proArray)
	{
		boolean validInput = true;
		do
		{
			try
			{
				int select = 0;
				String information1;
				do
				{
					System.out.println("Options: ");
					System.out.println("1. Search by Product Name");
					System.out.println("2. Search by Product ID");
					System.out.println("Please enter an option: ");
					if (input.hasNextInt()) 
					{ // Check if the next input is an integer
						select = input.nextInt();
						if (select != 1 && select != 2)
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
				} while (select != 1 && select != 2);
				System.out.println("Please enter the information of product: ");
				input.nextLine();
				information1 = input.nextLine();
				information1 = information1.toUpperCase();
				int num;
				Product products = null;
				switch(select)
				{
				case 1:
					num = searchName(information1, proArray);
					products = searchByName(information1, proArray);
					break;
				case 2:
					num = searchID(information1, proArray);
					products = searchByID(information1, proArray);
					break;
				default:
					System.out.println("Error! Invalid option!");
					num = -1;
					break;
				}
				if (num == -1)
					System.out.println("Product is not found!");
				else
				{
					System.out.println("Please enter the new minimum level of product: ");
					int newMinQuantity = input.nextInt();
					products.setProductMinQuantity(newMinQuantity);
					proArray.set(num, products);
					DataWriter<Product> productFileWriter = new Product();
					productFileWriter.writeDataToFile(filePath, proArray);
					System.out.println("Product minimum stock level has been updated!");
				}
				validInput = true;
			}
			catch (InputMismatchException e)
			{
				System.out.println("Invalid input! Please enter again.");
				input.nextLine();
				validInput =false;
			}
			catch (Exception e)
			{
				System.out.println("Some error occur! Please retry.");
				input.nextLine();
				validInput =false;
			}
		} while (!validInput);
	}

}