# Proguard rules for Wohnungsverwaltung Android App
# Keep the WebView classes and JavaScript interface
-keep public class * extends android.webkit.WebViewClient {
    public <init>(android.webkit.WebView);
}
-keep public class * extends android.webkit.WebSettings {
    public <init>(android.webkit.WebView);
}

# Keep the MainActivity class
-keep public class de.baumgartner.wohnungsverwaltung.MainActivity {
    public <init>(android.os.Bundle);
}
