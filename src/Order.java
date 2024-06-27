//Order class represents information about the order details.
//It includes methods for reading and writing data of an order to files,
//adding and updating transactions details, and displaying order records and transactions details.

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Order implements InfoReader<Order>, DataWriter<Order>
{
	// Defines necessary input and file paths for the Stationary Inventory Management System.
	static Scanner input = new Scanner(System.in);
	static String orderFilePath = "Order.txt";
	static String filePath = "Product.txt";
	
	//instance variables
	private String orderID;
	private String customerID;
	private String productName;
	private int stockOutQuantity;
	private String paymentMethod;
	private String paymentDate;
	
	//accessors
	public String getOrderID()
	{
		return orderID;
	}
	public String getCustomerID()
	{
		return customerID;
	}
	public String getProductName()
	{
		return productName;
	}
	public int getStockOutQuantity()
	{
		return stockOutQuantity;
	}
	public String getPaymentMethod()
	{
		return paymentMethod;
	}	
	public String getPaymentDate()
	{
		return paymentDate;
	}
	
	//mutators
	//modify stock out quantity
	public void setStockOutQuantity(int theStockOutQuantity)
	{
		stockOutQuantity = theStockOutQuantity;
	}
	//modify the payment method
	public void setPaymentMethod(String thePaymentMethod)
	{
		paymentMethod = thePaymentMethod;
	}
	
	//Overloaded constructor for the Order class.
	//Initializes an Order object with specific attributes.
	public Order(String theOrderID, String theCustomerID, String theProductName, int theStockOutQuantity, String thePaymentMethod, String thePaymentDate)
	{
		orderID = theOrderID;
		customerID = theCustomerID;
		productName = theProductName;
		stockOutQuantity = theStockOutQuantity;
		paymentMethod = thePaymentMethod;
		paymentDate = thePaymentDate;	
	}
	
	// Default constructor for the Order class.
	// Initializes an empty order object with default values.
	public Order()
	{
		
	}
	
	// Overrides toString method to provide a formatted string representation of the Order object
	@Override
	public String toString() 
	{
		return orderID + "," + customerID + "," + productName + "," + stockOutQuantity + "," + paymentMethod + "," + paymentDate;
    }
	
	// Reads data from a file and populates an ArrayList of Order objects.
	@Override
	public void readFile(String filePath, ArrayList<Order> dataList)
	{
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
        	String line;
            while ((line = reader.readLine()) != null) 
            {
            	String[] elements = line.split(","); 
            	if (elements.length >= 6) 
            	{
            		String orderID = elements[0];
            		String customerID = elements[1];
            		String productName = elements[2];
            		int stockOutQuantity = Integer.parseInt(elements[3]);
            		String paymentMethod = elements[4];
            		String paymentDate = elements [5];
            		
            		Order order = new Order(orderID, customerID, productName, stockOutQuantity, paymentMethod, paymentDate);
                    dataList.add(order);
            	}
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error reading the file: " + filePath);
        }
	}
	
	// Writes data from an ArrayList of Order objects to a file.
	@Override
	public void writeDataToFile(String orderFilePath, ArrayList<Order> transactionRecord) 
	{
		ArrayList<String> dataArray = new ArrayList<>();
		for (Order order : transactionRecord) 
		{
			dataArray.add(order.toString());
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(orderFilePath))) 
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
	
	//instance method
	//Add new transaction 
	public static void addTransaction(ArrayList<Order> transactionRecord, ArrayList<Product> proArray)
	{
		boolean validInput = true;
		boolean Exit = false;
		do
		{	
			//Handle potential exceptions that may occur during user input and processing
			try
			{
				String continueAddTransaction = "n";
				do
				{
					System.out.println("\n===================== Add Transaction ====================="); 
					String orderID;
			        String customerID;

			        // Validate Order ID input
			        do 
			        {
			        	System.out.println("\n[e/E:exit if do not have transaction to add]");
			            System.out.print("Enter Order ID: ORD00");
			            orderID = input.nextLine();
			            orderID = orderID.toUpperCase();
			            if (orderID.equals("E"))
						{
							Exit = true;
							break;
						}
			            else
			            {
			            	// Check if the input can be parsed as an integer
			            	try 
			            	{
			            		// If parsing is successful, it's a valid integer string
			            		int parsedOrderID = Integer.parseInt(orderID);
			            		// Exit the loop
			            		break; 
			            	} 
			            	catch (NumberFormatException e) 
			            	{
			            		System.out.println("Error! Invalid input.\n");
			            	}
			            }
			            
			        }while (true); // Loop until a valid integer string is provided
			        
			        if(!Exit)
			        {
			        	//Validate Customer ID input
			        	do 
			        	{
			        		System.out.print("Enter Customer ID: CUST0");
			        		customerID = input.nextLine();

			        		// Check if the input can be parsed as an integer
			        		try 
			        		{
			        			// If parsing is successful, it's a valid integer string
			        			int parsedCustomerID = Integer.parseInt(customerID);
			        			break; // Exit the loop
			        		} 
			        		catch (NumberFormatException e) 
			        		{
			        			System.out.println("Error! Invalid input.\n");
			        		}

			        	}while (true); // Loop until a valid integer string is provided

			        	//Validate product name and stock quantity within the acceptable range.
			        	boolean validProductName = false;
			        	String productName;

			        	boolean validStockOutQty = true;
			        	int stockOutQuantity = 0;

			        	System.out.println("\n\t\t -------------- ");
			        	System.out.println("\t\t |  Product:  |");
			        	System.out.println("\t\t -------------- ");
			        	for(Product product : proArray)
			        	{
			        		//Print out the products which are active
			        		if(product.getProductStatus().equals("ACTIVE"))
			        		{
			        			System.out.println("\t" + product.getProductName());
			        	
			        		}
			        	}

			        	do
			        	{
			        		System.out.print("Enter Product Name: ");
			        		productName = input.nextLine();
			        		productName = productName.toUpperCase();
			        		do
			        		{
			        			// Loop through product list to find a matching product name which it's status is active
			        			for(Product product : proArray)
			        			{
			        				if(productName.equals(product.getProductName()) && product.getProductStatus().equals("ACTIVE"))
			        				{
			        					//Print the product's available stock
			        					validProductName = true;
			        					System.out.println("\nAvailable Stock: " + product.getProductQuantity());

			        					System.out.print("Enter Stock Out Quantity: ");
			        					if (input.hasNextInt()) 
			        					{ // Check if the next input is an integer
			        						stockOutQuantity = input.nextInt();

			        						//If the stock out quantity entered is more the the available stock
			        						if(stockOutQuantity > product.getProductQuantity())
			        						{
			        							System.out.println("Error! Product is out of stock!");
			        							validStockOutQty = false;
			        							break;
			        						}
			        						//Ensure the stock-out quantity is not lesser or equals to 0
			        						else if(stockOutQuantity <= 0)
			        						{
			        							System.out.println("Error! Invalid input");
			        							validStockOutQty = false;
			        							break;
			        						}
			        						else
			        						{
			        							//compute new stock left
			        							int newStockLeft = product.getProductQuantity() - stockOutQuantity;
			        							product.setProductQuantity(newStockLeft);
			        							//write the updated stock left to 'Product.txt'
			        							DataWriter<Product> productFileWriter = new Product();
			        							productFileWriter.writeDataToFile(filePath, proArray);
			        							validStockOutQty = true;
			        							break;
			        						}
			        					}
			        					else
			        					{
			        						input.nextLine();
			        						System.out.println("Invalid input! Please enter again.");
			        						validStockOutQty = false;
			        						break;
			        					}
			        				}
			        			}
			        		}while(!validStockOutQty);
			        		if (!validProductName)
			        		{
			        			System.out.println("Error! Product Not Found!");
			        			System.out.println("Please enter again.\n");
			        			validProductName = false;
			        		}

			        	}while(!validProductName);

			        	//validate payment method
			        	boolean validPaymentMethod = true;
			        	String paymentMethod = null;
			        	int paymentMethodChoice;
			        	System.out.println("\n\t\t ------------------- ");
			        	System.out.println("\t\t | Payment Method: |");
			        	System.out.println("\t\t ------------------- ");
			        	System.out.println("\t\t | 1. Credit Card  |");
			        	System.out.println("\t\t | 2. Cash         |");
			        	System.out.println("\t\t | 3. E-wallet     |");
			        	System.out.println("\t\t ------------------- \n");
			        	do
			        	{
			        		input.nextLine();
			        		System.out.print("Enter Payment Method: ");
			        		if (input.hasNextInt()) 
			        		{
			        			paymentMethodChoice = input.nextInt();

			        			switch(paymentMethodChoice)
			        			{
			        				case 1:
			        					paymentMethod = "CREDIT CARD";
			        					validPaymentMethod = true;
			        					break;
			        				case 2:
			        					paymentMethod = "CASH";
			        					validPaymentMethod = true;
			        					break;
			        				case 3:
			        					paymentMethod = "E-WALLET";
			        					validPaymentMethod = true;
			        					break;
			        				default:
			        					System.out.println("Error! Invalid choice!");
					        			System.out.println("Please enter again.\n");
			        					validPaymentMethod = false;
			        					break;
			        			}
			        		}	
			        		else
			        		{
			        			System.out.println("Error! Invalid choice!");
			        			System.out.println("Please enter again.\n");
			        			validPaymentMethod = false;

			        		}
			        	}while(!validPaymentMethod);

			        	//validate date of payment
			        	boolean isValid;
			        	String paymentDate;
			        	input.nextLine();
			        	do
			        	{
			        		System.out.print("Enter Date of Payment (DD-MM-YYYY): ");
			        		paymentDate = input.nextLine();

			        		//Check the format of the date
			        		isValid = Order.dateValidation(paymentDate);
			        		if(isValid)
			        		{
			        			System.out.println("Order added successfully.");
			        			// Create an Order object with the provided details and add it to the list
			        			Order order = new Order(orderID, customerID, productName, stockOutQuantity, paymentMethod, paymentDate);
			        			transactionRecord.add(order);
			        			//write to 'Order.txt'
			        			DataWriter<Order> fileWriter = new Order();
			        			fileWriter.writeDataToFile("Order.txt", transactionRecord);
			        		}
			        		else 
			        		{
			        			System.out.println("\nInvalid date format! Please enter the date in the format DD-MM-YYYY.\n");
			        		}
			        	}while(!isValid);

			        	System.out.print("Do you want to add any new transactions? (y/n) : ");
			        	continueAddTransaction = input.nextLine();
			        }
			      // Continue adding transactions until the user chooses chooses N/n
				}while(continueAddTransaction.equals("y")||continueAddTransaction.equals("Y"));
			}
			// If an InputMismatchException occurs, notify the user and prompt to re-enter
			catch (InputMismatchException e)
            {
                System.out.println("Invalid input! Please enter again.");
                validInput = false;
            }
			// If any other unexpected exception occurs, prompt user to retry
            catch (Exception e)
            {
                System.out.println("Some error occur! Please retry.");
                validInput = false;
            }
		}while(!validInput);
	}	
	
	//Update transactions
	public static void updateTransaction(ArrayList<Order> transactionRecord, ArrayList<Product> proArray)
	{
		boolean validInput = true;
        do
        {
        	input.nextLine();
        	try
        	{
        		System.out.println("\n=================== Update Transaction ====================");
        		System.out.println("  ----------- Search Transaction -----------");

        		String orderIdToUpdate;
        		boolean orderFound = false;
        		
        		do {
        			System.out.print("Enter Order ID: ORD00");
        			orderIdToUpdate = input.nextLine();
        		
        			// Loop through order list to find a matching order using order ID
        			for (Order order : transactionRecord) 
        			{
        				if (orderIdToUpdate.equals(order.getOrderID())) 
        				{
        					orderFound = true;
        					break;
        				}
        			}
        			if(!orderFound)
                    {	
        				
                    	System.out.println("Error! Order not found!\n");
                    	
                    	 
                    }
        		}while(!orderFound);
                	
                if(orderFound)
                {
        		System.out.println("\t\t ------------------------- ");
    			System.out.println("\t\t |  Product in ORD00" + orderIdToUpdate + " :  |");
    			System.out.println("\t\t ------------------------- ");
    			for(Order order : transactionRecord)
    				if(orderIdToUpdate.equals(order.getOrderID()))
    				{
    					System.out.println("\t\t" + order.getProductName());
    				}
                }

        		boolean productFound = false;
        		do
        		{
        			System.out.print("\nEnter Product Name: ");
        			String productNameToUpdate = input.nextLine();
        			productNameToUpdate = productNameToUpdate.toUpperCase();
        			
        			// Loop through product list to find a matching product name
        			for(Product product : proArray)
        				if(productNameToUpdate.equals(product.getProductName()))
        				{
        					// Loop through order list to find a matching order using order ID and product name
        					for(Order order : transactionRecord)
        	        			if(order.getOrderID().equals(orderIdToUpdate)&& order.getProductName().equals(productNameToUpdate))
        	        			{
        	        				int select = 0;
        	        				int shown = 0;
        	        				boolean ctn = true;
        	        				
        	        				do
        	        				{
        	        					System.out.println("\n------------- Update Option --------------");
        	        					System.out.println("1. Update Stock-Out Quantity: ");
        	        					System.out.println("2. Update Payment Method: ");
        	        					System.out.println("3. Exit");
        	        					if (shown == 0)
        	        					{
        	        						System.out.println("Press enter to proceed");
        	        					}
        	        					input.nextLine();
        	        					System.out.print("Enter your choice: ");
        	        					if(input.hasNextInt())
        	        					{
        	        						select = input.nextInt();
        	        				
	        	        				    int num;
	        	        				    Order updateOrder = null;
	        	        				    int newStockOutQuantity = 0;
        					    		
	        					    		switch(select) 
	        					    		{
	        					    			//Update new stock-out quantity for an Order
		        					    		case 1:
		        					    			boolean validNewStockOutQty = true;
		        					    			do
		        					    			{
		        					    				num = Transactions.searchOrderDetails(orderIdToUpdate,productNameToUpdate, transactionRecord);
		        					    				updateOrder = Transactions.searchByOrderDetails(orderIdToUpdate,productNameToUpdate, transactionRecord);
		
		        					    				if(productNameToUpdate.equals(product.getProductName()))
		        					    				{	//If the product exist, it will display the available stock quantity
		        					    					System.out.println("\nCurrent Stock-Out Quantity: " + order.getStockOutQuantity());
		        					    					System.out.println("Available Stock: " + product.getProductQuantity());
		        					    					input.nextLine();
		        					    					System.out.print("Please enter new Stock-Out Quantity: ");
		        					    					if (input.hasNextInt()) 
		        					    					{ // Check if the next input is an integer
		        					    						newStockOutQuantity = input.nextInt();
		
		        					    						//If the stock out quantity entered is more the the available stock
		        					    						if(newStockOutQuantity > product.getProductQuantity())
		        					    						{
		        					    							System.out.println("Error! Product is out of stock!");
		        					    							validNewStockOutQty = false;
		        					    						}
		        					    						else if (newStockOutQuantity <= 0)
		        					    						{
		        					    							System.out.println("Error! Stock out quantity cannot be less than or equal to 0!");
		        					    							validNewStockOutQty = false;
		        					    						}
		        					    						//If the stock out quantity entered is lesser or equal to the available stock
		        					    						else
		        					    						{
		        					    							validNewStockOutQty = true;
		        					    						}
		        					    					}
		        					    					else
		        					    					{
		        					    						System.out.println("Invalid input! Please enter again.");
		        					    						validNewStockOutQty= false;
		        					    					}
		        					    				}
		        					    				
		        					    			}while(!validNewStockOutQty);
		        					    			//compute the new stock left
		        					    			Transactions.computeStockLeft(transactionRecord, proArray, orderIdToUpdate, productNameToUpdate, newStockOutQuantity); 
		        					    			//write the updated stock-out quantity to 'Order.txt'
		        					    			updateOrder.setStockOutQuantity(newStockOutQuantity);
		        					    			transactionRecord.set(num, updateOrder);
		        					    			System.out.println("Stock-Out Quantity successfully updated!");
		        					    			System.out.println("------------------------------------------");
		        					    			DataWriter<Order> orderFileWriter = new Order();
		        					    			orderFileWriter.writeDataToFile(orderFilePath, transactionRecord);
	
		        					    			break;
		        					    		//Update new payment method of an Order
		        					    		case 2: 	     
		        						        	boolean validPaymentMethod = true;
		        						        	String newPaymentMethod = null;
		        						        	int paymentMethodChoice;
		        						        	System.out.println("\n\t\t ------------------- ");
		        						        	System.out.println("\t\t | Payment Method: |");
		        						        	System.out.println("\t\t ------------------- ");
		        						        	System.out.println("\t\t | 1. Credit Card  |");
		        						        	System.out.println("\t\t | 2. Cash         |");
		        						        	System.out.println("\t\t | 3. E-wallet     |");
		        						        	System.out.println("\t\t ------------------- \n");
		        						        	do
		        						        	{
		        						        		input.nextLine();
		        						        		System.out.print("Please enter new payment method: ");
		        						        		//validate new payment method
		        						        		if (input.hasNextInt()) 
		        						        		{
		        						        			paymentMethodChoice = input.nextInt();
                                                                   
		        						        			switch(paymentMethodChoice)
		        						        			{
		        						        				case 1:
		        						        					newPaymentMethod = "CREDIT CARD";
		        						        					validPaymentMethod = true;
		        						        					break;
		        						        				case 2:
		        						        					newPaymentMethod = "CASH";
		        						        					validPaymentMethod = true;
		        						        					break;
		        						        				case 3:
		        						        					newPaymentMethod = "E-WALLET";
		        						        					validPaymentMethod = true;
		        						        					break;
		        						        				default:
		        						        					System.out.println("Error! Invalid choice!");
		        								        			System.out.println("Please enter again.\n");
		        						        					validPaymentMethod = false;
		        						        					break;
		        						        			}
		        						        		}	
		        						        		else
		        						        		{
		        						        			System.out.println("Error! Invalid choice!");
		        						        			System.out.println("Please enter again.\n");
		        						        			validPaymentMethod = false;

		        						        		}
		        						        	}while(!validPaymentMethod);
		
		        					    			num = Transactions.searchOrderDetails(orderIdToUpdate,productNameToUpdate, transactionRecord);
		        					    			updateOrder = Transactions.searchByOrderDetails(orderIdToUpdate,productNameToUpdate, transactionRecord);
		
		        					    			//write the updated payment method to 'Order.txt'
		        					    			updateOrder.setPaymentMethod(newPaymentMethod);
		        					    			transactionRecord.set(num, updateOrder);
		        					    			System.out.println("Payment Method successfully updated!");
		        					    			System.out.println("--------------------------------------");
		        					    			DataWriter<Order> orderFileWriter1 = new Order();
		        					    			orderFileWriter1.writeDataToFile(orderFilePath, transactionRecord);
		
		        					    			break;
		        					    			
		        					    		//return back to the Modification of Order
		        					    		case 3:
		        					    			System.out.println("Return to Modification of Order");
		        					    			return;
		
		        					    		default : 
		        					    			System.out.println("Error! Invalid choice!");
		        					    			break;
		
	        					    		}
        	        					}
        				        		else
        				        		{
        				        			System.out.println("Error! Invalid input!");
        				        			System.out.println("Please enter again.\n");
        				        			
        				        		}
        	        					shown++;
        	        				 }while ((select != 1 || select != 2) && ctn == true);
        	        				 validInput = true;	

        	        			}
        				}
        			if(!productFound)
        			{
        				System.out.println("Product is not found!");
        				productFound = false;
        			}
        			
        		}while(!productFound);
		
        	}
        	// If any other unexpected exception occurs, prompt user to retry
        	catch (Exception e)
        	{
        		System.out.println("Some error occur! Please retry.");
        		validInput =false;
        	}
        }while (!validInput);
	}
	
	//Search transactions and order records
	public static void searchTransactions(ArrayList<Order> transactionRecord, ArrayList<Product> proArray)
	{
		boolean validInput = true;
		do
        {
    		int select = 0;
    		try
        	{
    			do
    			{
    				System.out.println("\n====================== Search Option ======================");   
    				System.out.println("1. Search Order ");
    				System.out.println("2. Search Transaction");
    				System.out.print("Enter your choice: ");
    				if (input.hasNextInt()) 
    				{ // Check if the next input is an integer
    					select = input.nextInt();
    					if (select != 1 && select != 2)
    					{
    						System.out.println("Invalid option! Please enter again.");
    					}
    				}
    				else
    				{
    					System.out.println("Invalid input! Please enter again.");
    				}
    				input.nextLine();
    			}while(select != 1 && select != 2);

    			//Search order records
        		if(select == 1)
        		{

        			double price = 0;
        			double totalPrice = 0;
        			System.out.println("\n---------------------- Search Order -----------------------");
        			boolean orderFound = false;
        			// Validate Order ID input
			        do 
			        {
	        			String orderID;
	        			do
	        			{
	        				System.out.print("Enter Order ID: ORD00");
	        				orderID = input.nextLine();	
	        				// Check if the input can be parsed as an integer
	        				try 
	        				{
	        					// If parsing is successful, it's a valid integer string
	        					int parsedOrderID = Integer.parseInt(orderID);
	        					orderFound = false;
	        					
	        					// Loop through order list to find a matching order using order ID
	        					for (Order order : transactionRecord) 
	        					{
	        						if (orderID.equals(order.getOrderID()))
	        						{
	        							// Set to true if a matching order is found
	        							orderFound = true; 
	        							// Exit the loop
	        							break; 
	        						}
	        					}
	        					//If order not found
	        					if(!orderFound)
	        					{
	        						System.out.println("Error! Order not found!\n");
	        					}

	        				}
	        				//Handles the case where the input is not a valid integer.
	        				catch (NumberFormatException e) 
	        				{
	        					System.out.println("Error! Invalid input.\n");
	        				}
	        			}while (!orderFound); // Loop until a valid integer string is provided
	        			
	        			//Track if header details are already printed
	        			boolean headerPrinted = false; 
	        			
	        			// Loop through order list to find a matching order using order ID
	        			for (Order order : transactionRecord) 
	        			{
	        				if (orderID.equals(order.getOrderID())) 
	        				{
	        					orderFound = true;
	        					if (!headerPrinted) 
	        					{
	        						System.out.println("\n------------------------------------------------------ Order Records: ------------------------------------------------------");
	        						System.out.println("Order ID: ORD00" + order.getOrderID());
	        						System.out.println("Customer ID: CUST0" + order.getCustomerID());
	        						System.out.println("Payment Method: " + order.getPaymentMethod());
	        						System.out.println("Payment Date: " + order.getPaymentDate());

	        						System.out.println("\n--------------------------------------------------- Transaction Details: ---------------------------------------------------");   
	        						// Set to true after printing header
	        						headerPrinted = true; 
	        					}

	        					//compute the total price of an order
	        					totalPrice = Order.computeTotalPrice(transactionRecord, proArray, orderID);
	        					String productname = order.getProductName();
	        					
	        					//Loop through product list to find a matching product using product name
	        					for (Product product: proArray)
	        					{
	        						if (productname.equals(product.getProductName()))
	        						{
	        							//compute price for a each transactions
	        							price = Transactions.computePrice(transactionRecord, proArray, orderID, productname);
	        							System.out.printf("Product: %-54s Quantity: %d \tUnit Price: RM %.2f \tLine Total: RM %.2f \n", 
	        									order.getProductName(), order.getStockOutQuantity(), product.getProductPrice(), price);
	        							System.out.println(" ");
	        						}
	        					}
	        				}

	        			}

	        			if(orderFound)
	        			{
	        				//Print out an Order's total price
	        				System.out.println("----------------------------------------------------------------------------------------------------------------------------");
	        				System.out.printf("Total Price: RM %.2f" , totalPrice);
	        				System.out.println("\n----------------------------------------------------------------------------------------------------------------------------");
	        			}
        			
			        }while(false);

        		}
        		
        		//Search transaction
        		else if(select == 2)
        		{
        			double price;
        			System.out.println("\n-------------------- Search Transaction --------------------");
        			boolean orderFound = false;
        			do
        			{
        				String orderID;
	        			do
	        			{
	        				System.out.print("Enter Order ID: ORD00");
	        				orderID = input.nextLine();
	        				
	        				// Check if the input can be parsed as an integer
	        				try 
	        				{	
	        					orderFound = false;
	        					
	        					// Loop through order list to find a matching order using order ID
	        					for (Order order : transactionRecord) 
	        					{
	        						if (orderID.equals(order.getOrderID()))
	        						{
	        							orderFound = true; // Set the flag to true if a matching order is found
	        							break; // Exit the loop
	        						}
	        					}
	        					//If order ID does not exist, it will display error
	        					if(!orderFound)
	        					{
	        						System.out.println("Error! Order not found!\n");
	        					}

	        				}
	        				//Handles the case where the input is not a valid integer.
	        				catch (NumberFormatException e) 
	        				{
	        					System.out.println("Error! Invalid input.\n");
	        				}
	        			}while (!orderFound); // Loop until a valid integer string is provided
	        			
	        			//If order ID exist, display the products in that particular order.
	        			if(orderFound)
	        			{
	        				System.out.println("\t\t ------------------------- ");
	        				System.out.println("\t\t |  Product in ORD00" + orderID + " :  |");
	        				System.out.println("\t\t ------------------------- ");
	        				for(Order order : transactionRecord)
	        					if(orderID.equals(order.getOrderID()))
	        					{

	        						System.out.println("\t\t" + order.getProductName());
	        					}
	        			}
	        			
	        			boolean productFound = false;
	        			do
	        			{
		        			System.out.print("\nEnter Product Name: ");
		        			String productName = input.nextLine();
		        			productName = productName.toUpperCase();
		        			
			        			productFound = false;
			        			for(Order order : transactionRecord)
			        			{
			        				if(orderID.equals(order.getOrderID()) && productName.equals(order.getProductName()))
			        				{
			        					System.out.println("\n--------------------------------------------------- Transaction Record: ----------------------------------------------------");
			        					System.out.println("Order ID: ORD00" + order.getOrderID());
		
			        					System.out.println("\n--------------------------------------------------- Transaction Details: ---------------------------------------------------");
			        					String productname = order.getProductName();
			        					for (Product product: proArray)
			        						if (productname.equals(product.getProductName()))
			        						{
			        							price = Transactions.computePrice(transactionRecord, proArray, orderID, productName);
			        							System.out.printf("Product: %-54s Quantity: %d \tUnit Price: RM %.2f \tLine Total: RM %.2f \n", 
			        									order.getProductName(), order.getStockOutQuantity(), product.getProductPrice(), price);
			        							System.out.println("----------------------------------------------------------------------------------------------------------------------------");
			        							productFound = true;
			        							break;
			        						}
			        				}
			        			}
			        			if(!productFound)
			        			{
			        				System.out.println("Product is not found!");
			        			}

	        			}while(!productFound);
	        			
	        			
        			}while(false);
        		}
        		validInput = true;
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
	
	//Validate the date
	public static boolean dateValidation(String paymentDate)
	{	
		//Check if date is 'null' or empty
		if (paymentDate.trim().equals(""))
		{
			return true;
		}
		// Date is not 'null' or empty 
		else
		{
			//fixing Date format
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			formatter.setLenient(false); // Set leniency to false
			
		    try 
		    {
				Date parsedDate = formatter.parse(paymentDate);
				int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(parsedDate));
				
				if(year <= 2023)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		    catch (ParseException e) 
		    {
		    	return false;
		    }
		}
	}
	
	//Compute total price of an order
	public static double computeTotalPrice(ArrayList<Order> transactionRecord, ArrayList<Product> proArray, String theOrderID)
	{
		double totalPrice = 0.0; //initialize
	
		for(int i = 0; i < transactionRecord.size(); i++) 
		{
			Order orders = transactionRecord.get(i);
			//compare whether inserted order ID matched with order ID in order Array
			if(theOrderID.equals(orders.getOrderID())) 
			{
				for(int j = 0; j < proArray.size(); j++) 
				{
					Product products = proArray.get(j);
					//compare whether the product name in order Array matched with product name in product Array
					if(orders.getProductName().equals(products.getProductName()))
					{
						//if true compute the price of a each transaction
						double price = orders.getStockOutQuantity() * products.getProductPrice();
						//total up each transaction to get total price of an order
					    totalPrice += price;
					}		
				}
			}
		}
		return totalPrice;	
	}

}