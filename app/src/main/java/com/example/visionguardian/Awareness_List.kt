package com.example.visionguardian

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_awarness.*

class Awareness_List : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_awarness)
        val arrayList = ArrayList<AwarenessList>()
        arrayList.add(AwarenessList(9, R.drawable.ic_cataract, getString(R.string.awarness9)))
        arrayList.add(AwarenessList(1, R.drawable.ic_cataract, getString(R.string.awarness1)))
        arrayList.add(
            AwarenessList(
                2,
                R.drawable.ic_refractive_error,
                getString(R.string.awarness2)
            )
        )
        arrayList.add(AwarenessList(3, R.drawable.ic_glaucoma, getString(R.string.awarness3)))
        arrayList.add(
            AwarenessList(
                4,
                R.drawable.ic_corneal_blindness,
                getString(R.string.awarness4)
            )
        )
        arrayList.add(
            AwarenessList(
                5,
                R.drawable.ic_childhood_lindness,
                getString(R.string.awarness5)
            )
        )
        arrayList.add(
            AwarenessList(
                6,
                R.drawable.ic_diabetic_retinopathy,
                getString(R.string.awarness6)
            )
        )
        arrayList.add(
            AwarenessList(
                7,
                R.drawable.ic_hypertensive_retinopathy,
                getString(R.string.awarness7)
            )
        )
        arrayList.add(AwarenessList(8, R.drawable.ic_age_related, getString(R.string.awarness8)))
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val adapter = AdapterAwareness(this, arrayList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter

        back_arrow.setOnClickListener {
            finish()
        }

    }
}