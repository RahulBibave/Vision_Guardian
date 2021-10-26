package com.example.visionguardian

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Manual : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual)
    }

    fun Exit(view: View) {
        finish()
    }
}