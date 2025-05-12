import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class TMUberRegistered
{
    // These variables are used to generate user account and driver ids
    private static int firstUserAccountID = 900;
    private static int firstDriverId = 700;

    // Generate a new user account id
    public static String generateUserAccountId(ArrayList<User> current)
    {
        return "" + firstUserAccountID + current.size();
    }

    // Generate a new driver id
    public static String generateDriverId(ArrayList<Driver> current)
    {
        return "" + firstDriverId + current.size();
    }

    // Database of Preregistered users
    public static ArrayList<User> loadPreregisteredUsers(String filename) throws IOException // method to load users from file
    {

        ArrayList<User> users = new ArrayList<User>(); // creating an arrayList for users
        File file = new File(filename); // creating File object for filename
        Scanner input = new Scanner(file); // creating scanner object for file
        String name = ""; // initializing name variable
        String address = ""; // initializing address variable
        double wallet = 0; // initializing wallet variable
        while(input.hasNextLine()){ // iterating through each line in file
            String line = input.nextLine(); 
            name = line; // first line holds the name of user, storing in name variable
            address = input.nextLine(); // next line holds the address of user, storing in address variable
            wallet = Double.parseDouble(input.nextLine()); // next line holds the wallet amount, storing in wallet
            users.add(new User(generateUserAccountId(users), name, address, wallet)); // creating user object and adding to users arrayList
            name = ""; // resetting name variable
            address = ""; // resetting address variable
            wallet = 0; // resetting wallet amount
        }
        input.close(); // closing the scanner
        return users; // return users

    }

    // Database of Preregistered users
    public static ArrayList<Driver> loadPreregisteredDrivers(String filename) throws IOException
    {
        ArrayList<Driver> drivers = new ArrayList<Driver>(); // creating an arraylist for drivers
        File file = new File(filename); // creating a File object for filename
        Scanner input = new Scanner(file); // creating a scanner object for file
        String name = ""; // initializing name 
        String carModel = ""; // initializing carModel
        String licensePlate = ""; // initializing licensePlate
        String address = ""; // initializing address
        while(input.hasNextLine()){ // iterating through the file
            String line = input.nextLine();
            name = line; // the first line holds the name of driver, storing in driver variable
            carModel = input.nextLine(); // the next line holds the model of the car, storing in carModel variable
            licensePlate = input.nextLine(); // the next line holds the license plate, storing in licensePlate variable
            address = input.nextLine(); // the next line holds the address of driver, storing in address variable
            drivers.add(new Driver(generateDriverId(drivers), name, carModel, licensePlate, address)); // creating driver object and storing in drivers arraylist
            name = ""; // resetting name variable
            carModel = ""; // resetting carModel variable
            licensePlate = ""; // resetting licensePlate variable
            address = ""; // resetting address variable
        }
        input.close(); // closing the scanner
        return drivers; // returning drivers

    }
}
