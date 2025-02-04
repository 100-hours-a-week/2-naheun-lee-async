import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MenuManager menuManager = MenuManager.getInstance();
        menuManager.loadMenu();

        while (true) {
            System.out.println("\n================================");
            System.out.println("<샐러드 메뉴 관리 시스템>");
            System.out.println("1. 메뉴 조회 2. 메뉴 생성 3. 메뉴 삭제 4. 종료");
            System.out.print("메뉴를 선택하세요: ");
            int choice = getValidChoice(scanner, 1, 4);  // 숫자만 입력 받기

            switch (choice) {
                case 1:
                    viewMenu(menuManager);
                    break;
                case 2:
                    createMenu(scanner, menuManager);
                    break;
                case 3:
                    deleteMenu(scanner, menuManager);
                    break;
                case 4:
                    menuManager.saveMenu(); // 종료 전에 메뉴 저장
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 메뉴 조회
    private static void viewMenu(MenuManager menuManager) {
        System.out.println("----------------------------------");
        System.out.println("<샐러드 메뉴>");
        if(menuManager.getSalads().isEmpty()){
            System.out.println("메뉴 없음");
        }
        else{
            for (Salad salad : menuManager.getSalads()) {
                System.out.println("▶ " + salad);
            }
        }
        System.out.println("----------------------------------");
        System.out.println("<샐러드 세트 메뉴>");
        if(menuManager.getSaladSets().isEmpty()){
            System.out.println("메뉴 없음");
        }
        else{
            for (SaladSet saladSet : menuManager.getSaladSets()) {
                System.out.println(saladSet);
            }
        }
    }

    // 메뉴 생성
    private static void createMenu(Scanner scanner, MenuManager menuManager) {
        System.out.println("----------------------------------");
        System.out.println("생성하려는 메뉴를 선택하세요.");
        System.out.println("1. 샐러드 2. 샐러드 세트");
        System.out.print("메뉴 선택: ");
        int choice = getValidChoice(scanner, 1, 2);  // 숫자만 입력 받기

        if (choice == 1) {
            // 샐러드 생성
            createSalad(scanner, menuManager);
        } else if (choice == 2) {
            // 샐러드 세트 생성
            createSaladSet(scanner, menuManager);
        }
    }

    // 샐러드 생성
    private static void createSalad(Scanner scanner, MenuManager menuManager) {
        System.out.print("샐러드명: ");
        String name = scanner.nextLine();
        if (!Validator.isValidString(name)) return; 

        System.out.print("가격: ");
        int price = getValidNumber(scanner);  

        System.out.print("드레싱: ");
        String dressing = scanner.nextLine();
        if (!Validator.isValidString(dressing)) return;  

        menuManager.addSalad(name, price, dressing);
        // 완성된 세트 출력
        System.out.println("----------------------------------");
        System.out.println(name+"("+dressing+"): "+price+"(원) 메뉴가 생성되었습니다.");

        
    }

    // 샐러드 세트 생성
    private static void createSaladSet(Scanner scanner, MenuManager menuManager) {
        System.out.println("[샐러드 목록]");
        for (int i = 0; i < menuManager.getSalads().size(); i++) {
            System.out.println((i + 1) + ". " + menuManager.getSalads().get(i).getName());
        }
        System.out.print("샐러드 선택: ");
        int saladChoice = getValidChoice(scanner, 1, menuManager.getSalads().size()) - 1;
        Salad selectedSalad = menuManager.getSalads().get(saladChoice);
        String dressing = selectedSalad.getDressing();  // 선택한 샐러드의 드레싱을 그대로 사용
    
        System.out.println("[스프 목록]");
        for (int i = 0; i < menuManager.getSoups().size(); i++) {
            System.out.println((i + 1) + ". " + menuManager.getSoups().get(i).getName());
        }
        System.out.print("스프 선택: ");
        int soupChoice = getValidChoice(scanner, 1, menuManager.getSoups().size()) - 1;
        Soup selectedSoup = menuManager.getSoups().get(soupChoice);

        System.out.println("[음료 목록]");
        for (int i = 0; i < menuManager.getDrinks().size(); i++) {
            System.out.println((i + 1) + ". " + menuManager.getDrinks().get(i).getName());
        }
        System.out.print("음료 선택: ");
        int drinkChoice = getValidChoice(scanner, 1, menuManager.getDrinks().size()) - 1;
        Drink selectedDrink = menuManager.getDrinks().get(drinkChoice);
    
        // 가격 계산 (샐러드 + 스프 + 음료)
        int price = selectedSalad.getPrice() + selectedSoup.getPrice() + selectedDrink.getPrice();
    
        menuManager.addSaladSet(selectedSalad.getName(), price, dressing, selectedSoup.getName(), selectedDrink.getName());
    
        // 완성된 세트 출력
        System.out.println("----------------------------------");
        System.out.println(selectedSalad.getName()+"("+dressing+") + "+selectedSoup.getName()+" + "+selectedDrink.getName()+" 세트 : "+price+"(원) 메뉴가 생성되었습니다.");
    }

    // 메뉴 삭제
    private static void deleteMenu(Scanner scanner, MenuManager menuManager) {
        System.out.println("----------------------------------");
        System.out.println("어떤 메뉴를 삭제하시겠습니까?");
        System.out.println("1. 샐러드 2. 샐러드 세트");
        System.out.print("선택: ");
        int choice = getValidChoice(scanner, 1, 2);  

        if (choice == 1) {
            // 샐러드 삭제
            deleteSalad(scanner, menuManager);
        } else if (choice == 2) {
            // 샐러드 세트 삭제
            deleteSaladSet(scanner, menuManager);
        }
    }

    // 샐러드 삭제
    private static void deleteSalad(Scanner scanner, MenuManager menuManager) {
        for (int i = 0; i < menuManager.getSalads().size(); i++) {
            System.out.println((i + 1) + ". " + menuManager.getSalads().get(i));
        }
        System.out.print("삭제할 샐러드를 선택하세요: ");
        int saladChoice = getValidChoice(scanner, 1, menuManager.getSalads().size()) - 1;
        menuManager.removeSalad(menuManager.getSalads().get(saladChoice).getName());
        System.out.println("해당 메뉴가 삭제되었습니다.");
    }

    // 샐러드 세트 삭제
    private static void deleteSaladSet(Scanner scanner, MenuManager menuManager) {
        for (int i = 0; i < menuManager.getSaladSets().size(); i++) {
            System.out.println((i + 1) + ". " + menuManager.getSaladSets().get(i));
        }
        System.out.print("삭제할 샐러드 세트를 선택하세요: ");
        int saladSetChoice = getValidChoice(scanner, 1, menuManager.getSaladSets().size()) - 1;
        menuManager.removeSaladSet(menuManager.getSaladSets().get(saladSetChoice).getName());
        System.out.println("해당 메뉴가 삭제되었습니다.");
    }

    // 숫자 범위 유효성 검사
    private static int getValidChoice(Scanner scanner, int min, int max) {
        int choice = -1;
        while (choice < min || choice > max) {
            while (!scanner.hasNextInt()) {
                System.out.print("숫자만 입력 가능합니다. 다시 입력해주세요: ");
                scanner.next(); 
            }
            choice = scanner.nextInt();
            if (choice < min || choice > max) {
                System.out.print("잘못된 범위입니다. 다시 입력해주세요: ");
            }
        }
        scanner.nextLine();  
        return choice;
    }

    // 숫자만 입력받는 유효성 검사
    private static int getValidNumber(Scanner scanner) {
        int number = -1;
        while (number < 0) {
            while (!scanner.hasNextInt()) {
                System.out.print("가격은 숫자만 입력 가능합니다. 다시 입력해주세요: ");
                scanner.next();  
            }
            number = scanner.nextInt();
            if (number < 0) {
                System.out.print("가격은 0 이상의 숫자여야 합니다. 다시 입력해주세요: ");
            }
        }
        scanner.nextLine();  
        return number;
    }

    
}



