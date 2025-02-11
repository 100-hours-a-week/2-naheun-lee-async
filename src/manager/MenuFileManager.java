package manager;

import java.io.*;
import product.Drink;
import product.Salad;
import product.SaladSet;
import product.Soup;
import sharedData.SharedMenuData;

public class MenuFileManager {
    private static MenuFileManager instance;
    private SharedMenuData sharedMenuData = SharedMenuData.getInstance();

    private static final String SALADS_FILE = "src/data/salads.txt";
    private static final String SALADSETS_FILE = "src/data/saladsets.txt";
    private static final String SOUPS_FILE = "src/data/soups.txt";
    private static final String DRINKS_FILE = "src/data/drinks.txt";

    public static MenuFileManager getInstance() {
        if (instance == null) {
            instance = new MenuFileManager();
        }
        return instance;
    }

    //데이터 파일 읽기
    public void loadMenu() {
        loadSalads(SALADS_FILE);
        loadSoups(SOUPS_FILE);
        loadDrinks(DRINKS_FILE);
        loadSaladSets(SALADSETS_FILE);
    }

    private void loadSalads(String filename) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    sharedMenuData.getSalads().add(new Salad(parts[0], Integer.parseInt(parts[2]), parts[1], Integer.parseInt(parts[3])));
                }
            }
        } catch (IOException e) {
            System.out.println("샐러드 정보를 불러오는 동안 오류가 발생했습니다.");
        }
    }

    private void loadSaladSets(String filename) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String saladName = parts[0];
                    int price = Integer.parseInt(parts[4]);
                    String dressing = parts[1];

                    Soup soup = sharedMenuData.findSoup(parts[2]);
                    Drink drink = sharedMenuData.findDrink(parts[3]);

                    if (soup != null && drink != null) {
                        sharedMenuData.getSaladSets().add(new SaladSet(saladName, price, dressing, soup, drink, Integer.parseInt(parts[5])));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("샐러드 세트 정보를 불러오는 동안 오류가 발생했습니다.");
        }
    }

    private void loadSoups(String filename) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    sharedMenuData.getSoups().add(new Soup(parts[0], Integer.parseInt(parts[1])));
                }
            }
        } catch (IOException e) {
            System.out.println("스프 정보를 불러오는 동안 오류가 발생했습니다.");
        }
    }

    private void loadDrinks(String filename) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    sharedMenuData.getDrinks().add(new Drink(parts[0], Integer.parseInt(parts[1])));
                }
            }
        } catch (IOException e) {
            System.out.println("음료 정보를 불러오는 동안 오류가 발생했습니다.");
        }
    }

    //데이터 파일 쓰기
    public void saveMenu() {
        saveSalads(SALADS_FILE);
        saveSoups(SOUPS_FILE);
        saveDrinks(DRINKS_FILE);
        saveSaladSets(SALADSETS_FILE);
        System.out.println("메뉴 데이터가 저장되었습니다.");
    }

    private void saveSalads(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"))) {
            for (Salad salad : sharedMenuData.getSalads()) {
                bw.write(salad.getName() + "," + salad.getDressing() + "," + salad.getPrice() + ',' + salad.getStock());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("샐러드 정보를 저장하는 동안 오류가 발생했습니다.");
        }
    }

    private void saveSaladSets(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"))) {
            for (SaladSet set : sharedMenuData.getSaladSets()) {
                bw.write(set.getName() + "," + set.getDressing() + "," +
                         set.getSoup().getName() + "," + set.getDrink().getName() + "," + set.getPrice() + ',' + set.getStock());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("샐러드 세트 정보를 저장하는 동안 오류가 발생했습니다.");
        }
    }

    private void saveSoups(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"))) {
            for (Soup soup : sharedMenuData.getSoups()) {
                bw.write(soup.getName() + "," + soup.getPrice());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("스프 정보를 저장하는 동안 오류가 발생했습니다.");
        }
    }

    private void saveDrinks(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"))) {
            for (Drink drink : sharedMenuData.getDrinks()) {
                bw.write(drink.getName() + "," + drink.getPrice());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("음료 정보를 저장하는 동안 오류가 발생했습니다.");
        }
    }

}
