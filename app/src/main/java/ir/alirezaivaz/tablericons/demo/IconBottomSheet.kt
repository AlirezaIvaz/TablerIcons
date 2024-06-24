package ir.alirezaivaz.tablericons.demo

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import ir.alirezaivaz.tablericons.demo.databinding.SheetIconBinding

class IconBottomSheet(
    private val icon: IconItem
) : BottomSheetDialogFragment() {
    private val binding by lazy {
        SheetIconBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        icon.drawableRes.let {
            binding.icon8.setImageResource(it)
            binding.icon16.setImageResource(it)
            binding.icon24.setImageResource(it)
            binding.icon32.setImageResource(it)
            binding.icon40.setImageResource(it)
            binding.icon48.setImageResource(it)
            binding.icon56.setImageResource(it)
            binding.icon64.setImageResource(it)
            binding.icon72.setImageResource(it)
            binding.icon80.setImageResource(it)
        }
        binding.iconName.text = icon.name
        binding.iconResName.text = icon.drawableName
        binding.buttonCopyIconName.setOnClickListener {
            copyTextToClipboard(icon.name, "TablerIcons.${icon.name}")
        }
        binding.buttonCopyIconResName.setOnClickListener {
            copyTextToClipboard(icon.name, "@drawable/${icon.drawableName}")
        }
        binding.buttonShareIconName.setOnClickListener {
            shareText(icon.name, "TablerIcons.${icon.name}")
        }
        binding.buttonShareIconResName.setOnClickListener {
            shareText(icon.drawableName, "@drawable/${icon.drawableName}")
        }
        binding.buttonClose.setOnClickListener {
            dismiss()
        }
    }

    private fun copyTextToClipboard(label: String, text: String) {
        val clipboard =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Snackbar.make(binding.root, R.string.message_copy_success, Snackbar.LENGTH_SHORT).show()
    }

    private fun shareText(title: String, text: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TITLE, title)
            putExtra(Intent.EXTRA_TEXT, text)
        }
        val chooser = Intent.createChooser(intent, getString(R.string.action_share_via))
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            requireContext().startActivity(chooser)
        } else {
            Snackbar.make(binding.root, R.string.message_share_failed, Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val TAG = "IconBottomSheet"
    }
}