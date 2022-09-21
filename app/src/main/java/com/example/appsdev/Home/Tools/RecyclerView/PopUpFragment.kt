package com.example.appsdev.Home.Tools.RecyclerView

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentPopUpBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PopUpFragment() : DialogFragment() {
    private lateinit var binding: FragmentPopUpBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_pop_up, null, false)
        val alert = builder.setView(view).create()
        alert.window!!.setBackgroundDrawableResource(R.drawable.custom_dialog_20dp)
        binding = FragmentPopUpBinding.bind(view)

        binding.btn.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(), "Hola ${binding.edtNombre.text}", Toast.LENGTH_SHORT)
                .show()
        }

        return alert
    }
}