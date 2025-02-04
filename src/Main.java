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
        System.out.println("================================");
        System.out.println("<샐러드 메뉴>");
        for (Salad salad : menuManager.getSalads()) {
            System.out.println("▶ " + salad);
        }
        System.out.println("\n================================");
        System.out.println("<샐러드 세트 메뉴>");
        for (SaladSet saladSet : menuManager.getSaladSets()) {
            System.out.println(saladSet);
        }
    }

    // 메뉴 생성
    private static void createMenu(Scanner scanner, MenuManager menuManager) {
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
        if (!Validator.isValidString(name)) return;  // 빈 문자열 체크

        System.out.print("가격: ");
        int price = getValidNumber(scanner);  // 숫자만 입력 받기

        System.out.print("드레싱: ");
        String dressing = scanner.nextLine();
        if (!Validator.isValidString(dressing)) return;  // 빈 문자열 체크

        menuManager.addSalad(name, price, dressing);
    }

    // 샐러드 세트 생성
    private static void createSaladSet(Scanner scanner, MenuManager menuManager) {
        System.out.print("샐러드 이름: ");
        String saladName = scanner.nextLine();
        if (!Validator.isValidSalad(saladName, menuManager)) {
            System.out.println("해당 샐러드가 존재하지 않습니다.");
            return;
        }

        System.out.print("스프 이름: ");
        String soupName = scanner.nextLine();
        if (!Validator.isValidSoup(soupName, menuManager)) {
            System.out.println("해당 스프가 존재하지 않습니다.");
            return;
        }

        System.out.print("음료 이름: ");
        String drinkName = scanner.nextLine();
        if (!Validator.isValidDrink(drinkName, menuManager)) {
            System.out.println("해당 음료가 존재하지 않습니다.");
            return;
        }

        int price = menuManager.findSalad(saladName).getPrice() +
                menuManager.findSoup(soupName).getPrice() +
                menuManager.findDrink(drinkName).getPrice();

        menuManager.addSaladSet(saladName, price, "기본 드레싱", soupName, drinkName);
        System.out.println("샐러드 세트가 성공적으로 추가되었습니다.");
    }

    // 메뉴 삭제
    private static void deleteMenu(Scanner scanner, MenuManager menuManager) {
        System.out.println("어떤 메뉴를 삭제하시겠습니까?");
        System.out.println("1. 샐러드 2. 샐러드 세트");
        System.out.print("선택: ");
        int choice = getValidChoice(scanner, 1, 2);  // 숫자만 입력 받기

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
        System.out.println("삭제할 샐러드를 선택하세요.");
        for (int i = 0; i < menuManager.getSalads().size(); i++) {
            System.out.println((i + 1) + ". " + menuManager.getSalads().get(i));
        }
        int saladChoice = getValidChoice(scanner, 1, menuManager.getSalads().size()) - 1;
        menuManager.removeSalad(menuManager.getSalads().get(saladChoice).getName());
    }

    // 샐러드 세트 삭제
    private static void deleteSaladSet(Scanner scanner, MenuManager menuManager) {
        System.out.println("삭제할 샐러드 세트를 선택하세요.");
        for (int i = 0; i < menuManager.getSaladSets().size(); i++) {
            System.out.println((i + 1) + ". " + menuManager.getSaladSets().get(i));
        }
        int saladSetChoice = getValidChoice(scanner, 1, menuManager.getSaladSets().size()) - 1;
        menuManager.removeSaladSet(menuManager.getSaladSets().get(saladSetChoice).getName());
    }

    // 숫자 입력만 받는 유효성 검사
    private static int getValidChoice(Scanner scanner, int min, int max) {
        int choice = -1;
        while (choice < min || choice > max) {
            while (!scanner.hasNextInt()) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                scanner.next(); // 잘못된 입력 처리
            }
            choice = scanner.nextInt();
            if (choice < min || choice > max) {
                System.out.println("잘못된 범위입니다. 다시 입력해주세요.");
            }
        }
        scanner.nextLine();  // newline 문자 소비
        return choice;
    }

    // 숫자만 입력받는 유효성 검사
    private static int getValidNumber(Scanner scanner) {
        int number = -1;
        while (number < 0) {
            while (!scanner.hasNextInt()) {
                System.out.println("가격은 숫자만 입력 가능합니다.");
                scanner.next();  // 잘못된 입력 처리
            }
            number = scanner.nextInt();
            if (number < 0) {
                System.out.println("가격은 0 이상의 숫자여야 합니다.");
            }
        }
        scanner.nextLine();  // newline 문자 소비
        return number;
    }
}



