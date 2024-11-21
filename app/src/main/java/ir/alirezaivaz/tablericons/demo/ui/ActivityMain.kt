package ir.alirezaivaz.tablericons.demo.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.color.DynamicColors
import ir.alirezaivaz.tablericons.demo.R
import ir.alirezaivaz.tablericons.demo.adapter.RecyclerAdapter
import ir.alirezaivaz.tablericons.demo.databinding.ActivityMainBinding
import ir.alirezaivaz.tablericons.demo.dto.HomeState
import ir.alirezaivaz.tablericons.demo.utils.Preferences
import ir.alirezaivaz.tablericons.demo.viewmodel.MainViewModel

class ActivityMain : AppCompatActivity() {
    private val activityMain = this@ActivityMain
    private val viewmodel: MainViewModel by viewModels()
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val preferences by lazy {
        Preferences.getInstance(activityMain)
    }
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(preferences.getTheme())
        super.onCreate(savedInstanceState)
        if (preferences.getDynamicColors()) {
            DynamicColors.applyToActivityIfAvailable(activityMain)
        }
        enableEdgeToEdge()
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        viewmodel.initialize(resources)
        viewmodel.state.observe(this) {
            binding.errorLayout.isVisible = it == HomeState.NOTHING_FOUND
            binding.recyclerView.isVisible = it == HomeState.LOADED
            binding.progressIndicator.isVisible = it == HomeState.LOADING
        }
        viewmodel.allItems.observe(this) {
            adapter = RecyclerAdapter(it, supportFragmentManager)
            binding.recyclerView.adapter = adapter
        }
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
                viewmodel.updateState(HomeState.LOADING)
                newText?.let { filter(it) } ?: { viewmodel.updateState(HomeState.NOTHING_FOUND) }
                return false
            }
        })
        return true
    }

    private fun filter(text: String) {
        val items = viewmodel.allItems.value ?: emptyList()
        val filteredList = items.filter { it.name.lowercase().contains(text.lowercase()) }
        if (filteredList.isEmpty()) {
            viewmodel.updateState(HomeState.NOTHING_FOUND)
        } else {
            viewmodel.updateState(HomeState.LOADED)
            adapter.filterList(filteredList)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                val settingsBottomSheet = SettingsBottomSheet()
                settingsBottomSheet.show(supportFragmentManager, SettingsBottomSheet.TAG)
            }
        }
        return true
    }
}