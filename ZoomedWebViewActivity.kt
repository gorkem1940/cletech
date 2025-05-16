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
        webView.loadUrl("https://webnav.poilabs.com/?placeId=35cc7eba-1187-39e1-0b54-5bd017227c3f&language=en")
    }

    override fun onDestroy() {
        super.onDestroy()
        //Hafızayı temizle
        (findViewById<WebView>(android.R.id.content)).destroy()
    }
}
