package com.example.appsdev.Home.Compressor

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.appsdev.Core.EstadosResult
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import id.zelory.compressor.Compressor
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class CompressorViewModel @Inject constructor():ViewModel() {
    fun comprimir(uri: Uri, context: Context) = liveData(Dispatchers.IO) {
        emit(EstadosResult.Cargando)
        try {
            val file = uriToFile(uri, context)
            val fileCompreso = Compressor.compress(context, file)
            FOTO = fileCompreso.toString()

            val folder = FirebaseStorage.getInstance().reference.child("AppsDev")
            FOTO?.let {
                val appsdev = folder.child(it.toUri().lastPathSegment ?: "Sin_nombre")
                appsdev.putFile(it.toUri()).addOnSuccessListener {
                    appsdev.downloadUrl.addOnSuccessListener {url->
                        Log.i("fotoUrl",url.toString())
                    }
                }
            }
            emit(EstadosResult.Correcto(listOf(fileCompreso,file)))
        }catch (ex:Exception){
            emit(EstadosResult.Error(ex.message ?: "Error al comprimir"))
        }

    }


    private fun uriToFile(uri: Uri, context: Context):File {
        val bit = uriToBitmap(uri, context)
        val dir = ContextWrapper(context).getDir("fotosOriginales",Context.MODE_PRIVATE)
        val fos = FileOutputStream(File(dir, "${uri.lastPathSegment}.jpg"))
        bit.compress(Bitmap.CompressFormat.PNG,70,fos)
        fos.close()
        return File("$dir/${uri.lastPathSegment}.jpg")
    }

    private fun uriToBitmap(uri: Uri, context: Context):Bitmap {
        val data = context.contentResolver.openInputStream(uri)
        val matrix = Matrix()
        val imageBitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)

        val orientation = ExifInterface(data!!).getAttributeInt(
            ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED
        )

        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 ->  matrix.postRotate(270f)
        }

        return Bitmap.createBitmap(
            imageBitmap,0,0, imageBitmap.width, imageBitmap.height, matrix,true
        )
    }
}