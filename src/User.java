import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//User class represents a user in the system.
public class User implements InfoReader<User>, DataWriter<User>
{
	static Scanner sc = new Scanner(System.in);
	static String adminFilePath = "Admin.txt";
	static String empFilePath = "Employee.txt";
	
	//instance variable
	private String name;
	private String ic;
	private String username;
	private String password;
	private ArrayList<User>userList;
	
	//accessors
	public String getName()
	{
		return name;
	}
	public String getIC()
	{
		return ic;
	}
	public String getUsername()
	{
		return username;
	}
	public String getPassword()
	{
		return password;
	}
	
	//create arraylist
	public ArrayList<User> getUserList()
	{
		return userList;
	}
	
	//mutator / set method
	public void setName(String aName)
	{
		name = aName;
	}
	public void setIC(String aIC)
	{
		ic = aIC;
	}
	public void setUsername(String aUsername)
	{
		username = aUsername;
	}
	public void setPassword(String aPassword)
	{
		password = aPassword;
	}
	
	//Constructors
	public User(String theName,String theIC,String theUsername,String thePassword)
	{
		name = theName;
		ic = theIC;
		username = theUsername;
		password = thePassword;
	}
	public User(String theUsername,String thePassword)
	{
		username = theUsername;
		password = thePassword;
	}
	public User()
	{
		super();
	}

	//Overrides toString method to provide a formatted string representation of the User object.
	@Override
	public String toString() 
	{
		return name + "," + ic + "," + username + "," + password;
    }
	
	//Reads data from file and populates an ArrayList of User objects.
	@Override
	public void readFile(String filePath, ArrayList<User> userArray)
	{
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
        	String line;
            while ((line = reader.readLine()) != null) 
            {
            	String[] elements = line.split(","); 
            	if (elements.length >= 4) 
            	{
            		String name = elements[0];
                    String ic = elements[1];
                    String username = elements[2];
                    String password = elements[3];
                    User user = new User(name, ic, username, password);
                    userArray.add(user);
            	}
            }

        } 
        catch (IOException e) 
        {
            System.out.println("Error reading the file: " + filePath);
        }
	}
	
	//Writes data from an ArrayList of User objects to file.
	@Override
	public void writeDataToFile(String filePath, ArrayList<User> userList) 
	{
		ArrayList<String> dataArray = new ArrayList<>();
		for (User user : userList) 
		{
			dataArray.add(user.toString());
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
	
	//Handles the user login process.
	public static boolean login(String choice, ArrayList<User>userArray1, ArrayList<User>userArray2)
	{
		boolean count = true;
		int attempts = 0;
		do
		{			
			System.out.println("===========================================================");
			System.out.println("                            Login                          ");
			System.out.println("===========================================================");
			System.out.println("Enter username    (Eg: Lim_1212 | no space allow) : ");
			String username = sc.nextLine();
			System.out.println("Enter password (Eg : 0123456789 | no space allow) : ");
			String password = sc.nextLine();
			boolean userFounded = false;
			ArrayList<User>userArray = new ArrayList<User>();
			
			// Select admin or employee user list based on choice
			if (choice.equals("1"))
				userArray = userArray1;
			else
				userArray = userArray2;
			
			// Iterate through the user list to find a matching username and password
			for(int i=0;i< userArray.size();i++)
			{
				User users = userArray.get(i);
				if(users.getUsername().equals(username) && users.getPassword().equals(password))
				{
					System.out.println("Login succefully!!!");
					userFounded = true;
					count = false;
					break;
				}
			}
			if(!userFounded)
			{
				System.out.println("User not founded, username/password incorrect.");
				attempts++;
				if(attempts>=3)
				{
					System.out.println("Maximum 3 times of login attempts.You can't insert again.Program exiting...");
					return false;
				}
			}	
		}while(count);
		return true;
	}
	
	//Handles the password reset process for admin and employee users.
	public static boolean forgetPassword(ArrayList<User>admArray, ArrayList<User>empArray)
	{
		boolean validIC = true;
		boolean validICLength = true;
		System.out.println("===========================================================");
		System.out.println("              Reset username or/and password               ");
		System.out.println("===========================================================");
		System.out.println("Please enter your account name and IC number as verification");
		System.out.println("Role");
		System.out.println("1. Admin");
		System.out.println("2. Employee");
		
		boolean validRoleInput = true;
		boolean validUpdInput = true;
		do
		{
		System.out.print("Please enter your role: ");
		String role = sc.nextLine();
		
		// Check if the entered role is valid (1 for Admin, 2 for Employee)
		if (role.equals("1") || role.equals("2"))
		{
			int j = 0;
			ArrayList<User> array = new ArrayList<User>();
			String filePath;
			
			// Determine the ArrayList and file path based on the selected role
			if (role.equals("1"))
			{
				array = admArray;
				filePath = adminFilePath;
			}
							
			else
			{
				array = empArray;
				filePath = empFilePath;
			}
			do
			{
				System.out.print("Enter your name: ");
				String name = sc.nextLine();
				name = name.toUpperCase();
				
				// Iterate through the user array to find a matching user
				for (User user: array)
				{
					if (name.equals(user.getName()))
					{
						String ic = null;
						do
						{
							System.out.print("Enter your IC number (Eg: 012345678901): ");
							ic = sc.nextLine();
							
							// Check if the IC number has the correct length
							if (ic.length() != 12)
							{
								System.out.println("Invalid length of IC number! Please re-enter IC number!");
								validICLength = false;
							}
							else
								validICLength = true;
							
							// Check if the IC number consists of digits only
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
							
							// Display an error message if the IC number is invalid
							if(!validIC)
							{
									System.out.println("IC number should only consist of digit! Please re-enter IC number!");
							}
						} while (!validIC || !validICLength);
						
						// If the IC matches, proceed to update the username and/or password
						if (ic.equals(user.getIC()))
						{
							do
							{
								System.out.println("Update username password");
								System.out.println("1. Username");
								System.out.println("2. Password");
								System.out.println("3. Both");
								System.out.print("Please enter your choice: ");
								String choice = sc.nextLine();
								if (choice.equals("1"))
								{
									System.out.print("Please enter your new username: ");
									String newUsername = sc.nextLine();
									user.setUsername(newUsername);
									validUpdInput = true;
								}
								else if (choice.equals("2"))
								{
									System.out.print("Please enter your new passsword: ");
									String newPassword = sc.nextLine();
									user.setPassword(newPassword);
									validUpdInput = true;
								}
								else if (choice.equals("3"))
								{
									System.out.print("Please enter your new username: ");
									String newUsername = sc.nextLine();
									user.setUsername(newUsername);
									System.out.print("Please enter your new passsword: ");
									String newPassword = sc.nextLine();
									user.setPassword(newPassword);
									validUpdInput = true;
								}
								else
								{
									System.out.println("Error! Invalid input! Please enter again!");
									validUpdInput = false;
								}
									
							} while (!validUpdInput);
							
							// Write the updated user data to the file
							DataWriter<User> userWriter = new User();
							userWriter.writeDataToFile(filePath, array);
							System.out.println("You have successfully update your account information!");
							return true;
						}
					}
				}
				System.out.println("Error! Invalid name/IC! Please enter again!");
				j++;
				
			} while(j < 3);
					
            // Display an error message and exit if the maximum validation attempts are reached
			if (j == 3)
			{
				System.out.println("Validate error!");
				return false;
			}
		}
		else
		{
			System.out.println("Error! Invalid input! Please enter again!");
			validRoleInput = false;
		}
		} while (!validRoleInput);
		return true;
	}

}