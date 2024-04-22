import java.util.ArrayList;

public class ServiceCalculator {

    public ServiceCalculator () {

    }

    //method to calculate total time for different service combinations
    public int calculateTotalDuration(ArrayList<Service> serviceCombination) {
        int totalDuration = 0;

        for(Service service : serviceCombination) {
            totalDuration += service.getDuration();
        }
        return totalDuration;
    }

    //method to calculate total price for different service combinations
    public double calculateTotalPrice(ArrayList<Service> serviceCombination) {
        double totalPrice = 0.0;

        for(Service service : serviceCombination) {
            totalPrice += service.getPrice();
        }
        return totalPrice;
    }

}
