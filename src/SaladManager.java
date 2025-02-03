import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaladManager {
    private static final String SALAD_FILE = "salads.txt";
    private static SaladManager instance;
    private List<Salad> salads;

    private SaladManager() {
        this.salads = loadSalads();  // 파일에서 데이터 로드
    }

    public static SaladManager getInstance() {
        if (instance == null) {
            instance = new SaladManager();
        }
        return instance;
    }

    public void addSalad(Salad salad) {
        salads.add(salad);
        saveSalads();
    }

    public void removeSalad(int productId) {
        salads.removeIf(salad -> salad.getProductId() == productId);
        saveSalads();
    }

    public List<Salad> getSalads() {
        return salads;
    }

    // 상품 번호로 샐러드 찾기
    public Salad findSaladByProductId(int productId) {
        for (Salad salad : salads) {
            if (salad.getProductId() == productId) {
                return salad;
            }
        }
        return null;
    }

    // 이름으로 샐러드 찾기
    public Salad findSaladByName(String name) {
        for (Salad salad : salads) {
            if (salad.getName().equals(name)) {
                return salad;
            }
        }
        return null;
    }

    private List<Salad> loadSalads() {
        List<Salad> loadedSalads = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SALAD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int productId = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    int price = Integer.parseInt(parts[2].trim());
                    loadedSalads.add(new Salad(productId, name, price));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("저장된 샐러드 정보가 없습니다.");
        } catch (IOException e) {
            System.out.println("샐러드 정보를 불러오는 동안 오류가 발생했습니다.");
        }
        return loadedSalads;
    }

    private void saveSalads() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SALAD_FILE))) {
            for (Salad salad : salads) {
                writer.write(salad.getProductId() + "," + salad.getName() + "," + salad.getPrice());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("샐러드 정보를 저장하는 동안 오류가 발생했습니다.");
        }
    }
}
