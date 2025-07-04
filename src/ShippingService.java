import java.util.HashMap;
import java.util.List;

public class ShippingService {
    private double ratePerGram = 0.1; //Assuming shipping cost is calculated per gram

    public double calulateShippingCosts(List<Shippable> shippables) {
        double shippingCost = 0;
        for (Shippable shippable : shippables) {
            shippingCost += shippable.getWeight() * this.ratePerGram;
        }
        return shippingCost;
    }

    public void printShippingNotice(List<Shippable> shippables) {
        System.out.println("** Shipment notice **");
        HashMap<Shippable, Integer> itemCount = new HashMap<>();
        double totalWeight = 0;
        for (Shippable shippable : shippables) {
            itemCount.put(shippable, itemCount.getOrDefault(shippable, 0) + 1);
            totalWeight += shippable.getWeight()/1000;
        }

        for (Shippable shippable : itemCount.keySet()) {
            System.out.printf("x%d %-15s %dg%n", itemCount.get(shippable), shippable.getName(), (int) shippable.getWeight() * itemCount.get(shippable));
        }
        System.out.printf("Total package weight %.1fkg%n", totalWeight);
        System.out.println();

    }
}
