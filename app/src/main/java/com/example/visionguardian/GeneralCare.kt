package com.example.visionguardian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_general_care.*

class GeneralCare : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_care)

        val arrayList = ArrayList<CareList>()
        arrayList.add(CareList(1,"Hygiene and eye care"))
        arrayList.add(CareList(2,"Eat healthy for good vision"))
        arrayList.add(CareList(3,"Caring your spectacle"))
        arrayList.add(CareList(4,"Instillation of eye drops"))
        arrayList.add(CareList(5,"Postoperative care"))
        arrayList.add(CareList(6,"First aid"))
        arrayList.add(CareList(7,"Eye care solutions of digital eye strain"))
        arrayList.add(CareList(8,"Contact lens care"))
       // arrayList.add(CareList(9,"Regular physician check-up after 40 years"))

        val adapter=AdapterGCare(this,arrayList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerViewCare.layoutManager = layoutManager
        recyclerViewCare.itemAnimator = DefaultItemAnimator()
        recyclerViewCare.adapter = adapter




        back_arrow.setOnClickListener { finish() }
    }
}



