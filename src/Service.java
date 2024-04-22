import java.util.ArrayList;

public class Service implements Comparable<Service> {

    private String serviceName;
    private int duration;
    private double price;

    public Service() {

    }

    public Service(String serviceName, int duration, double price) {
        this.serviceName = serviceName;
        this.duration = duration;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public int getDuration() {
        return duration;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public int compareTo(Service other) {
        //compare based on the name of the service
        return this.serviceName.compareTo(other.serviceName);
    }

}
