$path = "src\main\resources\Basededatos\hello-view.fxml"
$content = [System.IO.File]::ReadAllText($path)

# Replace top VBox with HBox and include SideMenu
$pattern1 = '(?s)<VBox stylesheets="@styles\.css".*?<children>'
$replacement1 = @"
<HBox stylesheets="@styles.css"
      xmlns="http://javafx.com/javafx/25"
      xmlns:fx="http://javafx.com/fxml/1"
      fx:controller="besededatos.MainController">
    <children>

        <!-- MENÚ LATERAL -->
        <fx:include source="SideMenu.fxml" />

        <!-- CONTENIDO PRINCIPAL -->
        <VBox HBox.hgrow="ALWAYS" style="-fx-background-color: linear-gradient(to bottom, #f0f7ff, #e3f2fd);">
            <padding><Insets bottom="15" left="15" right="15" top="15" /></padding>
            <children>
"@

# Note: The above replacement string needs to replace the matches.
$regex = [System.Text.RegularExpressions.Regex]::new($pattern1)
$content = $regex.Replace($content, $replacement1, 1)

# Remove HEADER and NAVIGATION BAR
$pattern2 = '(?s)\s*<!-- ===== HEADER ===== -->.*?<!-- ===== TAB PANE ===== -->'
$replacement2 = @"

            <!-- ===== TAB PANE ===== -->
"@
$regex2 = [System.Text.RegularExpressions.Regex]::new($pattern2)
$content = $regex2.Replace($content, $replacement2, 1)

# Replace last </VBox>
$pattern3 = '(?s)</VBox>\s*$'
$replacement3 = @"
        </VBox>
    </children>
</HBox>
"@
$regex3 = [System.Text.RegularExpressions.Regex]::new($pattern3)
$content = $regex3.Replace($content, $replacement3, 1)

[System.IO.File]::WriteAllText($path, $content)
Write-Output "Done"
