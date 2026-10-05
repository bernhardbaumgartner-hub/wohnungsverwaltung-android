# Wohnungsverwaltung Android App

Android APK-Konfiguration für die Wohnungsverwaltung Web-App.

## Projektstruktur

```
wohnungsverwaltung-android/
├── app/
│   ├── build.gradle          # App-Modul Build-Konfiguration
│── src/
│   ├── main/
│   │   ├── AndroidManifest.xml  # Android Manifest mit Berechtigungen
│   │   ├── java/
│   │   │   └── de/baumgartner/wohnungsverwaltung/
│   │   │       └── MainActivity.kt  # MainActivity mit WebView
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml  # Layout mit WebView
│   │   │   ├── values/
│   │   │   │   ├── strings.xml  # String-Ressourcen
│   │   │   │   ├── themes.xml   # Theme-Konfiguration
│   │   │   │   └── colors.xml   # Farben
│   │   │   ├── drawable/        # ICO-App-Icons
│   │   │   └── mipmap-*/        # Launcher-Icons (verschiedene Größen)
│   │   └── assets/              # Web-App-Dateien
│   │       ├── index.html       # Haupt-Webseite
│   │       ├── datenbank.json   # Datenbank-Datei
│   │       ├── auswertung.html
│   │       ├── steuer_auswertung.html
│   │       ├── nebenkostenabrechnung.html
│   │       └── Hausgeld_Gesamtpruefung.html
├── build.gradle                # Projekt-level Build-Konfiguration
├── settings.gradle           # Settings-Konfiguration
├── gradle/                   # Gradle Wrapper
└── gradle.properties         # Gradle Properties
```

## Bauen und Ausführen

### Android Studio öffnen

1. Öffne Android Studio
2. Wähle "Open" und navigiere zu: `wohnungsverwaltung-android/`
3. Lasse Android Studio die Gradle-Builds synchronisieren

### Projekt ausführen

1. Verbinde ein Android-Gerät per USB mit aktiviertem "USB-Debugging"
   - Oder starte den Android Emulator über AVD Manager

2. Klicke in Android Studio auf den grünen "Run"-Button (Play-Symbol)
   - Oder drücke `Shift + F10`

3. Die App sollte starten und die `index.html` im WebView anzeigen

## Web-App-Integration

Die enthaltenen Web-App-Dateien wurden in `app/src/main/assets/wohnungsverwaltung/` kopiert:

- `index.html` - Haupt-Dashboard mit Mieterübersicht und KPIs
- `auswertung.html` - Kontoverwaltung-Auswertung
- `steuer_auswertung.html` - Finanzamt/Steuer-Auswertung
- `nebenkostenabrechnung.html` - Betriebskostenabrechnung
- `Hausgeld_Gesamtpruefung.html` - Hausgeldabrechnungen (WEG)
- `datenbank.json` - JSON-Datenbank mit Buchungen und Kontoständen

### Anpassungen für Android WebView

Die folgenden Änderungen wurden vorgenommen, um die Web-App im Android WebView zum Laufen zu bringen:

1. **Datenbank-Laden**: Die `datenbank.json` wird nun im Assets-Ordner geladen. Die MainActivity.kt enthält eine Methode `copyDatabaseIfNeeded()`, die die Datenbank bei Bedarf in den internen Speicher kopiert.

2. **URL-Behandlung**: Die `MainActivity.kt` behandelt relative URLs im HTML korrekt, indem sie diese in Asset-Pfade umwandelt (z.B. `../Betriebskostenabrechnung/nebenkostenabrechnung.html` wird zu `file:///android_asset/wohnungsverwaltung/Betriebskostenabrechnung/nebenkostenabrechnung.html`).

3. **JavaScript-Enabled**: JavaScript ist aktiviert und die notwendigen WebView-Einstellungen sind konfiguriert.

4. **Dateizugriff**: File-Access und Universal-Access-from-File-URLs sind aktiviert, um lokalen Dateizugriff zu ermöglichen.

5. **Kein Localhost-Server**: Die `shutdownServer()`-Funktion, die auf `http://localhost:8080/shutdown` zugreift, funktioniert im reinen WebView nicht. In der APK-Version ist dies durch einen einfachen "Beenden"-Button ersetzt wurde (oder müsste durch einen Android Service ersetzt werden).

## Wichtige Hinweise

### Icons & Branding
- Die `GRAPH.ICO`-Datei wurde als App-Icon verwendet
- Android erfordert spezifische Icon-Größen (mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi)
- Für die Veröffentlichung solltest du durch echte App-Icons ersetzen

### Datenbank-Pfad
- Die `datenbank.json` enthält die Mieter- und Finanzdaten
- Bei Erststart wird die Datei aus den Assets in den internen Speicher kopiert
- Änderungen an Daten bleiben in der App-Speicherung erhalten

### Module
Die folgenden Module sind enthalten und über Navigation erreichbar:
1. **Startseite** (`index.html`) - Übersicht mit KPIs und Mieterkarten
2. **Kontoverwaltung** (`auswertung.html`) - Bankbuchungen und Auswertung
3. **Steuer-Auswertung** (`steuer_auswertung.html`) - Anlage V Auswertung
4. **Nebenkostenabrechnung** (`nebenkostenabrechnung.html`) - Abrechnung für Mieter
5. **Hausgeldabrechnung** (`Hausgeld_Gesamtpruefung.html`) - WEG-Verwterabrechnungen

## Build-Problemlösung

### Häufige Probleme:

1. **"Cannot resolve symbol" Fehler**
   - Führe "Sync Project with Gradle Files" durch
   - Stelle sicher, dass Android SDK installiert ist

2. **WebView lädt nicht**
   - Überprüfe, dass `AndroidManifest.xml` die Internet-Berechtigung enthält
   - Stelle sicher, dass die HTML-Dateien im `assets/-Ordner` korrekt liegen

3. **Datenbank kann nicht geladen werden**
   - Stelle sicher, dass `datenbank.json` im `app/src/main/assets/wohnungsverwaltung/` liegt
   - Überprüfe das Format der JSON-Datei

4. **Relative Pfade funktionieren nicht**
   - Die HTML-Dateien verwenden relative Pfade (`../...`), die im Web funktionieren
   - In Android müssen diese zu absoluten Asset-Pfaden umgewandelt werden
   - Die `MainActivity.kt` übernimmt dies automatisch für bekannte Dateien

## Anpassungen

Um die App weiter anzupassen:

1. **Farben & Theme**: Bearbeite `res/values/colors.xml` und `res/values/themes.xml`
2. **App-Name**: Bearbeite `res/values/strings.xml`
3. **Icons**: Ersetze die ICO-Dateien durch eigene Grafiken in den mipmap-Verzeichnissen
4. **Modulen**: Füge weitere HTML-Dateien dem `assets/wohnungsverwaltung/-Ordner` hinzu und aktualisiere die Navigation in `index.html`
5. **Datenbank**: Ersetze `datenbank.json` durch deine eigene Datenbank-Datei

## Verteilung

Zum Erstellen eines signierten APKs für die Verteilung:

1. In Android Studio: `Build` → `Generate Signed Bundle/APK`
2. Wähle "Android APK" und folge dem Wizard
3. Erstelle oder wähle einen Keystore (Signaturschlüssel)
4. Wähle `minSdkVersion: 21` für breite Kompatibilität
5. Das erstellte APK befindet sich in `app/build/outputs/apk/release/`