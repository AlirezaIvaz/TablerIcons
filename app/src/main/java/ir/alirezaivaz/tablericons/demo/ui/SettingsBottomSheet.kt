package ir.alirezaivaz.tablericons.demo.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.color.DynamicColors
import ir.alirezaivaz.tablericons.demo.BuildConfig
import ir.alirezaivaz.tablericons.demo.R
import ir.alirezaivaz.tablericons.demo.databinding.SheetSettingsBinding
import ir.alirezaivaz.tablericons.demo.utils.Preferences
import ir.alirezaivaz.tablericons.demo.utils.Utils
import ir.alirezaivaz.tablericons.BuildConfig as TablerBuildConfig

class SettingsBottomSheet : BottomSheetDialogFragment() {
    private val binding by lazy {
        SheetSettingsBinding.inflate(layoutInflater)
    }
    private val prefs by lazy {
        Preferences.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDynamicColorsSwitch()
        initThemeToggle()
        initButtons()
        binding.aboutLibraryVersion.text = String.format(
            getString(R.string.about_library_version),
            TablerBuildConfig.LIBRARY_VERSION
        )
        binding.aboutBaseVersion.text = String.format(
            getString(R.string.about_library_base_version),
            TablerBuildConfig.TABLER_ICONS_VERSION
        )
    }

    private fun initDynamicColorsSwitch() {
        binding.dynamicColors.isVisible = DynamicColors.isDynamicColorAvailable()
        binding.dynamicColors.isChecked = prefs.getDynamicColors()
        binding.dynamicColors.setOnCheckedChangeListener { _, isChecked ->
            prefs.setDynamicColors(isChecked)
            dismiss()
            requireActivity().recreate()
        }
    }

    private fun initThemeToggle() {
        binding.themeBattery.isVisible = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
        binding.themeSystem.isVisible = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        binding.themeToggle.check(
            when (prefs.getTheme()) {
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> R.id.theme_system
                AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY -> R.id.theme_battery
                AppCompatDelegate.MODE_NIGHT_YES -> R.id.theme_night
                else -> R.id.theme_day
            }
        )
        binding.themeToggle.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            when (toggleButton.checkedButtonId) {
                R.id.theme_day -> {
                    prefs.setTheme(AppCompatDelegate.MODE_NIGHT_NO)
                }

                R.id.theme_night -> {
                    prefs.setTheme(AppCompatDelegate.MODE_NIGHT_YES)
                }

                R.id.theme_system -> {
                    prefs.setTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }

                R.id.theme_battery -> {
                    prefs.setTheme(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }
            }
            dismiss()
            requireActivity().recreate()
        }
    }

    private fun initButtons() {
        binding.rateButton.isVisible = BuildConfig.RATE_INTENT.isNotEmpty()
        binding.appsButton.isVisible = BuildConfig.APPS_INTENT.isNotEmpty()

        binding.githubButton.setOnClickListener {
            Utils.launchWebpage(
                context = requireContext(),
                view = binding.root,
                url = BuildConfig.GITHUB_REPO_URL
            )
        }
        binding.issuesButton.setOnClickListener {
            Utils.launchWebpage(
                context = requireContext(),
                view = binding.root,
                url = BuildConfig.GITHUB_ISSUES_URL
            )
        }
        binding.rateButton.setOnClickListener {
            val action = if (BuildConfig.FLAVOR == "cafebazaar") {
                Intent.ACTION_EDIT
            } else {
                Intent.ACTION_VIEW
            }
            Utils.launchMarketIntent(
                context = requireContext(),
                view = binding.root,
                url = BuildConfig.RATE_INTENT,
                action = action
            )
        }
        binding.appsButton.setOnClickListener {
            Utils.launchMarketIntent(
                context = requireContext(),
                view = binding.root,
                url = BuildConfig.APPS_INTENT,
            )
        }
    }

    companion object {
        const val TAG = "IconBottomSheet"
    }
}