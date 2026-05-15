import os
import glob

base_dir = r"c:\Users\Magde\IdeaProjects\Pa\src\main\java\besededatos"
resources_dir = r"c:\Users\Magde\IdeaProjects\Pa\src\main\resources\Basededatos"
module_info = r"c:\Users\Magde\IdeaProjects\Pa\src\main\java\module-info.java"

models = ["Cliente.java", "ClienteCompleto.java", "Compra.java", "ConfiguracionItem.java", "Desglose.java", "Instalacion.java", "InventarioItem.java", "MovimientoContable.java", "NominaItem.java", "OrdenTrabajo.java", "TecnicoCompleto.java", "Usuario.java"]
controllers = ["ClienteController.java", "MainController.java", "MenuController.java"]
config = ["Conexion.java", "Conexion2.java", "SessionManager.java"]

dirs_to_create = ["models", "controllers", "config"]

for d in dirs_to_create:
    os.makedirs(os.path.join(base_dir, d), exist_ok=True)

def read_file(path):
    with open(path, 'r', encoding='utf-8') as f:
        return f.read()

def write_file(path, content):
    with open(path, 'w', encoding='utf-8') as f:
        f.write(content)

for file in models:
    src = os.path.join(base_dir, file)
    dst = os.path.join(base_dir, "models", file)
    if os.path.exists(src):
        content = read_file(src)
        content = content.replace("package besededatos;", "package besededatos.models;\n\nimport besededatos.config.*;\n")
        write_file(dst, content)
        os.remove(src)

for file in controllers:
    src = os.path.join(base_dir, file)
    dst = os.path.join(base_dir, "controllers", file)
    if os.path.exists(src):
        content = read_file(src)
        content = content.replace("package besededatos;", "package besededatos.controllers;\n\nimport besededatos.models.*;\nimport besededatos.config.*;\nimport besededatos.utils.*;\n")
        write_file(dst, content)
        os.remove(src)

for file in config:
    src = os.path.join(base_dir, file)
    dst = os.path.join(base_dir, "config", file)
    if os.path.exists(src):
        content = read_file(src)
        content = content.replace("package besededatos;", "package besededatos.config;\n\nimport besededatos.models.*;\n")
        write_file(dst, content)
        os.remove(src)

for f in os.listdir(base_dir):
    if f.endswith(".java"):
        src = os.path.join(base_dir, f)
        content = read_file(src)
        if "package besededatos;" in content:
            content = content.replace("package besededatos;", "package besededatos;\n\nimport besededatos.models.*;\nimport besededatos.controllers.*;\nimport besededatos.config.*;\n")
            write_file(src, content)

for fxml in glob.glob(os.path.join(resources_dir, "*.fxml")):
    content = read_file(fxml)
    content = content.replace('fx:controller="besededatos.MainController"', 'fx:controller="besededatos.controllers.MainController"')
    content = content.replace('fx:controller="besededatos.ClienteController"', 'fx:controller="besededatos.controllers.ClienteController"')
    content = content.replace('fx:controller="besededatos.MenuController"', 'fx:controller="besededatos.controllers.MenuController"')
    write_file(fxml, content)

if os.path.exists(module_info):
    mod_content = read_file(module_info)
    if "opens besededatos.controllers" not in mod_content:
        new_opens = "    opens besededatos.controllers to javafx.fxml, javafx.graphics, javafx.base;\n    opens besededatos.models to javafx.base;\n    exports besededatos.controllers;\n    exports besededatos.models;\n    exports besededatos.config;"
        mod_content = mod_content.replace("    opens besededatos to javafx.fxml, javafx.graphics, javafx.base;", f"    opens besededatos to javafx.fxml, javafx.graphics, javafx.base;\n{new_opens}")
        write_file(module_info, mod_content)

print("Refactorizado MVC con exito")
