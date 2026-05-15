import re

def refactor_tabs():
    file_path = 'src/main/resources/Basededatos/hello-view.fxml'
    with open(file_path, 'r', encoding='utf-8') as f:
        text = f.read()

    # We want to match all tabs that start with "✏️ Registrar..." or similar. We can match any tab that has the specific VBox structure.
    # Looking at the code, the user has:
    # <VBox> <Label text="REGISTRAR ..." /> <Label text="Complete..." /> </VBox>
    # <VBox style="...white..."> ... children ... </VBox>
    
    pattern = re.compile(
        r'(<Tab text="[^"]*?Registrar[^"]*">.*?<VBox style="-fx-spacing: 15; -fx-padding: 20;">\s*<children>)\s*'
        r'<VBox>\s*'
        r'<Label[^>]*text="([^"]+)"\s*/>\s*'
        r'<Label[^>]*text="([^"]+)"\s*/>\s*'
        r'</VBox>\s*'
        r'<VBox style="-fx-background-color:white;-fx-background-radius:12;-fx-padding:22;-fx-spacing:18; -fx-effect:dropshadow\(gaussian,rgba\(10,61,98,0\.15\),12,0,0,4\);">\s*'
        r'<children>\s*'
        r'<Label[^>]*text="([^"]+)"\s*/>\s*'
        r'([\s\S]*?)\s*'
        r'</children>\s*'
        r'</VBox>\s*'
        r'(</children>\s*</VBox>\s*</ScrollPane>\s*</Tab>)',
        re.DOTALL
    )

    def replacer(match):
        pre_code = match.group(1)
        title = match.group(2)
        subtitle = match.group(3)
        form_title = match.group(4)
        form_content = match.group(5)
        post_code = match.group(6)

        emojis = ["📦", "🛒", "🏷️", "👥", "💹", "⭐", "👨‍🔧", "👤", "🛠️", "📏"]
        import random
        emoji = random.choice(emojis)

        # Build the new structure
        new_content = f'''
                            <!-- NEW CENTRAL CARD LAYOUT -->
                            <HBox alignment="CENTER" VBox.vgrow="ALWAYS" style="-fx-padding: 20;">
                                <HBox style="-fx-background-color:white;-fx-background-radius:15;-fx-effect:dropshadow(gaussian,rgba(10,61,98,0.15),20,0,0,8); -fx-max-width: 900;" minHeight="500" HBox.hgrow="ALWAYS">
                                    
                                    <!-- LEFT PANEL: ILUSTRACION -->
                                    <VBox alignment="CENTER" style="-fx-background-color: linear-gradient(to bottom right, #0a3d62, #1a5276); -fx-background-radius: 15 0 0 15; -fx-padding: 30; -fx-min-width: 250; -fx-max-width: 280;">
                                        <Label style="-fx-font-size: 80px;" text="{emoji}" />
                                        <Label style="-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white; -fx-padding: 20 0 10 0; -fx-text-alignment: CENTER;" wrapText="true" text="{title}" />
                                        <Label wrapText="true" style="-fx-font-size: 13px; -fx-text-fill: #aad4f0; -fx-text-alignment: CENTER;" text="{subtitle}\n\n• Ingrese todos los datos con cuidado.\n• Verifique antes de guardar.\n• Información segura." />
                                    </VBox>

                                    <!-- RIGHT PANEL: FORMULARIO -->
                                    <VBox style="-fx-padding: 40; -fx-spacing: 20;" HBox.hgrow="ALWAYS">
                                        <Label style="-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0a3d62;" text="{form_title}" />
                                        <Separator style="-fx-background-color: #f0f7ff;" />
                                        {form_content}
                                    </VBox>
                                </HBox>
                            </HBox>
'''
        return pre_code + new_content + post_code

    new_text, count = pattern.subn(replacer, text)
    print(f"Replaced {count} instances.")

    with open(file_path, 'w', encoding='utf-8') as f:
        f.write(new_text)

if __name__ == '__main__':
    refactor_tabs()
