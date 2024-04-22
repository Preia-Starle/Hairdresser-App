public class Result {

    private double totalTime;

    private double totalPrice;

    public Result() {

    }

    public Result(double totalTime, double totalPrice) {
        this.totalTime = totalTime;
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getTotalTime() {
        return totalTime;
    }
}
