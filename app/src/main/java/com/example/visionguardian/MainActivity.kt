package com.example.visionguardian



import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.txt_username
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.login_screen.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
        val sharedPreferences = getSharedPreferences("MySharedPrefLogin", MODE_PRIVATE)
        val userName= sharedPreferences.getString("username", "defaultName")
        val password= sharedPreferences.getString("password", "defaultName")

        Log.e("xxxxxxxxxxxxx",userName+"")
        Log.e("xxxxxxxxxxxxx",userName+"")

        login_button.setOnClickListener {
            val txtname=txt_username_.text.toString()
            val txtpassword=pass.text.toString()
            if (txtname.equals(userName) && txtpassword.equals(password)){
                val myEdit = sharedPreferences.edit()
                myEdit.putString("islogin", "Yes")
                myEdit.commit()
                myEdit.apply()
                var intent :Intent=Intent(this,LandingActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                showAlertDialog("Username or Password is incorrect")
            }


        }
        txt_newreg.setOnClickListener {
            var intent :Intent=Intent(this,SignUp::class.java)
            startActivity(intent)
            finish()
        }

      /*

        btn_login.setOnClickListener {
            if (txt_username.text.equals("")){
                Toast.makeText(applicationContext, "Enter Valid User Name",
                        Toast.LENGTH_SHORT).show()
            }
            else if (text_pass.text.equals("")){
                Toast.makeText(applicationContext, "Enter Valid Password",
                        Toast.LENGTH_SHORT).show()
            }
            else{
                var intent :Intent=Intent(this,LandingActivity::class.java)
                startActivity(intent)
            }
        }*/


    }

    private fun showAlertDialog(msg : String?) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Login Failed")
        alertDialog.setMessage(msg)
        alertDialog.setPositiveButton(
                "OK"
        ) { _, _ ->

        }

        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }





}


