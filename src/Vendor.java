//Vendor class represents information about the vendor details.
//It includes methods for reading and writing vendor data to files,
//adding and updating vendor details, and display vendor list.

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Vendor implements DataReader<Vendor>, DataWriter<Vendor>
{
	// Defines necessary input and file paths for the Stationary Inventory Management System.
		static Scanner input = new Scanner(System.in);
		static String filePath = "Vendor.txt";
		
		//instance variables
		private String id;
		private String name;
		private String contact;
		private String email;
		
		// accessors
		public String getID()
		{
			return id;
		}
		
		public String getName()
		{
			return name;
		}
		
		public String getContact()
		{
			return contact;
		}
		
		public String getEmail()
		{
			return email;
		}
		
		// mutators
		public void setID(String theID) 
		{
			id=theID;
		}
		
		public void setName(String aName)
		{
			name=aName;
		}
		
		public void setContact(String aContact)
		{
			contact=aContact;
		}
		
		public void setEmail(String anEmail)
		{
			email=anEmail;
		}
		
		// Default constructor for the Vendor class.
		// Initializes an empty vendor object with default values.
		public Vendor()
		{
			
		}
		
		//Overloaded constructor for the Vendor class.
		//Initializes an Vendor object with specific attributes.
		public Vendor(String theID, String theName, String theContact, String theEmail)
		{
			ArrayList<Vendor> vendorList = new ArrayList<>();
			id=theID;
			name=theName;
			contact=theContact;
			email=theEmail;
		}
		
		//Initializes an Vendor object with specific attributes.
		public Vendor(String theName, String theContact, String theEmail)
		{
			ArrayList<Vendor> vendorList = new ArrayList<>();
			name=theName;
			contact=theContact;
			email=theEmail;
		}
		
		// Overrides toString method to provide a formatted string representation of the Vendor object.
		@Override
		public String toString() 
		{
	        return id + "," + name + "," + contact + "," + email;
	    }
		
		//Add new vendor to the system.
		public static void addVendor(ArrayList<Vendor> vendorList, int vendorValue)
		{
			// Initialize variables to manage user input and validation
			boolean validInput = true;
			boolean Exit = false;
			String contact, email, name;
			
			do
			{
				// Handle potential exceptions that may occur during user input and processing
				try
				{
					System.out.println("----------------------------------------");
					System.out.println("Add Vendor");
					System.out.println("----------------------------------------");
					
					do
					{
						System.out.println("Enter e/E exit if do not have vendor to add");
						System.out.println("Vendor Name: ");
						name = input.nextLine();
						
						if (name.equalsIgnoreCase("E"))
						{
							Exit = true;
							break;
						}
						
						else
						{
							boolean isValidContact;
							boolean validLength;
							boolean noDash;
							do
							{
								System.out.println("Vendor Contact (enter without dash): ");
								contact = input.nextLine();
									
			                    isValidContact = true;
			                    validLength = true;
								noDash = true;
			                
			                    for (int j  = 0; j < contact.length(); j++)
			                    {
			                    	char c = contact.charAt(j);
			                        
			                        // Check if each character is an alphabet
			                        if (Character.isAlphabetic(c)) 
			                        {
			                        	isValidContact = false;
			                        	System.out.println("Invalid vendor contact! Vendor contact cannot contain alphabet! Please re-enter vendor contact");
			                        	break;
			                        }
			                        else
			                        {
			                        	//Check the contact length valid or not
			                        	if (contact.length() > 11 || contact.length() < 10)
					                    {
			                        		validLength = false;
			                        		isValidContact = false;
			                        		break;
					                    }
					                    
					                    if (contact.contains("-")) 
					                    {
					                        isValidContact = false;
					                        noDash = false;
					                        break;
					                    }
			                        }
			                    }
			                    if (!validLength)
			                    	System.out.println("Invalid vendor contact! Contact length must between 10-11 digits.");
			                    if(!noDash)
			                    	System.out.println("Invalid vendor contact! Contact cannot contain a dash (-). Please re-enter vendor contact.");
							}while(!isValidContact);
							
							boolean isValidEmail;
							do 
							{
								System.out.println("Vendor Email: ");
								email = input.nextLine();

							    // Validate vendor email
							    isValidEmail = true;

							    if (!email.endsWith(".com")) 
							    {
							        isValidEmail = false;
							        System.out.println("Invalid vendor email! Email must end with '.com'. Please re-enter vendor email");
							    }
							} while (!isValidEmail);

							System.out.println("Vendor successfully added!");
							
							// Create a Vendor object with the provided details and add it to the list
							Vendor vendor = new Vendor(Integer.toString(vendorValue), name, contact, email);  
							String amount = Integer.toString(vendorList.size()+1);
							vendor.setID(amount);
							vendorList.add(vendor);
							
							// Write the updated vendor list to 'Vendor.txt'
							DataWriter<Vendor> vendorWriter = new Vendor();
							vendorWriter.writeDataToFile(filePath, vendorList);
							
							// Increment the vendor ID value for the next vendor
							vendorValue++;
			                
						}
					}while(!Exit);  // Continue adding vendors until the user chooses to exit
					
					if (Exit)
					{
						System.out.println("Exit Successfully!");
						break;
					}
					
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
		
		//Update the contact number or email of a specific vendor.
		public static void updateVendor(ArrayList<Vendor> vendorList) 
		{
		    boolean validInput = true;
	        boolean found = false;
		    String vendorIDToSearch, information;
		    do 
		    {
		        try
		        {
		            do 
		            {
		                System.out.print("Type vendor ID to search: ");
		                vendorIDToSearch = input.nextLine();
		               	
		                //Go through vendor by vendor
		                for (Vendor vendor : vendorList) 
		                {
		                    if (vendor.getID().equals(vendorIDToSearch)) 
		                    {
		                        found = true;
		                        System.out.printf("ID: VD00%-6sVendor Name: %-30sContact: %-15s\tEmail: %-15s", vendor.getID(), vendor.getName(), vendor.getContact(), vendor.getEmail());
		                        break;
		                    }
		                }
		                
		                if (!found) 
		                {
		                    System.out.println("Vendor ID not found!");
		                }
		            } while (!found);
		            
		            int select = 0;
		            do 
		            {
			        	System.out.println("\nDo you want to");
			    		System.out.println("1. Update Contact Number");
			    		System.out.println("2. Update Email");
			    		System.out.print("Selection: ");
		
			    	    if (input.hasNextInt()) 
			    	    {
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
	           		}while (select!=1 && select!=2);
		  					
		   			int num;
		   			Vendor vendor = null;
		   			num = searchID(vendorIDToSearch, vendorList);
		   			if (num == -1)
		   				System.out.println("Vendor is not found!");
		   			else 
		   			{
		   			    vendor = vendorList.get(num);
		   				switch (select)
		   				{
		   				case 1:
		   					// Update the vendor's contact number
		   		    	    boolean isValidContact;
							boolean validLength;
							boolean noDash;
							do
							{
								System.out.print("Please enter the new contact number to update of vendor (enter without dash): ");
			   		    	    information = input.nextLine();
									
			                    isValidContact = true;
			                    validLength = true;
								noDash = true;
			                
			                    for (int j  = 0; j < information.length(); j++)
			                    {
			                    	char c = information.charAt(j);
			                        
			                        // Check if each character is an alphabet
			                        if (Character.isAlphabetic(c)) 
			                        {
			                        	isValidContact = false;
			                        	System.out.println("Invalid vendor contact! Vendor contact cannot contain alphabet! Please re-enter vendor contact");
			                        	break;
			                        }
			                        else
			                        {
			                        	if (information.length() > 11 || information.length() < 10)
					                    {
			                        		validLength = false;
			                        		isValidContact = false;
			                        		break;
					                    }
					                    
					                    if (information.contains("-")) 
					                    {
					                        isValidContact = false;
					                        noDash = false;
					                        break;
					                    }
			                        }
			                    }
			                    if (!validLength)
			                    	System.out.println("Invalid vendor contact! Contact length must between 10-11 digits.");
			                    if(!noDash)
			                    	System.out.println("Invalid vendor contact! Contact cannot contain a dash (-). Please re-enter vendor contact.");
							}while(!isValidContact);
		   					vendor.setContact(information);
		   					//vendorList.set(num, vendor);
		   					break;
		   				case 2:
		   					// Update the vendor's email
		   					boolean isValidEmail;
							do 
							{
								System.out.print("Please enter the new e-mail to update of vendor: ");
			   		    	    information = input.nextLine();

							    // Validate vendor email
							    isValidEmail = true;

							    if (!information.endsWith(".com")) 
							    {
							        isValidEmail = false;
							        System.out.println("Invalid vendor email! Email must end with '.com'. Please re-enter vendor email");
							    }
							} while (!isValidEmail);
		   					vendor.setEmail(information);
		   					//vendorList.set(num, vendor);
		   					break;
		   				default:
		   					System.out.println("Error! Invalid option!");
		   					num = -1;
		   					break;
		   					
		   				}
		
		   			if (num == -1)
		    		{
	 					System.out.println("Vendor is not found!");
		   			}
		   			else
		   			{
		   				System.out.println("Vendor information has been updated!");
	    				
		   				// Write the updated vendor information to a file
		   				DataWriter<Vendor> vendorWriter = new Vendor();
		    			vendorWriter.writeDataToFile(filePath, vendorList);
		    		} 
		   		}
		    }
		        catch (Exception e) 
		        {
		            System.out.println("An error occurred: " + e.getMessage());
		        }
		    } while (!validInput);
		}
		
		//Searches for an vendor object by its ID in the vendorList
		public static Vendor searchByID(String theID, ArrayList<Vendor> vendorList)  
		{
			for (int i = 0; i < vendorList.size(); i++)
			{
				Vendor vendor = vendorList.get(i);
				if (theID.equals(vendor.getID()))
					return vendor;
			}
			return null;
		}
		
		//Searches for the index of an Vendor object by its ID in the given list of vendorList.
		public static int searchID(String theID, ArrayList<Vendor> vendorList)  
		{
			for (int i = 0; i < vendorList.size(); i++)
			{
				Vendor vendor = vendorList.get(i);
				if (theID.equals(vendor.getID()))
					return i;
			}
			return -1;
		}
		
		//Remove unwanted value
		public static void removeVendor(ArrayList<Vendor> vendorList)
		{
			boolean validInput = true;
			String vendorIDToSearch;
			
	        do
	        {
	        	try
	        	{
	        		System.out.print("Type vendor ID to delete : V00");
	    			vendorIDToSearch = input.nextLine();
	        		int num;
	        		Vendor vendor = null;
	        		num = searchID(vendorIDToSearch, vendorList);
	        		vendor = searchByID(vendorIDToSearch, vendorList);
	    			
	   				if (num == -1)
	   					System.out.println("Vendor is not found!");
	    			else
	    			{
	    				vendorList.remove(num);
	    				System.out.println("Vendor is successfully deleted!");
	    				DataWriter<Vendor> vendorWriter = new Vendor();
	   		            vendorWriter.writeDataToFile(filePath, vendorList);
	   				}
	   				validInput=true;	
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
	        }while(!validInput); 
		}
		
		//Displays the details of all vendors i vendorList.
		public static void displayVendor(ArrayList<Vendor> vendorList)
		{
			boolean validInput = true;
			do
			{
				try
				{
					// Loop through the vendorList and display vendor information
					for (Vendor vendor : vendorList) 
					{
						System.out.printf("ID : VD00%-6sVendor Name: %-30sContact: %-15s\tEmail: %-15s", vendor.getID(), vendor.getName(), vendor.getContact(), vendor.getEmail());
						System.out.println("");
					}
					validInput=true; // Set validInput to true since the operation was successful
				}
				catch (Exception e)
	            {
	                System.out.println("Some error occur! Please retry.");
	                validInput =false;
	            }
			}while (!validInput);
		}
		
		// Reads data from a file and populates an ArrayList of Vendor objects.
		@Override
		public int readFile(String filePath, ArrayList<Vendor> vendorList)
		{
			try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
			{
		        String line;
		        int count = 1;
		        while ((line = reader.readLine()) != null) 
		        {
		            String[] elements = line.split(","); 
		            if (elements.length >= 4) 
		            {
		            	String id = elements[0];
		                String name = elements[1];
		                String contact = elements[2];
		                String email = elements[3];
		                
		                // Create a new Inventory object and add it to the ArrayList
		                Vendor vendor = new Vendor(id, name, contact, email);
		                vendorList.add(vendor);
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
		
		// Writes data from an ArrayList of Vendor objects to a file.
		@Override
		public void writeDataToFile(String filePath, ArrayList<Vendor> vendorList) 
		{
			// Create an ArrayList to store the data as strings
			ArrayList<String> dataArray = new ArrayList<>();
			for (Vendor vendor : vendorList) 
			{
				// Convert each Inventory object to string representation and add it to the dataArray
				dataArray.add(vendor.toString());
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
	
}