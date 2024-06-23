package ir.alirezaivaz.tablericons.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ir.alirezaivaz.tablericons.TablerIcons
import ir.alirezaivaz.tablericons.demo.databinding.ActivityMainBinding

class ActivityMain : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val iconItems = mutableListOf<IconItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        TablerIcons::class.java.declaredFields.forEach { field ->
            if (field.type == Int::class.javaPrimitiveType) {
                field.isAccessible = true
                val name = field.name
                val drawableRes = field.getInt(null)
                val drawableName = resources.getResourceEntryName(drawableRes)
                iconItems.add(IconItem(name, drawableRes, drawableName))
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val recyclerAdapter = RecyclerAdapter(iconItems)
        binding.recyclerView.adapter = recyclerAdapter
    }
}