import sys
import re

file_path = 'src/main/resources/Basededatos/hello-view.fxml'
with open(file_path, 'r', encoding='utf-8') as f:
    content = f.read()

# Find the VBox declaration
start_vbox_pattern = re.compile(r'<VBox\s+stylesheets="@styles\.css".*?<children>', re.DOTALL)
header_pattern = re.compile(r'\s*<!-- ===== HEADER ===== -->.*?<!-- ===== TAB PANE ===== -->', re.DOTALL)

# Let's replace
content = start_vbox_pattern.sub(
    '<HBox stylesheets="@styles.css"\n'
    '      xmlns="http://javafx.com/javafx/25"\n'
    '      xmlns:fx="http://javafx.com/fxml/1"\n'
    '      fx:controller="besededatos.MainController">\n'
    '    <children>\n\n'
    '        <!-- MENÚ LATERAL -->\n'
    '        <fx:include source="SideMenu.fxml" />\n\n'
    '        <!-- CONTENIDO PRINCIPAL -->\n'
    '        <VBox HBox.hgrow="ALWAYS" style="-fx-background-color: linear-gradient(to bottom, #f0f7ff, #e3f2fd);">\n'
    '            <padding><Insets bottom="15" left="15" right="15" top="15" /></padding>\n'
    '            <children>', content, count=1)

content = header_pattern.sub('                <!-- ===== TAB PANE ===== -->', content, count=1)

# Now find the last </VBox> and replace with </VBox> \n </HBox>
parts = content.rsplit('</VBox>', 1)
if len(parts) == 2:
    content = '</VBox>\n            </children>\n        </VBox>\n    </children>\n</HBox>'.join(parts)

with open(file_path, 'w', encoding='utf-8') as f:
    f.write(content)

print('Done')
