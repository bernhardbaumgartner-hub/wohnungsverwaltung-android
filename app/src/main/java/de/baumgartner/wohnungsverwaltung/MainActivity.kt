package de.baumgartner.wohnungsverwaltung

import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private val databaseFileName = "datenbank.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)

        // Enable JavaScript and necessary settings
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.setAllowFileAccess(true)
        webSettings.setAllowFileAccessFromFileURLs(true)
        webSettings.setAllowUniversalAccessFromFileURLs(true)
        webSettings.setSupportZoom(true)
        webView.setBuiltInZoomControls(true)

        // Configure WebViewClient to handle URL loading
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest?): Boolean {
                val url = request?.url?.toString()
                return if (url != null) {
                    loadUrlWithAssetsHandling(view, url)
                    true
                } else {
                    false
                }
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                if (url != null) {
                    loadUrlWithAssetsHandling(view, url)
                    return true
                }
                return false
            }
        }

        // Copy database to app's internal storage if needed and load initial URL
        copyDatabaseIfNeeded()
        webView.loadUrl("file:///android_asset/wohnungsverwaltung/index.html")
    }

    private fun loadUrlWithAssetsHandling(view: WebView, url: String) {
        // Handle local file URLs
        if (url.startsWith("file:///android_asset/")) {
            view.loadUrl(url)
        } else if (url.startsWith("http://") || url.startsWith("https://")) {
            view.loadUrl(url)
        } else {
            // Handle relative URLs by prefixing with assets path
            val assetUrl = "file:///android_asset/wohnungsverwaltung/$url"
            view.loadUrl(assetUrl)
        }
    }

    private fun copyDatabaseIfNeeded() {
        try {
            // Try to read from assets first
            val input = assets.open("wohnungsverwaltung/$databaseFileName")
            val reader = BufferedReader(InputStreamReader(input))
            val jsonContent = reader.readText()
            reader.close()
            input.close()

            // Save to app's internal storage for read/write access
            val file = File(filesDir, databaseFileName)
            file.writeText(jsonContent)
        } catch (e: Exception) {
            e.printStackTrace()
            // If assets file not found, we'll rely on web fetch fallback
        }
    }
}