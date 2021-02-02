package com.example.visionguardian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_awarness.*

class Awarness_List : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_awarness)
       // val myArray = arrayOf("Cataract","Refractive error and low vision", "Glaucoma","Diabetic retinopathy","Hypertensive retinopathy","Corneal blindness","Childhood Blindness","Age related macular degeneration" )
        val arrayList = ArrayList<AwarnessList>()
        arrayList.add(AwarnessList(9,R.drawable.ic_cataract,"Structure & Function"))
        arrayList.add(AwarnessList(1,R.drawable.ic_cataract,"Cataract"))
        arrayList.add(AwarnessList(2,R.drawable.ic_refractive_error,"Refractive error and low vision"))
        arrayList.add(AwarnessList(3,R.drawable.ic_glaucoma,"Glaucoma"))
        arrayList.add(AwarnessList(4,R.drawable.ic_corneal_blindness,"Corneal blindness"))
        arrayList.add(AwarnessList(5,R.drawable.ic_childhood_lindness,"Childhood Blindness"))
        arrayList.add(AwarnessList(6,R.drawable.ic_diabetic_retinopathy,"Diabetic retinopathy"))
        arrayList.add(AwarnessList(7,R.drawable.ic_hypertensive_retinopathy,"Hypertensive retinopathy"))
        arrayList.add(AwarnessList(8,R.drawable.ic_age_related,"Age related macular degeneration"))
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val adapter=AdapterAwarness(this,arrayList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter

        back_arrow.setOnClickListener{
            finish()
        }

    }
}