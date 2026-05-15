import re

with open('src/main/resources/Basededatos/hello-view.fxml', encoding='utf-8') as f:
    text = f.read()

matches = re.finditer(r'<Tab text=\"[^\"]*?\(Registro\)\".*?>[\s\S]*?</Tab>', text)
count = 0
for m in matches:
    print(f'\n--- Tab {count} ---')
    print(m.group(0)[:1000])
    count += 1
