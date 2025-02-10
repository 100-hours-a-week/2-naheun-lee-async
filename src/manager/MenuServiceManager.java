package manager;

import java.util.Scanner;
import product.Drink;
import product.Salad;
import product.SaladSet;
import product.Soup;
import sharedData.SharedMenuData;
import validation.Validation;

public class MenuServiceManager {
    private static MenuServiceManager instance;
    private SharedMenuData sharedMenuData = SharedMenuData.getInstance();
    Scanner scanner = new Scanner(System.in);

    public static MenuServiceManager getInstance() {
        if (instance == null) {
            instance = new MenuServiceManager();
        }
        return instance;
    }

    // 메뉴 조회
    public void viewMenu() {
        System.out.println("----------------------------------");
        System.out.println("<샐러드 메뉴>");
        if(sharedMenuData.getSalads().isEmpty()){
            System.out.println("메뉴 없음");
        }
        else{
            for (Salad salad : sharedMenuData.getSalads()) {
                System.out.println("# " + salad);
            }
        }
        System.out.println("----------------------------------");
        System.out.println("<샐러드 세트 메뉴>");
        if(sharedMenuData.getSaladSets().isEmpty()){
            System.out.println("메뉴 없음");
        }
        else{
            for (SaladSet saladSet : sharedMenuData.getSaladSets()) {
                System.out.println(saladSet);
            }
        }
    }

    // 메뉴 생성
    public void createMenu() {
        System.out.println("----------------------------------");
        System.out.println("생성하려는 메뉴를 선택하세요.");
        System.out.println("1. 샐러드 2. 샐러드 세트");
        System.out.print("메뉴 선택: ");
        int choice = Validation.getValidChoice(scanner, 1, 2); 

        if (choice == 1) {
            // 샐러드 생성
            createSalad();
        } else if (choice == 2) {
            // 샐러드 세트 생성
            createSaladSet();
        }
    }

    // 샐러드 생성
    public void createSalad() {
        System.out.print("샐러드명: ");
        String name = scanner.nextLine();
        if (!Validation.isValidString(name)) return; 

        System.out.print("가격: ");
        int price = Validation.getValidNumber(scanner);  

        System.out.print("드레싱: ");
        String dressing = scanner.nextLine();
        if (!Validation.isValidString(dressing)) return;  

        sharedMenuData.getSalads().add(new Salad(name, price, dressing));
        // 완성된 세트 출력
        System.out.println("----------------------------------");
        System.out.println(name+"("+dressing+"): "+price+"(원) 메뉴가 생성되었습니다.");
        
    }

    // 샐러드 세트 생성
    public void createSaladSet() {
        if (sharedMenuData.getSalads().isEmpty()) {
            System.out.println("샐러드가 없습니다. 먼저 샐러드를 생성해주세요.");
            return;
        }
        System.out.println("[샐러드 목록]");
        for (int i = 0; i < sharedMenuData.getSalads().size(); i++) {
            System.out.println((i + 1) + ". " + sharedMenuData.getSalads().get(i).getName());
        }
        System.out.print("샐러드 선택: ");
        int saladChoice = Validation.getValidChoice(scanner, 1, sharedMenuData.getSalads().size()) - 1;
        Salad selectedSalad = sharedMenuData.getSalads().get(saladChoice);
        String dressing = selectedSalad.getDressing();  // 선택한 샐러드의 드레싱을 그대로 사용
    
        System.out.println("[스프 목록]");
        for (int i = 0; i < sharedMenuData.getSoups().size(); i++) {
            System.out.println((i + 1) + ". " + sharedMenuData.getSoups().get(i).getName());
        }
        System.out.print("스프 선택: ");
        int soupChoice = Validation.getValidChoice(scanner, 1, sharedMenuData.getSoups().size()) - 1;
        Soup soup = sharedMenuData.getSoups().get(soupChoice);

        System.out.println("[음료 목록]");
        for (int i = 0; i < sharedMenuData.getDrinks().size(); i++) {
            System.out.println((i + 1) + ". " + sharedMenuData.getDrinks().get(i).getName());
        }
        System.out.print("음료 선택: ");
        int drinkChoice = Validation.getValidChoice(scanner, 1, sharedMenuData.getDrinks().size()) - 1;
        Drink drink = sharedMenuData.getDrinks().get(drinkChoice);
    
        // 가격 계산 (샐러드 + 스프 + 음료)
        int price = selectedSalad.getPrice() + soup.getPrice() + drink.getPrice();
        
        if (soup != null && drink != null) {
            sharedMenuData.getSaladSets().add(new SaladSet(selectedSalad.getName(), price, dressing, soup, drink));
        }
    
        System.out.println("----------------------------------");
        System.out.println(selectedSalad.getName()+"("+dressing+") + "+soup.getName()+" + "+drink.getName()+" 세트 : "+price+"(원) 메뉴가 생성되었습니다.");
    }

    // 메뉴 삭제
    public void deleteMenu() {
        System.out.println("----------------------------------");
        System.out.println("어떤 메뉴를 삭제하시겠습니까?");
        System.out.println("1. 샐러드 2. 샐러드 세트");
        System.out.print("선택: ");
        int choice = Validation.getValidChoice(scanner, 1, 2);  

        if (choice == 1) {
            // 샐러드 삭제
            deleteSalad();
        } else if (choice == 2) {
            // 샐러드 세트 삭제
            deleteSaladSet();
        }
    }

    // 샐러드 삭제
    public void deleteSalad() {
        if (sharedMenuData.getSalads().isEmpty()) {
            System.out.println("삭제할 샐러드가 없습니다.");
            return;
        }
        for (int i = 0; i < sharedMenuData.getSalads().size(); i++) {
            System.out.println((i + 1) + ". " + sharedMenuData.getSalads().get(i));
        }
        System.out.print("삭제할 샐러드를 선택하세요: ");
        int saladChoice = Validation.getValidChoice(scanner, 1, sharedMenuData.getSalads().size()) - 1;
        removeSalad(sharedMenuData.getSalads().get(saladChoice).getName());
        System.out.println("해당 메뉴가 삭제되었습니다.");
    }

    // 샐러드 세트 삭제
    public void deleteSaladSet() {
        if (sharedMenuData.getSaladSets().isEmpty()) {
            System.out.println("삭제할 샐러드 세트가 없습니다.");
            return;
        }
        for (int i = 0; i < sharedMenuData.getSaladSets().size(); i++) {
            System.out.println((i + 1) + ". " + sharedMenuData.getSaladSets().get(i));
        }
        System.out.print("삭제할 샐러드 세트를 선택하세요: ");
        int saladSetChoice = Validation.getValidChoice(scanner, 1, sharedMenuData.getSaladSets().size()) - 1;
        removeSaladSet(sharedMenuData.getSaladSets().get(saladSetChoice).getName());
        System.out.println("해당 메뉴가 삭제되었습니다.");
    }

    //샐러드 삭제
    public void removeSalad(String name) {
        sharedMenuData.getSalads().removeIf(salad -> salad.getName().equals(name));
        sharedMenuData.getSaladSets().removeIf(set -> set.isSaladFromSet(name));
    }

     //샐러드 세트 삭제
    public void removeSaladSet(String name) {
        sharedMenuData.getSaladSets().removeIf(set -> set.getName().equals(name));
    }
    
}
