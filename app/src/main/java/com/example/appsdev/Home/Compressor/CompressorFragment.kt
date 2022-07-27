package com.example.appsdev.Home.Compressor

import android.Manifest.permission.*
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.format.Formatter
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.Core.EstadosResult
import com.example.appsdev.databinding.FragmentCompressorBinding
import com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.models.PermissionRequest
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class CompressorFragment :
    BaseFragment<FragmentCompressorBinding>(FragmentCompressorBinding::inflate) {
    private val viewModel: CompressorViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {

        binding.btnEscoger.setOnClickListener {
            if (permisos()) {
                abrirGaleria()
            }else{
                show("Debe aceptar los permisos primero")
            }
        }

        binding.btnComprimir.setOnClickListener {
            imgUri?.let {
                viewModel.comprimir(it,requireContext()).observe(viewLifecycleOwner){res->
                    result(res){file->
                        Glide.with(requireActivity()).load(file!![0]).into(binding.ivDos)
                        val pesoCompreso = Formatter.formatShortFileSize(requireContext(), file[0].length())
                        val pesoActual = Formatter.formatShortFileSize(requireContext(), file[1].length())
                        binding.tvCompresa.text = "Tamaño: $pesoCompreso"
                        binding.tvActual.text = "Tamaño: $pesoActual"
                    }
                }
            }

        }
        binding.btnEditar.setOnClickListener {
            imgUri2 = imgUri
            UCrop.of(imgUri!!,imgUri2!! )
                .withAspectRatio(16f, 9f)
                .withMaxResultSize(400,400)
                .start(requireActivity())
        }
        binding.btnFoto3.setOnClickListener {
            FOTO?.let {
                val d = File(it).absolutePath
                val bitmap = BitmapFactory.decodeFile(d)
                binding.tvFoto31.text = bitmap.toString()
                Glide.with(requireContext()).load(bitmap).into(binding.ivFoto3)
            }
        }
    }

    private var imgUri:Uri? = null
    private var imgUri2:Uri? = null

    private val resultGal = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            imgUri = it.data?.data
            Glide.with(requireActivity()).load(imgUri).into(binding.ivUno)
        }
    }

    private fun abrirGaleria() {
        val intentGaleria = Intent(Intent.ACTION_PICK)
        intentGaleria.type = "image/*"
        resultGal.launch(intentGaleria)
    }

    private fun permisos(): Boolean {
        val request = PermissionRequest.Builder(requireActivity())
            .code(REQUEST_CODE)
            .perms(arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE))
            .rationale("Acepta para que funcione.")
            .positiveButtonText("Aceptar")
            .negativeButtonText("Cancelae")
            .build()
        EasyPermissions.requestPermissions(requireActivity(), request)
        return requireActivity().checkSelfPermission(CAMERA) != PERMISSION_DENIED &&
                requireActivity().checkSelfPermission(WRITE_EXTERNAL_STORAGE) != PERMISSION_DENIED &&
                requireActivity().checkSelfPermission(READ_EXTERNAL_STORAGE) != PERMISSION_DENIED
    }
}

var FOTO :String? = null