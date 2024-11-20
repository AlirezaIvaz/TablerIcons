package ir.alirezaivaz.tablericons.demo

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.alirezaivaz.tablericons.TablerIcons
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val _state = MutableLiveData<HomeState>()
    val state: LiveData<HomeState> = _state

    private val _allItems = MutableLiveData<List<IconItem>>()
    val allItems = _allItems

    fun updateState(state: HomeState) {
        _state.value = state
    }

    private fun updateAllItems(items: List<IconItem>) {
        _allItems.value = items
    }

    fun initialize(resources: Resources) {
        viewModelScope.launch {
            updateState(HomeState.LOADING)

            val icons = loadIcons(resources)
            updateAllItems(icons)
            if (allItems.value.isNullOrEmpty()) {
                updateState(HomeState.NOTHING_FOUND)
            } else {
                updateState(HomeState.LOADED)
            }
        }
    }

    private fun loadIcons(resources: Resources): List<IconItem> {
        val icons = mutableListOf<IconItem>()
        TablerIcons::class.java.declaredFields.forEach { field ->
            if (field.type == Int::class.javaPrimitiveType) {
                field.isAccessible = true
                val name = field.name
                val drawableRes = field.getInt(null)
                val drawableName = resources.getResourceEntryName(drawableRes)
                icons.add(IconItem(name, drawableRes, drawableName))
            }
        }
        return icons
    }
}
