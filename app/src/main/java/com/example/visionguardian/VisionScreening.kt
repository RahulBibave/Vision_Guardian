package com.example.visionguardian

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_vision_screening.*


class VisionScreening : AppCompatActivity() {

    var disWithoutGL=""
    var disWithoutGR=""
    var disWithGR=  ""
    var disWithGL=""
    var nearVisionWithR=""
    var nearVisionWithL=""
    var nearVisionWithOutL=""
    var nearVisionWithOutR=""
    var catOfVisual=""

    var flagvisionwithout=1
    var flagvisionwith=1
    var flagnearwith=1
    var flagnearwithout=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vision_screening)

        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
       // val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)

       var ques9 = sharedPreferences.getString("history_9", "").toString()
        if (ques9.equals("Not wearing glasses")){
            card_2.visibility=View.GONE
            card_4.visibility=View.GONE
        }else if (ques9.equals("Yes, No difficulty with glasses")){
            card_1.visibility=View.GONE
            card_3.visibility=View.GONE
        }

        redio_gp1_1.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flagvisionwithout += 1
                    if (flagvisionwithout==3){
                        card_1.setBackgroundColor(Color.GRAY)
                    }

                    disWithoutGR=radio_langange.text.toString()
                    //Toast.makeText(applicationContext, " On Checked change :${}", Toast.LENGTH_SHORT).show()
                }
        )

        redio_gp1_2.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flagvisionwithout+= 1
                    if (flagvisionwithout==3){
                        card_1.setBackgroundColor(Color.GRAY)
                    }
                    disWithoutGL=radio_langange.text.toString()
                   // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )

        redio_gp2_1.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flagvisionwith +=1
                    if (flagvisionwith==3){
                        card_2.setBackgroundColor(Color.GRAY)
                    }

                    disWithGR=radio_langange.text.toString()
                   // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )

        redio_gp2_2.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flagvisionwith +=1
                    if (flagvisionwith==3){
                        card_2.setBackgroundColor(Color.GRAY)
                    }
                    disWithGL=radio_langange.text.toString()
                   // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )

        redio_gp3_1.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flagnearwith +=1
                    if (flagnearwith==3){
                        card_3.setBackgroundColor(Color.GRAY)
                    }
                    nearVisionWithOutR=radio_langange.text.toString()
                   // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )
        redio_gp3_2.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flagnearwith +=1
                    if (flagnearwith==3){
                        card_3.setBackgroundColor(Color.GRAY)
                    }
                    nearVisionWithOutL=radio_langange.text.toString()
                   // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )


        redio_gp4_1.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flagnearwithout +=1
                    if (flagnearwithout==3){
                        card_4.setBackgroundColor(Color.GRAY)
                    }

                    nearVisionWithR=radio_langange.text.toString()
                   // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )

        redio_gp4_2.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    flagnearwithout +=1
                    if (flagnearwithout==3){
                        card_4.setBackgroundColor(Color.GRAY)
                    }
                    nearVisionWithL=radio_langange.text.toString()
                   // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )


        redio_gp5.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    card_5.setBackgroundColor(Color.GRAY)
                    catOfVisual=radio_langange.text.toString()
                   // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                }
        )

        submit_next.setOnClickListener {

            val myEdit = sharedPreferences.edit()
            myEdit.putString("DVWithoutR", disWithoutGR)
            myEdit.putString("DVWithoutL", disWithoutGL)
            myEdit.putString("DVWithL", disWithGL)
            myEdit.putString("DVWithR", disWithGR)
            myEdit.putString("NVWithoutR", nearVisionWithOutR)
            myEdit.putString("NVWithoutL", nearVisionWithOutL)
            myEdit.putString("NVWithL", nearVisionWithL)
            myEdit.putString("NVWithR", nearVisionWithR)
            myEdit.putString("catOfVI", catOfVisual)

            myEdit.commit()
            myEdit.apply()
            val intent =Intent(this, Refraction::class.java)
            startActivity(intent)
        }

        back_arrow.setOnClickListener { finish() }
    }
}