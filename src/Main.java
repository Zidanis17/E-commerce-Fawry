import java.time.LocalDate;
import java.util.*;


public class Main {
    //Assuming I don't need to catch errors, I just need to throw them, catching them is easy just don't feel like that's what the challenge wants
    // Some errors are thrown within the function itself
    public static void  checkout(Customer customer, Cart cart) {
        ShippingService shippingService = new ShippingService();

        if(cart.isEmpty()){
            throw new IllegalStateException("Cart is empty");
        }

        double subTotal = 0;
        double total = 0;
        double shippingCost = 0;
        HashMap<Product, Integer> itemCount = new HashMap<>();
        List<Shippable> toBeShipped = new ArrayList<>();
        for(Product item : cart.getCartItems().keySet()){
            if(item instanceof Expirable && ((Expirable)item).isExpired()){
                throw new IllegalStateException( item.getName() + " is expired");
            }

            if(item instanceof Shippable){ //Since it is required in the challenge to only give the shipping service an array of shippables, I have to input the shippable multiple times based on the quantity
                for (int i = 0 ; i < cart.getCartItems().get(item); i++)
                    toBeShipped.add((Shippable)item);
            }


            subTotal += item.getPrice() *cart.getCartItems().get(item);
        }
        if(!toBeShipped.isEmpty()) {
            shippingService.printShippingNotice(toBeShipped);
            shippingCost = shippingService.calulateShippingCosts(toBeShipped);
        }



        total += subTotal + shippingCost;
        customer.deductBalance(total);

        System.out.println("** Checkout receipt **");
        for(Product p : cart.getCartItems().keySet()){ // this extra loop is solely for the reason that I don't repeated prints for the same item, if the customer does cart.addProduct(scratchCard, 2); 2 times I want to see 4 scratch Cards as a whole in the receipt
            System.out.printf("%dx %-15s %.0f%n", cart.getCartItems().get(p), p.getName(), p.getPrice()*cart.getCartItems().get(p));// the Limit on th String -15 so that all prices appear in the same column
        }
        System.out.println("----------------------");
        System.out.println("Subtotal:          "+ subTotal);
        System.out.println("Shipping:          " + shippingCost);
        System.out.println("Amount:            " + total);


    }

    public static void main(String[] args) {

        ExpirableShippableProduct cheese = new ExpirableShippableProduct("Cheese", 100, 10, LocalDate.now().plusDays(2), 200);
        NonExpirableShippableProduct tv = new NonExpirableShippableProduct("TV",  10000, 100, 10000);
        ExpirableShippableProduct biscuits = new ExpirableShippableProduct("biscuits", 100, 10, LocalDate.now().plusDays(4), 200);
        SimpleProduct scratchCard = new SimpleProduct("Scratch Card", 5, 1000);
        Cart cart = new Cart();
        cart.addProduct(cheese, 2);

        cart.addProduct(tv, 1);
        cart.addProduct(biscuits, 1);
        cart.addProduct(scratchCard, 2);
        Customer customer = new Customer("Himothy", 1000000000);

        checkout(customer, cart);
    }
}