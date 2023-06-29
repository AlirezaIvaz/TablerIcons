package ir.alirezaivaz.tablericons

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

object TablerIcons {
    /**
     * Get icon resource id from it's name
     *
     * @param context Application or activity context (recommended: activity)
     * @param icon Name of desired icon
     * @return Icon resource id
     * @throws Resources.NotFoundException
     * @author Alireza Ivaz
     * @since 1.0.0
     */
    @SuppressLint("DiscouragedApi")
    @JvmStatic
    @DrawableRes
    fun getResId(context: Context, icon: String): Int {
        val resourceId = context.resources.getIdentifier(icon, "drawable", context.packageName)
        if (resourceId != 0) {
            return resourceId
        } else {
            throw Resources.NotFoundException("Icon with name '${icon}' not found!")
        }
    }

    /**
     * Get icon as drawable from it's name
     *
     * @param context Application or activity context (recommended: activity)
     * @param icon Name of desired icon
     * @return Icon drawable or `null` if not found
     * @author Alireza Ivaz
     * @since 1.0.0
     */
    @JvmStatic
    fun getDrawable(context: Context, icon: String): Drawable? {
        val resourceId = getResId(context, icon)
        return try {
            context.getDrawable(resourceId)
        } catch (e: Exception) {
            null
        }
    }
}