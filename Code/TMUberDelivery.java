/*
 * 
 * This class simulates a food delivery service for a simple Uber app
 * 
 */
public class TMUberDelivery extends TMUberService
{
  public static final String TYPENAME = "DELIVERY";
 
  private String restaurant; 
  private String foodOrderId;
      
  public TMUberDelivery(String from, String to, User user, int distance, double cost, // constructor for TMUberDelivery
                        String restaurant, String order)
  {
    super(from, to, user, distance, cost, TMUberDelivery.TYPENAME); // calling the constructor from superclass to initialize
    this.restaurant = restaurant;
    this.foodOrderId = order;

  }
 
  
  public String getServiceType()
  {
    return TYPENAME;
  }
  
  public String getRestaurant()
  {
    return restaurant;
  }

  public void setRestaurant(String restaurant)
  {
    this.restaurant = restaurant;
  }

  public String getFoodOrderId()
  {
    return foodOrderId;
  }

  public void setFoodOrderId(String foodOrderId)
  {
    this.foodOrderId = foodOrderId;
  }
  /*
   * Two Delivery Requests are equal if they are equal in terms of TMUberServiceRequest
   * and restaurant and food order id
   */
  public boolean equals(Object other)
  {
    // First check to see if other is a Delivery type
    // Cast other to a TMUService reference and check type
    // If not a delivery, return false
    TMUberService req = (TMUberService)other; // casting other to a TMUberService object
    if (!req.getServiceType().equals(TMUberDelivery.TYPENAME)) // if the service types do not match, return false
      return false;
    
    // Now check if this delivery and other delivery are equal
    TMUberDelivery delivery = (TMUberDelivery)other; // casting other to TMUberDelivery object
    return super.equals(other) && delivery.getRestaurant().equalsIgnoreCase(restaurant) &&  // if deliveries match then return true. Otherwise return false
                                  delivery.getFoodOrderId().equalsIgnoreCase(foodOrderId);
  }
  /*
   * Print Information about a Delivery Request
   */
  public void printInfo()
  {
    super.printInfo();
    System.out.printf("\nRestaurant: %-9s Food Order #: %-3s", restaurant, foodOrderId); 
  }
}
