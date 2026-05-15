import java.nio.file.*;
import java.util.regex.*;
import java.util.*;

public class RefactorTabs {
    public static void main(String[] args) throws Exception {
        Path path = Paths.get("src/main/resources/Basededatos/hello-view.fxml");
        String content = new String(Files.readAllBytes(path), "UTF-8");

        String patternStr = "(<Tab text=\"[^\"]*?Registrar[^\"]*\">.*?<VBox style=\"-fx-spacing: 15; -fx-padding: 20;\">\\s*<children>)\\s*" +
                            "<VBox>\\s*" +
                            "<Label[^>]*text=\"([^\"]+)\"\\s*/>\\s*" +
                            "<Label[^>]*text=\"([^\"]+)\"\\s*/>\\s*" +
                            "</VBox>\\s*" +
                            "<VBox style=\"-fx-background-color:white;-fx-background-radius:12;-fx-padding:22;-fx-spacing:18; -fx-effect:dropshadow\\(gaussian,rgba\\(10,61,98,0\\.15\\),12,0,0,4\\);\">\\s*" +
                            "<children>\\s*" +
                            "<Label[^>]*text=\"([^\"]+)\"\\s*/>\\s*" +
                            "([\\s\\S]*?)\\s*" +
                            "</children>\\s*" +
                            "</VBox>\\s*" +
                            "(</children>\\s*</VBox>\\s*</ScrollPane>\\s*</Tab>)";

        Pattern p = Pattern.compile(patternStr, Pattern.DOTALL);
        Matcher m = p.matcher(content);
        
        StringBuffer sb = new StringBuffer();
        int count = 0;
        String[] emojis = {"📦", "🛒", "🏷️", "👥", "💹", "⭐", "👨‍🔧", "👤", "🛠️", "📏", "📐", "💵"};
        Random rand = new Random();

        while (m.find()) {
            count++;
            String preCode = m.group(1);
            String title = m.group(2);
            String subtitle = m.group(3);
            String formTitle = m.group(4);
            String formContent = m.group(5);
            String postCode = m.group(6);
            
            String emoji = emojis[rand.nextInt(emojis.length)];

            String replacement = "\n                            <!-- NEW CENTRAL CARD LAYOUT -->\n" +
                "                            <HBox alignment=\"CENTER\" VBox.vgrow=\"ALWAYS\" style=\"-fx-padding: 20;\">\n" +
                "                                <HBox style=\"-fx-background-color:white;-fx-background-radius:15;-fx-effect:dropshadow(gaussian,rgba(10,61,98,0.15),20,0,0,8); -fx-max-width: 900;\" minHeight=\"500\" HBox.hgrow=\"ALWAYS\">\n" +
                "                                    \n" +
                "                                    <!-- LEFT PANEL: ILUSTRACION -->\n" +
                "                                    <VBox alignment=\"CENTER\" style=\"-fx-background-color: linear-gradient(to bottom right, #0a3d62, #1a5276); -fx-background-radius: 15 0 0 15; -fx-padding: 30; -fx-min-width: 250; -fx-max-width: 280;\">\n" +
                "                                        <Label style=\"-fx-font-size: 80px;\" text=\"" + emoji + "\" />\n" +
                "                                        <Label style=\"-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white; -fx-padding: 20 0 10 0; -fx-text-alignment: CENTER;\" wrapText=\"true\" text=\"" + title + "\" />\n" +
                "                                        <Label wrapText=\"true\" style=\"-fx-font-size: 13px; -fx-text-fill: #aad4f0; -fx-text-alignment: CENTER;\" text=\"" + subtitle + "\\n\\n• Ingrese todos los datos con cuidado.\\n• Verifique antes de guardar.\\n• Información 100% segura.\" />\n" +
                "                                    </VBox>\n" +
                "\n" +
                "                                    <!-- RIGHT PANEL: FORMULARIO -->\n" +
                "                                    <VBox style=\"-fx-padding: 40; -fx-spacing: 20;\" HBox.hgrow=\"ALWAYS\">\n" +
                "                                        <Label style=\"-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0a3d62;\" text=\"" + formTitle + "\" />\n" +
                "                                        <Separator style=\"-fx-background-color: transparent; -fx-border-color: #f0f7ff; -fx-border-width: 1 0 0 0;\" />\n" +
                "                                        " + Matcher.quoteReplacement(formContent) + "\n" +
                "                                    </VBox>\n" +
                "                                </HBox>\n" +
                "                            </HBox>\n";

            m.appendReplacement(sb, Matcher.quoteReplacement(preCode) + replacement + Matcher.quoteReplacement(postCode));
        }
        m.appendTail(sb);

        Files.write(path, sb.toString().getBytes("UTF-8"));
        System.out.println("Replaced " + count + " tabs.");
    }
}
