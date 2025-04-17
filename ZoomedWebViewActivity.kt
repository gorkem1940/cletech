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
        webView.loadUrl("https://www.midtown.com.tr/24207-2/")
    }

    override fun onDestroy() {
        super.onDestroy()
        // Hafızayı temizle
        (findViewById<WebView>(android.R.id.content)).destroy()
    }
}
