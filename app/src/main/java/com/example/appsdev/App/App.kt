package com.example.appsdev.App

import android.annotation.SuppressLint
import android.app.Application
import com.example.appsdev.Core.Utils.*
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        spPermissionsCamera = NewSharedPreferences(
            applicationContext,
            SP_CAMERA_PERMISSIONS,
            KEY_CAMERA_PERMISSIONS
        )

        spPermissionsGallery = NewSharedPreferences(
            applicationContext,
            SP_GALLERY_PERMISSIONS,
            KEY_GALLERY_PERMISSIONS
        )
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var spPermissionsCamera : NewSharedPreferences
        @SuppressLint("StaticFieldLeak")
        lateinit var spPermissionsGallery : NewSharedPreferences
    }
}