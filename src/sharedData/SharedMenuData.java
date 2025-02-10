package sharedData;

import java.util.ArrayList;
import java.util.List;
import product.Drink;
import product.Salad;
import product.SaladSet;
import product.Soup;

public class SharedMenuData {

    private static SharedMenuData instance;
    private List<Salad> salads;
    private List<SaladSet> saladSets;
    private List<Soup> soups;
    private List<Drink> drinks;

    private SharedMenuData() {
        salads = new ArrayList<>();
        saladSets = new ArrayList<>();
        soups = new ArrayList<>();
        drinks = new ArrayList<>();
    }

    public static SharedMenuData getInstance() {
        if (instance == null) {
            instance = new SharedMenuData();
        }
        return instance;
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
