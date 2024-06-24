package ir.alirezaivaz.tablericons.demo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ir.alirezaivaz.tablericons.demo.databinding.ItemIconBinding

class RecyclerAdapter(
    private var items: List<IconItem>,
    private val fragmentManager: FragmentManager,
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
            root.setOnClickListener {
                val iconBottomSheet = IconBottomSheet(item)
                iconBottomSheet.show(fragmentManager, IconBottomSheet.TAG)
            }
            icon.setImageResource(item.drawableRes)
            name.text = item.name
        }
    }

    override fun getItemCount() = items.size

    fun filterList(filteredList: List<IconItem>) {
        items = filteredList
        notifyDataSetChanged()
    }
}
