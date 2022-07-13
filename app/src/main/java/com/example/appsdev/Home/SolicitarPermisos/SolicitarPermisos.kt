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
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import com.example.appsdev.ActivityViewModel
import com.example.appsdev.App.App.Companion.spPermissionsCamera
import com.example.appsdev.App.App.Companion.spPermissionsGallery
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.Core.Utils.CAMARA
import com.example.appsdev.Core.Utils.GALLERY
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
            show("Foto acualizada")
            alertPhoto()
        }
        btnVolver.setOnClickListener {
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
            show()
            AlertVerFotoBinding.bind(view).apply {
                if (imgBitmap!=null) ivVistaPrevia.setImageBitmap(imgBitmap)
                contenedorAlert.setOnClickListener { dismiss() }
            }
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
     *  caso contrario se irá al ELSE*/
    private fun permisosCamera()= with(requireActivity()){
        if (checkSelfPermission(CAMERA) == PERMISSION_DENIED) {
            //|| checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PERMISSION_DENIED){
            //ActivityCompat.requestPermissions(requireActivity(), arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE), CAMARA)
            verificarEstadoPermisoCamara()

        }else{
            openCamera()
        }
    }

    /**Verificaremos si el usuario rechazó los permisos o es la primera vez que le aparece
     * Patrones pa entender: SP(Sin presionar ningun permiso) - NP(No permitir)
     * SP -> false - false (Ingresó 1era vez)
     * NP -> true - false (Ingresó 2da vez)
     * SP -> true - true  (Ingresó 2da vez)
     * NP -> false - true (Ingresó 3ra vez y brinda mensaje de error (muchos intentos))**/
    private fun verificarEstadoPermisoCamara() {
        val bool1 = ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), CAMERA)

        if (!bool1 && spPermissionsCamera.getSP(false)){
            //show("Ingresó Tercera vez - Camara ('Muchos permisos :)'")
            show("Demasiados intentos al abrir la cámara'")
        }else if (bool1 || spPermissionsCamera.getSP(false)){
            //show("Ingresó Segunda vez - Camara")
            spPermissionsCamera.saveSP(bool1)
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE),
                CAMARA
            )
            verificarEstadoPermisosCamaraGaleria()
        }else{
            //show("Ingresó Primera vez")
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE),
                CAMARA
            )
            verificarEstadoPermisosCamaraGaleria()
        }

    }

    /**Funcionará a la par con la funcion "verificarEstadoPermisoCamara"*/
    private fun verificarEstadoPermisosCamaraGaleria() {
        val bool1 = ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), WRITE_EXTERNAL_STORAGE)//true
        show("$bool1 --- ${spPermissionsGallery.getSP(false)}")

        if (!bool1 && spPermissionsGallery.getSP(false)){
            show("Ingresó Tercera vez Galería - Muchos permisos :3")
        }else if (bool1 || spPermissionsCamera.getSP(false)){
            spPermissionsGallery.saveSP(bool1)
            //show("Ingresó Segunda vez - Galeria")
        }
        else{
            //show("Ingresó Primera vez - Galeria")
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
            verificarEstadoPermisoGaleria()
        }else{
            openGallery()
        }
    }

    /** Verificamos si ya se ha rechazado por segunda vez los permisos de Galeria
     * Con tiene los mismos posas de la funcion "verificarEstadoPermisoCamara"*/
    private fun verificarEstadoPermisoGaleria() {
        val bool1 = ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), READ_EXTERNAL_STORAGE)//true

        if (!bool1 && spPermissionsGallery.getSP(false)){
            //show("Ingresó Tercera vez - Galeria - Muchos permisos ya :)")
            show("Demasiados intentos al abrir Galería")
        }else if (bool1 || spPermissionsGallery.getSP(false)){ //1er -> false y true //2da(si presiona no permitir será) true u true
            //show("Ingresó Segunda vez - Galeria")
            spPermissionsGallery.saveSP(bool1)
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(READ_EXTERNAL_STORAGE),
                GALLERY
            )
        }else{
            //show("Ingresó Primera vez - Galeria")
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(READ_EXTERNAL_STORAGE),
                GALLERY
            )
        }
    }

    /**Obtenemos la imagen en formato URI y lo insertarmos a "cropImageView"*/
    private val registerForActivityResultGallery = registerForActivityResult(StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            binding.cropImageView.setImageUriAsync(it.data?.data)
            binding.containerCropImageView.isGone = false
            binding.containerButtons.isGone = true
        }else{
            show("No se logró obtener foto de galeria")
        }
    }

    /**Insertarmos la imagen Uri a "cropImageView" que por defecto ya viene obtenido
     * al tomar laofot de la cámara*/
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