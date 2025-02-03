public class Salad {
    private int productId;  // 상품 번호 추가
    private String name;
    private int price;

    public Salad(int productId, String name, int price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}


