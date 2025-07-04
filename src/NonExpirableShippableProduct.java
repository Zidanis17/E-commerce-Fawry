public class NonExpirableShippableProduct extends Product implements Shippable{
    double weight;
    public NonExpirableShippableProduct(String name, double price, int quantity, double weight) {
        super(name,price,quantity);
        if(weight<=0){
            throw new IllegalArgumentException("Weight must be greater than 0");
        }
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

}
