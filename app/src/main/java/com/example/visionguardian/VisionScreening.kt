package com.example.visionguardian

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_vision_screening.*
import java.util.*


class VisionScreening : AppCompatActivity() {

    var disWithoutGL = ""
    var disWithoutGL_ = ""
    var disWithoutGR = ""
    var disWithoutGR_ = ""
    var disWithGR = ""
    var disWithGL = ""
    var nearVisionWithR = ""
    var nearVisionWithL = ""
    var nearVisionWithOutL = ""
    var nearVisionWithOutR = ""
    var catOfVisual = ""

    var mDistWithoutR = ""
    var mDistWithoutL = ""
    var mDistWithR = ""
    var mDistWithL = ""
    var mNearWithoutR = ""
    var mNearWithoutL = ""
    var mNearWithR = ""
    var mNearWithL = ""


    var flagvisionwithout = 1
    var flagvisionwith = 1
    var flagnearwith = 1
    var flagnearwithout = 1
    var distArray = arrayOf(
        "Select",
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
    var nearArray =
        arrayOf("Select", "N6", "N8", "N10", "N12", "N18", "N24", "N36", "Less than N36")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vision_screening)
        loadLocate()
        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        // val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)

        togglebuttonVS.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                scroll_1.visibility = View.GONE
                new_scroll.visibility = View.VISIBLE
            } else {
                scroll_1.visibility = View.VISIBLE
                new_scroll.visibility = View.GONE
            }
        }

        val adapter = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            distArray // Array
        )
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)

        // Finally, data bind the spinner object with dapter
        spinnerWithoutR.adapter = adapter
        spinnerWithoutL.adapter = adapter
        spinnerWithR.adapter = adapter
        spinnerWithL.adapter = adapter



        spinnerWithoutR.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Display the selected item text on text view
                // classSpinner.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                mDistWithoutR = parent.getItemAtPosition(position).toString()
                // Log.e("ssssssssssssssssssss",""+parent.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        spinnerWithoutL.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Display the selected item text on text view
                // classSpinner.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                mDistWithoutL = parent.getItemAtPosition(position).toString()
                // Log.e("ssssssssssssssssssss",""+parent.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
        spinnerWithL.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Display the selected item text on text view
                // classSpinner.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                mDistWithL = parent.getItemAtPosition(position).toString()
                // Log.e("ssssssssssssssssssss",""+parent.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
        spinnerWithR.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Display the selected item text on text view
                // classSpinner.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                mDistWithR = parent.getItemAtPosition(position).toString()
                // Log.e("ssssssssssssssssssss",""+parent.getItemAtPosition(position).toString())
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
        spinnerNearWithoutL.adapter = adapterNear
        spinnerNearWithoutR.adapter = adapterNear
        spinnerNearWithR.adapter = adapterNear
        spinnerNearWithL.adapter = adapterNear
        spinnerNearWithoutR.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Display the selected item text on text view
                // classSpinner.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                mNearWithoutR = parent.getItemAtPosition(position).toString()
                //Log.e("ssssssssssssssssssss",""+parent.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
        spinnerNearWithoutL.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Display the selected item text on text view
                // classSpinner.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                mNearWithoutL = parent.getItemAtPosition(position).toString()
                // Log.e("ssssssssssssssssssss",""+parent.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        spinnerNearWithR.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Display the selected item text on text view
                // classSpinner.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                mNearWithR = parent.getItemAtPosition(position).toString()
                //Log.e("ssssssssssssssssssss",""+parent.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        spinnerNearWithL.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Display the selected item text on text view
                // classSpinner.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                mNearWithL = parent.getItemAtPosition(position).toString()
                // Log.e("ssssssssssssssssssss",""+parent.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }


        var ques9 = sharedPreferences.getString("history_9", "").toString()
        if (ques9 == "Not wearing glasses") {
            card_2.visibility = View.GONE
            card_4.visibility = View.GONE
        } else if (ques9 == "Yes, No difficulty with glasses") {
            card_1.visibility = View.GONE
            card_3.visibility = View.GONE
        }

        redio_gp1_1.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flagvisionwithout += 1
                if (flagvisionwithout == 3) {
                    card_1.setBackgroundColor(Color.GRAY)
                }

                when {
                    radio_langange.text.toString() == getString(R.string.distance_vision1) -> {
                        disWithoutGR = "Less than 3/60"
                    }
                    radio_langange.text.toString() == getString(R.string.distance_vision2) -> {
                        disWithoutGR = "Less than 6/60, more than 3/60"
                    }
                    radio_langange.text.toString() == getString(R.string.distance_vision3) -> {
                        disWithoutGR = "More than 6/60, less than 6/18"
                    }
                    radio_langange.text.toString() == getString(R.string.distance_vision4) -> {
                        disWithoutGR = "More than 6/18"
                    }
                    radio_langange.text.toString() == getString(R.string.distance_vision5) -> {
                        disWithoutGR = "≥ 6/12"
                    }
                }
                disWithoutGR_ = radio_langange.text.toString()
                checkDiss(disWithoutGR_, disWithoutGL_)


            }
        )

        redio_gp1_2.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flagvisionwithout += 1
                if (flagvisionwithout == 3) {
                    card_1.setBackgroundColor(Color.GRAY)
                }
                when {
                    radio_langange.text.toString() == getString(R.string.distance_vision1) -> {
                        disWithoutGL = "Less than 3/60"
                    }
                    radio_langange.text.toString() == getString(R.string.distance_vision2) -> {
                        disWithoutGL = "Less than 6/60, more than 3/60"
                    }
                    radio_langange.text.toString() == getString(R.string.distance_vision3) -> {
                        disWithoutGL = "More than 6/60, less than 6/18"
                    }
                    radio_langange.text.toString() == getString(R.string.distance_vision4) -> {
                        disWithoutGL = "More than 6/18"
                    }
                    radio_langange.text.toString() == getString(R.string.distance_vision5) -> {
                        disWithoutGL = "≥ 6/12"
                    }
                }

                disWithoutGL_ = radio_langange.text.toString()
                checkDiss(disWithoutGR_, disWithoutGL_)

            }
        )

        redio_gp2_1.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flagvisionwith += 1
                if (flagvisionwith == 3) {
                    card_2.setBackgroundColor(Color.GRAY)
                }
                when {
                    radio_langange.text.toString() == getString(R.string.distance_vision1) -> disWithGR =
                        "Less than 3/60"
                    radio_langange.text.toString() == getString(R.string.distance_vision2) -> disWithGR =
                        "Less than 6/60, more than 3/60"
                    radio_langange.text.toString() == getString(R.string.distance_vision3) -> disWithGR =
                        "More than 6/60, less than 6/18"
                    radio_langange.text.toString() == getString(R.string.distance_vision4) -> disWithGR =
                        "More than 6/18"
                    radio_langange.text.toString() == getString(R.string.distance_vision5) -> disWithGR =
                        "≥ 6/12"
                }

                //  disWithGR = radio_langange.text.toString()
                checkDiss(disWithGR, disWithGL)
            }
        )

        redio_gp2_2.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flagvisionwith += 1
                if (flagvisionwith == 3) {
                    card_2.setBackgroundColor(Color.GRAY)
                }
                when {
                    radio_langange.text.toString() == getString(R.string.distance_vision1) -> disWithGL =
                        "Less than 3/60"
                    radio_langange.text.toString() == getString(R.string.distance_vision2) -> disWithGL =
                        "Less than 6/60, more than 3/60"
                    radio_langange.text.toString() == getString(R.string.distance_vision3) -> disWithGL =
                        "More than 6/60, less than 6/18"
                    radio_langange.text.toString() == getString(R.string.distance_vision4) -> disWithGL =
                        "More than 6/18"
                    radio_langange.text.toString() == getString(R.string.distance_vision5) -> disWithGL =
                        "≥ 6/12"
                }
                // disWithGL = radio_langange.text.toString()
                checkDiss(disWithGR, disWithGL)
            }
        )

        redio_gp3_1.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flagnearwith += 1
                if (flagnearwith == 3) {
                    card_3.setBackgroundColor(Color.GRAY)
                }
                if (radio_langange.text.toString() == getString(R.string.near_vision1)) nearVisionWithOutR =
                    "Can not see N6"
                else if (radio_langange.text.toString() == getString(R.string.near_vision2)) nearVisionWithOutR =
                    "Can see N6"
                // nearVisionWithOutR = radio_langange.text.toString()
                // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )
        redio_gp3_2.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flagnearwith += 1
                if (flagnearwith == 3) {
                    card_3.setBackgroundColor(Color.GRAY)
                }
                if (radio_langange.text.toString() == getString(R.string.near_vision1)) nearVisionWithOutL =
                    "Can not see N6"
                else if (radio_langange.text.toString() == getString(R.string.near_vision2)) nearVisionWithOutL =
                    "Can see N6"
                //nearVisionWithOutL = radio_langange.text.toString()
                // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )


        redio_gp4_1.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flagnearwithout += 1
                if (flagnearwithout == 3) {
                    card_4.setBackgroundColor(Color.GRAY)
                }
                if (radio_langange.text.toString() == getString(R.string.near_vision1)) nearVisionWithR =
                    "Can not see N6"
                else if (radio_langange.text.toString() == getString(R.string.near_vision2)) nearVisionWithR =
                    "Can see N6"
                //  nearVisionWithR = radio_langange.text.toString()
                // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )

        redio_gp4_2.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio_langange: RadioButton = findViewById(checkedId)
                flagnearwithout += 1
                if (flagnearwithout == 3) {
                    card_4.setBackgroundColor(Color.GRAY)
                }
                if (radio_langange.text.toString() == getString(R.string.near_vision1)) nearVisionWithL =
                    "Can not see N6"
                else if (radio_langange.text.toString() == getString(R.string.near_vision2)) nearVisionWithL =
                    "Can see N6"
                // nearVisionWithL = radio_langange.text.toString()
                // Toast.makeText(applicationContext, " On Checked change :${radio_langange.text}", Toast.LENGTH_SHORT).show()
            }
        )

        val intent = Intent(this, VisionImage::class.java)
        without_R.setOnClickListener {
            intent.putExtra("Vision", "IN")
            startActivityForResult(intent, 1)
        }


        without_L.setOnClickListener {
            intent.putExtra("Vision", "IN")
            startActivityForResult(intent, 2)
        }


        with_R.setOnClickListener {
            intent.putExtra("Vision", "IN")
            startActivityForResult(intent, 3)
        }


        with_L.setOnClickListener {
            intent.putExtra("Vision", "IN")
            startActivityForResult(intent, 4)
        }
        vision_without_R.setOnClickListener {
            intent.putExtra("Vision", "Visible")
            startActivityForResult(intent, 5)
        }
        vision_without_L.setOnClickListener {
            intent.putExtra("Vision", "Visible")
            startActivityForResult(intent, 6)
        }
        vision_with_R.setOnClickListener {
            intent.putExtra("Vision", "Visible")
            startActivityForResult(intent, 7)
        }
        vision_with_L.setOnClickListener {
            intent.putExtra("Vision", "Visible")
            startActivityForResult(intent, 8)
        }

        submit_next.setOnClickListener {
            when {
                vs_result.text.toString() == getString(R.string.binocular_blindness) -> catOfVisual =
                    "Binocular blindness"
                vs_result.text.toString() == getString(R.string.monocular_blindness) -> catOfVisual =
                    "Monocular Blindness"
                vs_result.text.toString() == getString(R.string.binocular_severe_visual_impairment) -> catOfVisual =
                    "Binocular severe visual impairment"
                vs_result.text.toString() == getString(R.string.monocular_visual_impairment) -> catOfVisual =
                    "Monocular visual impairment"
                vs_result.text.toString() == getString(R.string.binocular_moderate_visual_impairment) -> catOfVisual =
                    "Binocular moderate visual impairment"
                vs_result.text.toString() == getString(R.string.monocular_moderate_visual_impairment) -> catOfVisual =
                    "Monocular moderate visual impairment"
                vs_result.text.toString() == getString(R.string.binocular_mild_visual_impairment) -> catOfVisual =
                    "Binocular mild visual impairment"
                vs_result.text.toString() == getString(R.string.no_visual_impairement) -> catOfVisual =
                    "No visual impairement"
            }
            //catOfVisual = vs_result.text.toString()
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
            val intent = Intent(this, Refraction::class.java)
            startActivity(intent)
        }



        submit_next_new.setOnClickListener {

            val myEdit = sharedPreferences.edit()
            myEdit.putString("DVWithoutR", mDistWithoutR)
            myEdit.putString("DVWithoutL", mDistWithoutL)
            myEdit.putString("DVWithL", mDistWithL)
            myEdit.putString("DVWithR", mDistWithR)
            myEdit.putString("NVWithoutR", mNearWithoutR)
            myEdit.putString("NVWithoutL", mNearWithoutL)
            myEdit.putString("NVWithL", mNearWithL)
            myEdit.putString("NVWithR", mNearWithR)
            myEdit.putString("catOfVI", "")

            myEdit.commit()
            myEdit.apply()
            val intent = Intent(this, Refraction::class.java)
            startActivity(intent)
        }















        back_arrow.setOnClickListener { finish() }
    }


    private fun checkDiss(right: String, left: String) {

        if ((right == getString(R.string.distance_vision1)) && (left == getString(R.string.distance_vision1))) {
            vs_result.text = getString(R.string.binocular_blindness)
        } else if (right.equals(getString(R.string.distance_vision1)) && left.equals(getString(R.string.distance_vision2))) {
            vs_result.text = getString(R.string.monocular_blindness)
        } else if (right.equals(getString(R.string.distance_vision1)) && left.equals(getString(R.string.distance_vision3))) {
            vs_result.text = getString(R.string.monocular_blindness)
        } else if (right.equals(getString(R.string.distance_vision1)) && left.equals(getString(R.string.distance_vision4))) {
            vs_result.text = getString(R.string.monocular_blindness)
        } else if (right.equals(getString(R.string.distance_vision1)) && left.equals(getString(R.string.distance_vision5))) {
            vs_result.text = getString(R.string.monocular_blindness)
        } else if (right.equals(getString(R.string.distance_vision2)) && left.equals(getString(R.string.distance_vision2))) {
            vs_result.text = getString(R.string.binocular_severe_visual_impairment)
        } else if (right.equals(getString(R.string.distance_vision2)) && left.equals(getString(R.string.distance_vision3))) {
            vs_result.text = getString(R.string.monocular_visual_impairment)
        } else if (right.equals(getString(R.string.distance_vision2)) && left.equals(getString(R.string.distance_vision4))) {
            vs_result.text = getString(R.string.monocular_visual_impairment)
        } else if (right.equals(getString(R.string.distance_vision2)) && left.equals(getString(R.string.distance_vision5))) {
            vs_result.text = getString(R.string.monocular_visual_impairment)
        } else if (right.equals(getString(R.string.distance_vision2)) && left.equals(getString(R.string.distance_vision1))) {
            vs_result.text = getString(R.string.monocular_blindness)
        } else if (right.equals(getString(R.string.distance_vision3)) && left.equals(getString(R.string.distance_vision3))) {
            vs_result.text = getString(R.string.binocular_moderate_visual_impairment)
        } else if (right.equals(getString(R.string.distance_vision3)) && left.equals(getString(R.string.distance_vision4))) {
            vs_result.text = getString(R.string.monocular_moderate_visual_impairment)
        } else if (right.equals(getString(R.string.distance_vision3)) && left.equals(getString(R.string.distance_vision5))) {
            vs_result.text = getString(R.string.monocular_moderate_visual_impairment)
        } else if (right.equals(getString(R.string.distance_vision3)) && left.equals(getString(R.string.distance_vision1))) {
            vs_result.text = getString(R.string.monocular_blindness)
        } else if (right.equals(getString(R.string.distance_vision3)) && left.equals(getString(R.string.distance_vision2))) {
            vs_result.text = getString(R.string.monocular_visual_impairment)
        } else if (right.equals(getString(R.string.distance_vision4)) && left.equals(getString(R.string.distance_vision4))) {
            vs_result.text = getString(R.string.binocular_mild_visual_impairment)
        } else if (right.equals(getString(R.string.distance_vision4)) && left.equals(getString(R.string.distance_vision5))) {
            vs_result.text = getString(R.string.monocular_mild_visual_impairment)
        } else if (right.equals(getString(R.string.distance_vision4)) && left.equals(getString(R.string.distance_vision1))) {
            vs_result.text = getString(R.string.monocular_blindness)
        } else if (right.equals(getString(R.string.distance_vision4)) && left.equals(getString(R.string.distance_vision2))) {
            vs_result.text = getString(R.string.monocular_visual_impairment)
        } else if (right.equals(getString(R.string.distance_vision4)) && left.equals(getString(R.string.distance_vision3))) {
            vs_result.text = getString(R.string.binocular_moderate_visual_impairment)
        } else if (right.equals(getString(R.string.distance_vision5)) && left.equals(getString(R.string.distance_vision5))) {
            vs_result.text = getString(R.string.no_visual_impairement)
        } else if (right.equals(getString(R.string.distance_vision5)) && left.equals(getString(R.string.distance_vision1))) {
            vs_result.text = getString(R.string.monocular_blindness)
        } else if (right.equals(getString(R.string.distance_vision5)) && left.equals(getString(R.string.distance_vision2))) {
            vs_result.text = getString(R.string.monocular_visual_impairment)
        } else if (right.equals(getString(R.string.distance_vision5)) && left.equals(getString(R.string.distance_vision3))) {
            vs_result.text = getString(R.string.monocular_moderate_visual_impairment)
        } else if (right.equals(getString(R.string.distance_vision5)) && left.equals(getString(R.string.distance_vision4))) {
            vs_result.text = getString(R.string.monocular_mild_visual_impairment)
        }
    }

    fun openDialog() {
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
        var Rsultdata = data!!.getIntExtra("ResultData", 0)
        if (requestCode == 1) {

            when (Rsultdata) {
                1 -> {
                    without_R_brown.isChecked = true
                }
                2 -> {
                    without_R_yellow.isChecked = true
                }
                3 -> {
                    without_R_green.isChecked = true
                }
            }

        } else if (requestCode == 2) {
            when (Rsultdata) {
                1 -> {
                    without_L_brown.isChecked = true
                }
                2 -> {
                    without_L_yellow.isChecked = true
                }
                3 -> {
                    without_L_green.isChecked = true
                }
            }
        } else if (requestCode == 3) {
            when (Rsultdata) {
                1 -> {
                    with_R_brown.isChecked = true
                }
                2 -> {
                    with_R_yellow.isChecked = true
                }
                3 -> {
                    with_R_green.isChecked = true
                }
            }
        } else if (requestCode == 4) {
            when (Rsultdata) {
                1 -> {
                    with_L_brown.isChecked = true
                }
                2 -> {
                    with_L_yellow.isChecked = true
                }
                3 -> {
                    with_L_green.isChecked = true
                }
            }
        } else if (requestCode == 5) {
            if (Rsultdata == 6) {
                vs_R_without1.isChecked = true
            } else if (Rsultdata == 7) {
                vs_R_without2.isChecked = true
            }
        } else if (requestCode == 6) {
            if (Rsultdata == 6) {
                vs_L_without1.isChecked = true
            } else if (Rsultdata == 7) {
                vs_L_without2.isChecked = true
            }
        } else if (requestCode == 7) {
            if (Rsultdata == 6) {
                vs_R_with1.isChecked = true
            } else if (Rsultdata == 7) {
                vs_R_with2.isChecked = true
            }
        } else if (requestCode == 8) {
            if (Rsultdata == 6) {
                vs_L_with1.isChecked = true
            } else if (Rsultdata == 7) {
                vs_L_with2.isChecked = true
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