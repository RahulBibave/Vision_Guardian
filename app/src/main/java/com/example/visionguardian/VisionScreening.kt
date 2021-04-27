package com.example.visionguardian

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_refferal_details.*
import kotlinx.android.synthetic.main.activity_vision_screening.*
import kotlinx.android.synthetic.main.activity_vision_screening.back_arrow
import java.util.*


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
        loadLocate()
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

                    checkDiss(disWithoutGR,disWithoutGL)




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
                    checkDiss(disWithoutGR,disWithoutGL)

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
                    checkDiss(disWithGR,disWithGL)
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
                    checkDiss(disWithGR,disWithGL)
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

        val intent=Intent(this,VisionImage::class.java)
        without_R.setOnClickListener {
            intent.putExtra("Vision","IN")
            startActivityForResult(intent,1) }


        without_L.setOnClickListener {
            intent.putExtra("Vision","IN")
            startActivityForResult(intent,2)
        }


        with_R.setOnClickListener {
            intent.putExtra("Vision","IN")
            startActivityForResult(intent,3)
        }


        with_L.setOnClickListener {
            intent.putExtra("Vision","IN")
            startActivityForResult(intent,4)
        }
        vision_without_R.setOnClickListener {
            intent.putExtra("Vision","Visible")
            startActivityForResult(intent,5)
        }
        vision_without_L.setOnClickListener {
            intent.putExtra("Vision","Visible")
            startActivityForResult(intent,6)
        }
        vision_with_R.setOnClickListener {
            intent.putExtra("Vision","Visible")
            startActivityForResult(intent,7)
        }
        vision_with_L.setOnClickListener {
            intent.putExtra("Vision","Visible")
            startActivityForResult(intent,8)
        }

        submit_next.setOnClickListener {
            catOfVisual=vs_result.text.toString()
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


    fun checkDiss(right:String,left:String){
        if(right.equals("Less than 3/60") && left.equals("Less than 3/60")){
            vs_result.text="Binocular blindness"
        }
        else if (right.equals("Less than 3/60") && left.equals("Less than 6/60, more than 3/60")){
            vs_result.text="Monocular blindness"
        }
        else if (right.equals("Less than 3/60") && left.equals("More than 6/60, less than 6/18")){
            vs_result.text="Monocular blindness"
        }
        else if (right.equals("Less than 3/60") && left.equals("More than 6/18")){
            vs_result.text="Monocular blindness"
        }
        else if (right.equals("Less than 3/60") && left.equals("≥ 6/12")){
            vs_result.text="Monocular blindness"
        }



        else if (right.equals("Less than 6/60, more than 3/60") && left.equals("Less than 6/60, more than 3/60")){
            vs_result.text="Binocular severe visual impairment "
        }
        else if (right.equals("Less than 6/60, more than 3/60") && left.equals("More than 6/60, less than 6/18")){
            vs_result.text="Monocular visual impairment"
        }
        else if (right.equals("Less than 6/60, more than 3/60") && left.equals("More than 6/18")){
            vs_result.text="Monocular visual impairment"
        }
        else if (right.equals("Less than 6/60, more than 3/60") && left.equals("≥ 6/12")){
            vs_result.text="Monocular visual impairment"
        }
        else if (right.equals("Less than 6/60, more than 3/60") && left.equals("Less than 3/60")){
            vs_result.text="Monocular blindness"
        }



        else if (right.equals("More than 6/60, less than 6/18") && left.equals("More than 6/60, less than 6/18")){
            vs_result.text="Binocular moderate visual impairment"
        }
        else if (right.equals("More than 6/60, less than 6/18") && left.equals("More than 6/18")){
            vs_result.text="Monocular moderate visual impairment"
        }
        else if (right.equals("More than 6/60, less than 6/18") && left.equals("≥ 6/12")){
            vs_result.text="Monocular moderate visual impairment"
        }
        else if (right.equals("More than 6/60, less than 6/18") && left.equals("Less than 3/60")){
            vs_result.text="Monocular blindness"
        }
        else if (right.equals("More than 6/60, less than 6/18") && left.equals("Less than 6/60, more than 3/60")){
            vs_result.text="Monocular visual impairment"
        }



        else if (right.equals("More than 6/18") && left.equals("More than 6/18")){
            vs_result.text="Binocular mild visual impairment"
        }
        else if (right.equals("More than 6/18") && left.equals("≥ 6/12")){
            vs_result.text="Monocular mild visual impairment"
        }
        else if (right.equals("More than 6/18") && left.equals("Less than 3/60")){
            vs_result.text="Monocular blindness"
        }
        else if (right.equals("More than 6/18") && left.equals("Less than 6/60, more than 3/60")){
            vs_result.text="Monocular visual impairment"
        }
        else if (right.equals("More than 6/18") && left.equals("More than 6/60, less than 6/18")){
            vs_result.text="Monocular moderate visual impairment"
        }


        else if (right.equals("≥ 6/12") && left.equals("≥ 6/12")){
            vs_result.text="No visual impairement "
        }
        else if (right.equals("≥ 6/12)") && left.equals("Less than 3/60")){
            vs_result.text="Monocular blindness "
        }
        else if (right.equals("≥ 6/12") && left.equals("Less than 6/60, more than 3/60")){
            vs_result.text="Monocular visual impairment "
        }
        else if (right.equals("≥ 6/12") && left.equals("More than 6/60, less than 6/18")){
            vs_result.text="Monocular moderate visual impairment "
        }
        else if (right.equals("≥ 6/12") && left.equals("More than 6/18")){
            vs_result.text="Monocular mild visual impairment  "
        }
    }

    fun openDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Yes") { dialog, which ->
            Toast.makeText(applicationContext, "Yes button Clicked", Toast.LENGTH_LONG).show()

            dialog.dismiss()
        }

        val inflater = layoutInflater
        val dialoglayout = inflater.inflate(R.layout.custom_dialog_image, null)

        builder.setView(dialoglayout)
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var Rsultdata=data!!.getIntExtra("ResultData",0)
        if (requestCode == 1) {

            if (Rsultdata==1){
                without_R_brown.isChecked=true
            }
            else if (Rsultdata==2){
                without_R_yellow.isChecked=true
            }
            else if (Rsultdata==3){
                without_R_green.isChecked=true
            }

        }
        else if(requestCode == 2){
            if (Rsultdata==1){
                without_L_brown.isChecked=true
            }
            else if (Rsultdata==2){
                without_L_yellow.isChecked=true
            }
            else if (Rsultdata==3){
                without_L_green.isChecked=true
            }
        }
        else if(requestCode == 3){
            if (Rsultdata==1){
                with_R_brown.isChecked=true
            }
            else if (Rsultdata==2){
                with_R_yellow.isChecked=true
            }
            else if (Rsultdata==3){
                with_R_green.isChecked=true
            }
        }
        else if(requestCode == 4){
            if (Rsultdata==1){
                with_L_brown.isChecked=true
            }
            else if (Rsultdata==2){
                with_L_yellow.isChecked=true
            }
            else if (Rsultdata==3){
                with_L_green.isChecked=true
            }
        }
        else if(requestCode == 5){
            if (Rsultdata==6){
                vs_R_without1.isChecked=true
            }else if(Rsultdata==7){
                vs_R_without2.isChecked=true
            }
        }
        else if(requestCode == 6){
            if (Rsultdata==6){
                vs_L_without1.isChecked=true
            }else if(Rsultdata==7){
                vs_L_without2.isChecked=true
            }
        }
        else if(requestCode == 7){
            if (Rsultdata==6){
               vs_R_with1.isChecked=true
            }else if(Rsultdata==7){
                vs_R_with2.isChecked=true
            }
        }
        else if(requestCode == 8){
            if (Rsultdata==6){
                vs_L_with1.isChecked=true
            }else if(Rsultdata==7){
                vs_L_with2.isChecked=true
            }
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