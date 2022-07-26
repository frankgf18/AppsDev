package com.example.appsdev.Home.Dogedex

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.appsdev.Core.Utils.LABEL_PATH
import com.example.appsdev.Core.Utils.MODEL_PATH
import com.example.appsdev.Core.Utils.activarProgressBar
import com.example.appsdev.Home.Dogedex.Di.Api.ApiResponseStatus
import com.example.appsdev.Home.Dogedex.machinelearning.Classifier
import com.example.appsdev.Home.Dogedex.machinelearning.DogRecognition
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentDogedexBinding
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.support.common.FileUtil
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
/**
 * Ing. Joel Maldonado Fernandez
 * joelmaldonadodev@gmail.com
 * */
@AndroidEntryPoint
class Dogedex : Fragment() {
    private lateinit var binding: FragmentDogedexBinding
    private val viewModel: DogedexViewModel by viewModels()
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService
    private var isCameraReady = false
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
        viewModel.status.observe(viewLifecycleOwner){
            requireActivity().apply {
                when(it){
                    is ApiResponseStatus.Loading -> activarProgressBar(true)
                    is ApiResponseStatus.Succes ->{
                        activarProgressBar(false)
                    }
                    is ApiResponseStatus.Error ->{
                        activarProgressBar(false)
                    }
                }
            }
        }
        viewModel.dog.observe(viewLifecycleOwner){
            if (it !=null){
                val dir = DogedexDirections.actionDogedexToDogDetalle(it)
                findNavController().navigate(dir)
            }
        }
    }

    private fun events()  = with(binding){
        findNavController().apply {
            btnMenu.setOnClickListener { navigate(R.id.action_dogedex_to_dogList) }
            btnCamara.setOnClickListener {
                if (isCameraReady){

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::cameraExecutor.isInitialized) cameraExecutor.shutdown()
    }


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

    /*private fun takePhoto(){
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(getFile()).build()
        imageCapture.takePicture(outputFileOptions,cameraExecutor, object : ImageCapture.OnImageSavedCallback{
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
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
    }*/

    private fun startCamera() = with(binding){
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())
        cameraProviderFuture.addListener({
            val camProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(camaraPreview.surfaceProvider)
            val camSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(cameraExecutor) {
                val bitmat = imgToBitmap(it)
                if (bitmat !=null){
                    val dogRecognition = classifier.recognizeImage(bitmat).first()

                    enable(dogRecognition)
                }
                it.close()
            }

            camProvider.bindToLifecycle(viewLifecycleOwner,camSelector,preview,imageCapture,imageAnalysis)
        }, ContextCompat.getMainExecutor(requireActivity()))
    }

    private fun enable(dogRecognition: DogRecognition) {
        if (dogRecognition.confidence > 70.0){
            binding.btnCamara.alpha = 1f
            binding.btnCamara.setOnClickListener {
                viewModel.getDogBymlId(dogRecognition.id)
            }
        }else{
            binding.btnCamara.setOnClickListener {null}
            binding.btnCamara.alpha = 0.2f
        }
    }

    private lateinit var classifier: Classifier
    override fun onStart() {
        super.onStart()
        classifier = Classifier(FileUtil.loadMappedFile(requireContext(),MODEL_PATH),
            FileUtil.loadLabels(requireContext(), LABEL_PATH))
    }

    private fun imgToBitmap(imageProxy: ImageProxy): Bitmap? {
        val image = imageProxy.image ?: return null

        val yBuffer = image.planes[0].buffer
        val uBuffer = image.planes[1].buffer
        val vBuffer = image.planes[2].buffer

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize+uSize+vSize)

        yBuffer.get(nv21,0,ySize)
        vBuffer.get(nv21,ySize,vSize)
        uBuffer.get(nv21,ySize + vSize, uSize)

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(
            Rect(0,0,yuvImage.width,yuvImage.height),100,out
        )
        val imageBytes = out.toByteArray()
        return BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.size)
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