## Reversi 
![alt text](https://www.coolmathgames.com/sites/default/files/Reversi%20OG%20Image.png)

# Projektet virker ikke i VS-code
Projektet kører på JAVAFx, så hvis du gerne vil bruge VS-code, så skal du selv have en mappe med settings.json. Følg linket eller skriv til mig, hvis der er problemer. VS-code mappen er her nemlig ikke pga. stien er anderledes for alle brugere.
https://dtu.bogoe.eu/02121/javafx/

Har ikke prøvet at kode det i andre IDE og Editors end VS-code, så følg linket hvis du skal have hjælp.

# JAR-filen gider ikke at åbne
Brug terminalen og skriv kommandoen:
"java" --module-path "\path\javafx-sdk-19\lib" --add-modules javafx.controls,javafx.fxml,javafx.media --enable-preview -jar Reversi.jar

Du skal nok installere JavaFx-sdk før det virker på: [Download](https://gluonhq.com/products/javafx/)
