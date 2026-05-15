import java.nio.file.*;
import java.util.List;

public class FindTabs {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/Basededatos/hello-view.fxml"));
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("<Tab ")) {
                System.out.println("Line " + (i + 1) + ": " + lines.get(i).trim());
            }
        }
    }
}
