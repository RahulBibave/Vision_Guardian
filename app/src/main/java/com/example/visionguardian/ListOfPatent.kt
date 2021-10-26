package com.example.visionguardian

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.visionguardian.db.MyDatabase
import com.example.visionguardian.db.Patient
import kotlinx.android.synthetic.main.activity_list_of_paitent.*
import java.util.*

class ListOfPatent : AppCompatActivity() {

    var myDatabase: MyDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_paitent)
        loadLocate()
        setUpDB()
        getAllData()


        back_arrow.setOnClickListener { finish() }
        search.setOnClickListener { withEditText() }
    }

    private fun getAllData() {
        val stuData = myDatabase!!.dao()!!.patient
        //  val recyclerView: RecyclerView = findViewById(R.id.recyclerViewPatient)
        val adapter = ListAdapter(this, stuData as ArrayList<Patient>)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerViewPatient.layoutManager = layoutManager
        recyclerViewPatient.itemAnimator = DefaultItemAnimator()
        recyclerViewPatient.adapter = adapter
    }

    private fun setUpDB() {
        myDatabase = Room.databaseBuilder(this, MyDatabase::class.java, "StudentDB")
            .allowMainThreadQueries().build()
    }

    private fun withEditText() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Search Patient")
        val dialogLayout = inflater.inflate(R.layout.search_dialog, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.editText)
        builder.setView(dialogLayout)
        builder.setPositiveButton("Search") { dialogInterface, i -> getSearch(editText.text.toString()) }
        builder.show()
    }

    private fun getSearch(string: String) {
        val stuData = myDatabase!!.dao()!!.findUserWithName(string)
        if (stuData.isEmpty()) {
            getAllData()
        } else {
            val adapter = ListAdapter(this, stuData as ArrayList<Patient>)
            val layoutManager = LinearLayoutManager(applicationContext)
            recyclerViewPatient.layoutManager = layoutManager
            recyclerViewPatient.itemAnimator = DefaultItemAnimator()
            recyclerViewPatient.adapter = adapter
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