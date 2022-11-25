package uz.mamadaliev.simplewebview

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.webkit.WebViewClient
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import uz.mamadaliev.simplewebview.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val shared by lazy {
        SharedPrefrenceHelper(this)
    }
    var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        webViewSetup()
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun webViewSetup() {
        binding.webView.webViewClient = WebViewClient()

        binding.apply {
            shared.getLastUrl()?.let { webView.loadUrl(it) }
            webView.settings.javaScriptEnabled = true

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                webView.settings.safeBrowsingEnabled = true
            }
            btnHome.setOnClickListener {
                binding.webView.loadUrl("https://www.google.com/")
            }
        }



        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if (query.endsWith(".uz", true) || query.endsWith(".ru",
                            true) || query.endsWith(".com", true) || query.endsWith(".net",
                            true) || query.endsWith(".org", true)
                    ) {
                        binding.webView.loadUrl("https://$query")
                    } else {
                        binding.webView.loadUrl("https://www.google.com/search?q=$query")
                    }
                    binding.search.setQuery("", false);
                    binding.search.clearFocus();
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }


    override fun onBackPressed() {
        binding.webView.url?.let { shared.setLastUrl(it) }
        if (binding.webView.canGoBack()) binding.webView.goBack()
        if (doubleBackToExitPressedOnce) {

            super.onBackPressed()
            return
        }
        doubleBackToExitPressedOnce = true
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}