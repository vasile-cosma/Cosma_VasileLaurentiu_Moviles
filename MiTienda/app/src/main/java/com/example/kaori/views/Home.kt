package com.example.kaori.views

import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

/*
Fuente: https://medium.com/@oktaygenc/integrating-webview-in-jetpack-compose-managing-web-content-in-modern-android-1803c56a2da7
 */
@Composable
fun Home(url: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                loadUrl(url)
            }
        }
    )
}