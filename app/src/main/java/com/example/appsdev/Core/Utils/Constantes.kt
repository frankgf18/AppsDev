package com.example.appsdev.Core.Utils

import androidx.activity.result.ActivityResultLauncher
import com.example.appsdev.Home.ItemsCards
import com.example.appsdev.R


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


