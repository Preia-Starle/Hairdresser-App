import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Hairsalon_GUI {

    //GUI elements
    private JFrame frame;
    private JComboBox<String> serviceMenu;
    private JButton addToCartButton;
    private JButton removeFromCartButton;
    private JButton calculateButton;
    private JLabel totalTimeLabel;
    private JLabel totalPriceLabel;

    //JList for displaying the cart
    private JList<String> cartList;

    //DefaultListModel to update JList dynamically
    private DefaultListModel<String> cartListModel;

    //map service names to Service objects
    private HashMap<String, Service> serviceMap;

    ServiceCalculator serviceCalculator;
    ArrayList<Service> serviceCombination;
    MemoCache memoCache;
    PerformanceTester performanceTest;

    //constructor
    public Hairsalon_GUI() {
        serviceCalculator = new ServiceCalculator();
        serviceCombination = new ArrayList<>();
        memoCache = new MemoCache();
        performanceTest = new PerformanceTester();
        //initialize the service map with services
        serviceMap = new HashMap<>();
        serviceMap.put("Haircut (Short Hair)", new Service("Haircut (Short Hair)", 30, 500.0));
        serviceMap.put("Haircut (Long Hair)", new Service("Haircut (Long Hair)", 50, 700.0));
        serviceMap.put("Color (Short Hair)", new Service("Color (Short Hair)", 75, 950.0));
        serviceMap.put("Color (Long Hair)", new Service("Color (Long Hair)", 90, 1250.0));
        serviceMap.put("Styling", new Service("Styling", 45, 750.0));
        serviceMap.put("Treatment", new Service("Treatment", 30, 650.0));
        serviceMap.put("Extensions", new Service("Extensions", 120, 1450.0));
        //initialize the cartListModel for the JList
        cartListModel = new DefaultListModel<>();
    }


    public void createAndShowGUI() {
        frame = new JFrame("Hairdresser App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //initialize and add components
        initializeComponents();

        //set layout and add components to the frame
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(serviceMenu);
        frame.add(addToCartButton);
        frame.add(removeFromCartButton);
        frame.add(calculateButton);
        frame.add(totalTimeLabel);
        frame.add(totalPriceLabel);
        frame.add(new JScrollPane(cartList));  //add JScrollPane for the JList

        //set frame properties
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }



    public void handleAddToCart(String selectedServiceName) {
        //retrieve the corresponding Service object from the service map
        Service selectedService = this.serviceMap.get(selectedServiceName);
        //add it to the service combination array
        serviceCombination.add(selectedService);
        //update UI
        updateCartList();
    }

    public void handleRemoveFromCart(String selectedServiceName) {
        //retrieve the corresponding Service object from the service map
        Service selectedService = this.serviceMap.get(selectedServiceName);
        //remove it from the service combination array
        serviceCombination.remove(selectedService);
        //update UI
        updateCartList();

    }

    public void performCalculationWithCaching(String serviceCombinationKey) {
        //check if the result is in the cache
        if (memoCache.containsKey(serviceCombinationKey)) {
            //retrieve if yes and update labels with retrieved result
            updateLabels(memoCache.getValue(serviceCombinationKey));
        } else {
            //if not calculate
            Result newResult = updateResult();
            //save to cache
            memoCache.addToCache(serviceCombinationKey, newResult);
            //update labels with calculated result
            updateLabels(newResult);
        }
    }

    //update GUI labels
    private void updateLabels(Result result) {
        SwingUtilities.invokeLater(() -> {
            //update labels
            totalTimeLabel.setText("Total Time: " + result.getTotalTime());
            totalPriceLabel.setText("Total Price: " + result.getTotalPrice());
        });
    }

    //update the GUI for displaying items in the cart
    private void updateCartList() {
        SwingUtilities.invokeLater(() -> {
            //clear the cartListModel and add the updated list of services
            cartListModel.clear();
            for (Service service : serviceCombination) {
                cartListModel.addElement(service.getServiceName());
            }
        });
    }

    //perform calculations and return result object with total duration and total price for selected service combination
    public Result updateResult() {
        //perform the necessary calculations using ServiceCalculator
        int resultDuration = serviceCalculator.calculateTotalDuration(serviceCombination);
        double resultPrice = serviceCalculator.calculateTotalPrice(serviceCombination);
        //construct a new result object with total duration and price
        Result newResult = new Result(resultDuration, resultPrice);
        return newResult;
    }

    private void initializeComponents() {
        //list of services to be displayed to user
        String[] services = {"Haircut (Short Hair)", "Haircut (Long Hair)", "Color (Short Hair)", "Color (Long Hair)", "Styling", "Treatment", "Extensions"};
        serviceMenu = new JComboBox<>(services);
        //initialize the cartList with the cartListModel
        cartList = new JList<>(cartListModel);

        addToCartButton = new JButton("Add to Cart");
        removeFromCartButton = new JButton("Remove from Cart");
        totalTimeLabel = new JLabel("Total Time: ");
        totalPriceLabel = new JLabel("Total Price: ");
        calculateButton = new JButton("Calculate");

        //add action listeners to the buttons
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //handle the "Add to Cart" button click
                //get the selected service from the menu
                String selectedService = (String) serviceMenu.getSelectedItem();
                //update serviceCombination and UI based on selectedService
                handleAddToCart(selectedService);
            }
        });

        removeFromCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //handle the "Remove from Cart" button click
                //get the selected service from the cart
                String selectedService = (String) cartList.getSelectedValue();
                //update serviceCombination and UI based on selectedService
                handleRemoveFromCart(selectedService);
            }
        });


        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //handle the "Calculate" button click
                //generate key for current serviceCombination
                String serviceCombinationKey = memoCache.generateKey(serviceCombination);
                //perform the necessary calculations and update labels
                long totalTime = PerformanceTester.runPerformanceTest(() -> {
                    performCalculationWithCaching(serviceCombinationKey);
                });

                System.out.println("Total execution: " + totalTime + " nanoseconds");
            }
        });
    }
}

