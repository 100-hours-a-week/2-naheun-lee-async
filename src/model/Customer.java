package model;

import product.Salad;
import product.SaladSet;
import sharedData.SharedMenuData;

import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Customer implements Runnable {
    private static Random random = new Random();
    private String name;
    private SharedMenuData sharedMenuData = SharedMenuData.getInstance();
    public Customer(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public void run() {
        int choice = random.nextInt(2) + 1;  // 1: 샐러드, 2: 샐러드 세트

        if (choice == 1) {
            placeSaladOrder();
        } else if (choice == 2) {
            placeSaladSetOrder();
        }
    }

    // 샐러드 주문 처리
    private void placeSaladOrder() {
        
        if (sharedMenuData.getSalads().isEmpty()) {
            System.out.println("샐러드가 없습니다. 다음에 다시 주문해주세요!");
            return;
        }

        // 랜덤으로 샐러드 선택
        Salad selectedSalad = sharedMenuData.getSalads().get(random.nextInt(sharedMenuData.getSalads().size()));
            
        // 주문 처리 시간 랜덤 (1초 ~ 10초)
        int processingTime = random.nextInt(10) + 1;  
            
        try {
            Thread.sleep(processingTime * 1000);  
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 재고 확인 후 주문 처리
                
        if (selectedSalad.getStock() > 0) {
            int printSalad = selectedSalad.getStock()-1; //현재 재고-1
            selectedSalad.adjustStock(-1);
            System.out.println("\n\033[32m[카운터 "+Thread.currentThread().getName().split("-")[3].replaceAll("\\D+", "") +"]\033[0m"+ 
            " - "+getName()+": 주문 완료! " + selectedSalad.getName() + "\033[31m (" + printSalad + "개 남음)\033[0m");
        } else {
                System.out.println("\n\033[32m[카운터 "+Thread.currentThread().getName().split("-")[3].replaceAll("\\D+", "") +"]\033[0m"+
                " - " +getName()+"\033[31m - 재고 부족! " + selectedSalad.getName() + " 재고가 없습니다. 다음에 다시 주문해주세요.\033[0m");
        }
  
    }

    // 샐러드 세트 주문 처리
    private void placeSaladSetOrder() {
        
        if (sharedMenuData.getSaladSets().isEmpty()) {
            System.out.println("샐러드 세트가 없습니다. 다음에 다시 주문해주세요!");
            return;
        }

        // 랜덤으로 샐러드 세트 선택
        SaladSet selectedSaladSet = sharedMenuData.getSaladSets().get(random.nextInt(sharedMenuData.getSaladSets().size()));
        
        // 주문 처리 시간 랜덤 (1초 ~ 10초)
        int processingTime = random.nextInt(10) + 1;  
        try {
            Thread.sleep(processingTime * 1000);  
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // 재고 확인 후 주문 처리
        if (selectedSaladSet.getStock() > 0) {
            int printSaladSet = selectedSaladSet.getStock()-1; //현재 재고-1
            selectedSaladSet.adjustStock(-1);;
            System.out.println("\n\033[32m[카운터 "+Thread.currentThread().getName().split("-")[3].replaceAll("\\D+", "")+"]\033[0m" + 
            " - " +getName()+": 주문 완료! "+ selectedSaladSet.getName() + "\033[31m (" + printSaladSet + "개 남음)\033[0m");
        } else {
            System.out.println("\n\033[32m[카운터 "+Thread.currentThread().getName().split("-")[3].replaceAll("\\D+", "") +"]\033[0m"+
            " - " +getName()+"\033[31m - 재고 부족! " + selectedSaladSet.getName() + " 재고가 없습니다. 다음에 다시 주문해주세요.\033[0m");
        }
    }
  
}



