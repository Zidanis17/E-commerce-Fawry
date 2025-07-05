import java.time.LocalDate;
import java.util.*;


public class Main {
    // Assuming I don't need to catch errors, I just need to throw them, catching them is easy just don't feel like that's what the challenge wants
    // Some errors are thrown within the function itself
    // I just caught the errors while typing up some test cases
    // Changed the output to 1 decimal point because this is a receipt, prices don't always have to be in int format
    // added a comment in the function below with the code change to the print to match the output in the pdf if needed
    public static void checkout(Customer customer, Cart cart) {
        ShippingService shippingService = new ShippingService();

        if (cart.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        double subTotal = 0;
        double total = 0;
        double shippingCost = 0;
        HashMap<Product, Integer> itemCount = new HashMap<>();
        List<Shippable> toBeShipped = new ArrayList<>();
        for (Product item : cart.getCartItems().keySet()) {
            if (item instanceof Expirable && ((Expirable) item).isExpired()) {
                throw new IllegalStateException(item.getName() + " is expired");
            }

            if (item instanceof Shippable) { //Since it is required in the challenge to only give the shipping service an array of shippables, I have to input the shippable multiple times based on the quantity
                for (int i = 0; i < cart.getCartItems().get(item); i++)
                    toBeShipped.add((Shippable) item);
            }


            subTotal += item.getPrice() * cart.getCartItems().get(item);
        }
        if (!toBeShipped.isEmpty()) {
            shippingService.printShippingNotice(toBeShipped);
            shippingCost = shippingService.calculateShippingCosts(toBeShipped);
        }


        total += subTotal + shippingCost;
        customer.deductBalance(total);

        System.out.println("** Checkout receipt **");
        for (Product p : cart.getCartItems().keySet()) { // this extra loop is solely for the reason that I don't repeated prints for the same item, if the customer does cart.addProduct(scratchCard, 2); 2 times I want to see 4 scratch Cards as a whole in the receipt
            System.out.printf("%dx %-15s %.0f%n", cart.getCartItems().get(p), p.getName(), p.getPrice() * cart.getCartItems().get(p));// the Limit on th String -15 so that all prices appear in the same column
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal:          %.1f%n", subTotal);
        System.out.printf("Shipping:          %.1f%n", shippingCost);
        System.out.printf("Amount:            %.1f%n", total);
        System.out.printf("Customer balance:  %.1f%n", customer.getBalance());

// change to exactly match console in example if needed
//        System.out.println("----------------------");
//        System.out.printf("Subtotal:          %.0f%n", subTotal);
//        System.out.printf("Shipping:          %.0f%n", shippingCost);
//        System.out.printf("Amount:            %.0f%n", total);
//        System.out.printf("Customer balance:  %.0f%n", customer.getBalance());

    }

    //Assuming that creating to instances with the same name even is still a different product since it is a different class
    public static void main(String[] args) {

        // TEST 1: Normal successful example provided in challenge
        // Shipping price will be different since a different rate per gram is used
        // rate per gram is not provided so I am assuming this is fine, if you round up it will be the same I just chose not to round for more accuracy

        try {
            // Create products
            ExpirableShippableProduct cheese = new ExpirableShippableProduct("Cheese", 100, 10, LocalDate.now().plusDays(5), 200);
            ExpirableShippableProduct biscuits = new ExpirableShippableProduct("Biscuits", 150, 20, LocalDate.now().plusDays(7), 700);


            // Create customer with sufficient balance
            Customer customer = new Customer("Himothy", 1000);

            // Create cart and add products
            Cart cart = new Cart();
            cart.addProduct(cheese, 2);
            cart.addProduct(biscuits, 1);

            System.out.println("Customer balance before checkout:" + customer.getBalance());
            System.out.println();

            // Checkout
            checkout(customer, cart);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }


        // TEST 2: Empty cart error handling
//        try {
//            Customer customer = new Customer("Himothy", 500);
//            Cart cart = new Cart(); // Empty cart
//
//            checkout(customer, cart);
//
//        } catch (IllegalStateException e) {
//            System.out.println("error caught: " + e.getMessage());
//        }


        // TEST 3: Insufficient funds error handling
//        try {
//            ExpirableShippableProduct cheese = new ExpirableShippableProduct("Cheese", 100, 10, LocalDate.now().plusDays(5), 200);
//            NonExpirableShippableProduct tv = new NonExpirableShippableProduct("TV", 10000, 5, 5000);
//
//            // Customer with insufficient balance
//            Customer customer = new Customer("Not Himothy", 50);
//
//            Cart cart = new Cart();
//            cart.addProduct(cheese, 1);
//            cart.addProduct(tv, 1);
//
//            System.out.println("Customer balance: $" + customer.getBalance());
//            System.out.println("Cart total would be: $" + (100 + 10000 + 52)); // Including shipping
//
//            checkout(customer, cart);
//
//        } catch (IllegalArgumentException e) {
//            System.out.println("✓ Expected error caught: " + e.getMessage());
//        }

        // TEST 4: Expired product error handling

//        try {
//            // Create expired product
//            ExpirableShippableProduct expiredCheese = new ExpirableShippableProduct("Cheese", 50, 10, LocalDate.now().minusDays(1), 200);
//            SimpleProduct scratchCard = new SimpleProduct("Scratch Card", 5, 1000);
//
//            Customer customer = new Customer("Himothy", 500);
//
//            Cart cart = new Cart();
//            cart.addProduct(expiredCheese, 1); //adding expired product
//            cart.addProduct(scratchCard, 1);
//
//
//            checkout(customer, cart);
//
//        } catch (IllegalStateException e) {
//            System.out.println("✓ Expected error caught: " + e.getMessage());
//        } catch (Exception e) {
//            System.out.println("Unexpected error: " + e.getMessage());
//        }


        // TEST 5: Out of stock error handling

//        try {
//            // Create product with limited stock
//            SimpleProduct limitedProduct = new SimpleProduct("Limited Item", 25, 2);
//
//            Customer customer = new Customer("Herothy", 1000);
//
//            Cart cart = new Cart();
//            System.out.println("Product stock: " + limitedProduct.getQuantity());
//
//            cart.addProduct(limitedProduct, 5); // throws an error
//
//        } catch (IllegalArgumentException e) {
//            System.out.println("✓ Expected error caught: " + e.getMessage());
//        }


        // TEST 6: Non-shippable items only checkout

//        try {
//            SimpleProduct scratchCard1 = new SimpleProduct("Scratch Card", 5, 1000);
//            SimpleProduct scratchCard2 = new SimpleProduct("Gift Card", 10, 500);
//
//            Customer customer = new Customer("Herothy no Shipping", 100);
//
//            Cart cart = new Cart();
//            cart.addProduct(scratchCard1, 3);
//            cart.addProduct(scratchCard2, 2);
//
//            System.out.println("Customer balance before checkout: " + customer.getBalance());
//            System.out.println();
//
//            checkout(customer, cart);
//
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//        }


        // TEST 7: Mixed product types checkout

//        try {
//            // All different types of products
//            ExpirableShippableProduct cheese = new ExpirableShippableProduct("Cheese", 80, 10, LocalDate.now().plusDays(3), 150);
//            NonExpirableShippableProduct laptop = new NonExpirableShippableProduct("Laptop", 1200, 5, 2000);
//            ExpirableProduct milk = new ExpirableProduct("Milk", 4, 20, LocalDate.now().plusDays(2));
//            SimpleProduct digitalCode = new SimpleProduct("Gift Card", 15, 100);
//
//            Customer customer = new Customer("Himothy", 2000);
//
//            Cart cart = new Cart();
//            cart.addProduct(cheese, 1);
//            cart.addProduct(laptop, 1);
//            cart.addProduct(digitalCode, 2);
//
//            System.out.println("Customer balance before checkout: " + customer.getBalance());
//            System.out.println();
//
//            checkout(customer, cart);
//
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//        }


        // TEST 8: Adding same product multiple times

//        try {
//            ExpirableShippableProduct cheese = new ExpirableShippableProduct("Cheese", 50, 20, LocalDate.now().plusDays(4), 100);
//
//            Customer customer = new Customer("Herothy", 1000);
//
//            Cart cart = new Cart();
//
//            cart.addProduct(cheese, 3);
//            System.out.println("First addition: 3 cheese items");
//
//            cart.addProduct(cheese, 2);
//            System.out.println("Second addition: 2 more cheese items");
//
//            System.out.println("Total cheese in cart should be: 5");
//            System.out.println("Customer balance before checkout: " + customer.getBalance());
//            System.out.println();
//
//            checkout(customer, cart);
//
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//        }


        // TEST 9: Expired product without shipping error handling

//        try {
//            // Create expired with no shipping product
//            ExpirableProduct expiredCheese = new ExpirableProduct("Cheese", 50, 10, LocalDate.now().minusDays(1));
//            SimpleProduct scratchCard = new SimpleProduct("Scratch Card", 5, 1000);
//
//            Customer customer = new Customer("Himothy", 500);
//
//            Cart cart = new Cart();
//            cart.addProduct(expiredCheese, 1); //adding expired product
//            cart.addProduct(scratchCard, 1);
//
//
//            checkout(customer, cart);
//
//        } catch (IllegalStateException e) {
//            System.out.println("✓ Expected error caught: " + e.getMessage());
//        } catch (Exception e) {
//            System.out.println("Unexpected error: " + e.getMessage());
//        }
    }
}