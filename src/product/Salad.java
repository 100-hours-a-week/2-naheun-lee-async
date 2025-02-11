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

    public synchronized void adjustStock(int amount) {
        if (amount > 0) {
            stock += amount;  // 재고 증가
        } else if (amount < 0) {
            if (stock >= Math.abs(amount)) {  // 재고가 충분한지 다시 확인
                stock += amount;  // 감소
            } else {
                System.out.println(Thread.currentThread().getName() + " - 재고 부족! " + name + " 재고가 없습니다. 다시 주문해주세요.");
            }
        }
    }

    @Override
    public String toString() {
        return String.format("%s(%s): %d원 \033[31m[재고: %d]\033[0m", name, dressing, price, stock);
    }
}




