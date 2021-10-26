package com.example.visionguardian

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.visionguardian.db.ExaminationData
import com.example.visionguardian.db.MyDatabase
import kotlinx.android.synthetic.main.activity_tourch_light_examination.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class TourchLightExamination : AppCompatActivity() {
    var myDatabase: MyDatabase? = null
    var devLeft = ""
    var devRight = ""
    var sizeLeft = ""
    var sizeRight = ""
    var eyelidsRight = ""
    var eyelidsLeft = ""
    var eyelashesLeft = ""

    var eyelashesRight = ""
    var whiteRight = ""
    var whiteLeft = ""
    var blackLeft = ""
    var blackRight = ""
    var pupilRight = ""
    var pupilLeft = ""
    var externalLeft = ""
    var externalRight = ""
    var foreignRight = ""
    var foreignLeft = ""

    var ques1 = ""
    var ques2 = ""
    var ques3 = ""
    var ques4 = ""
    var ques5 = ""
    var ques6 = ""
    var ques6_1 = ""

    var Q6_op = ""
    var Q6_duration = ""
    var Q7_op = ""
    var Q7_duration = ""


    var ques7 = ""
    var ques8 = ""
    var ques9 = ""
    var ques10 = ""
    var ques11 = ""
    var ques12 = ""
    var patientID = 0
    var flag = 1

    var disWithoutGL = ""
    var disWithoutGR = ""
    var disWithGR = ""
    var disWithGL = ""
    var nearVisionWithR = ""
    var nearVisionWithL = ""
    var nearVisionWithOutL = ""
    var nearVisionWithOutR = ""
    var catOfVisual = ""


    var sphereR = ""
    var sphereL = ""
    var cylinderR = ""
    var cylinderL = ""
    var axisR = ""
    var axisL = ""
    var dateOfExam = ""

    var flagDev = 1
    var flagsize = 1
    var flageyelids = 1
    var flageyelashes = 1
    var flageyeWhite = 1
    var flageyeBlack = 1
    var flageyePupile = 1
    var flageyeInjury = 1
    var flageyeFB = 1
    val REQUEST_CODE = 1

    var sp_R = ""
    var sp_L = ""
    var cy_R = ""
    var cy_L = ""
    var axis_R = ""
    var axis_L = ""
    var sub_visionR = ""
    var sub_visionL = ""
    var sub_AddR = ""
    var sub_AddL = ""
    var sub_NVR = ""
    var sub_NVL = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tourch_light_examination)
        loadLocate()
        setUpDB()
        getShareData()



        dateOfExam = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            var answer: String = current.format(formatter)
            answer
        } else {
            var date = Date()
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            val answer: String = formatter.format(date)
            answer
        }


        var back_arrow = findViewById<ImageView>(R.id.back_arrow)
        back_arrow.setOnClickListener { finish() }

        radio_deviation_right.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flagDev += 1
                if (flagDev == 3) {
                    card_t1.setBackgroundColor(Color.GRAY)
                }
                if (radio_langange.text.toString() == getString(R.string.absent)) {
                    devRight = "Absent"
                } else if (radio_langange.text.toString() == getString(R.string.present)) {
                    devRight = "Present"
                }

                // devRight = radio_langange.text.toString()
                //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )

        radio_deviation_left.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flagDev += 1
                if (flagDev == 3) {
                    card_t1.setBackgroundColor(Color.GRAY)
                }
                //  flag += 1
                if (radio_langange.text.toString() == getString(R.string.absent)) {
                    devLeft = "Absent"
                } else if (radio_langange.text.toString() == getString(R.string.present)) {
                    devLeft = "Present"
                }
                // devLeft = radio_langange.text.toString()
                // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )

        radio_size_right.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flagsize += 1
                if (flagsize == 3) {
                    card_t2.setBackgroundColor(Color.GRAY)
                }

                //  flag += 1
                if (radio_langange.text.toString() == getString(R.string.yes)) {
                    sizeRight = "Yes"
                } else if (radio_langange.text.toString() == getString(R.string.no)) {
                    sizeRight = "No"
                }
                // sizeRight = radio_langange.text.toString()
                //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )

        radio_size_left.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flagsize += 1
                if (flagsize == 3) {
                    card_t2.setBackgroundColor(Color.GRAY)
                }
                if (radio_langange.text.toString() == getString(R.string.yes)) {
                    sizeLeft = "Yes"
                } else if (radio_langange.text.toString() == getString(R.string.no)) {
                    sizeLeft = "No"
                }
                //  flag += 1
                //  sizeLeft = radio_langange.text.toString()
                //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )


        radio_eyelids_right.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flageyelids += 1
                if (flageyelids == 3) {
                    card_t3.setBackgroundColor(Color.GRAY)
                }
                when {
                    radio_langange.text.toString() == getString(R.string.normal) -> {
                        eyelidsRight = "Normal"
                    }
                    radio_langange.text.toString() == getString(R.string.diffuse) -> {
                        eyelidsRight = "Diffuse swelling"
                    }
                    radio_langange.text.toString() == getString(R.string.whitescale) -> {
                        eyelidsRight = "White scales on the lid margin"
                    }
                    radio_langange.text.toString() == getString(R.string.stye) -> {
                        eyelidsRight = "Stye / chalazion"
                    }
                    radio_langange.text.toString() == getString(R.string.abnormal) -> {
                        eyelidsRight = "Abnormal closure"
                    }
                }

                //  flag += 1
                // eyelidsRight = radio_langange.text.toString()
                //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )
        radio_eyelids_left.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flageyelids += 1
                if (flageyelids == 3) {
                    card_t3.setBackgroundColor(Color.GRAY)
                }
                //  flag += 1
                when {
                    radio_langange.text.toString() == getString(R.string.normal) -> {
                        eyelidsLeft = "Normal"
                    }
                    radio_langange.text.toString() == getString(R.string.diffuse) -> {
                        eyelidsLeft = "Diffuse swelling"
                    }
                    radio_langange.text.toString() == getString(R.string.whitescale) -> {
                        eyelidsLeft = "White scales on the lid margin"
                    }
                    radio_langange.text.toString() == getString(R.string.stye) -> {
                        eyelidsLeft = "Stye / chalazion"
                    }
                    radio_langange.text.toString() == getString(R.string.abnormal) -> {
                        eyelidsLeft = "Abnormal closure"
                    }
                }
                // eyelidsLeft = radio_langange.text.toString()
                //   Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )



        radio_eyelashes_right.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flageyelashes += 1
                if (flageyelashes == 3) {
                    card_t4.setBackgroundColor(Color.GRAY)
                }

                when {
                    radio_langange.text.toString() == getString(R.string.normal) -> {
                        eyelashesRight = "Normal"
                    }
                    radio_langange.text.toString() == getString(R.string.lashes) -> {
                        eyelashesRight = "Lashes facing inward"
                    }
                    radio_langange.text.toString() == getString(R.string.crusting) -> {
                        eyelashesRight = "Crusting"
                    }
                }
                //  flag += 1
                //eyelashesRight = radio_langange.text.toString()
                //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )
        radio_eyelashes_left.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flageyelashes += 1
                if (flageyelashes == 3) {
                    card_t4.setBackgroundColor(Color.GRAY)
                }
                when {
                    radio_langange.text.toString() == getString(R.string.normal) -> {
                        eyelashesLeft = "Normal"
                    }
                    radio_langange.text.toString() == getString(R.string.lashes) -> {
                        eyelashesLeft = "Lashes facing inward"
                    }
                    radio_langange.text.toString() == getString(R.string.crusting) -> {
                        eyelashesLeft = "Crusting"
                    }
                }
                //  flag += 1
                //eyelashesLeft = radio_langange.text.toString()
                //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )



        radio_white_right.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flageyeWhite += 1
                if (flageyeWhite == 3) {
                    card_t5.setBackgroundColor(Color.GRAY)
                }

                when {
                    radio_langange.text.toString() == getString(R.string.whitewith) -> {
                        whiteRight = "White with few veins"
                    }
                    radio_langange.text.toString() == getString(R.string.red) -> {
                        whiteRight = "Red "
                    }
                    radio_langange.text.toString() == getString(R.string.whitest) -> {
                        whiteRight = "Whitish mass present"
                    }
                    radio_langange.text.toString() == getString(R.string.other) -> {
                        whiteRight = "Other"
                    }
                }
                //  flag += 1
                // whiteRight = radio_langange.text.toString()
                // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )
        radio_white_left.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flageyeWhite += 1
                if (flageyeWhite == 3) {
                    card_t5.setBackgroundColor(Color.GRAY)
                }
                //  flag += 1
                when {
                    radio_langange.text.toString() == getString(R.string.whitewith) -> {
                        whiteLeft = "White with few veins"
                    }
                    radio_langange.text.toString() == getString(R.string.red) -> {
                        whiteLeft = "Red "
                    }
                    radio_langange.text.toString() == getString(R.string.whitest) -> {
                        whiteLeft = "Whitish mass present"
                    }
                    radio_langange.text.toString() == getString(R.string.other) -> {
                        whiteLeft = "Other"
                    }
                }
                // whiteLeft = radio_langange.text.toString()
                //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )




        radio_black_right.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flageyeBlack += 1
                if (flageyeBlack == 3) {
                    card_t6.setBackgroundColor(Color.GRAY)
                }

                when {
                    radio_langange.text.toString() == getString(R.string.bkackandsk) -> {
                        blackRight = "Black and shiny "
                    }
                    radio_langange.text.toString() == getString(R.string.whiteorgrey) -> {
                        blackRight = "White or grey areas "
                    }
                    radio_langange.text.toString() == getString(R.string.foreign) -> {
                        blackRight = "Foreign body "
                    }
                }
                //  flag += 1
                // blackRight = radio_langange.text.toString()
                //    Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )
        radio_black_left.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flageyeBlack += 1
                if (flageyeBlack == 3) {
                    card_t6.setBackgroundColor(Color.GRAY)
                }
                //  flag += 1
                when {
                    radio_langange.text.toString() == getString(R.string.bkackandsk) -> {
                        blackLeft = "Black and shiny "
                    }
                    radio_langange.text.toString() == getString(R.string.whiteorgrey) -> {
                        blackLeft = "White or grey areas "
                    }
                    radio_langange.text.toString() == getString(R.string.foreign) -> {
                        blackLeft = "Foreign body "
                    }
                }
                // blackLeft = radio_langange.text.toString()
                //   Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )





        radio_pupil_right.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flageyePupile += 1
                if (flageyePupile == 3) {
                    card_t7.setBackgroundColor(Color.GRAY)
                }

                if (radio_langange.text.toString() == getString(R.string.black)) {
                    pupilRight = "Black "
                } else if (radio_langange.text.toString() == getString(R.string.whiteorgrey)) {
                    pupilRight = "White or grey areas "
                }
                //  flag += 1
                // pupilRight = radio_langange.text.toString()
                //   Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )
        radio_pupil_left.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flageyePupile += 1
                if (flageyePupile == 3) {
                    card_t7.setBackgroundColor(Color.GRAY)
                }
                if (radio_langange.text.toString() == getString(R.string.black)) {
                    pupilLeft = "Black "
                } else if (radio_langange.text.toString() == getString(R.string.whiteorgrey)) {
                    pupilLeft = "White or grey areas "
                }
                //  flag += 1
                // pupilLeft = radio_langange.text.toString()
                //   Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )

        radio_external_right.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flageyeInjury += 1
                if (flageyeInjury == 3) {
                    card_t8.setBackgroundColor(Color.GRAY)
                }

                if (radio_langange.text.toString() == getString(R.string.yes)) {
                    externalRight = "Yes"
                } else if (radio_langange.text.toString() == getString(R.string.no)) {
                    externalRight = "No"
                }
                //  flag += 1
                //externalRight = radio_langange.text.toString()
                // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )
        radio_external_left.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flageyeInjury += 1
                if (flageyeInjury == 3) {
                    card_t8.setBackgroundColor(Color.GRAY)
                }
                if (radio_langange.text.toString() == getString(R.string.yes)) {
                    externalLeft = "Yes"
                } else if (radio_langange.text.toString() == getString(R.string.no)) {
                    externalLeft = "No"
                }
                //  flag += 1
                //externalLeft = radio_langange.text.toString()
                //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )



        radio_foreign_right.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flageyeFB += 1
                if (flageyeFB == 3) {
                    card_t9.setBackgroundColor(Color.GRAY)
                }
                if (radio_langange.text.toString() == getString(R.string.yes)) {
                    foreignRight = "Yes"
                } else if (radio_langange.text == getString(R.string.no)) {
                    foreignRight = "No"
                }
                //  flag += 1
                // foreignRight = radio_langange.text.toString()
                //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )
        radio_foreign_left.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flageyeFB += 1
                if (flageyeFB == 3) {
                    card_t9.setBackgroundColor(Color.GRAY)
                }
                if (radio_langange.text.toString() == getString(R.string.yes)) {
                    foreignLeft = "Yes"
                } else if (radio_langange.text == getString(R.string.no)) {
                    foreignLeft = "No"
                }
                //  flag += 1
                // foreignLeft = radio_langange.text.toString()

                // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )





        submit.setOnClickListener {
            ReferDialog()
        }


    }

    private fun submitData(reffer: String) {
        val examination = ExaminationData(
            ques1,
            ques2,
            ques3,
            ques4,
            ques5,
            ques6,
            ques6_1,
            ques7,
            reffer,
            ques9,
            ques10,
            ques11,
            ques12, Q6_op, Q6_duration, Q7_op, Q7_duration,
            patientID,
            disWithoutGL,
            disWithoutGR,
            disWithGL,
            disWithGR,
            nearVisionWithL,
            nearVisionWithR,
            nearVisionWithOutR,
            nearVisionWithOutL,
            catOfVisual,
            sphereL,
            sphereR,
            cylinderL,
            cylinderR,
            axisR,
            axisL,
            devLeft,
            devRight,
            sizeLeft,
            eyelidsRight,
            eyelidsLeft,
            eyelashesRight,
            eyelashesLeft,
            whiteRight,
            whiteLeft,
            blackLeft,
            blackRight,
            pupilRight,
            pupilLeft,
            externalLeft,
            externalRight,
            foreignRight,
            foreignLeft,
            dateOfExam,
            sp_R,
            sp_L,
            cy_R,
            cy_L,
            axis_R,
            axis_L,
            sub_visionR,
            sub_visionL,
            sub_AddR,
            sub_AddL,
            sub_NVR,
            sub_NVL, ""
        )
        myDatabase!!.examDao()!!.patientExamInsertion(examination)
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        sharedPreferences.edit().clear().commit()
        showAlertDialog()
    }

    private fun setUpDB() {
        myDatabase = Room.databaseBuilder(this, MyDatabase::class.java, "StudentDB")
                .allowMainThreadQueries().build()
    }

    private fun showAlertDialog() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("DigiDrishti")
        alertDialog.setMessage("Examination Completed...")
        alertDialog.setPositiveButton(
            "OK"

        ) { _, _ ->
            val intent = Intent(applicationContext, LandingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("EXIT", true)
            startActivity(intent)
            // finish()
        }

        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    private fun getShareData() {
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        sharedPreferences.getString("username", "defaultName")
        ques1 = sharedPreferences.getString("history_1", "").toString()
        ques2 = sharedPreferences.getString("history_2", "").toString()
        ques3 = sharedPreferences.getString("history_3", "").toString()
        ques4 = sharedPreferences.getString("history_4", "").toString()
        ques5 = sharedPreferences.getString("history_5", "").toString()
        ques6 = sharedPreferences.getString("history_6", "").toString()
        ques6_1 = sharedPreferences.getString("history_6_1", "").toString()
        ques7 = sharedPreferences.getString("history_7", "").toString()
        ques8 = sharedPreferences.getString("history_8", "").toString()
        ques9 = sharedPreferences.getString("history_9", "").toString()
        ques10 = sharedPreferences.getString("history_10", "").toString()
        ques11 = sharedPreferences.getString("history_11", "").toString()
        //note added on 23 Aug 2021
        ques12 = sharedPreferences.getString("note", "").toString()
        Q6_op = sharedPreferences.getString("Q6_op", "").toString()
        Q6_duration = sharedPreferences.getString("Q6_duration", "").toString()
        Q7_op = sharedPreferences.getString("Q7_op", "").toString()
        Q7_duration = sharedPreferences.getString("Q7_duration", "").toString()

        patientID = sharedPreferences.getInt("p_ID", 1)

        disWithoutGL = sharedPreferences.getString("DVWithoutL", "").toString()
        disWithoutGR = sharedPreferences.getString("DVWithoutR", "").toString()
        disWithGR = sharedPreferences.getString("DVWithR", "").toString()
        disWithGL = sharedPreferences.getString("DVWithL", "").toString()
        nearVisionWithR = sharedPreferences.getString("NVWithR", "").toString()
        nearVisionWithL = sharedPreferences.getString("NVWithL", "").toString()
        nearVisionWithOutL = sharedPreferences.getString("NVWithoutL", "").toString()
        nearVisionWithOutR = sharedPreferences.getString("NVWithoutR", "").toString()
        catOfVisual = sharedPreferences.getString("catOfVI", "").toString()

        sphereR = sharedPreferences.getString("sphereR", "").toString()
        sphereL = sharedPreferences.getString("sphereL", "").toString()
        cylinderR = sharedPreferences.getString("cylinderR", "").toString()
        cylinderL = sharedPreferences.getString("cylinderL", "").toString()
        axisR = sharedPreferences.getString("axisR", "").toString()
        axisL = sharedPreferences.getString("axisL", "").toString()

        sp_R = sharedPreferences.getString("sphereRAuto", "").toString()
        sp_L = sharedPreferences.getString("sphereLAuto", "").toString()
        cy_R = sharedPreferences.getString("cylinderRAuto", "").toString()
        cy_L = sharedPreferences.getString("cylinderLAuto", "").toString()
        axis_R = sharedPreferences.getString("axisRAuto", "").toString()
        axis_L = sharedPreferences.getString("axisLAuto", "").toString()

        sub_visionR = sharedPreferences.getString("sub_visionR", "").toString()
        sub_visionL = sharedPreferences.getString("sub_visionL", "").toString()
        sub_AddR = sharedPreferences.getString("sub_AddR", "").toString()
        sub_AddL = sharedPreferences.getString("sub_AddL", "").toString()
        sub_NVR = sharedPreferences.getString("sub_NVR", "").toString()
        sub_NVL = sharedPreferences.getString("sub_NVL", "").toString()

    }

    private fun ReferDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.doureffer))
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                /*val intent = Intent(this, OpenCamera::class.java)
                startActivityForResult(intent, REQUEST_CODE)*/
                submitData("Yes")

            }
            .setNegativeButton("No") { dialog, id ->
                submitData("No")
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    fun OnClickNormal(view: View) {
        val checked = (view as CheckBox).isChecked
        when (view.getId()) {

            R.id.right_eye_normal -> if (checked) {
                devRight = "Absent"
                devNormalR.isChecked = true
                sizeRight = "Yes"
                sizeNormalR.isChecked = true
                eyelidsRight = "Normal"
                eyelidsNormalR.isChecked = true
                eyelashesRight = "Normal"
                eyelashesNR.isChecked = true
                whiteRight = "White with few veins"
                whiteNR.isChecked = true
                blackRight = "Black and shiny"
                blackNR.isChecked = true
                pupilRight = "Black"
                pupilNR.isChecked = true
                externalRight = "No"
                externalNR.isChecked = true
                foreignRight = "No"
                foreignNR.isChecked = true

            } else Toast.makeText(applicationContext, "Right Unched ", Toast.LENGTH_SHORT).show()

            R.id.left_eye_normal -> if (checked) {
                devLeft = "Absent"
                devNormalL.isChecked = true
                sizeLeft = "Yes"
                sizeNormalL.isChecked = true
                eyelidsLeft = "Normal"
                eyelidsNormalL.isChecked = true
                eyelashesLeft = "Normal"
                eyelashesNL.isChecked = true
                whiteLeft = "White with few veins"
                whiteNL.isChecked = true
                blackLeft = "Black and shiny"
                blackNL.isChecked = true
                pupilLeft = "Black"
                pupilNL.isChecked = true
                externalLeft = "No"
                externalNL.isChecked = true
                foreignLeft = "No"
                foreignNL.isChecked = true

            } else Toast.makeText(applicationContext, "Left Unched ", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            submitData("Yes")
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
