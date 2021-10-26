package com.example.visionguardian

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_vision_image.*

class VisionImage : AppCompatActivity() {
    var rotaton = 0
    var res = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vision_image)
        var vision = intent.getStringExtra("Vision").toString()
        if (vision.equals("Visible")) {
            big_e.visibility = View.GONE
            vision_view.visibility = View.VISIBLE
        } else {
            big_e.visibility = View.VISIBLE
        }

        big_flip.setOnClickListener {
            rotaton = (rotaton + 90F).toInt()
            big_image.rotation = rotaton + 90F
        }
        small_flip.setOnClickListener {
            rotaton = (rotaton + 90F).toInt()
            small_image.rotation = rotaton + 90F
        }
        mid_flip.setOnClickListener {
            rotaton = (rotaton + 90F).toInt()
            mid_image.rotation = rotaton + 90F
        }
        big_e_yes.setOnClickListener {
            big_e.visibility = View.GONE
            small_e.visibility = View.GONE
            mid_e.visibility = View.VISIBLE

        }
        vision_no.setOnClickListener {
            res = 6
            onBackPressed()
        }
        vision_yes.setOnClickListener {
            res = 7
            onBackPressed()
        }

        big_e_no.setOnClickListener {
            showDialog("Show finger count at 3 meter")
        }


        mid_e_yes.setOnClickListener {
            big_e.visibility = View.GONE
            mid_e.visibility = View.GONE
            small_e.visibility = View.VISIBLE
        }

        mid_e_no.setOnClickListener {
            res = 1
            onBackPressed()
        }

        small_e_yes.setOnClickListener {
            res = 3
            onBackPressed()
        }

        small_e_no.setOnClickListener {
            res = 2
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        backtoActivity()
    }

    private fun backtoActivity() {
        var intent = Intent()
        intent.putExtra("ResultData", res)
        setResult(RESULT_OK, intent)
        finish()
    }

    fun OnBack(view: View) {
        onBackPressed()
    }

    private fun showDialog(msg: String?) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("DigiDrishti")
        alertDialog.setMessage(msg)
        alertDialog.setPositiveButton(
            "OK"
        ) { dialog, id ->
            onBackPressed()
            // submitData("Yes")

        }

        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }


}