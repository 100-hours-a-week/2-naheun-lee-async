import java.io.*;
import java.util.*;

public class MenuManager {
    private static MenuManager instance;
    private List<Salad> salads;
    private List<SaladSet> saladSets;
    private List<Soup> soups;
    private List<Drink> drinks;

    private MenuManager() {
        salads = new ArrayList<>();
        saladSets = new ArrayList<>();
        soups = new ArrayList<>();
        drinks = new ArrayList<>();
    }

    public static MenuManager getInstance() {
        if (instance == null) {
            instance = new MenuManager();
        }
        return instance;
    }

    public void loadMenu() {
        loadSalads("src/salads.txt");
        loadSaladSets("src/saladsets.txt");
        loadSoups("src/soups.txt");
        loadDrinks("src/drinks.txt");
    }

    private void loadSalads(String filename) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    salads.add(new Salad(parts[0], Integer.parseInt(parts[2]), parts[1]));
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
                if (parts.length == 5) {
                    String saladName = parts[0];
                    int price = Integer.parseInt(parts[4]);
                    String dressing = parts[1];

                    Soup soup = findSoup(parts[2]);
                    Drink drink = findDrink(parts[3]);

                    if (soup != null && drink != null) {
                        saladSets.add(new SaladSet(saladName, price, dressing, soup, drink));
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
                    soups.add(new Soup(parts[0], Integer.parseInt(parts[1])));
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
                    drinks.add(new Drink(parts[0], Integer.parseInt(parts[1])));
                }
            }
        } catch (IOException e) {
            System.out.println("음료 정보를 불러오는 동안 오류가 발생했습니다.");
        }
    }

    public List<Salad> getSalads() {
        return salads;
    }

    public List<SaladSet> getSaladSets() {
        return saladSets;
    }

    public List<Soup> getSoups() {
        return soups;
    }
    
    public List<Drink> getDrinks() {
        return drinks;
    }
    

    public void addSalad(String name, int price, String dressing) {
        salads.add(new Salad(name, price, dressing));
    }

    public void addSaladSet(String name, int price, String dressing, String soupName, String drinkName) {
        Soup soup = findSoup(soupName);
        Drink drink = findDrink(drinkName);
        if (soup != null && drink != null) {
            saladSets.add(new SaladSet(name, price, dressing, soup, drink));
        }
    }

    public void removeSalad(String name) {
        salads.removeIf(salad -> salad.getName().equals(name));
        saladSets.removeIf(set -> set.isSaladFromSet(name));
    }

    public void removeSaladSet(String name) {
        saladSets.removeIf(set -> set.getName().equals(name));
    }



    public void saveMenu() {
        saveSalads("src/salads.txt");
        saveSaladSets("src/saladsets.txt");
        saveSoups("src/soups.txt");
        saveDrinks("src/drinks.txt");
        System.out.println("메뉴 데이터가 저장되었습니다.");
    }

    private void saveSalads(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"))) {
            for (Salad salad : salads) {
                bw.write(salad.getName() + "," + salad.getDressing() + "," + salad.getPrice());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("샐러드 정보를 저장하는 동안 오류가 발생했습니다.");
        }
    }

    private void saveSaladSets(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"))) {
            for (SaladSet set : saladSets) {
                bw.write(set.getName() + "," + set.getDressing() + "," +
                         set.getSoup().getName() + "," + set.getDrink().getName() + "," + set.getPrice());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("샐러드 세트 정보를 저장하는 동안 오류가 발생했습니다.");
        }
    }

    private void saveSoups(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"))) {
            for (Soup soup : soups) {
                bw.write(soup.getName() + "," + soup.getPrice());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("스프 정보를 저장하는 동안 오류가 발생했습니다.");
        }
    }

    private void saveDrinks(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"))) {
            for (Drink drink : drinks) {
                bw.write(drink.getName() + "," + drink.getPrice());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("음료 정보를 저장하는 동안 오류가 발생했습니다.");
        }
    }

    public Soup findSoup(String name) {
        return soups.stream().filter(s -> s.getName().equals(name)).findFirst().orElse(null);
    }

    public Drink findDrink(String name) {
        return drinks.stream().filter(d -> d.getName().equals(name)).findFirst().orElse(null);
    }
    public Salad findSalad(String name) {
        return salads.stream()
                     .filter(salad -> salad.getName().equals(name))
                     .findFirst()
                     .orElse(null);  
    }

}
