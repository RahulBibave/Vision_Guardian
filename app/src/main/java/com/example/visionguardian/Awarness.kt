package com.example.visionguardian

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_age_related.*
import kotlinx.android.synthetic.main.layout_age_related.back_arrow
import kotlinx.android.synthetic.main.layout_childhood_blindness.*
import java.util.*

class Awarness : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocate()
        val stringOne = intent.getStringExtra("data")
       // val myArray = arrayOf("Cataract","Refractive error and low vision", "Glaucoma","Diabetic retinopathy","Hypertensive retinopathy","Corneal blindness","Childhood Blindness","Age related macular degeneration" )
        if (stringOne==getString(R.string.awarness1)){
            setContentView(R.layout.view_cataract)
        }
        else if (stringOne==getString(R.string.awarness2)){
            setContentView(R.layout.layout_refractive_error)
        }
        else if (stringOne==getString(R.string.awarness3)){
            setContentView(R.layout.layout_glaucoma)
        }
        else if (stringOne==getString(R.string.awarness4)){
            setContentView(R.layout.layout_corneal_blindness)
        }
        else if (stringOne==getString(R.string.awarness5)){
            setContentView(R.layout.layout_childhood_blindness)
        }
        else if (stringOne==getString(R.string.awarness6)){
            setContentView(R.layout.layout_diabetic_retinopathy)
        }
        else if (stringOne==getString(R.string.awarness7)){
            setContentView(R.layout.layout_hypertensive_retinopathy)
        }
        else if (stringOne==getString(R.string.awarness8)){
            setContentView(R.layout.layout_age_related)
        }
        else if (stringOne==getString(R.string.awarness9)){
            setContentView(R.layout.layout_structure)
        }

//        refer_catract.setOnClickListener{
//            setContentView(R.layout.view_cataract)
//        }

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