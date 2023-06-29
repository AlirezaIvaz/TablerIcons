package ir.alirezaivaz.tablericons

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
            AppCompatResources.getDrawable(context, resourceId)
        } catch (e: Exception) {
            null
        }
    }
}

/**
 * Sets a tabler icon as the content of this ImageView.
 *
 * @param icon Name of desired icon
 * @author Alireza Ivaz
 * @since 1.1.0
 */
fun ImageView.setIcon(icon: Icons) {
    setImageResource(icon.resId)
}

/**
 * Sets a tabler icon as the content of this ImageView.
 * Allows the use of vector drawables when running on older versions of the platform.
 *
 * @param icon Name of desired icon
 * @author Alireza Ivaz
 * @since 1.1.0
 */
fun AppCompatImageView.setIcon(icon: Icons) {
    setImageResource(icon.resId)
}

/**
 * Sets a tabler icon as the content of this ImageView.
 * Allows the use of vector drawables when running on older versions of the platform.
 *
 * @param icon Name of desired icon
 * @author Alireza Ivaz
 * @since 1.1.0
 */
fun FloatingActionButton.setIcon(icon: Icons) {
    setImageResource(icon.resId)
}

/**
 * Sets the icon to show for this button. By default, this icon will be shown on the left side of
 * the button.
 *
 * @param icon Name of desired icon
 * @author Alireza Ivaz
 * @since 1.1.0
 */
fun ExtendedFloatingActionButton.setIcon(context: Context, icon: Icons) {
    setIcon(ContextCompat.getDrawable(context, icon.resId))
}
