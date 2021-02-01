package com.example.visionguardian

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.visionguardian.db.ExaminationData
import com.example.visionguardian.db.MyDatabase
import kotlinx.android.synthetic.main.activity_examination.*
import kotlinx.android.synthetic.main.activity_refraction.*
import kotlinx.android.synthetic.main.activity_registration.*
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
    var dateOfExam=""

    var flagDev=1
    var flagsize=1
    var flageyelids=1
    var flageyelashes=1
    var flageyeWhite=1
    var flageyeBlack=1
    var flageyePupile=1
    var flageyeInjury=1
    var flageyeFB=1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tourch_light_examination)
        setUpDB()
        getShareData()



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            var answer: String =  current.format(formatter)
            dateOfExam=answer
        } else {
            var date = Date()
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            val answer: String = formatter.format(date)
            dateOfExam=answer
        }


       var back_arrow=findViewById<ImageView>(R.id.back_arrow)
        back_arrow.setOnClickListener { finish() }

        radio_deviation_right.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flagDev +=1
                    if (flagDev==3){
                        card_t1.setBackgroundColor(Color.GRAY)
                    }

                    devRight = radio_langange.text.toString()
                  //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )

        radio_deviation_left.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flagDev +=1
                    if (flagDev==3){
                        card_t1.setBackgroundColor(Color.GRAY)
                    }
                    //  flag += 1
                    devLeft = radio_langange.text.toString()
                   // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )

        radio_size_right.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flagsize +=1
                    if (flagsize==3){
                        card_t2.setBackgroundColor(Color.GRAY)
                    }

                    //  flag += 1
                    sizeRight = radio_langange.text.toString()
                  //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )

        radio_size_left.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flagsize +=1
                    if (flagsize==3){
                        card_t2.setBackgroundColor(Color.GRAY)
                    }
                    //  flag += 1
                    sizeLeft = radio_langange.text.toString()
                  //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )


        radio_eyelids_right.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flageyelids +=1
                    if (flageyelids==3){
                        card_t3.setBackgroundColor(Color.GRAY)
                    }

                    //  flag += 1
                    eyelidsRight = radio_langange.text.toString()
                  //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )
        radio_eyelids_left.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flageyelids +=1
                    if (flageyelids==3){
                        card_t3.setBackgroundColor(Color.GRAY)
                    }
                    //  flag += 1
                    eyelidsLeft = radio_langange.text.toString()
                 //   Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )



        radio_eyelashes_right.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flageyelashes +=1
                    if (flageyelashes==3){
                        card_t4.setBackgroundColor(Color.GRAY)
                    }

                    //  flag += 1
                    eyelashesRight = radio_langange.text.toString()
                  //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )
        radio_eyelashes_left.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flageyelashes +=1
                    if (flageyelashes==3){
                        card_t4.setBackgroundColor(Color.GRAY)
                    }
                    //  flag += 1
                    eyelashesLeft = radio_langange.text.toString()
                  //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )



        radio_white_right.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flageyeWhite +=1
                    if (flageyeWhite==3){
                        card_t5.setBackgroundColor(Color.GRAY)
                    }

                    //  flag += 1
                    whiteRight = radio_langange.text.toString()
                   // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )
        radio_white_left.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flageyeWhite +=1
                    if (flageyeWhite==3){
                        card_t5.setBackgroundColor(Color.GRAY)
                    }
                    //  flag += 1
                    whiteLeft = radio_langange.text.toString()
                  //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )




        radio_black_right.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flageyeBlack +=1
                    if (flageyeBlack==3){
                        card_t6.setBackgroundColor(Color.GRAY)
                    }

                    //  flag += 1
                    blackRight = radio_langange.text.toString()
                //    Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )
        radio_black_left.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flageyeBlack +=1
                    if (flageyeBlack==3){
                        card_t6.setBackgroundColor(Color.GRAY)
                    }
                    //  flag += 1
                    blackLeft = radio_langange.text.toString()
                 //   Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )





        radio_pupil_right.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flageyePupile +=1
                    if (flageyePupile==3){
                        card_t7.setBackgroundColor(Color.GRAY)
                    }

                    //  flag += 1
                    pupilRight = radio_langange.text.toString()
                 //   Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )
        radio_pupil_left.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flageyePupile +=1
                    if (flageyePupile==3){
                        card_t7.setBackgroundColor(Color.GRAY)
                    }
                    //  flag += 1
                    pupilLeft = radio_langange.text.toString()
                 //   Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )

        radio_external_right.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flageyeInjury +=1
                    if (flageyeInjury==3){
                        card_t8.setBackgroundColor(Color.GRAY)
                    }

                    //  flag += 1
                    externalRight = radio_langange.text.toString()
                   // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )
        radio_external_left.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flageyeInjury +=1
                    if (flageyeInjury==3){
                        card_t8.setBackgroundColor(Color.GRAY)
                    }
                    //  flag += 1
                    externalLeft = radio_langange.text.toString()
                  //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )



        radio_foreign_right.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flageyeFB +=1
                    if (flageyeFB==3){
                        card_t9.setBackgroundColor(Color.GRAY)
                    }

                    //  flag += 1
                    foreignRight = radio_langange.text.toString()
                  //  Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )
        radio_foreign_left.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flageyeFB +=1
                    if (flageyeFB==3){
                        card_t9.setBackgroundColor(Color.GRAY)
                    }
                    //  flag += 1
                    foreignLeft = radio_langange.text.toString()

                   // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )





        submit.setOnClickListener {
            RefferDialog()
        }


    }
    fun submitData( reffer:String){
        val examination = ExaminationData(ques1, ques2, ques3, ques4, ques5, ques6, ques6_1, ques7, reffer, ques9, ques10, ques11, ques12, patientID,
                disWithoutGL, disWithoutGR, disWithGL, disWithGR, nearVisionWithL, nearVisionWithR, nearVisionWithOutR, nearVisionWithOutL, catOfVisual,
                sphereL, sphereR, cylinderL, cylinderR,axisR, axisL,
                devLeft, devRight, sizeLeft, eyelidsRight, eyelidsLeft, eyelashesRight, eyelashesLeft, whiteRight, whiteLeft, blackLeft, blackRight, pupilRight, pupilLeft, externalLeft, externalRight, foreignRight, foreignLeft,dateOfExam)
        myDatabase!!.examDao()!!.patientExamInsertion(examination)
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        sharedPreferences.edit().clear().commit();
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

    fun getShareData() {
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
        ques12 = sharedPreferences.getString("history_12", "").toString()
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

        sphereR =sharedPreferences.getString("sphereR", "").toString()
        sphereL =sharedPreferences.getString("sphereL", "").toString()
        cylinderR = sharedPreferences.getString("cylinderR", "").toString()
        cylinderL =sharedPreferences.getString("cylinderL", "").toString()
        axisR = sharedPreferences.getString("axisR", "").toString()
        axisL =sharedPreferences.getString("axisL", "").toString()

    }

    fun RefferDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you want to Reffer ?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                   submitData("Yes")

                }
                .setNegativeButton("No") { dialog, id ->
                    submitData("NO")
                    dialog.dismiss()
                }
        val alert = builder.create()
        alert.show()
    }
}
