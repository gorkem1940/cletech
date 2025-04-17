package com.example.playeropener

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class ZoomedWebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)
        setContentView(webView)

        webView.settings.apply {
            javaScriptEnabled = true
            builtInZoomControls = true
            displayZoomControls = false
            setSupportZoom(true)
        }

        webView.webViewClient = WebViewClient()
        webView.setInitialScale(125) // %125 zoom
        webView.loadUrl("https://inmapper.com/zorlucenter/?mode=web&lang=tr&id=ID-245&toTitle=Zorlu%20PSM%20Metro%20Kat%C4%B1&G%20Store")
    }

    override fun onDestroy() {
        super.onDestroy()
        // Hafızayı temizle
        (findViewById<WebView>(android.R.id.content)).destroy()
    }
}
