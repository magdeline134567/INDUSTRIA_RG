import java.nio.file.*;
import java.util.regex.*;
import java.util.*;

public class RefactorAllTabs {
    public static void main(String[] args) throws Exception {
        Path path = Paths.get("src/main/resources/Basededatos/hello-view.fxml");
        String content = new String(Files.readAllBytes(path), "UTF-8");

        // We find each <Tab text="...Registrar..."> block
        Pattern tabPattern = Pattern.compile("<Tab text=\"[^\"]*?Registrar[^\"]*\">[\\s\\S]*?</Tab>");
        Matcher tabMatcher = tabPattern.matcher(content);
        
        StringBuffer sb = new StringBuffer();
        int count = 0;
        String[] emojis = {"📦", "🛒", "🏷️", "👥", "💹", "⭐", "👨‍🔧", "👤", "🛠️", "📏", "📐", "💵"};
        Random rand = new Random();

        while (tabMatcher.find()) {
            String tabContent = tabMatcher.group();

            // Only process tabs that haven't been refactored yet.
            if (tabContent.contains("NEW CENTRAL CARD LAYOUT")) {
                tabMatcher.appendReplacement(sb, Matcher.quoteReplacement(tabContent));
                continue;
            }

            // Extract the tab tag itself
            Matcher tagMatcher = Pattern.compile("<Tab text=\"([^\"]*)\">").matcher(tabContent);
            String tabName = "";
            if (tagMatcher.find()) tabName = tagMatcher.group(1);

            // Extract the first two Labels that define the Title and Subtitle.
            // These usually look like: <Label style="...22px..." text="TITLE" /> <Label style="...12px..." text="SUBTITLE" />
            Pattern labelPattern = Pattern.compile("<Label[^>]*text=\"([^\"]+)\"\\s*/>");
            Matcher lMatcher = labelPattern.matcher(tabContent);
            
            String title = tabName;
            String subtitle = "Complete el formulario con los detalles requeridos.";
            
            int labelCount = 0;
            while(lMatcher.find()) {
                if(labelCount == 0 && lMatcher.group(1).length() > 5) title = lMatcher.group(1);
                if(labelCount == 1 && lMatcher.group(1).length() > 5) subtitle = lMatcher.group(1);
                labelCount++;
                if(labelCount >= 2) break;
            }

            // Find the main form container. It's usually a VBox with white background
            Pattern formBoxPattern = Pattern.compile("<VBox style=\"-fx-background-color:white;-fx-background-radius:12;-fx-padding:22;-fx-spacing:18; -fx-effect:dropshadow\\(gaussian,rgba\\(10,61,98,0\\.15\\),12,0,0,4\\);\">\\s*<children>([\\s\\S]*?)</children>\\s*</VBox>");
            Matcher fMatcher = formBoxPattern.matcher(tabContent);
            
            String formContent = "";
            if (fMatcher.find()) {
                formContent = fMatcher.group(1);
            } else {
                // If the specific style doesn't match, maybe the style is slightly different. Let's try a fallback
                Pattern fallbackPattern = Pattern.compile("<GridPane[\\s\\S]*?</GridPane>");
                Matcher gbMatcher = fallbackPattern.matcher(tabContent);
                if (gbMatcher.find()) {
                    formContent = gbMatcher.group(0);
                    // Add the button if there is an HBox with buttons below it
                    Pattern btnPattern = Pattern.compile("<HBox[^>]*alignment=\"(?:CENTER|CENTER_RIGHT)\"[^>]*>\\s*<children>\\s*<Button[\\s\\S]*?</HBox>");
                    Matcher btnMatcher = btnPattern.matcher(tabContent);
                    if (btnMatcher.find()) {
                        formContent += "\n" + btnMatcher.group(0);
                    }
                } else {
                    System.out.println("Could not parse form for tab: " + tabName);
                    tabMatcher.appendReplacement(sb, Matcher.quoteReplacement(tabContent));
                    continue;
                }
            }
            
            String emoji = emojis[rand.nextInt(emojis.length)];

            String newContent = "<Tab text=\"" + tabName + "\">\n" +
                "                <ScrollPane fitToWidth=\"true\" hbarPolicy=\"NEVER\" vbarPolicy=\"AS_NEEDED\" style=\"-fx-background-color: transparent; -fx-background: transparent;\">\n" +
                "                            <!-- NEW CENTRAL CARD LAYOUT -->\n" +
                "                            <HBox alignment=\"CENTER\" VBox.vgrow=\"ALWAYS\" style=\"-fx-padding: 30;\">\n" +
                "                                <HBox style=\"-fx-background-color:white;-fx-background-radius:20;-fx-effect:dropshadow(gaussian,rgba(10,61,98,0.1),20,0,0,8); -fx-max-width: 950;\" minHeight=\"500\" HBox.hgrow=\"ALWAYS\">\n" +
                "                                    \n" +
                "                                    <!-- LEFT PANEL: ILUSTRACION -->\n" +
                "                                    <VBox alignment=\"CENTER\" style=\"-fx-background-color: linear-gradient(to bottom right, #0a3d62, #1a5276); -fx-background-radius: 20 0 0 20; -fx-padding: 30; -fx-min-width: 280; -fx-max-width: 300; -fx-spacing: 15;\">\n" +
                "                                        <Label style=\"-fx-font-size: 80px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);\" text=\"" + emoji + "\" />\n" +
                "                                        <Label style=\"-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white; -fx-text-alignment: CENTER;\" wrapText=\"true\" text=\"" + title + "\" />\n" +
                "                                        <Label wrapText=\"true\" style=\"-fx-font-size: 13px; -fx-text-fill: #aad4f0; -fx-text-alignment: CENTER; -fx-line-spacing: 4px;\" text=\"" + subtitle + "\\n\\n• Revise todos los campos antes de guardar.\\n• No deje campos obligatorios vacíos.\\n• La conexión es segura.\" />\n" +
                "                                    </VBox>\n" +
                "\n" +
                "                                    <!-- RIGHT PANEL: FORMULARIO -->\n" +
                "                                    <VBox style=\"-fx-padding: 40; -fx-spacing: 25;\" HBox.hgrow=\"ALWAYS\">\n" +
                "                                        <Label style=\"-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0a3d62;\" text=\"Complete el formulario\" />\n" +
                "                                        <Separator style=\"-fx-background-color: transparent; -fx-border-color: #f0f7ff; -fx-border-width: 1 0 0 0;\" />\n" +
                "                                        " + Matcher.quoteReplacement(formContent) + "\n" +
                "                                    </VBox>\n" +
                "                                </HBox>\n" +
                "                            </HBox>\n" +
                "                </ScrollPane>\n" +
                "            </Tab>";

            count++;
            tabMatcher.appendReplacement(sb, Matcher.quoteReplacement(newContent));
        }
        tabMatcher.appendTail(sb);

        Files.write(path, sb.toString().getBytes("UTF-8"));
        System.out.println("Replaced " + count + " tabs successfully.");
    }
}
