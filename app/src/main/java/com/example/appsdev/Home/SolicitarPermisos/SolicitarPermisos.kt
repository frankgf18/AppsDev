package com.example.appsdev.Home.SolicitarPermisos

import android.Manifest.permission.*
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.EXTRA_OUTPUT
import android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
import android.provider.MediaStore.MediaColumns.TITLE
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import com.example.appsdev.ActivityViewModel
import com.example.appsdev.App.App
import com.example.appsdev.App.App.Companion.spPermissionsCamera
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.Core.Utils.CAMARA
import com.example.appsdev.Core.Utils.GALLERY
import com.example.appsdev.Core.Utils.NewSharedPreferences
import com.example.appsdev.R
import com.example.appsdev.databinding.AlertVerFotoBinding
import com.example.appsdev.databinding.FragmentSolicitarPermisosBinding

class SolicitarPermisos : BaseFragment<FragmentSolicitarPermisosBinding>(FragmentSolicitarPermisosBinding::inflate) {
    private val viewModelPadre : ActivityViewModel by activityViewModels()
    private var imgUri : Uri? = null
    private var imgBitmap : Bitmap? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        events()
        observer()
    }

    private fun observer() {
        viewModelPadre.isCameraAllowed.observe {bool->
            if (bool == 7000){
                openCamera()
                viewModelPadre.isCameraAllowed.value = 0
            }
            if (bool == 7001){
                openGallery()
                viewModelPadre.isCameraAllowed.value = 0
            }
        }
    }

    /** Link de practica **/
    //https://cursokotlin.com/capitulo-21-gestion-de-permisos-en-android/
    //https://www.youtube.com/watch?v=rdfjT0bQBgs&ab_channel=Programaci%C3%B3nAndroidbyAristiDevs

    private fun events()= with(binding){
        btnGaleria.setOnClickListener { permisosGallery() }

        btnCamara.setOnClickListener { permisosCamera() }

        btnRecortar.setOnClickListener {
            imgBitmap = cropImageView.getCroppedImage(1000,1000)!!
            containerButtons.isGone = false
            containerCropImageView.isGone = true
        }

        tvVerFoto.setOnClickListener { alertPhoto() }
    }

    /**Mostramos la vista previa de la foto*/
    private fun alertPhoto() {
        val builder = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.alert_ver_foto, null)
        builder.setView(view).create().apply {
            window!!.setBackgroundDrawableResource(R.drawable.custom_dialog_alert)
            AlertVerFotoBinding.bind(view).apply {
                if (imgBitmap!=null) ivVistaPrevia.setImageBitmap(imgBitmap)
                contenedorAlert.setOnClickListener { dismiss() }
            }
            show()
            setCancelable(true)
        }
    }

    /**Abrir cámara**/
    private fun openCamera(){
        val value = ContentValues()
        value.put(TITLE, "NOTE_001")
        imgUri = requireActivity().contentResolver.insert(EXTERNAL_CONTENT_URI, value)
        val camaraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(EXTRA_OUTPUT, imgUri)
        registerForActivityResultCamera.launch(camaraIntent)
    }

    /** La condicional detectará si el permiso esta denegado( es decir,
     *  si el usuario ya ha denegado los permisos o será la primera vez que los pide)
     *  caso contrario se irá al ELSE **/
    private fun permisosCamera()= with(requireActivity()){
        if (checkSelfPermission(CAMERA) == PERMISSION_DENIED){
            //|| checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PERMISSION_DENIED){
            //ActivityCompat.requestPermissions(requireActivity(), arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE), CAMARA)
            verificarEstadoPermisoCamara()

        }else{
            openCamera()
        }
    }

    /**Verificaremos si el usuario rechazó los permisos o es la primera vez que le aparece**/
    private fun verificarEstadoPermisoCamara() {
        val bool1 = ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), CAMERA)//true
        //val bool2 = ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), WRITE_EXTERNAL_STORAGE)
        show(bool1.toString())
        if (bool1 || spPermissionsCamera.getSP(false)){ //1er -> false y true //2da(si presiona no permitir será) true u true
            spPermissionsCamera.saveSP(bool1)

            if (spPermissionsCamera.getSP(true))
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE),
                    CAMARA
                )
            else show("Muchos permisos")
        }
        else{
            //Pide por primera vez la camara
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE),
                CAMARA
            )
        }

    }

    /**Abrir galería**/
    private fun openGallery(){
        val intentGallery = Intent(Intent.ACTION_PICK)
        intentGallery.type = "image/*"
        registerForActivityResultGallery.launch(intentGallery)
    }

    /**Verificamos si ya se han aceptados los permisos*/
    private fun permisosGallery(){
        if (checkSelfPermission(requireActivity(), READ_EXTERNAL_STORAGE) == PERMISSION_DENIED){
            //verificarEstadoPermisoGaleria()
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(READ_EXTERNAL_STORAGE), GALLERY)
        }else{
            openGallery()
        }
    }

    /** Verificamos si ya se ha rechazado por segunda vez los permisos de Galeria*/
    private fun verificarEstadoPermisoGaleria() {
        when(true){
            ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), READ_EXTERNAL_STORAGE) ->{
                show("Ya se ha rechazado los permisos de la cámara")
            }

            else->{
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(READ_EXTERNAL_STORAGE), GALLERY)
            }
        }
    }

    /**Obtenemos la imagen en formato URI*/
    private val registerForActivityResultGallery = registerForActivityResult(StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            binding.cropImageView.setImageUriAsync(it.data?.data)
            binding.containerCropImageView.isGone = false
            binding.containerButtons.isGone = true
        }else{
            show("No se logró obtener foto de galeria")
        }
    }

    private val registerForActivityResultCamera = registerForActivityResult(StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            binding.cropImageView.setImageUriAsync(imgUri)
            binding.containerCropImageView.isGone = false
            binding.containerButtons.isGone = true
        }else{
            show("No se logró tomar foto")
        }
    }
}