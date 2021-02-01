package com.example.visionguardian

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_examination.*

import kotlinx.android.synthetic.main.activity_refraction.*


import java.math.RoundingMode
import java.text.DecimalFormat

class Refraction : AppCompatActivity() {

    var sphereR = ""
    var sphereL = ""
    var cylinderR = ""
    var cylinderL = ""
    var axisR = "10"
    var axisL = "10"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refraction)
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)


        val axisRange = arrayOf("10", "20", "30", "40","50","60","70","80","90","100","110","120","130","140","150","160","170","180")
        val adapter = ArrayAdapter(
                this, // Context
                android.R.layout.simple_spinner_item, // Layout
                axisRange // Array
        )
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)

        // Finally, data bind the spinner object with dapter
        axisSpinnerL.adapter = adapter
        axisSpinnerR.adapter = adapter

        axisSpinnerL.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
                // classSpinner.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                axisL=parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }
        axisSpinnerR.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
                // classSpinner.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                axisR=parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }








        btn_right_avg.setOnClickListener {

            average_right.visibility = View.VISIBLE
            if (!sphere_right1.text.isEmpty() && !sphere_right2.text.isEmpty() && !sphere_right3.text.isEmpty()) {
                btn_right_avg.visibility = View.GONE
                average_right.visibility = View.VISIBLE
                var R1 = sphere_right1.text.toString().toDouble()
                var R2 = sphere_right2.text.toString().toDouble()
                var R3 = sphere_right3.text.toString().toDouble()
                var total = (R1 + R2 + R3) / 3
                val df = DecimalFormat("#.##")
                df.roundingMode = RoundingMode.CEILING
                if(total>0){
                    average_right.text = "Average : +" + df.format(total) + " D"
                    sphereR = "+"+df.format(total) + " D"
                }else{
                    average_right.text = "Average : " + df.format(total) + " D"
                    sphereR = df.format(total) + " D"
                }


            }

        }
        btn_left_avg.setOnClickListener {
            if (!sphere_left1.text.isEmpty() && !sphere_left2.text.isEmpty() && !sphere_left3.text.isEmpty()) {
                average_left.visibility = View.VISIBLE
                btn_left_avg.visibility = View.GONE
                var L1 = sphere_left1.text.toString().toDouble()
                var L2 = sphere_left2.text.toString().toDouble()
                var L3 = sphere_left3.text.toString().toDouble()
                var total = (L1 + L2 + L3) / 3
                val df = DecimalFormat("#.##")
                df.roundingMode = RoundingMode.CEILING
                if (total>0){
                    average_left.text = "Average : +" + df.format(total) + " D"
                    sphereL ="+"+df.format(total) + " D"
                }else{
                    average_left.text = "Average : " + df.format(total) + " D"
                    sphereL = df.format(total) + " D"
                }

            }


            radio_group_rf1.setOnCheckedChangeListener(
                    RadioGroup.OnCheckedChangeListener { group, checkedId ->
                        val radio_langange: RadioButton = findViewById(checkedId)
                        card_rf1.setBackgroundColor(Color.GRAY)

                        cylinderR = radio_langange.text.toString()
                        // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                    }
            )

            radio_group_rf2.setOnCheckedChangeListener(
                    RadioGroup.OnCheckedChangeListener { group, checkedId ->
                        val radio_langange: RadioButton = findViewById(checkedId)
                        card_rf2.setBackgroundColor(Color.GRAY)

                        cylinderL = radio_langange.text.toString()
                        // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
                    }
            )















        }
        submit_next_t.setOnClickListener {
            Log.e("cccccccccccccccccc","clicked")
            //axisR = axisSpinnerR.text.toString()
          //  axisL = edit_axis_L.text.toString()

            val myEdit = sharedPreferences.edit()
            myEdit.putString("sphereR", sphereR)
            myEdit.putString("sphereL", sphereL)
            myEdit.putString("cylinderR", cylinderR)
            myEdit.putString("cylinderL", cylinderL)
            myEdit.putString("axisR", axisR)
            myEdit.putString("axisL", axisL)
            myEdit.commit()
            myEdit.apply()
            val intent = Intent(this, TourchLightExamination::class.java)
            startActivity(intent)
        }


        back_arrow_t.setOnClickListener{finish()}
    }
}