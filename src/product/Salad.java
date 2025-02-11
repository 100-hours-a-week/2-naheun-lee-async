package product;
public class Salad extends Product {
    protected String dressing;
    protected int stock;

    public Salad(String name, int price, String dressing, int stock) {
        super(name, price);
        this.dressing = dressing;
        this.stock = stock;
    }

    public String getDressing() {
        return dressing;
    }

    public int getStock(){
        return stock;
    }
    
    public void decreaseStock() {
        if (stock > 0) {
            stock--;  // 재고 1개 감소
        } else {
            System.out.println("재고가 부족합니다.");
        }
    }

    @Override
    public String toString() {
        return String.format("%s(%s): %d원 \033[31m[재고: %d]\033[0m", name, dressing, price, stock);
    }
}




