package validation;

import java.util.Scanner;

public class Validation {

    public static boolean isValidString(String input) {
        if (input == null || input.trim().isEmpty()) {
            System.out.println("입력값이 비어있습니다.다시 입력해주세요.");
            return false;
        }
        return true;
    }

     // 숫자 범위 유효성 검사
     public static int getValidChoice(Scanner scanner, int min, int max) {
        int choice;
        do {
            while (!scanner.hasNextInt()) {
                System.out.print("숫자만 입력 가능합니다.\n다시 입력해주세요: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            if (choice < min || choice > max) {
                System.out.print("잘못된 범위입니다.\n다시 입력해주세요: ");
            }
        } while (choice < min || choice > max);

        scanner.nextLine(); 
        return choice;
    }

    // 숫자만 입력받는 유효성 검사
    public static int getValidNumber(Scanner scanner) {
        int number;
        do {
            while (!scanner.hasNextInt()) {
                System.out.print("가격은 숫자만 입력 가능합니다.\n다시 입력해주세요: ");
                scanner.next();
            }
            number = scanner.nextInt();
            if (number < 0) {
                System.out.print("가격은 0 이상의 숫자여야 합니다.\n다시 입력해주세요: ");
            }
        } while (number < 0);

        scanner.nextLine(); 
        return number;
    }

    
}
