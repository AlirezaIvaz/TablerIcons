package ir.alirezaivaz.tablericons

import android.content.Context
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
