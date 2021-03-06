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
import kotlinx.android.synthetic.main.activity_refraction.*
import java.math.RoundingMode
import java.text.DecimalFormat


class Refraction : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var sphereR = ""
    var sphereL = ""
    var cylinderR = ""
    var cylinderL = ""
    var axisR = "10"
    var axisL = "10"

    var sphereR_auto = ""
    var sphereL_auto = ""
    var cylinderR_auto = ""
    var cylinderL_auto = ""
    var axisR_auto = ""
    var axisL_auto = ""

    var su_visionR = ""
    var su_visionL = ""
    var su_nvR = ""
    var su_nvL = ""

    var angles = arrayOf(
        "Nill",
        "15",
        "30",
        "45",
        "60",
        "75",
        "90",
        "105",
        "120",
        "135",
        "150",
        "165",
        "180"
    )
    var distArray = arrayOf(
        "Vision",
        "6/6",
        "6/9",
        "6/12",
        "6/18",
        "6/24",
        "6/36",
        "6/60",
        "5/60",
        "4/60",
        "3/60",
        "2/60",
        "1/60",
        "CF 50cm",
        "CFCF",
        "HM",
        "PL+ accurate",
        "PL+ inaccurate",
        "PL-"
    )
    var nearArray = arrayOf("NV", "N6", "N8", "N10", "N12", "N18", "N24", "N36", "Less than N36")

    var images = intArrayOf(
        R.drawable.ic_white_arrow_drop_down,
        R.drawable.ic_moon_white,
        R.drawable.ic_star_white,
        R.drawable.ic_elct_white,
        R.drawable.ic_sun_white,
        R.drawable.ic_cloud_white,
        R.drawable.ic_drop_white,
        R.drawable.ic_moon_black,
        R.drawable.ic_star_black,
        R.drawable.ic_elct_black,
        R.drawable.ic_sun_black,
        R.drawable.ic_cloud_black,
        R.drawable.ic_drop_black
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refraction)
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)


        togglebutton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                txt_ccr.text = getString(R.string.title4_1)
                card_c1.visibility = View.VISIBLE
                img_info.visibility = View.GONE
                card_ccr1.visibility = View.GONE
                card_ccr3.visibility = View.GONE
                card_ccr2.visibility = View.GONE
            } else {
                txt_ccr.text = getString(R.string.title4)
                card_c1.visibility = View.GONE
                card_ccr1.visibility = View.VISIBLE
                card_ccr3.visibility = View.VISIBLE
                card_ccr2.visibility = View.VISIBLE
                img_info.visibility = View.VISIBLE
            }
        }


        val adapter = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            distArray // Array
        )
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)

        // Finally, data bind the spinner object with dapter
        sp_visionR.adapter = adapter
        sp_visionL.adapter = adapter

        sp_visionR.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

                su_visionR = parent.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
        sp_visionL.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

                su_visionL = parent.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }


        val adapterNear = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            nearArray // Array
        )
        adapterNear.setDropDownViewResource(android.R.layout.simple_list_item_1)

        // Finally, data bind the spinner object with dapter
        sp_NVR.adapter = adapterNear
        sp_NVL.adapter = adapterNear
        sp_NVR.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

                su_nvR = parent.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
        sp_NVL.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

                su_nvL = parent.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }


        /*val adapter = ArrayAdapter(
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
        }*/

        // val spinR = findViewById<View>(R.id.axisSpinnerR) as Spinner

        axisSpinnerL.onItemSelectedListener = this
        axisSpinnerR.onItemSelectedListener = this
        axisSpinnerL.onItemClickListener {
            AdapterView.OnItemClickListener { parent, view, position, id ->
                axisL = angles[position]
                Log.e("zzzzzzzzzzzzz", "" + position)
            }
        }


        val customAdapter = CustomAdapter(applicationContext, images, angles)
        axisSpinnerR.adapter = customAdapter
        axisSpinnerL.adapter = customAdapter










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
                if (total > 0) {
                    average_right.text = "Average : +" + df.format(total) + " D"
                    sphereR = "+" + df.format(total) + " D"
                } else {
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
                if (total > 0) {
                    average_left.text = "Average : +" + df.format(total) + " D"
                    sphereL = "+" + df.format(total) + " D"
                } else {
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


            // Log.e("cccccccccccccccccc",axisR)
            // Log.e("cccccccccccccccccc",axisL)
            //  axisL = edit_axis_L.text.toString()

            val myEdit = sharedPreferences.edit()
            myEdit.putString("sphereR", sphereR)
            myEdit.putString("sphereL", sphereL)
            myEdit.putString("cylinderR", cylinderR)
            myEdit.putString("cylinderL", cylinderL)
            myEdit.putString("axisR", axisR)
            myEdit.putString("axisL", axisL)

            myEdit.putString("sphereRAuto", edt_sphR.text.toString())
            myEdit.putString("sphereLAuto", edt_sphL.text.toString())
            myEdit.putString("cylinderRAuto", edt_cylR.text.toString())
            myEdit.putString("cylinderLAuto", edt_cylL.text.toString())
            myEdit.putString("axisRAuto", edt_axisR.text.toString())
            myEdit.putString("axisLAuto", edt_axisL.text.toString())

            myEdit.putString("sub_visionR", su_visionR)
            myEdit.putString("sub_visionL", su_visionL)
            myEdit.putString("sub_AddR", edt_addR.text.toString())
            myEdit.putString("sub_AddL", edt_addL.text.toString())
            myEdit.putString("sub_NVR", su_nvR)
            myEdit.putString("sub_NVL", su_nvL)


            myEdit.putString("note", edt_note.text.toString())
            myEdit.commit()
            myEdit.apply()
            val intent = Intent(this, TourchLightExamination::class.java)
            startActivity(intent)
        }


        back_arrow_t.setOnClickListener { finish() }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        when (parent!!.id) {
            R.id.axisSpinnerL -> {
                axisL = angles[position]
            }
            R.id.axisSpinnerR -> {
                axisR = angles[position]
            }
            else -> {
            }
        }

    }

    /* override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        // Log.e("zzzzzzzzzzzzzzzz",""+parent.id)
         when (parent.id) {
             R.id.axisSpinnerL -> {
                 Toast.makeText(this, "LeftClicked", Toast.LENGTH_SHORT).show()
             }
             R.id.axisSpinnerR -> {

                 Toast.makeText(this, "RightClicked", Toast.LENGTH_SHORT).show()
             }
             else -> {
             }
         }
     }*/

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // TODO("Not yet implemented")
    }

    fun onClickInfo(view: View) {
        val intent = Intent(this, Manual::class.java)
        startActivity(intent)
    }

}

private operator fun AdapterView.OnItemClickListener?.invoke(function: () -> AdapterView.OnItemClickListener) {

}
