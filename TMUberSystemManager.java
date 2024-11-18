import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/*
 * 
 * This class contains the main logic of the system.
 * 
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 * 
 */
public class TMUberSystemManager
{
  private Map<String, User> users; // declaring a Map with String type key and User type value
  private ArrayList<Driver> drivers; // declaring an arrayList of drivers
  private ArrayList<User> userList; // declaring  an arrayList of users


  private Queue<TMUberService>[] serviceRequests; // declaring an array of queues 
  public double totalRevenue; // declaring total revenue which will be used upon drop offs of rides and deliveries 
  
  // Rates per city block
  private static final double DELIVERYRATE = 1.2;
  private static final double RIDERATE = 1.5;
  
  // Portion of a ride/delivery cost paid to the driver
  private static final double PAYRATE = 0.1;

  private boolean sorted = false; // setting boolean value sorted to false. This will be used when sorting by name and sorting by wallet

  // These variables are used to generate user account and driver ids
  int userAccountId = 900;
  int driverId = 700;

  @SuppressWarnings("unchecked")
  public TMUberSystemManager() // constructor for class
  {
    users = new TreeMap<String, User>(); // initializing TreeMap for users
    drivers = new ArrayList<Driver>(); // initializing arraylist for drivers
    userList = new ArrayList<User>(); // initializing arraylist for users
    serviceRequests = new Queue[4]; // initializing array of 4 queues
    for(int i = 0; i < serviceRequests.length; i++){ // initializing each queue of array with LinkedList
      serviceRequests[i] = new LinkedList<TMUberService>();
    }
    totalRevenue = 0; // initializing totalRevenue to 0
  }



  
  // Generate a new user account id
  private String generateUserAccountId()
  {
    return "" + userAccountId + users.size();
  }
  
  // Generate a new driver id
  private String generateDriverId()
  {
    return "" + driverId + drivers.size();
  }

  // Given user account id, find user in list of users
  public User getUser(String accountId)
  {
    for(User user : users.values()){ // iterating through Map of user values
      if(user.getAccountId().equals(accountId)){ // if accountID matches the user's account id, return user
        return user;
      }
    }
    return null; // else return null
  }
  
  // Check for duplicate user
  private boolean userExists(User user)
  {
    for(User existingUser : users.values()){ // iterating through Map of user values
      if(existingUser.equals(user)){ // if user already exists, return true
        return true;
      }
    }
    return false; // else return false
  }
  
 // Check for duplicate driver
 private boolean driverExists(Driver driver)
 {
   
   for (int i = 0; i < drivers.size(); i++) // going through drivers arraylist
   {
     if (drivers.get(i).equals(driver)) // if driver exists, return true
       return true;
   }
   return false; // else return false
 }
  
 
 // Given a user, check if user ride/delivery request already exists in service requests
 private boolean existingRequest(TMUberService req)
 {   
   for(Queue<TMUberService> queue : serviceRequests){ // iterating through serviceRequests
    for(TMUberService service : queue){ // for service in queue, if service matches request, return true
      if(service.equals(req)){
        return true;
      }
    }
   }
   return false; // else return false
 }
 
  
  // Calculate the cost of a ride or of a delivery based on distance 
  private double getDeliveryCost(int distance)
  {
    return distance * DELIVERYRATE;
  }

  private double getRideCost(int distance)
  {
    return distance * RIDERATE;
  }

  // Go through all drivers and see if one is available
  // Choose the first available driver
  private Driver getAvailableDriver()
  {
    for (int i = 0; i < drivers.size(); i++) // going through drivers arraylist
    {
      Driver driver = drivers.get(i); // setting each index of drivers arraylist to a Driver object
      if (driver.getStatus() == Driver.Status.AVAILABLE) // if the status of driver is available, return driver
        return driver;
    }
    return null; // else return null
  }

  //Print Information (printInfo()) about all registered users in the system
  public void listAllUsers()
  {
    
    System.out.println();
    int index = 1; // index value will be used to list each user, starting from 1

    for(User user : users.values()){ // iterating through the user map values and printing the info for each user. Increment index by 1 after each iteration
      System.out.printf("%-2s. ", index);
      user.printInfo();
      System.out.println();
      index++;
    }
  }

  public void listAllSortedUsers(){ // this method is used for listing all users once sorted (by name or by wallet)
    System.out.println();
    int index = 1; // index value will be used to list each user, starting from 1
    for(User user : userList){ // iterating through the userList, print info of each user and increment index by 1 after iteration
        System.out.printf("%-2s. ", index);
        user.printInfo();
        System.out.println();
        index++;
      }
    }

  // Print Information (printInfo()) about all registered drivers in the system
  public void listAllDrivers()
  {
    System.out.println();
    
    for (int i = 0; i < drivers.size(); i++) // iterating through the drivers arraylist
    {
      int index = i + 1; // index is used to list each driver starting from 1 (increments as i is incremented)
      System.out.printf("%-2s. ", index);
      drivers.get(i).printInfo(); // print info for each driver
      Driver driver = drivers.get(i); // setting Driver object for each driver in arraylist
      TMUberService service = driver.getService(); // Setting the service of driver to a TMUberService object
      if(drivers.get(i).getStatus().equals(Driver.Status.DRIVING)){ // if the status of driver is driving then print the from address and to address of request being completed
        System.out.print("\nFrom: " + service.getFrom() + "   " + "To: " + service.getTo());
      }
      System.out.println(); 
      if(i != drivers.size()-1){ 
        System.out.println();
      }
    }
  }

  // Print Information (printInfo()) about all current service requests
  public void listAllServiceRequests()
  {
    for(int i = 0; i < serviceRequests.length; i++){ // iterating through service requests, i representing the zone
      System.out.println("\nZONE " + i); // print zone number
      System.out.println("======"); // printing the separator for each zone
      Queue<TMUberService> queue = serviceRequests[i]; // getting the queue of service requests for zone
      int index = 1; // index is used to list service requests
      for(TMUberService service : queue){ // iterating through each service in queue
        System.out.println();
        System.out.print(index + ". ------------------------------------------------------------"); // used to separate each request
        service.printInfo(); // print the information of the service
        System.out.println();
        index++; // increment index
      }
    }
  }


  public boolean registerNewUser(String name, String address, double wallet) // method used to register new user
  {

    if (name == null || name.equals("")) // if name is null or empty, throw InvalidUserNameException
    {
      throw new InvalidUserNameException("Invalid User Name");
    }

    if (!CityMap.validAddress(address)) // if address is not valid, throw InvalidAddressException
    {
      throw new InvalidAddressException("Invalid User Address");
    }

    if (wallet < 0) // if wallet amount is less than 0, throw InvalidWalletAmountException
    {
      throw new InvalidWalletAmountException("Invalid Money in Wallet");
    }

    User user = new User(generateUserAccountId(), name, address, wallet); // creating a new user object
    if (userExists(user)) // if user already exists, throw ExistingUserException
    {
      throw new ExistingUserException("User Already Exists in System");
    }
    users.put(user.getAccountId(), user); // else put accountId and user as key and value in users map
    return true;
  }


  public boolean registerNewDriver(String name, String carModel, String carLicencePlate, String address) // method to register new driver
  {

    if (name == null || name.equals("")) // if name is null or empty, throw InvalidDriverNameException
    {
      throw new InvalidDriverNameException("Invalid Driver Name");
    }

    if (carModel == null || carModel.equals("")) // if car model is null or empty, throw InvalidCarModelException
    {
      throw new InvalidCarModelException("Invalid Car Model");
    }

    if (carLicencePlate == null || carLicencePlate.equals("")) // if license plate is null or empty, throw InvalidCarLicensePlateException
    {
      throw new InvalidCarLicensePlateException("Invalid Car Licence Plate");
    }
    if(address == null || address.equals("") || !CityMap.validAddress(address)){ // if address is null or address is empty or address is not valid, throw InvalidAddressException
      throw new InvalidAddressException("Invalid Address");
    }

    Driver driver = new Driver(generateDriverId(), name, carModel, carLicencePlate, address); // creating a new Driver object
    if (driverExists(driver)) // if the driver already exists, throw ExistingDriverException
    {
      throw new ExistingDriverException("Driver Already Exists in System");
    }
    drivers.add(driver); // adding driver to drivers
    return true;
  }


  public boolean requestRide(String accountId, String from, String to) // method used to request a ride
  {

    User user = getUser(accountId); // using getUser to create user object using accountId
    if (user == null) // if user is null, throw InvalidUserNameException
    {
      throw new InvalidUserNameException("User Account Not Found");
    }

    if (!CityMap.validAddress(from)) // if the from address is invalid, throw InvalidAddressException
    {
      throw new InvalidAddressException("Invalid Address");
    }
    if (!CityMap.validAddress(to)) // if the to address is invalid, throw InvalidAddressException
    {
      throw new InvalidAddressException("Invalid Address");
    }

    int distance = CityMap.getDistance(from, to); // getting the distance using from and to addresses (in blocks)

    if (!(distance > 1)) // if the distance is not greater than 1, throw TravelDistanceException
    {
      throw new TravelDistanceException("Insufficient Travel Distance");
    }
    int zone = CityMap.getCityZone(from); // getting the zone of the from address

    double cost = getRideCost(distance); // getting the cost of distance
    if (user.getWallet() < cost) // if the cost is more than the user's wallet, throw InsufficientFundsException
    {
      throw new InsufficientFundsException("Insufficient Funds");
    }

    Driver driver = getAvailableDriver(); // using getAvailableDriver to create driver object
    if (driver == null)  // if driver is null, throw DriverNotAvailableException
    {
      throw new DriverNotAvailableException("No Drivers Available");
    }

    TMUberRide req = new TMUberRide(from, to, user, distance, cost); // creating a TMUberRide object (the request itself)
    

    if (existingRequest(req)) // if the request already exists, throw ExistingRequestException
    {
      throw new ExistingRequestException("User Already Has Ride Request");
    }
    serviceRequests[zone].add(req); // using the zone to add request to the appropriate service requests
    user.addRide(); // incrementing the ride count for user
    return true;
  }

  public boolean requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId) // method used to request delivery
  {

    User user = getUser(accountId); // Using getUser to create user object using accountID
    if (user == null) // if the user is null, throw InvalidUserNameException
    {
      throw new InvalidUserNameException("User Account Not Found");
    }

    if (!CityMap.validAddress(from)) // if the from address is invalid, throw InvalidAddressException
    {
      throw new InvalidAddressException("Invalid Address");
    }
    if (!CityMap.validAddress(to)) // if the to address is invalid, throw InvalidAddressException
    {
      throw new InvalidAddressException("Invalid Address");
    }
    if(restaurant == null || restaurant.length() == 0){
      throw new InvalidRestaurantNameException("Invalid Restaurant");
    }
    if(foodOrderId == null || foodOrderId.length() == 0){
      throw new InvalidOrderIdException("Invalid Food Order ID");
    }
    int zone = CityMap.getCityZone(from); // getting the zone of the from address

    int distance = CityMap.getDistance(from, to); // determining the distance between the from and to addresses

    if (distance == 0) // if distance is 0, throw TravelDistanceException
    {
      throw new TravelDistanceException("Insufficient Travel Distance");
    }

    double cost = getDeliveryCost(distance); // getting the cost of delivery according to distance
    if (user.getWallet() < cost) // if the cost is more than the user's wallet, throw InsufficientFundsException
    {
      throw new InsufficientFundsException("Insufficient Funds");
    }

    Driver driver = getAvailableDriver(); // using getAvailableDriver to create driver object
    if (driver == null)  // if driver is null, throw DriverNotAvailableException
    {
      throw new DriverNotAvailableException("No Drivers Available");
    }
    TMUberDelivery delivery = new TMUberDelivery(from, to, user, distance, cost, restaurant, foodOrderId); // creating a TMUberDelivery object

    if (existingRequest(delivery)) // if the delivery already exists, throw ExistingRequestException
    {
      throw new ExistingRequestException("User Already Has Delivery Request at Restaurant with this Food Order");
    }
    serviceRequests[zone].add(delivery); // using the zone to add request to the appropriate service requests
    user.addDelivery(); // increment the delivery count for user
    return true;
  }



  public boolean cancelServiceRequest(int request, int zone) // method used to cancel a service request
  {

    if(zone < 0 || zone >= serviceRequests.length){ // if zone is less than 0 or greater than the serviceRequests array length, throw InvalidZoneException
      throw new InvalidZoneException("Invalid Zone");
    }
    if(request < 1 || request > serviceRequests[zone].size()){ // if request number is less than 1 or greater than the number of requests in the zone,  throw InvalidRequestException
      throw new InvalidRequestException("Invalid Request in Zone " + zone);
    }

    Iterator<TMUberService> iter = serviceRequests[zone].iterator(); // Using Iterator to iterate through serviceRequests in zone
    int count = 0; // initialize count to 0
    while(iter.hasNext()){ // iterating through the service requests in zone
      TMUberService service = iter.next(); // getting the next service
      if(++count == request){ // increment counter and check if it matches the request number
        iter.remove(); // remove request and break from while loop
        break;
      }
    }
    return true;
  }

  public Driver getDriver(String driverID){ // method to return driver using driverId
    Driver driver = null; // setting driver object to null
    for(Driver d : drivers){ // iterating through drivers arraylist
      if(d.getId().equals(driverID)){ // if the driver id matches, store driver in driver variable and break
        driver = d;
        break;
      }
    }
    if(driver == null){ // if driver remains null, throw DriverNotFoundException
      throw new DriverNotFoundException("Driver Not Found");
    }
    return driver; // return driver
  }

  public void pickup(String driverID){ // method to return pickup using driverId
    Driver driver = null; // setting driver object to null;
    for(Driver d : drivers){ // iterating through drivers arraylist
      if(d.getId().equals(driverID)){  // if id matches, then set d as driver and break out of loop
        driver = d;
        break;
      }
    }
    if(driver == null){ // if driver remains null, throw DriverNotAvailableException
      throw new DriverNotFoundException("Driver Not Found");
    }

    if(driver.getStatus().equals(Driver.Status.DRIVING)){
      throw new DriverNotAvailableException("Driver Not Available");
    }

    String driverAddress = driver.getAddress(); // using the getAddress method of driver object to obtain address
    int cityZone = CityMap.getCityZone(driverAddress); // using the getCityZone method of CityMap to get the zone of driver's address

    if(serviceRequests[cityZone].isEmpty()){ // if there are no requests in the zone, throw a NoServiceREquestsException
      throw new NoServiceRequestsException("\n No Service Request in Zone " + cityZone);
    }

    TMUberService service = serviceRequests[cityZone].peek(); // obtaining the first service request in zone
    if(service.getClass().getName().equals("TMUberRide") || service.getClass().getName().equals("TMUberDelivery")){ // if service is a ride or delivery then do the following below
      driver.setService(service); // give service to driver
      driver.setStatus(Driver.Status.DRIVING); // change driver status to driving
      serviceRequests[cityZone].remove(); // remove the request from the queue
      driver.setAddress(service.getFrom()); // setting driver address to the pickup address
      System.out.println("\nDriver " + driver.getId() + " Picking Up in Zone " + cityZone);
    }
    else{
      throw new InvalidRequestException("Invalid Request #"); // for an invalid service, throw an InvalidRequestException
    }
  }
  


  public void dropOff(String driverID){ // method used to perform a drop off
    Driver driver = null; // setting driver to null
    for(Driver d : drivers){ // iterating through the drivers arraylist
      if(d.getId().equals(driverID)){ // if the driver's id matches then set driver to d and break out of loop
        driver = d;
        break;
      }
    }

    if(driver == null) { // if driver remains null, throw a DriverNotFoundException
      throw new DriverNotFoundException("Driver Not Found");
    }
    if(!driver.getStatus().equals(Driver.Status.DRIVING)){ // if the driver is not driving, throw a DriverNotDrivingException
      throw new DriverNotDrivingException("Driver Not Driving");
    }

    TMUberService service = driver.getService(); // creating a TMUberService object representing the driver's service
    if(service == null) { // if the service is null, throw NoServiceRequestsException
      throw new NoServiceRequestsException("Service Request Not Found for Driver");
    }

    if(service.getClass().getName().equals("TMUberRide")){ // if the service is a ride, updating the total revenue accordingly
      totalRevenue += ((TMUberRide) service).getCost();
    }
    else if(service.getClass().getName().equals("TMUberDelivery")){ // if the service is delivery, updating the total revenue accordingly
      totalRevenue += ((TMUberDelivery) service).getCost();
    }

    driver.pay(service.getCost()*PAYRATE); // paying driver according to the service cost and pay rate
    totalRevenue -= service.getCost()*PAYRATE; // deducting the amount paid to driver from total revenue
    User user = service.getUser(); 
    user.payForService(service.getCost()); // deducting amount of the service from user
    driver.setStatus(Driver.Status.AVAILABLE); // setting the driver's status to available
    driver.setAddress(service.getTo()); // setting driver's address to the drop off address
    int newZone = CityMap.getCityZone(service.getTo()); // getting the zone of the drop off location and updating driver's zone accordingly
    driver.setZone(newZone);

  }

    public void driveTo(String driverID, String address){ // method used to change user's current location / address / zone
      Driver driver = null; // setting driver to null
      for(Driver d : drivers){ // iterating through the drivers arraylist
        if(d.getId().equals(driverID)){ // if the driver's id matches, set driver to d and break out of loop
          driver = d;
          break;
        }
      }
    if(driver == null){ // if driver remains null, throw DriverNotFoundException
      throw new DriverNotFoundException("Driver Not Found");
    }
    if(!driver.getStatus().equals(Driver.Status.AVAILABLE)){ // if the driver is not available, throw DriverNotAvailableException
      throw new DriverNotAvailableException("Driver Not Available for Driving to Address: " + address);
    }
    if(!CityMap.validAddress(address)){ // if the address is invalid, throw an Invalid AddressException
      throw new InvalidAddressException("Invalid Address");
    }

    String priorAddress = driver.getAddress(); // getting driver's previous address
    driver.setAddress(address); // setting driver's new address

    int priorZone = CityMap.getCityZone(priorAddress); // getting the driver's previous zone
    int newZone = CityMap.getCityZone(address); // getting the driver's new zone

    if(priorZone != newZone){ // if the previous and new zones are not the same, update driver's zone
      driver.setZone(newZone);
    }
    System.out.println("Driver " + driver.getId() + " Now in Zone " + newZone);
  }

  private void getUserList(){
    userList.clear(); // clearing the existing userList
    for(User user : users.values()){ // iterating through the users map and adding each user to the userList
      userList.add(user);
    }
  }



  public void sortByUserName() // sorting users by name
  {
    sorted = true; // set sorted to true
    getUserList(); // call the getUserList() method
    Collections.sort(userList, new NameComparator()); // sort the userList by using the NameComparator
    listAllSortedUsers(); // call listAllSortedUsers() method to print out users
  }

  private class NameComparator implements Comparator<User>  // comparator class used to compare names of users
  {
    public int compare(User user1, User user2) // compare method used to compare names
    {
      return user1.getName().compareTo(user2.getName()); 
    }
  }

  public void sortByWallet() // sorting users by wallet amount
  {
    sorted = true; // set sorted to true
    getUserList(); // call the getUserList() method
    Collections.sort(userList, new UserWalletComparator()); // sort the userList by using the UserWalletComparator
    listAllSortedUsers(); // call listAllSortedUsers() method to print out users
  }

  private class UserWalletComparator implements Comparator<User> // comparator class used to compare the wallet amount of users
  {
    public int compare(User user1, User user2) // compare method used to compare 
    {
      if(user1.getWallet() > user2.getWallet()){ // if user1's wallet is greater, return 1
        return 1;
      }
      if(user1.getWallet() < user2.getWallet()){ // if user2's wallet is greater, return -1
        return -1; 
      }
      return 0; // else return 0 if equal
    }
  }


  public void setUsers(ArrayList<User> userList){ // method to use userList arraylist to set users
    users.clear(); // clearing the map of users
    for(User user : userList){ // iterating through the userList
      users.put(user.getAccountId(), user); // using put method to put each user in user map with the Id as the key
    }
  }

  public void setDrivers(ArrayList<Driver> drivers){ // method to use drivers arraylist to set drivers
    this.drivers = drivers;
  }

}

// Below are a set of custom exception classes which extend RunTimeException.
// Each custom exception class below is specific to particular error handling situations
// Each has a constructor which takes in a a custom error message
// calls the super constructor to set message

class InvalidUserNameException extends RuntimeException{
  public InvalidUserNameException(String message){
    super(message);
  }
}

class InvalidAddressException extends RuntimeException{
  public InvalidAddressException(String message){
    super(message);
  }
}

class InvalidWalletAmountException extends RuntimeException{
  public InvalidWalletAmountException(String message){
    super(message);
  }
}

class ExistingUserException extends RuntimeException{
  public ExistingUserException(String message){
    super(message);
  }
}

class InvalidDriverNameException extends RuntimeException{
  public InvalidDriverNameException(String message){
    super(message);
  }
}

class InvalidCarModelException extends RuntimeException{
  public InvalidCarModelException(String message){
    super(message);
  }
}

class InvalidCarLicensePlateException extends RuntimeException{
  public InvalidCarLicensePlateException(String message){
    super(message);
  }
}

class ExistingDriverException extends RuntimeException{
  public ExistingDriverException(String message){
    super(message);
  }
}

class TravelDistanceException extends RuntimeException{
  public TravelDistanceException(String message){
    super(message);
  }
}

class InsufficientFundsException extends RuntimeException{
  public InsufficientFundsException(String message){
    super(message);
  }
}

class DriverNotAvailableException extends RuntimeException{
  public DriverNotAvailableException(String message){
    super(message);
  }
}

class ExistingRequestException extends RuntimeException{
  public ExistingRequestException(String message){
    super(message);
  }
}

class InvalidRequestException extends RuntimeException{
  public InvalidRequestException(String message){
    super(message);
  }
}

class NoServiceRequestsException extends RuntimeException{
  public NoServiceRequestsException(String message){
    super(message);
  }
}

class DriverNotFoundException extends RuntimeException{
  public DriverNotFoundException(String message){
    super(message);
  }
}

class InvalidZoneException extends RuntimeException{
  public InvalidZoneException(String message){
    super(message);
  }
}

class DriverNotDrivingException extends RuntimeException{
  public DriverNotDrivingException(String message){
    super(message);
  }
}

class InvalidRestaurantNameException extends RuntimeException{
  public InvalidRestaurantNameException(String message){
    super(message);
  }
}

class InvalidOrderIdException extends RuntimeException{
  public InvalidOrderIdException(String message){
    super(message);
  }
}