import java.nio.file.*;
import java.util.regex.*;
import java.util.*;

public class RestoreTabs {
    public static String getFormContent(String tabContent) {
        String tag = "<VBox style=\"-fx-background-color:white";
        int startIdx = tabContent.indexOf(tag);
        if (startIdx == -1) return "";
        
        int current = startIdx;
        int depth = 0;
        int endIdx = -1;
        while(current < tabContent.length()) {
            int nextOpen = tabContent.indexOf("<VBox", current);
            int nextClose = tabContent.indexOf("</VBox>", current);
            
            if (nextClose == -1) break;
            
            if (nextOpen != -1 && nextOpen < nextClose) {
                depth++;
                current = nextOpen + 5;
            } else {
                depth--;
                current = nextClose + 7;
                if (depth == 0) {
                    endIdx = nextClose;
                    break;
                }
            }
        }
        
        if (endIdx != -1) {
            String formStr = tabContent.substring(startIdx + tag.length(), endIdx);
            int childrenStart = formStr.indexOf("<children>");
            if (childrenStart != -1) {
                int lastChildren = formStr.lastIndexOf("</children>");
                if (lastChildren != -1) {
                    return formStr.substring(childrenStart + 10, lastChildren);
                }
            }
            return formStr.substring(formStr.indexOf(">") + 1);
        }
        return "";
    }

    public static void main(String[] args) throws Exception {
        Path compPath = Paths.get("src/main/resources/Basededatos/hello-view-responsive-COMPLETO.txt");
        Path currPath = Paths.get("src/main/resources/Basededatos/hello-view.fxml");
        String completo = new String(Files.readAllBytes(compPath), "UTF-8");
        String current = new String(Files.readAllBytes(currPath), "UTF-8");

        Pattern p = Pattern.compile("<Tab text=\"([^\"]*?Registrar[^\"]*)\">([\\s\\S]*?)</Tab>");
        Matcher m = p.matcher(completo);
        
        String[] emojis = {"📦", "🛒", "🏷️", "👥", "💹", "⭐", "👨‍🔧", "👤", "🛠️", "📏", "📐", "💵"};
        Random rand = new Random();
        int count = 0;

        while(m.find()) {
            String tabName = m.group(1);
            String tabContent = m.group();
            
            String formContent = getFormContent(tabContent);
            if (formContent.isEmpty()) {
                System.out.println("Could not parse form for: " + tabName);
                continue;
            }
            
            Matcher lMatcher = Pattern.compile("<Label[^>]*text=\"([^\"]+)\"\\s*/>").matcher(tabContent);
            String title = tabName;
            String subtitle = "Complete el formulario con los detalles requeridos.";
            int lCount = 0;
            while(lMatcher.find()) {
                if(lCount == 0 && lMatcher.group(1).length() > 5) title = lMatcher.group(1);
                if(lCount == 1 && lMatcher.group(1).length() > 5) subtitle = lMatcher.group(1);
                lCount++;
                if (lCount >= 2) break;
            }
            
            String emoji = emojis[rand.nextInt(emojis.length)];
            
            String newTab = "<Tab text=\"" + tabName + "\">\n" +
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
            
            // Replace in current
            current = current.replaceAll("(?s)<Tab text=\"" + Pattern.quote(tabName) + "\">[\\s\\S]*?</Tab>", Matcher.quoteReplacement(newTab));
            count++;
        }
        
        Files.write(currPath, current.getBytes("UTF-8"));
        System.out.println("Restored and replaced " + count + " tabs.");
    }
}
