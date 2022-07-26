package com.example.appsdev.Home.Animacion.BottomLayout

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appsdev.Core.BaseAdapter
import com.example.appsdev.Core.Utils.cargarFoto
import com.example.appsdev.Core.Utils.inflarLayout
import com.example.appsdev.Core.Utils.toast
import com.example.appsdev.Home.Home.ItemsCards
import com.example.appsdev.R
import com.example.appsdev.databinding.CardHomeItemBinding
import com.example.appsdev.databinding.FragmentBottomPadreBinding
import com.example.appsdev.databinding.LayoutBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

@SuppressLint("SetTextI18n")
class BottomPadre : Fragment() {

    private lateinit var binding : FragmentBottomPadreBinding
    private lateinit var bindingBottomSheetDialog: LayoutBottomSheetBinding

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.fragment_bottom_padre, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBottomPadreBinding.bind(view)
        init()
    }

    private val adaptador: BaseAdapter<ItemsCards> = object : BaseAdapter<ItemsCards>(emptyList()) {
        override fun getViewHolder(parent: ViewGroup): BaseAdapterViewHolder<ItemsCards> {
            val view = requireContext().inflarLayout(R.layout.card_home_item, parent)
            return object : BaseAdapterViewHolder<ItemsCards>(view) {
                override fun bind(entity: ItemsCards) {
                    CardHomeItemBinding.bind(view).apply {
                        tvName.text = entity.name
                        ivIcono.cargarFoto(entity.image)
                        cardItem.setOnClickListener {
                            bindingBottomSheetDialog.apply {
                                tvFruta.text = entity.name
                                tvPrecio.text = "$ 0${entity.navigation}.00"
                            }
                            //findNavController().navigate(entity.navigation!!)
                        }
                    }
                }
            }
        }
    }

    var listAnimation = listOf(
        ItemsCards( 1,"Manzana", 1),
        ItemsCards( 2,"Plantano",2),
        ItemsCards( 3,"Fresa",3),
        ItemsCards( 4,"Papaya",4)
    )

    private fun init()= with(binding){
        btnMostrar.setOnClickListener {
            mostrarBottomSheet()
        }
    }


    private fun mostrarBottomSheet(){
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.apply {
            show()
            bindingBottomSheetDialog = LayoutBottomSheetBinding.bind(view).apply {
                rvBottomLayout.adapter = adaptador
                adaptador.update(listAnimation)

                btnCancelar.setOnClickListener {
                    requireActivity().toast("Se canceló la transacción")
                    dismiss()
                }

                btnComprarAhora.setOnClickListener {
                    requireActivity().toast("Comprando...")
                    dismiss()
                }

            }
        }
    }

}