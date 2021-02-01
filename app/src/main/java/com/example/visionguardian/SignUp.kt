package com.example.visionguardian

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*


class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btn_signUp.setOnClickListener {
            if (Validate()){
                val sharedPreferences = getSharedPreferences("MySharedPrefLogin", MODE_PRIVATE)
                val myEdit = sharedPreferences.edit()
                myEdit.putString("username", txt_username.text.toString())
                myEdit.putString("password", edt_password.text.toString())
                myEdit.putString("name", edt_name.text.toString()+" "+edt_lastname.text.toString())
                myEdit.putString("visioncenter", text_visionCente.text.toString())
                myEdit.putString("Mobile no", edt_mobile.text.toString())
                myEdit.commit()
                myEdit.apply()
                var intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }

        dob_vg.setOnClickListener {
            val c= Calendar.getInstance()
            val year= c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            var dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val mmMonth = mMonth+1
                val date = "$mDay/$mmMonth/$mYear"

                dob_vg.setText(date)
            },year,month,day)
            dpd.show()
        }


    }
    private fun showAlertDialog(msg : String?) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Data Incomplete")
        alertDialog.setMessage(msg)
        alertDialog.setPositiveButton(
                "OK"
        ) { _, _ ->

        }

        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }


     fun Validate(): Boolean {
        if (!hasText(txt_username,"Enter Username")) return false
        if (!hasText(edt_password,"Enter Password")) return false
        if (!hasText(edt_name,"Enter First Name")) return false
        if (!hasText(edt_lastname,"Enter Last Name")) return false
        if (!hasText(edt_mobile,"Enter Mobile Number")) return false
        if (!hasText(text_visionCente,"Enter Vision Center Name")) return false
        return  true
    }
    fun hasText(editText: EditText, error_message: String?): Boolean {
        val text = editText.text.toString().trim { it <= ' ' }
        editText.error = null

        // length 0 means there is no text
        if (text.length == 0) {
            editText.error = error_message
            showAlertDialog(error_message)
            return false
        }
        return true
    }

}