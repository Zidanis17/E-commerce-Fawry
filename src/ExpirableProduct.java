import java.time.LocalDate;

public class ExpirableProduct extends Product implements Expirable {
    private final LocalDate expiryDate;


    public ExpirableProduct(String name, double price, int quantity, LocalDate expiryDate) {
        super(name, price, quantity);

        this.expiryDate = expiryDate;
    }


    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public boolean isExpired(){
        if (expiryDate.isBefore(LocalDate.now())){
            return true;
        }else {
            return false;
        }
    }
}
