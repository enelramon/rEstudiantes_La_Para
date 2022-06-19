package com.tutorial.myapplication.ui.student.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.tutorial.myapplication.R

class OptionsDialogFragment : DialogFragment() {
    companion object {
        const val TAG = "OptionsDialogFragment"
        const val EDIT_OPTION = 0
        const val DELETE_OPTION = 1
    }
    private lateinit var _onClick: (dialog: DialogInterface, which: Int) -> Unit

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.options_dialog_title))
            .setItems(
                arrayOf<String>(
                    getString(R.string.options_dialog_edit),
                    getString(R.string.options_dialog_delete)
                )
            ) { dialog, which ->
                _onClick(dialog, which)
            }
            .create()
    }

    fun onClick(callback: (dialog: DialogInterface, which: Int) -> Unit) {
        _onClick = callback
    }

}