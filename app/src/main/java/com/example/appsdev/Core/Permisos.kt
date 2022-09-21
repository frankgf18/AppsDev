package com.example.appsdev.Core

import android.Manifest.permission.*
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.annotation.Size
import androidx.fragment.app.Fragment
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.models.PermissionRequest

class Permisos(val fragment:Fragment) {

    fun camara(landa:(Uri?,Intent)->Unit){
        perm(CAMERA, WRITE_EXTERNAL_STORAGE){
            val value = ContentValues()
            value.put(MediaStore.Images.Media.TITLE, "NUEVA IMAGEN")
            val uri = fragment.requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, value)
            val camaraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            landa(uri,camaraIntent)
        }
    }

    fun galeria(landa: (Intent) -> Unit){
        perm(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, CAMERA, WRITE_EXTERNAL_STORAGE){
            val intentGaleria = Intent(Intent.ACTION_PICK)
            intentGaleria.type = "image/*"
            landa(intentGaleria)
        }
    }

    private fun perm(@Size(min = 1) vararg permisos: String, lan: () -> Unit){
        if (EasyPermissions.hasPermissions(fragment.requireContext(), *permisos)){
            lan()
        }else{
            val request = PermissionRequest.Builder(fragment.requireContext())
                .code(1000)
                .perms(permisos)
                .rationale("Primero se deben aceptar los permisos.")
                .positiveButtonText("Confirmar")
                .negativeButtonText("Cancelar")
                .build()
            EasyPermissions.requestPermissions(fragment, request)
        }
    }
}