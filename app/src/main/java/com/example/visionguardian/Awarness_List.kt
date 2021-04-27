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
        arrayList.add(AwarnessList(9,R.drawable.ic_cataract,getString(R.string.awarness9)))
        arrayList.add(AwarnessList(1,R.drawable.ic_cataract,getString(R.string.awarness1)))
        arrayList.add(AwarnessList(2,R.drawable.ic_refractive_error,getString(R.string.awarness2)))
        arrayList.add(AwarnessList(3,R.drawable.ic_glaucoma,getString(R.string.awarness3)))
        arrayList.add(AwarnessList(4,R.drawable.ic_corneal_blindness,getString(R.string.awarness4)))
        arrayList.add(AwarnessList(5,R.drawable.ic_childhood_lindness,getString(R.string.awarness5)))
        arrayList.add(AwarnessList(6,R.drawable.ic_diabetic_retinopathy,getString(R.string.awarness6)))
        arrayList.add(AwarnessList(7,R.drawable.ic_hypertensive_retinopathy,getString(R.string.awarness7)))
        arrayList.add(AwarnessList(8,R.drawable.ic_age_related,getString(R.string.awarness8)))
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