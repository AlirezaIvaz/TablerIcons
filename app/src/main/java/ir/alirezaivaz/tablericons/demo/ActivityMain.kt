package ir.alirezaivaz.tablericons.demo

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ir.alirezaivaz.tablericons.TablerIcons
import ir.alirezaivaz.tablericons.demo.databinding.ActivityMainBinding

class ActivityMain : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val iconItems = mutableListOf<IconItem>()
    private lateinit var adapter: RecyclerAdapter

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
        adapter = RecyclerAdapter(iconItems, supportFragmentManager)
        binding.recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView: SearchView? = searchItem.actionView as SearchView?
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filter(it) }
                return false
            }
        })
        return true
    }

    private fun filter(text: String) {
        val filteredList = iconItems.filter { it.name.lowercase().contains(text.lowercase()) }
        if (filteredList.isEmpty()) {
            Snackbar.make(binding.root, R.string.message_no_data, Snackbar.LENGTH_SHORT).show()
        } else {
            adapter.filterList(filteredList)
        }
    }
}