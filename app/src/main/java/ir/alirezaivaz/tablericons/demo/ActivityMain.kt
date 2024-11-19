package ir.alirezaivaz.tablericons.demo

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.color.DynamicColors
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            DynamicColors.applyToActivityIfAvailable(this)
        }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_rate -> {
                try {
                    val intentAction = if (BuildConfig.FLAVOR == "cafebazaar")
                        Intent.ACTION_EDIT
                    else
                        Intent.ACTION_VIEW
                    val intent = Intent(intentAction, Uri.parse(BuildConfig.RATE_INTENT))
                    startActivity(intent)
                } catch (e: Exception) {
                    Snackbar.make(
                        binding.root,
                        R.string.message_share_failed,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            R.id.action_apps -> {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.APPS_INTENT))
                    startActivity(intent)
                } catch (e: Exception) {
                    Snackbar.make(
                        binding.root,
                        R.string.message_share_failed,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            R.id.action_repo -> {
                try {
                    // TODO: Use Androidx browser
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.GITHUB_REPO_URL))
                    startActivity(intent)
                } catch (e: Exception) {
                    Snackbar.make(
                        binding.root,
                        R.string.message_share_failed,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            R.id.action_issues -> {
                try {
                    // TODO: Use Androidx browser
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.GITHUB_ISSUES_URL))
                    startActivity(intent)
                } catch (e: Exception) {
                    Snackbar.make(
                        binding.root,
                        R.string.message_share_failed,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return true
    }
}