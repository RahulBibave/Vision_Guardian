package com.example.visionguardian

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.capture_image.*
import java.io.File
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class OpenCamera : AppCompatActivity() {
    private val REQUEST_PERMISSION = 100
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_IMAGE_CAPTURE_LEFT = 2
    private val REQUEST_IMAGE_CAPTURE_RIGHT = 3
    var name=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.capture_image)
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        name=sharedPreferences.getString("patientName", "").toString()
        btCapturePhoto.setOnClickListener { openCamera(REQUEST_IMAGE_CAPTURE) }
        btCapturePhotoLeft.setOnClickListener { openCamera(REQUEST_IMAGE_CAPTURE_LEFT) }
        btCapturePhotoRight.setOnClickListener { openCamera(REQUEST_IMAGE_CAPTURE_RIGHT) }
        back_arrow.setOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        val intent = Intent()
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_PERMISSION
            )
        }
    }
    private fun openCamera(CODE: Int) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, CODE)
            }
        }
    }

    var fos: OutputStream? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var answer=""
        if (resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                answer=  current.format(formatter)

            } else {
                var date = Date()
                val formatter = SimpleDateFormat("dd/MM/yyyy")
                answer= formatter.format(date)

            }
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                val bitmap = data?.extras?.get("data") as Bitmap
                ivImage.setImageBitmap(bitmap)


                    contentResolver?.also { resolver ->
                        val contentValues = ContentValues().apply {
                            put(
                                MediaStore.MediaColumns.DISPLAY_NAME,
                                name + answer + "_Both" + ".jpg"
                            )
                            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                            put(
                                MediaStore.MediaColumns.RELATIVE_PATH,
                                Environment.DIRECTORY_PICTURES + File.separator + "TestFolder"
                            )
                        }
                        val imageUri: Uri? =
                                resolver.insert(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    contentValues
                                )
                        fos = imageUri?.let { resolver.openOutputStream(it) }
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                        Objects.requireNonNull(fos)
                    }

            }
            else if (requestCode == REQUEST_IMAGE_CAPTURE_LEFT) {
                val bitmap = data?.extras?.get("data") as Bitmap
                ivImageLeft.setImageBitmap(bitmap)

                    contentResolver?.also { resolver ->
                        val contentValues = ContentValues().apply {
                            put(
                                MediaStore.MediaColumns.DISPLAY_NAME,
                                name + answer + "_Left" + ".jpg"
                            )
                            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                            put(
                                MediaStore.MediaColumns.RELATIVE_PATH,
                                Environment.DIRECTORY_PICTURES + File.separator + "TestFolder"
                            )
                        }
                        val imageUri: Uri? =
                                resolver.insert(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    contentValues
                                )
                        fos = imageUri?.let { resolver.openOutputStream(it) }
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                        Objects.requireNonNull(fos)

                }
            }
            else if (requestCode == REQUEST_IMAGE_CAPTURE_RIGHT) {
                val bitmap = data?.extras?.get("data") as Bitmap
                ivImageRight.setImageBitmap(bitmap)

                    contentResolver?.also { resolver ->
                        val contentValues = ContentValues().apply {
                            put(
                                MediaStore.MediaColumns.DISPLAY_NAME,
                                name + answer + "_Right" + ".jpg"
                            )
                            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                            put(
                                MediaStore.MediaColumns.RELATIVE_PATH,
                                Environment.DIRECTORY_PICTURES + File.separator + "PaitentPhotograph"
                            )
                        }
                        val imageUri: Uri? =
                                resolver.insert(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    contentValues
                                )
                        fos = imageUri?.let { resolver.openOutputStream(it) }
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                        Objects.requireNonNull(fos)
                    }

            }
        }
    }
}