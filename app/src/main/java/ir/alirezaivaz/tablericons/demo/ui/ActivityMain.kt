package ir.alirezaivaz.tablericons.demo.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.color.DynamicColors
import ir.alirezaivaz.tablericons.demo.BuildConfig
import ir.alirezaivaz.tablericons.demo.R
import ir.alirezaivaz.tablericons.demo.adapter.RecyclerAdapter
import ir.alirezaivaz.tablericons.demo.databinding.ActivityMainBinding
import ir.alirezaivaz.tablericons.demo.dto.HomeState
import ir.alirezaivaz.tablericons.demo.utils.Utils
import ir.alirezaivaz.tablericons.demo.viewmodel.MainViewModel

class ActivityMain : AppCompatActivity() {
    private val activityMain = this@ActivityMain
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewmodel: MainViewModel by viewModels()
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            DynamicColors.applyToActivityIfAvailable(this)
        }
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
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
        if (BuildConfig.RATE_INTENT.isEmpty()) {
            menu.findItem(R.id.action_rate).isVisible = false
        }
        if (BuildConfig.APPS_INTENT.isEmpty()) {
            menu.findItem(R.id.action_apps).isVisible = false
        }
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
            R.id.action_rate -> {
                val action = if (BuildConfig.FLAVOR == "cafebazaar") {
                    Intent.ACTION_EDIT
                } else {
                    Intent.ACTION_VIEW
                }
                Utils.launchMarketIntent(
                    context = activityMain,
                    view = binding.root,
                    url = BuildConfig.RATE_INTENT,
                    action = action
                )
            }

            R.id.action_apps -> {
                Utils.launchMarketIntent(
                    context = activityMain,
                    view = binding.root,
                    url = BuildConfig.APPS_INTENT,
                )
            }

            R.id.action_repo -> {
                Utils.launchWebpage(
                    context = activityMain,
                    view = binding.root,
                    url = BuildConfig.GITHUB_REPO_URL
                )
            }

            R.id.action_issues -> {
                Utils.launchWebpage(
                    context = activityMain,
                    view = binding.root,
                    url = BuildConfig.GITHUB_ISSUES_URL
                )
            }
        }
        return true
    }
}