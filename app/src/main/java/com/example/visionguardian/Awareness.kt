package com.example.visionguardian

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_age_related.*
import java.util.*

class Awareness : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocate()
        when (intent.getStringExtra("data")) {
            getString(R.string.awarness1) -> {
                setContentView(R.layout.view_cataract)
            }
            getString(R.string.awarness2) -> {
                setContentView(R.layout.layout_refractive_error)
            }
            getString(R.string.awarness3) -> {
                setContentView(R.layout.layout_glaucoma)
            }
            getString(R.string.awarness4) -> {
                setContentView(R.layout.layout_corneal_blindness)
            }
            getString(R.string.awarness5) -> {
                setContentView(R.layout.layout_childhood_blindness)
            }
            getString(R.string.awarness6) -> {
                setContentView(R.layout.layout_diabetic_retinopathy)
            }
            getString(R.string.awarness7) -> {
                setContentView(R.layout.layout_hypertensive_retinopathy)
            }
            getString(R.string.awarness8) -> {
                setContentView(R.layout.layout_age_related)
            }
            getString(R.string.awarness9) -> {
                setContentView(R.layout.layout_structure)
            }
        }


        back_arrow.setOnClickListener {
            finish()
        }

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