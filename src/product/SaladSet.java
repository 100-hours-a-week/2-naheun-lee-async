package product;
public class SaladSet extends Salad {
    private Soup soup;
    private Drink drink;

    public SaladSet(String name, int price, String dressing, Soup soup, Drink drink, int stock) {
        super(name, price, dressing, stock);
        this.soup = soup;
        this.drink = drink;
    }

    public Soup getSoup() {
        return soup;
    }

    public Drink getDrink() {
        return drink;
    }

    //샐러드 삭제 여부
    public boolean isSaladFromSet(String saladName) {
        if (this.getName().equals(saladName)) {
            return true;  // 해당 샐러드가 세트에 포함되어 있으면 제거됨
        }
        return false; 
    }

    @Override
    public String toString() {
        return String.format("%s(%s)+%s+%s 세트: %d원 \033[31m[재고: %d]\033[0m", name, dressing, soup.name, drink.name, price, stock);
    }
}

