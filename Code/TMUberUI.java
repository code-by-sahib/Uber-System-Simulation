import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringTokenizer;


// Simulation of a Simple Command-line based Uber App 

// This system supports "ride sharing" service and a delivery service

public class TMUberUI
{
  public static void main(String[] args)
  {
    // Create the System Manager - the main system code is in here 

    TMUberSystemManager tmuber = new TMUberSystemManager();
    
    boolean sortedByName = false;
    boolean sortedByWallet = false;
    Scanner scanner = new Scanner(System.in);
    System.out.print(">");

    // Process keyboard actions
    while (scanner.hasNextLine())
    {
      String action = scanner.nextLine();

      if (action == null || action.equals("")) 
      {
        System.out.print("\n>");
        continue;
      }
      // Quit the App
      else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
        return;
      // Print all the registered drivers
      else if (action.equalsIgnoreCase("DRIVERS"))  // List all drivers
      {
        tmuber.listAllDrivers(); 
      }
      else if(action.equalsIgnoreCase("PICKUP")){ // if the action is pickup
        System.out.print("Driver Id: "); // ask for driver id
        String driverID = scanner.nextLine(); 
        try{
          tmuber.pickup(driverID); // call pickup method with the driver id provided
        }
        catch(DriverNotAvailableException exception){ // catch exception if driver is not available
          System.out.println(exception.getMessage());
        }
        catch(DriverNotFoundException exception){ // catch exception if driver is not found
          System.out.println(exception.getMessage());
        }
        catch(NoServiceRequestsException exception){ // catch exception if there are no service requests
          System.out.println(exception.getMessage());
        }
        catch(InvalidRequestException exception){ // catch exception if the request is invalid
          System.out.println(exception.getMessage());
        }
      }
      // Print all the registered users
      else if (action.equalsIgnoreCase("USERS"))  // List all users
      {
        if(!sortedByName && !sortedByWallet){ // if user list is not sorted, call listAllUsers method
          tmuber.listAllUsers(); 
        }
        else if(sortedByName){ // if sorted by name, call sortByUserName method
          tmuber.sortByUserName();
        }
        else if(sortedByWallet){ // if sorted by wallet, call sortByWallet method
          tmuber.sortByWallet();
        }
      }
      else if(action.equalsIgnoreCase("LOADUSERS")){ // if the action is loadusers
        System.out.print("User File: "); // asking for file name
        String filename = scanner.nextLine();
        try{
          ArrayList<User> userList = TMUberRegistered.loadPreregisteredUsers(filename); // calling the loadPreregisteredUsers method with filename and storing in arraylist
          tmuber.setUsers(userList); // call setUsers method with arraylist
          System.out.println("Users Loaded");
        }
        catch(FileNotFoundException exception){ // catch exception if file is not found
          System.out.println("Users File: " + filename + " Not Found");
        }
        catch(IOException exception){
          System.out.println("Error occurred");
          return;
        }
      }
      else if(action.equalsIgnoreCase("LOADDRIVERS")){ // if action is loaddrivers
        System.out.print("Driver File: "); // ask for file name
        String filename = scanner.nextLine();
        try{
          ArrayList<Driver> driverList = TMUberRegistered.loadPreregisteredDrivers(filename); // call loadPreregisteredDrivers method with filename and storing in arraylist
          tmuber.setDrivers(driverList); // call setDrivers method with arrayList
          System.out.println("Drivers Loaded");
        }
        catch(FileNotFoundException exception){ // catch exception if file not found
          System.out.println("Drivers File: " + filename + " Not Found");
        }
        catch(IOException exception){ 
          System.out.println("Error occurred");
          return;
        }
      }
      else if(action.equalsIgnoreCase("DRIVETO")){ // if action is driveto
        System.out.print("Driver Id: "); // ask for driver id
        String driverID = scanner.nextLine();
        System.out.print("Address: "); // ask for address 
        String address = scanner.nextLine();
        try{
        tmuber.driveTo(driverID, address); // call driveTo method with driver's id and the address
        }
        catch(DriverNotFoundException exception){ // catch exception if driver is not found
          System.out.println(exception.getMessage());
        }
        catch(DriverNotAvailableException exception){ // catch exception if driver is not available
          System.out.println(exception.getMessage());
        }
        catch(InvalidAddressException exception){ // catch exception is address is invalid
          System.out.println(exception.getMessage());
        }
      }
      // Print all current ride requests or delivery requests
      else if (action.equalsIgnoreCase("REQUESTS"))  // List all requests
      {
        tmuber.listAllServiceRequests(); 
      }
      // Register a new driver
      else if (action.equalsIgnoreCase("REGDRIVER"))  
      {
        // initializing variables with empty string
        String name = "";
        String carModel = "";
        String license = "";
        String address = "";
        System.out.print("Name: "); // asking user for name
        if (scanner.hasNextLine())
        {
          name = scanner.nextLine(); 
        }
        System.out.print("Car Model: "); // asking user for car model
        if (scanner.hasNextLine())
        {
          carModel = scanner.nextLine();
        }
        System.out.print("Car License: "); // asking user for license
        if (scanner.hasNextLine())
        {
          license = scanner.nextLine();
        }
        System.out.print("Address: "); // asking user for address
        if(scanner.hasNextLine()){
          address = scanner.nextLine();
        }
        try{
        if (tmuber.registerNewDriver(name, carModel, license, address)) // calling registerNewDriver method with variables to create a new driver in system
          System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-15s Address: %-10s", name, carModel, license, address);
        }
        catch(InvalidDriverNameException exception){ // catch exception if name is invalid
          System.out.println(exception.getMessage());
        }
        catch(InvalidCarModelException exception){ // catch exception if car model is invalid
          System.out.println(exception.getMessage());
        }
        catch(InvalidCarLicensePlateException exception){ // catch exception if license is invalid
          System.out.println(exception.getMessage());
        }
        catch(InvalidAddressException exception){ // catch exception if address is invalid
          System.out.println(exception.getMessage());
        }
        catch(ExistingDriverException exception){ // catch exception if driver already exists in system
          System.out.println(exception.getMessage());
        }
      }
      // Register a new user
      else if (action.equalsIgnoreCase("REGUSER")) 
      {
        // initializing variables with empty strings
        String name = "";
        String address = "";
        double wallet = 0.0;
        System.out.print("Name: "); // asking for name
        if (scanner.hasNextLine())
        {
          name = scanner.nextLine();
        }
        System.out.print("Address: "); // asking for address
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        System.out.print("Wallet: "); // asking for wallet amount
        if (scanner.hasNextDouble())
        {
          wallet = scanner.nextDouble();
          scanner.nextLine();
        }
        else{
          scanner.nextLine();
        }
        try{
          if (tmuber.registerNewUser(name, address, wallet)) // calling registerNewUser method with variables
            System.out.printf("User: %-15s Address: %-15s Wallet: %2.2f", name, address, wallet);
        }
        catch(InvalidUserNameException exception){ // catch exception if name is invalid
          System.out.println(exception.getMessage());
        } 
        catch(InvalidAddressException exception){ // catch exception if address is invalid
          System.out.println(exception.getMessage());
        }
        catch(InvalidWalletAmountException exception){ // catch exception if wallet amount is invalid
          System.out.println(exception.getMessage());
        }
        catch(ExistingUserException exception){ // catch exception if user already exists in system
          System.out.println(exception.getMessage());
        }
      }
      // Request a ride
      else if (action.equalsIgnoreCase("REQRIDE")) 
      {
        // initializing variables with empty strings
        String account = "";
        String from = "";
        String to = "";
        System.out.print("User Account Id: "); // ask for user account id
        if (scanner.hasNextLine())
        {
          account = scanner.nextLine();
        }
        System.out.print("From Address: "); // ask for pick up location
        if (scanner.hasNextLine())
        {
          from = scanner.nextLine();
        }
        System.out.print("To Address: "); // ask for destination
        if (scanner.hasNextLine())
        {
          to = scanner.nextLine();
        }
        try{
        if (tmuber.requestRide(account, from, to)) // calling requestRide method with variables
        {
          User user = tmuber.getUser(account); // calling getUser method to create user with account id
          System.out.printf("\nRIDE for: %-15s From: %-15s To: %-15s", user.getName(), from, to);
        }
        }
        catch(InvalidUserNameException exception){ // catch exception if user name is invalid
          System.out.println(exception.getMessage());
        }
        catch(InvalidAddressException exception){ // catch exception if address is invalid
          System.out.println(exception.getMessage());
        }
        catch(TravelDistanceException exception){ // catch exception if the travel distance is invalid
          System.out.println(exception.getMessage());
        }
        catch(InsufficientFundsException exception){ // catch exception if there are insufficient funds
          System.out.println(exception.getMessage());
        }
        catch(DriverNotAvailableException exception){ // catch exception if driver is not available
          System.out.println(exception.getMessage());
        }
        catch(ExistingRequestException exception){ // catch exception if the request already exists
          System.out.println(exception.getMessage());
        }
      }
      // Request a food delivery
      else if (action.equalsIgnoreCase("REQDLVY")) 
      {
        // initializing variables to empty strings
        String account = "";
        String from = "";
        String to = "";
        String restaurant = "";
        String foodOrder = "";
        System.out.print("User Account Id: "); // ask for account id
        if (scanner.hasNextLine())
        {
          account = scanner.nextLine();
        }
        System.out.print("From Address: "); // ask for the address to pick up the delivery from
        if (scanner.hasNextLine())
        {
          from = scanner.nextLine();
        }
        System.out.print("To Address: "); // ask for the address to drop off the delivery to
        if (scanner.hasNextLine())
        {
          to = scanner.nextLine();
        }
        System.out.print("Restaurant: "); // ask for restaurant name
        if (scanner.hasNextLine())
        {
          restaurant = scanner.nextLine();
        }
        System.out.print("Food Order #: "); // ask for order number
        if (scanner.hasNextLine())
        {
          foodOrder = scanner.nextLine();
        }
        try{
        if (tmuber.requestDelivery(account, from, to, restaurant, foodOrder)) // call requestDelivery method with variables
        {
          User user = tmuber.getUser(account); // call getUser method to create User object with account id
          System.out.printf("\nDELIVERY for: %-15s From: %-15s To: %-15s", user.getName(), from, to);  
        }
        }
        catch(InvalidUserNameException exception){ // catch exception if user name is invalid
          System.out.println(exception.getMessage());
        }
        catch(InvalidAddressException exception){ // catch exception if address is invalid
          System.out.println(exception.getMessage());
        }
        catch(TravelDistanceException exception){ // catch exception if the travel distance is invalid
          System.out.println(exception.getMessage());
        }
        catch(InsufficientFundsException exception){ // catch exception if there are insufficient funds for service
          System.out.println(exception.getMessage());
        }
        catch(DriverNotAvailableException exception){ // catch exception if driver is not available
          System.out.println(exception.getMessage());
        }
        catch(ExistingRequestException exception){ // catch exception if request already exists
          System.out.println(exception.getMessage());
        }
        catch(InvalidRestaurantNameException exception){
          System.out.println(exception.getMessage());
        }
        catch(InvalidOrderIdException exception){
          System.out.println(exception.getMessage());
        }
      }
      // Sort users by name
      else if (action.equalsIgnoreCase("SORTBYNAME")) 
      {
        sortedByName = true; // if action is sortbyname, set sortedByName to true and set sortedbywallet to false
        sortedByWallet = false;
        tmuber.sortByUserName(); // call sortByUserName method
      }
      // Sort users by number of ride they have had
      else if (action.equalsIgnoreCase("SORTBYWALLET")) 
      {
        sortedByWallet = true; // if action is sortbywallet, set sortedByWallet to true and set sortedByName to false
        sortedByName = false;
        tmuber.sortByWallet(); // call sortByWallet method
      }
      else if (action.equalsIgnoreCase("CANCELREQ")) 
      {
        int zone = -1; // initializing zone variable
        int request = -1; // initializing request variable
        System.out.print("Zone #: "); // asking for zone number
        if(scanner.hasNextLine()){
          zone = scanner.nextInt();
          scanner.nextLine();
        }
        System.out.print("Request #: "); // asking for request number
        if (scanner.hasNextInt())
        {
          request = scanner.nextInt();
          scanner.nextLine(); 
        }
        try{
        if (tmuber.cancelServiceRequest(request, zone)) // calling cancelServiceRequest method with variables
          System.out.println("Service request #" + request + " in zone " + zone + " cancelled");
        }
        catch(InvalidZoneException exception){ // catch exception if zone number is invalid
          System.out.println(exception.getMessage());
        } 
        catch(InvalidRequestException exception){ // catch exception if request number is invalid
          System.out.println(exception.getMessage());
        }
        catch(InputMismatchException exception){ // catch exception if value entered is not a digit
          System.out.println(exception.getMessage());
        }
      }
      // Drop-off the user or the food delivery to the destination address
      else if (action.equalsIgnoreCase("DROPOFF")) 
      {
        String driverId = "";
        System.out.print("Driver Id: "); // asking for driver id
        if(scanner.hasNextLine()){
          driverId = scanner.nextLine();
        }
        try{
        Driver driver = tmuber.getDriver(driverId); // calling getDriver method to create Driver object with driver id
        if(driver != null){ // does the following if driver is not equal to null
          TMUberService service = driver.getService(); // calling getService method to create TMUberService object
          tmuber.dropOff(driverId); // call dropOff method with driver id
          System.out.println("Driver " + driver.getId() + " Dropping Off");
        }
        }
        catch(DriverNotDrivingException exception){ // catch exception if driver is not driving
          System.out.println(exception.getMessage());
        }
        catch(DriverNotFoundException exception){ // catch exception if driver is not found
          System.out.println(exception.getMessage());
        }
        catch(NoServiceRequestsException exception){ // catch exception if there are no service requests
          System.out.println(exception.getMessage());
        }
      }
      // Get the Current Total Revenues
      else if (action.equalsIgnoreCase("REVENUES")) 
      {
        System.out.println("Total Revenue: " + tmuber.totalRevenue);
      }
      // Unit Test of Valid City Address 
      else if (action.equalsIgnoreCase("ADDR")) 
      {
        String address = ""; // initializing address variable to empty string
        System.out.print("Address: "); // ask for address
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        System.out.print(address);
        if (CityMap.validAddress(address)) // calling validAddress with address variable to check if valid or bad address
          System.out.println("\nValid Address"); 
        else
          System.out.println("\nBad Address"); 
      }
      // Unit Test of CityMap Distance Method
      else if (action.equalsIgnoreCase("DIST")) 
      {
        String from = ""; // initializing from address with empty string
        System.out.print("From: "); // ask for the from address
        if (scanner.hasNextLine())
        {
          from = scanner.nextLine();
        }
        String to = ""; // initializing to address with empty string
        System.out.print("To: "); // ask for the to address
        if (scanner.hasNextLine())
        {
          to = scanner.nextLine();
        }
        System.out.print("\nFrom: " + from + " To: " + to);
        System.out.println("\nDistance: " + CityMap.getDistance(from, to) + " City Blocks"); // using getDistance method with from and to variables to get the city blocks of distance
      }
      
      System.out.print("\n>");
    }
  }
}
