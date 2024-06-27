import java.util.ArrayList;

public class Transactions
{
	//Instance Variables
	private ArrayList<Order> transactionRecord;
	private ArrayList<Product> proArray;
   
	//Overloaded constructor for the Transactions class.
	//Initializes a Transactions object with specific attributes.
	public Transactions(ArrayList<Product> theProductArray, ArrayList<Order> theTransactionRecord)
	{
		proArray = theProductArray;
		transactionRecord = theTransactionRecord;
	}
	
	//instance method
	//compute stock left
	public static void computeStockLeft(ArrayList<Order> transactionRecord, ArrayList<Product> proArray, String orderIdToUpdate, String productNameToUpdate, int newStockOut) 
	{
			for(Product product : proArray)
				if(product.getProductName().equals(productNameToUpdate))
				{
					int currentStock = product.getProductQuantity();
					for(Order order : transactionRecord)
						if(order.getOrderID().equals(orderIdToUpdate)&& order.getProductName().equals(productNameToUpdate))
						{
							int stockOutQuantity = order.getStockOutQuantity();
							int newStockLeft;
							if (newStockOut > stockOutQuantity) 
							{
								int stockOut = newStockOut - stockOutQuantity;
						        newStockLeft = currentStock - stockOut;
							}
							else
							{
								int stockOut = stockOutQuantity - newStockOut;
								newStockLeft = currentStock + stockOut;
							}
							
							product.setProductQuantity(newStockLeft);
					        DataWriter<Product> productWriter = new Product();
			                productWriter.writeDataToFile("Product.txt", proArray);
			               
						}
				} 		
    }

	
	//compute price for a each transactions
	public static double computePrice(ArrayList<Order> transactionRecord, ArrayList<Product> proArray, String theOrderID, String theProductName)
	{
		double price = 0.0; //initialize
	
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
					if(orders.getProductName().equals(products.getProductName())&& theProductName.equals(products.getProductName()))
					{
						//if true compute the price of a each transaction
						price = orders.getStockOutQuantity() * products.getProductPrice();
					}		
				}
			}
		}
		return price;	
	}	
	
	//search the position for the transaction
	public static int searchOrderDetails(String theOrderID, String theProductName, ArrayList<Order> transactionRecord)
	{
		for (int i = 0; i < transactionRecord.size(); i++)
		{
			Order order_details = transactionRecord.get(i);
			if(theOrderID.equals(order_details.getOrderID()) && theProductName.equals(order_details.getProductName()))
				return i;
		}
		return -1;
	}
	
	//search whether the transaction exist in the order(using order details by order ID and product Name)
	public static Order searchByOrderDetails(String theOrderID, String theProductName, ArrayList<Order> transactionRecord)  
	{
		for (int i = 0; i < transactionRecord.size(); i++)
		{
			Order order_details = transactionRecord.get(i);
			if(theOrderID.equals(order_details.getOrderID()) && theProductName.equals(order_details.getProductName()))
				return order_details;
		}
		return null;
	}
}