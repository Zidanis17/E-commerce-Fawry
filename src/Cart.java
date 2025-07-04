import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cart {
    private HashMap<Product, Integer> cartItems = new HashMap<Product, Integer>();

    public Cart() {

    }

    public HashMap<Product, Integer> getCartItems() {
        return cartItems;
    }

    public void addProduct(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        if(product.getQuantity() < quantity){
            throw new IllegalArgumentException("Provided quantity is more than what we have in stock");
        }

        if(cartItems.containsKey(product)){
            cartItems.put(product, cartItems.get(product) + quantity);
        }else {
            cartItems.put(product, quantity);
        }
        product.reduceQuantity(quantity);
    }

    public boolean isEmpty(){
        return cartItems.isEmpty();
    }
}
