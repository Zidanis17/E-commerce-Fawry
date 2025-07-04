import java.time.LocalDate;

public class ExpirableShippableProduct extends Product implements Shippable, Expirable {
    private double weight;
    private LocalDate expiryDate;
    public ExpirableShippableProduct(String name, double price, int quantity, LocalDate expiryDate, double weight) {
        super(name,price,quantity);
        if(weight<=0){
            throw new IllegalArgumentException("Weight must be greater than 0");
        }
        this.weight = weight;

        this.expiryDate = expiryDate;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    public boolean isExpired(){
        if (expiryDate.isBefore(LocalDate.now())){
            return true;
        }else {
            return false;
        }
    }
}
