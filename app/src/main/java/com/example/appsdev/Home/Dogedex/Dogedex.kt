package com.example.appsdev.Home.Dogedex

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentDogedexBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class Dogedex : Fragment() {
    private lateinit var binding: FragmentDogedexBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dogedex, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDogedexBinding.bind(view)

        init()
        events()
    }


    private fun init() {
        requestCameraPermisos()
    }

    private fun events()  = with(binding){
        findNavController().apply {
            btnMenu.setOnClickListener { navigate(R.id.action_dogedex_to_dogList) }
            btnCamara.setOnClickListener {
                if (isCameraReady){
                    takePhoto()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::cameraExecutor.isInitialized) cameraExecutor.shutdown()
    }

    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService
    private var isCameraReady = false

    private fun setupCamera(){
        binding.camaraPreview.post{
            imageCapture = ImageCapture.Builder()
                .setTargetRotation(binding.camaraPreview.display.rotation)
                .build()
            cameraExecutor = Executors.newSingleThreadExecutor()
            startCamera()
            isCameraReady = true
        }
    }

    private fun takePhoto(){
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(getFile()).build()
        imageCapture.takePicture(outputFileOptions,cameraExecutor, object : ImageCapture.OnImageSavedCallback{
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                Toast.makeText(requireContext(), "Se tomo la foto", Toast.LENGTH_SHORT).show()
            }
            override fun onError(exception: ImageCaptureException) {
                Toast.makeText(requireContext(), exception.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getFile(): File = with(requireActivity()){
        val mediaDir = externalMediaDirs.firstOrNull()?.let{
            File(it,resources.getString(R.string.app_name)+".jpg").apply{mkdirs()}
        }
        return if (mediaDir != null && mediaDir.exists()){
            mediaDir
        }else{
            filesDir
        }
    }

    private fun startCamera(){
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())
        cameraProviderFuture.addListener({
            val camProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.camaraPreview.surfaceProvider)
            val camSelector = CameraSelector.DEFAULT_BACK_CAMERA
            camProvider.bindToLifecycle(this,camSelector,preview)
        }, ContextCompat.getMainExecutor(requireActivity()))
    }



    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            setupCamera()
        } else {
            Toast.makeText(requireContext(), "Necesitas aceptar los permisos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestCameraPermisos() {
        when {
            ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                setupCamera()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                AlertDialog.Builder(requireActivity())
                    .setTitle("Aceptame Plssss")
                    .setMessage("Acepta la Camara")
                    .setPositiveButton("Aceptar"){_,_->
                        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                    .setNegativeButton("Cancelar"){_,_ ->
                    }.show()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
}