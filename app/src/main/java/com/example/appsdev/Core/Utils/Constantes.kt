package com.example.appsdev.Core.Utils

import androidx.activity.result.ActivityResultLauncher


//Progress Bar
var RETROCEDER = true
var LOADER_ACTIVADO = false
var MENSAJE_LOADER = ""
var registerForActivityResult: ActivityResultLauncher<Any>? = null


const val BASE_URL = "https://todogs.herokuapp.com/api/v1/"

const val MAX_RECOGNITION_DOG_RESULTS = 5
const val MODEL_PATH = "model.tflite"
const val LABEL_PATH = "labels.txt"

const val CAMARA = 1000
const val GALLERY = 1001


//--------------------------------------------------------//
//---- NOMBRE DEL ARCHIVO Y LLAVE DE SHAREDPREFERENCES ---//
//--------------------------------------------------------//
const val SP_CAMERA_PERMISSIONS  = "SP_CAMERA_PERMISSIONS"
const val KEY_CAMERA_PERMISSIONS = "KEY_CAMERA_PERMISSIONS"

const val SP_GALLERY_PERMISSIONS = "SP_GALLERY_PERMISSIONS"
const val KEY_GALLERY_PERMISSIONS = "KEY_GALLERY_PERMISSIONS"

