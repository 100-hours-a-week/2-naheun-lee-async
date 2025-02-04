public class Salad extends Product {
    protected String dressing;

    public Salad(String name, int price, String dressing) {
        super(name, price);
        this.dressing = dressing;
    }

    public String getDressing() {
        return dressing;
    }

    @Override
    public String toString() {
        return String.format("%s(%s): %d원", name, dressing, price);
    }
}




