package ir.alirezaivaz.tablericons.demo.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import com.google.android.material.snackbar.Snackbar
import ir.alirezaivaz.tablericons.demo.R

object Utils {
    /**
     * Launch a market intent (e.g rate intent)
     *
     * @param context The context to use.
     * @param view Root view of screen for showing possible snackbar errors
     * @param url The Intent data URL.
     * @param action The Intent action
     */
    fun launchMarketIntent(
        context: Context,
        view: ViewGroup,
        url: String,
        action: String = Intent.ACTION_VIEW,
    ) {
        try {
            val intent = Intent(action, Uri.parse(url))
            context.startActivity(intent)
        } catch (e: Exception) {
            Snackbar.make(
                view,
                R.string.message_share_failed,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Launch a webpage using AndroidX browser
     *
     * @param context The context to use.
     * @param view Root view of screen for showing possible snackbar errors
     * @param url URL of webpage to launch
     */
    fun launchWebpage(
        context: Context,
        view: ViewGroup,
        url: String
    ) {
        try {
            CustomTabsIntent.Builder()
                .build()
                .launchUrl(context, Uri.parse(url))
        } catch (e: Exception) {
            Snackbar.make(
                view,
                R.string.message_share_failed,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}
