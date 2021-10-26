package com.example.visionguardian

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.view_eat_healthy.*
import java.util.*

class GeneralCareDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocate()
        when (intent.getStringExtra("GKdata")) {
            getString(R.string.care1) -> setContentView(R.layout.view_hygiene)
            getString(R.string.care2) -> setContentView(R.layout.view_eat_healthy)
            getString(R.string.care3) -> setContentView(R.layout.view_caring)
            getString(R.string.care4) -> setContentView(R.layout.view_instillation)
            getString(R.string.care5) -> setContentView(R.layout.view_postoperative)
            getString(R.string.care6) -> setContentView(R.layout.view_firstaid)
            getString(R.string.care7) -> setContentView(R.layout.view_eyecare)
            getString(R.string.care8) -> setContentView(R.layout.view_contactlens)
        }
        // else if (stringGK=="Regular physician check-up after 40 years") setContentView(R.layout.view_regularphysician)


        back_arrow.setOnClickListener { finish() }
    }

    private fun setLocate(Lang: String) {

        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    private fun loadLocate() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        setLocate(language.toString())
    }
}