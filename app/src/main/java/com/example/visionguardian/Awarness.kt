package com.example.visionguardian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_age_related.*
import kotlinx.android.synthetic.main.layout_age_related.back_arrow
import kotlinx.android.synthetic.main.layout_childhood_blindness.*

class Awarness : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val stringOne = getIntent().getStringExtra("data")
       // val myArray = arrayOf("Cataract","Refractive error and low vision", "Glaucoma","Diabetic retinopathy","Hypertensive retinopathy","Corneal blindness","Childhood Blindness","Age related macular degeneration" )
        if (stringOne=="Cataract"){
            setContentView(R.layout.view_cataract)
        }
        else if (stringOne=="Refractive error and low vision"){
            setContentView(R.layout.layout_refractive_error)
        }
        else if (stringOne=="Glaucoma"){
            setContentView(R.layout.layout_glaucoma)
        }
        else if (stringOne=="Diabetic retinopathy"){
            setContentView(R.layout.layout_diabetic_retinopathy)
        }
        else if (stringOne=="Hypertensive retinopathy"){
            setContentView(R.layout.layout_hypertensive_retinopathy)
        }
        else if (stringOne=="Corneal blindness"){
            setContentView(R.layout.layout_corneal_blindness)
        }
        else if (stringOne=="Childhood Blindness"){
            setContentView(R.layout.layout_childhood_blindness)
        }
        else if (stringOne=="Age related macular degeneration"){
            setContentView(R.layout.layout_age_related)
        }
        else if (stringOne=="Structure & Function"){
            setContentView(R.layout.layout_structure)
        }

//        refer_catract.setOnClickListener{
//            setContentView(R.layout.view_cataract)
//        }

        back_arrow.setOnClickListener(){
            finish()
        }

    }

}