$baseDir = "src\main\java\besededatos"
$resourcesDir = "src\main\resources\Basededatos"
$moduleInfo = "src\main\java\module-info.java"

$models = @("Cliente.java", "ClienteCompleto.java", "Compra.java", "ConfiguracionItem.java", "Desglose.java", "Instalacion.java", "InventarioItem.java", "MovimientoContable.java", "NominaItem.java", "OrdenTrabajo.java", "TecnicoCompleto.java", "Usuario.java")
$controllers = @("ClienteController.java", "MainController.java", "MenuController.java")
$config = @("Conexion.java", "Conexion2.java", "SessionManager.java")

New-Item -ItemType Directory -Force -Path "$baseDir\models" | Out-Null
New-Item -ItemType Directory -Force -Path "$baseDir\controllers" | Out-Null
New-Item -ItemType Directory -Force -Path "$baseDir\config" | Out-Null

foreach ($file in $models) {
    $src = "$baseDir\$file"
    $dst = "$baseDir\models\$file"
    if (Test-Path $src) {
        $content = [System.IO.File]::ReadAllText($src)
        $content = $content.Replace("package besededatos;", "package besededatos.models;`n`nimport besededatos.config.*;`n")
        [System.IO.File]::WriteAllText($dst, $content)
        Remove-Item $src
    }
}

foreach ($file in $controllers) {
    $src = "$baseDir\$file"
    $dst = "$baseDir\controllers\$file"
    if (Test-Path $src) {
        $content = [System.IO.File]::ReadAllText($src)
        $content = $content.Replace("package besededatos;", "package besededatos.controllers;`n`nimport besededatos.models.*;`nimport besededatos.config.*;`nimport besededatos.utils.*;`n")
        [System.IO.File]::WriteAllText($dst, $content)
        Remove-Item $src
    }
}

foreach ($file in $config) {
    $src = "$baseDir\$file"
    $dst = "$baseDir\config\$file"
    if (Test-Path $src) {
        $content = [System.IO.File]::ReadAllText($src)
        $content = $content.Replace("package besededatos;", "package besededatos.config;`n`nimport besededatos.models.*;`n")
        [System.IO.File]::WriteAllText($dst, $content)
        Remove-Item $src
    }
}

$remaining = Get-ChildItem -Path $baseDir -Filter "*.java" -File
foreach ($file in $remaining) {
    $src = $file.FullName
    $content = [System.IO.File]::ReadAllText($src)
    if ($content.Contains("package besededatos;")) {
        $content = $content.Replace("package besededatos;", "package besededatos;`n`nimport besededatos.models.*;`nimport besededatos.controllers.*;`nimport besededatos.config.*;`n")
        [System.IO.File]::WriteAllText($src, $content)
    }
}

$fxmls = Get-ChildItem -Path $resourcesDir -Filter "*.fxml" -File
foreach ($fxml in $fxmls) {
    $src = $fxml.FullName
    $content = [System.IO.File]::ReadAllText($src)
    $content = $content.Replace('fx:controller="besededatos.MainController"', 'fx:controller="besededatos.controllers.MainController"')
    $content = $content.Replace('fx:controller="besededatos.ClienteController"', 'fx:controller="besededatos.controllers.ClienteController"')
    $content = $content.Replace('fx:controller="besededatos.MenuController"', 'fx:controller="besededatos.controllers.MenuController"')
    [System.IO.File]::WriteAllText($src, $content)
}

if (Test-Path $moduleInfo) {
    $modContent = [System.IO.File]::ReadAllText($moduleInfo)
    if (-not $modContent.Contains("opens besededatos.controllers")) {
        $newOpens = "    opens besededatos.controllers to javafx.fxml, javafx.graphics, javafx.base;`n    opens besededatos.models to javafx.base;`n    exports besededatos.controllers;`n    exports besededatos.models;`n    exports besededatos.config;`n    exports besededatos.utils;"
        $modContent = $modContent.Replace("    opens besededatos to javafx.fxml, javafx.graphics, javafx.base;", "    opens besededatos to javafx.fxml, javafx.graphics, javafx.base;`n$newOpens")
        [System.IO.File]::WriteAllText($moduleInfo, $modContent)
    }
}

Write-Output "Refactorizado MVC con exito con PowerShell"
