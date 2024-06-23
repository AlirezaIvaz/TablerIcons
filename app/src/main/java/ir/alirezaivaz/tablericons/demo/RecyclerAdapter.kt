package ir.alirezaivaz.tablericons.demo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.alirezaivaz.tablericons.demo.databinding.ItemIconBinding

class RecyclerAdapter(
    private val items: List<IconItem>
) : RecyclerView.Adapter<RecyclerAdapter.DrawableViewHolder>() {

    inner class DrawableViewHolder(
        val binding: ItemIconBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawableViewHolder {
        val binding = ItemIconBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DrawableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrawableViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            imageView.setImageResource(item.drawableRes)
            textView.text = item.name
        }
    }

    override fun getItemCount() = items.size
}
