package com.example.visionguardian

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.visionguardian.db.ExaminationData
import com.example.visionguardian.db.MyDatabase
import com.example.visionguardian.db.Patient
import kotlinx.android.synthetic.main.activity_examination.*
import java.util.*


class Examination : AppCompatActivity() {
    var flag = 1
    var myDatabase: MyDatabase? = null
    private var str1 = ""
    var str2 = ""
    var str3 = ""
    var str4 = ""
    var str5 = ""
    var str6 = ""
    var str7 = ""
    var str8 = ""
    var str9 = ""
    var str10 = ""
    var str11 = ""
    var str12 = ""
    var str13 = ""
    var str14 = ""
    var str15 = ""
    var str16 = ""
    var srt_10_1 = ""
    private var srt_10_2 = ""
    private var srt_10_3 = ""
    private var srt_10_4 = ""
    private var srt_10_5 = ""
    private var srt_10_6 = ""
    var srt_11_1 = ""
    var srt_11_2 = ""
    var srt_11_3 = ""
    var srt_11_4 = ""
    var srt_11_5 = ""
    var srt_11_6 = ""
    var srt_11_7 = ""
    var srt_11_8 = ""
    var srt_11_9 = ""

    var ques1 = ""
    var ques1_which = ""
    var ques1_duration = ""
    var ques1_unit = ""
    var ques2 = ""
    var ques2_which = ""
    var ques2_duration = ""
    var ques2_unit = ""

    var ques3 = ""
    var ques3_unit = ""
    var ques3_duration = ""
    var ques3_which = ""

    var ques4 = ""
    var ques5 = ""
    var ques6 = ""
    var ques6_1 = ""
    var ques7 = ""
    var ques8 = ""
    var ques9 = ""
    var ques10 = ""
    var ques10_unit = ""
    var ques11 = ""
    var ques11_1 = ""
    var ques11_3 = ""
    var ques12 = ""
    var patientID = 0
    var patientName = ""

    var str3_1 = ""
    var str3_2 = ""
    var str3_3 = ""
    var str3_4 = ""
    var str3_5 = ""
    var ques3_tital = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_examination)
        loadLocate()
        setUpDB()
        val intent: Intent = intent
        patientID = intent.getIntExtra("patientID", 5)
        patientName = intent.getStringExtra("patientName").toString()
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)


        val duration = arrayOf("Years", "Months", "Weeks", "Days")
        val adapter = ArrayAdapter(
                this, // Context
                android.R.layout.simple_spinner_item, // Layout
                duration // Array
        )
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)

        // Finally, data bind the spinner object with dapter
        classSpinner.adapter = adapter
        classSpinner_q2.adapter = adapter
        classSpinner_q3.adapter = adapter
        spinnerunit10.adapter = adapter
        spinnerunit10_1.adapter = adapter

        classSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Display the selected item text on text view
                // classSpinner.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                ques1_unit = parent.getItemAtPosition(position).toString()
                //
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        btShowmore.setOnClickListener {
            btShowmore.visibility = View.GONE
            linear_more_option.visibility = View.VISIBLE

        }




        radio_group1.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    card1.setBackgroundColor(Color.WHITE)

                    if (radio_langange.text.toString() == "Yes") {
                        card1.setBackgroundColor(Color.WHITE)
                        which_eye1.visibility = View.VISIBLE
                        firstDuration.visibility = View.VISIBLE
                    } else if (radio_langange.text.toString() == "No") {
                        card1.setBackgroundColor(Color.GRAY)
                        which_eye1.visibility = View.GONE
                        firstDuration.visibility = View.GONE
                    } else {
                        card1.setBackgroundColor(Color.WHITE)
                    }
                    ques1 = radio_langange.text.toString()
                    // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )

        which_eye1.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    ques1_which = radio_langange.text.toString()
                    /*val builder = AlertDialog.Builder(this)
                    val inflater = layoutInflater
                    builder.setTitle("Enter Duration")
                    val dialogLayout = inflater.inflate(R.layout.duration_dialog, null)
                    val editText  = dialogLayout.findViewById<EditText>(R.id.editText)
                    builder.setView(dialogLayout)
                    builder.setPositiveButton("Ok") { dialogInterface, i ->
                        card1.setBackgroundColor(Color.GRAY)
                        question1_duration.setText(editText.text.toString())
                        ques1_duration=editText.text.toString()
                    }
                    builder.show()*/

                })

        which_eye2.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    ques2_which = radio_langange.text.toString()
                    /* val builder = AlertDialog.Builder(this)
                     val inflater = layoutInflater
                     builder.setTitle("Enter Duration")
                     val dialogLayout = inflater.inflate(R.layout.duration_dialog, null)
                     val editText  = dialogLayout.findViewById<EditText>(R.id.editText)
                     builder.setView(dialogLayout)
                     builder.setPositiveButton("Ok") { dialogInterface, i ->
                         card2.setBackgroundColor(Color.GRAY)
                         question2_duration.setText(editText.text.toString())
                         ques2_duration=editText.text.toString()
                     }
                     builder.show()*/

                })

        classSpinner_q2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Display the selected item text on text view
                // classSpinner.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                ques2_unit = parent.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }






        radio_group2.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)

                    if (radio_langange.text.toString() == "Yes") {
                        card2.setBackgroundColor(Color.WHITE)
                        which_eye2.visibility = View.VISIBLE
                        secondDuration.visibility = View.VISIBLE
                    } else {
                        card2.setBackgroundColor(Color.GRAY)
                        which_eye2.visibility = View.GONE
                        secondDuration.visibility = View.GONE
                    }
                    ques2 = radio_langange.text.toString()
                    // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )


        //Q 3
        radio_group3.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)

                    if (radio_langange.text.toString() == "Yes") {
                        card3.setBackgroundColor(Color.WHITE)
                        which_eye3.visibility = View.VISIBLE
                        Duration3.visibility = View.VISIBLE
                        qua3Checkbox.visibility = View.VISIBLE
                    } else {
                        card3.setBackgroundColor(Color.GRAY)
                        which_eye3.visibility = View.GONE
                        Duration3.visibility = View.GONE
                        qua3Checkbox.visibility = View.GONE
                    }
                    ques3 = radio_langange.text.toString()




                    
                    //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )

        classSpinner_q3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Display the selected item text on text view

                // classSpinner.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                ques3_unit = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
        which_eye3.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    ques3_which = radio_langange.text.toString()

                    /* val builder = AlertDialog.Builder(this)
                     val inflater = layoutInflater
                     builder.setTitle("Enter Duration")
                     val dialogLayout = inflater.inflate(R.layout.duration_dialog, null)
                     val editText  = dialogLayout.findViewById<EditText>(R.id.editText)
                     builder.setView(dialogLayout)
                     builder.setPositiveButton("Ok") { dialogInterface, i ->
                         question3_duration.setText(editText.text.toString())
                         ques3_duration=editText.text.toString()
                     }
                     builder.show()*/

                })




        radio_group5.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    card5.setBackgroundColor(Color.GRAY)
                    flag += 1
                    ques5 = radio_langange.text.toString()
                    //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )

//        radio_group7.setOnCheckedChangeListener(
//                RadioGroup.OnCheckedChangeListener { group, checkedId ->
//                    val radio_langange: RadioButton = findViewById(checkedId)
//                    card7.setBackgroundColor(Color.GRAY)
//                    flag += 1
//                    ques7=radio_langange.text.toString()
//                  //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
//                }
//        )
//
//        radio_group8.setOnCheckedChangeListener(
//                RadioGroup.OnCheckedChangeListener { group, checkedId ->
//                    val radio_langange: RadioButton = findViewById(checkedId)
//                    card8.setBackgroundColor(Color.GRAY)
//                    flag += 1
//                    ques8=radio_langange.text.toString()
//                    //Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
//                }
//        )


        radio_group9.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    card9.setBackgroundColor(Color.GRAY)
                    flag += 1
                    ques9 = radio_langange.text.toString()
                    // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )

        radio_group12.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    card12.setBackgroundColor(Color.GRAY)
                    flag += 1
                    ques12 = radio_langange.text.toString()
                    // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )


        //q11
        addSecond7.setOnClickListener {
            addSecond7.visibility = View.GONE
            q7_1.visibility = View.VISIBLE
        }

        spinnerunit11.adapter = adapter
        spinnerunit11_1.adapter = adapter
        spinnerunit11.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Display the selected item text on text view
                // classSpinner.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                ques11_1 = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }


        //Q11
        val medication = arrayOf("Nil", "Diabetes", "Hypertension", "Asthma", "Thyroid", "Seizure", "Allergy", "Arthritis", "Others")
        val adaptermedi = ArrayAdapter(
                this, // Context
                android.R.layout.simple_spinner_item, // Layout
                medication // Array
        )
        adaptermedi.setDropDownViewResource(android.R.layout.simple_list_item_1)
        medicationSpinner.adapter = adaptermedi
        medicationSpinner_1.adapter = adaptermedi
        medicationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Display the selected item text on text view
                // classSpinner.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                ques11_3 = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        //Q10
        val pastHistoryList = arrayOf("Nil", "Cataract surgery", "Retinal surgery", "glaucoma treatment", "Eye injuries", "Laser treatment", "Other")
        val adapterpast = ArrayAdapter(
                this, // Context
                android.R.layout.simple_spinner_item, // Layout
                pastHistoryList // Array
        )
        adapterpast.setDropDownViewResource(android.R.layout.simple_list_item_1)
        pastHistorySpinner.adapter = adapterpast
        pastHistorySpinner1.adapter = adapterpast
        pastHistorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Display the selected item text on text view
                // classSpinner.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                ques10 = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        pastHistorySpinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Display the selected item text on text view
                // classSpinner.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                ques10 = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        spinnerunit10.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Display the selected item text on text view
                // classSpinner.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                ques10_unit = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        spinnerunit10_1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Display the selected item text on text view
                // classSpinner.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                // ques10_unit=parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }




        addSecond6.setOnClickListener {
            addSecond6.visibility = View.GONE
            add_lin_6.visibility = View.VISIBLE
        }


        /* question6_answer.addTextChangedListener(object : TextWatcher {

             override fun afterTextChanged(s: Editable) {}

             override fun beforeTextChanged(s: CharSequence, start: Int,
                                            count: Int, after: Int) {
             }

             override fun onTextChanged(s: CharSequence, start: Int,
                                        before: Int, count: Int) {
                 if (count>=1){
                     card6.setBackgroundColor(Color.GRAY)
                 }
             }
         })*/

        submit_next.setOnClickListener {
            if (flag >= 0) {
                ques11 = " (" + question11_duration.text + " " + ques11_1 + " )"
                ques6 = ques11_3
                ques7 = " (" + pastHistory_duration.text + " " + ques10_unit + " )"
                if (ques1 == "Yes") {
                    ques1 = "$ques1_which ( ${question1_duration.text} $ques1_unit )"
                }
                if (ques2 == "Yes") {
                    ques2 = "$ques2_which ( ${question2_duration.text} $ques2_unit )"
                }
                if (ques3 == "Yes") {
                    ques3_duration = question3_duration.text.toString()
                    ques3 = ques3_tital
                    ques5 = " $ques3_which( ${question3_duration.text} $ques3_unit )"
                }
                val myEdit = sharedPreferences.edit()
                myEdit.putString("history_1", ques1)
                myEdit.putString("history_2", ques2)
                myEdit.putString("history_3", ques3)
                myEdit.putString("history_4", ques4)
                myEdit.putString("history_5", ques5)
                myEdit.putString("history_6", ques6)
                myEdit.putString("history_6_1", ques6_1)
                myEdit.putString("history_7", ques7)
                myEdit.putString("history_8", ques8)
                myEdit.putString("history_9", ques9)
                myEdit.putString("history_10", ques10)
                myEdit.putString("history_11", ques11)
                myEdit.putString("history_12", ques12)
                myEdit.putInt("p_ID", patientID)
                myEdit.putString("patientName", patientName)
                myEdit.commit()
                myEdit.apply()


                val intent = Intent(this, VisionScreening::class.java)
                startActivity(intent)
            } else
                showAlertDialog()

            /* val intent=Intent(this, VisionScreening::class.java)
             startActivity(intent)*/
        }

        back_arrow.setOnClickListener { finish() }

    }

    //Q 4
    fun onCheckboxClicked4(view: View) {
        card4.setBackgroundColor(Color.GRAY)
        val checked = (view as CheckBox).isChecked

        // Check which checkbox was clicked
        when (view.getId()) {
            R.id.question4_choice1 -> str1 = if (checked) "Black spots in front of the eye, " else ""
            R.id.question4_choice2 -> str2 = if (checked) "Growth on the eye, " else ""
            R.id.question4_choice3 -> str3 = if (checked) "Swelling on the eyelid, " else ""
            R.id.question4_choice4 -> str4 = if (checked) "Itching/irritation, " else ""
            R.id.question4_choice5 -> str5 = if (checked) "Eyestrain, " else ""
            R.id.question4_choice6 -> str6 = if (checked) "Headache, " else ""
            R.id.question4_choice7 -> str7 = if (checked) "Light sensitivity, " else ""
            R.id.question4_choice8 -> str8 = if (checked) "Flashes, " else ""
            R.id.question4_choice9 -> str9 = if (checked) "White scales on the lid margin, " else ""
            R.id.question4_choice10 -> str10 = if (checked) "Deviation of eyes, " else ""
            R.id.question4_choice11 -> str11 = if (checked) "Foreign body sensation, " else ""
            R.id.question4_choice12 -> str12 = if (checked) "Dryness, " else ""
            R.id.question4_choice13 -> str13 = if (checked) "Coloured halos around light, " else ""
            R.id.question4_choice14 -> str14 = if (checked) "Glare, " else ""
            R.id.question4_choice15 -> str15 = if (checked) "Double vision, " else ""
            R.id.question4_choice16 -> str16 = if (checked) "Nil " else ""


        }
        ques4 = str1 + str2 + str3 + str4 + str5 + str6 + str7 + str8 + str9 + str10 + str11 + str12 + str13 + str14 + str15 + str16
        // Toast.makeText(getApplicationContext(), str1 + str2 + str3, Toast.LENGTH_SHORT).show()

    }

    //Q 10
    /*  fun onCheckboxClicked10(view: View) {
          card10.setBackgroundColor(Color.GRAY)
          val checked = (view as CheckBox).isChecked()

          // Check which checkbox was clicked
          when (view.getId()) {
              R.id.question10_choice1 -> srt_10_1 = if (checked) "Cataract surgery" else ""
              R.id.question10_choice2 -> srt_10_2 = if (checked) "Retinal surgery " else ""
              R.id.question10_choice3 -> srt_10_3 = if (checked) "glaucoma treatment" else ""
              R.id.question10_choice4 -> srt_10_4 = if (checked) "Eye injuries " else ""
              R.id.question10_choice5 -> srt_10_5 = if (checked) "Other " else ""
              R.id.question10_choice6 -> srt_10_6 = if (checked) "Laser treatment" else ""
          }

      }*/

//    //Q 11
//    fun onCheckboxClicked11(view: View) {
//        card11.setBackgroundColor(Color.GRAY)
//        val checked = (view as CheckBox).isChecked()
//
//        // Check which checkbox was clicked
//        when (view.getId()) {
//            R.id.question11_choice1 -> srt_11_1 = if (checked) "Diabetes " else ""
//            R.id.question11_choice2 -> srt_11_2 = if (checked) "Hypertension" else ""
//            R.id.question11_choice3 -> srt_11_3 = if (checked) "Asthma" else ""
//            R.id.question11_choice4 -> srt_11_4 = if (checked) "Thyroid" else ""
//            R.id.question11_choice5 -> srt_11_5 = if (checked) "Seizure " else ""
//            R.id.question11_choice6 -> srt_11_6 = if (checked) "Allergy " else ""
//            R.id.question11_choice7 -> srt_11_7 = if (checked) "Arthritis" else ""
//            R.id.question11_choice8 -> srt_11_8 = if (checked) "Others " else ""
//            R.id.question11_choice9 -> srt_11_9 = if (checked) "Nil" else ""
//
//
//        }
//    }


    private fun showAlertDialog() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Data Incomplete")
        alertDialog.setMessage("Enter All Data...")
        alertDialog.setPositiveButton(
                "OK"
        ) { _, _ ->

        }

        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    private fun setUpDB() {
        myDatabase = Room.databaseBuilder(this, MyDatabase::class.java, "StudentDB")
                .allowMainThreadQueries().build()
    }

    fun onCheckboxClicked3(view: View) {
        val checked = (view as CheckBox).isChecked

        // Check which checkbox was clicked
        when (view.getId()) {
            R.id.question3_1 -> str3_1 = if (checked) "Redness " else ""
            R.id.question3_2 -> str3_2 = if (checked) "Pain " else ""
            R.id.question3_3 -> str3_3 = if (checked) "Tearing " else ""
            R.id.question3_4 -> str3_4 = if (checked) "Discharge " else ""
            R.id.question3_5 -> str3_5 = if (checked) "Uncomfortable eyes " else ""
        }
        ques3_tital = str3_1 + str3_2 + str3_3 + str3_4 + str3_5
    }

    /* fun withEditText() {
     val builder = AlertDialog.Builder(this)
     val inflater = layoutInflater
     builder.setTitle("Search Patient")
     val dialogLayout = inflater.inflate(R.layout.search_dialog, null)
     val editText  = dialogLayout.findViewById<EditText>(R.id.editText)
     builder.setView(dialogLayout)
     builder.setPositiveButton("Search") { dialogInterface, i ->editText.text.toString()) }
     builder.show()
 }*/

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