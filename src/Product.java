public abstract class Product {
    private final String name;
    private double price;
    private int quantity;


    public Product(String name, double price, int quantity) {
        if (quantity < 0){
            throw new IllegalArgumentException("Quantity must be greater than or equal to zero");
        }
        if(price < 0){
            throw new IllegalArgumentException("Price must be greater than or equal to zero");
        }

        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity(){
        return quantity;
    }

    public void reduceQuantity(int amount) {
        if (amount > quantity) {
            throw new IllegalArgumentException("Not enough stock for " + name); //Assumption is that I am grabbing something from the shelf so its quantity decreases when Item it is adding to Cart
        }
        quantity -= amount;
    }
}
