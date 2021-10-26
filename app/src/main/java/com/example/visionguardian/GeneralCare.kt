package com.example.visionguardian

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_general_care.*
import java.util.*
import kotlin.collections.ArrayList

class GeneralCare : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_care)
        loadLocate()
        val arrayList = ArrayList<CareList>()
        arrayList.add(CareList(1, R.drawable.ic_hygiene_eyecare, getString(R.string.care1)))
        arrayList.add(CareList(2, R.drawable.ic_goodvision, getString(R.string.care2)))
        arrayList.add(CareList(3, R.drawable.ic_caring_spectacles, getString(R.string.care3)))
        arrayList.add(CareList(4, R.drawable.ic_instillation, getString(R.string.care4)))
        arrayList.add(CareList(5, R.drawable.ic_postoperative_care, getString(R.string.care5)))
        arrayList.add(CareList(6, R.drawable.ic_firstaid, getString(R.string.care6)))
        arrayList.add(CareList(7, R.drawable.ic_layer, getString(R.string.care7)))
        arrayList.add(CareList(8, R.drawable.ic_lens_care, getString(R.string.care8)))
        // arrayList.add(CareList(9,"Regular physician check-up after 40 years"))

        val adapter = AdapterGCare(this, arrayList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerViewCare.layoutManager = layoutManager
        recyclerViewCare.itemAnimator = DefaultItemAnimator()
        recyclerViewCare.adapter = adapter




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



