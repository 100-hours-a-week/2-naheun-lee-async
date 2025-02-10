import java.util.*;
import manager.MenuFileManager;
import manager.MenuServiceManager;
import validation.Validation;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MenuFileManager fileManager = MenuFileManager.getInstance();
        fileManager.loadMenu();
        MenuServiceManager serviceManager = MenuServiceManager.getInstance();

        while (true) {
            System.out.println("\n================================");
            System.out.println("<샐러드 메뉴 관리 시스템>");
            System.out.println("1. 메뉴 조회 2. 메뉴 생성 3. 메뉴 삭제 4. 종료");
            System.out.print("메뉴를 선택하세요: ");
            int choice = Validation.getValidChoice(scanner, 1, 4);  

            switch (choice) {
                case 1:
                    serviceManager.viewMenu();
                    break;
                case 2:
                    serviceManager.createMenu();
                    break;
                case 3:
                    serviceManager.deleteMenu();
                    break;
                case 4:
                    fileManager.saveMenu(); // 종료 전에 메뉴 저장
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }
    
}



