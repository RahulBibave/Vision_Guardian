package com.example.visionguardian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Manual : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual)
    }

    fun Exit(view: View) {
        finish()
    }
}