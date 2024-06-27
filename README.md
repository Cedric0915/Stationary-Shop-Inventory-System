# Stationary-Shop-Inventory-System
## Introduction: 
- This Java project mainly performs a stationary inventory system for a shop. In this project, there will be two types of users which are administrator and employee that can perform different tasks according to their job scope. When first enter the project, user will be required to login or register. After doing this, the main menu with different scope to be modify will be shown, user can choose one scope to modify and exit the modify page when he/she choose to exit to return to the main menu page. To close this system, the logged in user will be required to return to main menu page and choose to log out option to close this system.

> [!NOTE]
> System users: Administrator and employee
> Administrator -- responsible for managing and overseeing various aspects of this system.
> Employee -- worker who mainly operates in the stationary shop every day.

### 1.	Login & Register
[Users: Login -- administrator, employee	Register -- employee]
The purpose of this functionality is to ensure the security of the inventory system. By having this functionality, the user can only use this system when it login or registers in this system. Hence, unauthorized access can be prevented and the content in this system will not be maliciously changed or modified. By including different access level, for the administrator and employee, we can decide the scope of the functionality based on their role. For instance, if a product would like to be deleted, this action should be done by the administrator but not any employee. In this system, the user will be given choices of login based on role or register. If login is chosen, the user will be given three attempts. If a user failed to login after three attempts, the system will be closed to prevent an infinite loop. For registration, the new user which is the new employee can register a new account to use this system. If the IC number key in is exist in the employee file, the system may give another selection for forget username and password to the user.

### 2.	Forget username or password
[Users: administrator, employee]
The purpose for this feature is to let the user which has an account but accidentally forget their username and/or password validate their identity by key in their name and IC number in order to change their username and password. By having this feature, users can have a chance to change their account information rather than create a new account. This function will be used either by selecting the options in the login or register page or during the registration process, if the IC key in by user is exist, the system will prompt them whether they forget their username and password or not. 

### 3.	Manage product, transaction and vendor
[Users: administrator, employee]
The purpose of this feature is to manage the product in the inventory system. The functions of managing products, transactions and vendors include adding new products, updating products, transactions, and vendors information, and deleting products and vendors. Hence, when a user tries to retrieve product, transaction and vendor data, the accuracy of data can be guaranteed due to the presence of unique identifiers which are product ID, transaction ID, and vendor ID. Besides, the product, vendor name is all stored in text file in capital letter. When the user input the name of the product and vendors, the alphabets will be changed to upper case in order to find the information in the data text file. Hence, both lowercase letters and capital letters can be used to find the information desired to be get by the user. For the update function, it is mainly use in updating information such as vendor contact number, product minimum quantity level, current stock remains in product, product price, stock in and out amount and so on in order to ensure that the information inside the text files is timely and adjust according to situation. For deleting functionality in product, it will only change the status of product to inactive as the user may need to retrieve old transaction and inventory records. By having this product status, the information of product can still be retrieved although it is deleted (inactive). After done using the functionality chosen, the user will be redirected to the modification page that contains other functionalities until they click exit to return to either admin or employee’s main menu page. 

### 4.	Display low stock product
[Users: administrator, employee]
This functionality will be displayed when a user successfully login his/her account in order to notify about the alert of product may running out of stock. This helps to remind the user to arrange for re-stock process as soon as possible. By having this, customer dissatisfaction due to lack of product stock can be avoided. 

### 5.	Search function
[Users: administrator, employee]
This functionality is mainly used in finding products, vendor, transaction and inventory. It plays a vital role especially when the user wants to modify certain information of the data. The purpose of this feature is to let users search for a particular product by typing in the. By having this feature, the user can get the information in the text files of the inventory system without reading line by line to find certain products. In this feature, the user can choose to search the desired object by key in different information such as ID or name. By having the different scope, it will be easier for the user to find the desired object rather than only depending on the name as the user may accidentally remember wrong the name of desired object to be found.

### 6.	Stock-in transaction
[Users: employee]
The purpose of this functionality is to record the stock in product information such as inventory ID, product ID, product quantity, product price, vendor ID, stock in date and so on. By having this functionality, the user can trace back the record of inventory according to time, product or vendor. This may help to do the accounting process to check whether the outgoing money is correct or not.

### 7.	Stock-out transaction
[Users: employee]
The purpose of this feature is to record the stock out product information such as order ID, product ID, product quantity, product price, customer ID, stock out date and so on. By having this functionality, the user can trace back the record of transaction with customer according to order ID. This may help to do the accounting process to check the incoming money is correct. Besides, it can also act as evidence if the customers have any problem or issue according to product price or quantity.

### 8.	Compute total price
[User: administrator]
The purpose of this functionality is to use it when the administrator wants to search for a particular order record. This feature will help to find the total price of the order by adding up the price of the transactions that are having the same order ID. By having this feature, the human error of calculating can be reduced and the time to retrieve a total price of the order can be decreased. Hence, it can give convenience to the administrator.

### 9.	Compute stock left
[User: employee]
The purpose of this feature is to calculate the stock left of product when a new transaction is being added to ensure that the available product sock information is timely and accurate. It can also act as evidence when there is any incompatible information during the accounting process.
