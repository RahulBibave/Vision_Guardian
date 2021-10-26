package com.example.visionguardian


import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login_demo.*
import kotlinx.android.synthetic.main.login_screen.txt_newreg
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xxx)
        val sharedPreferences = getSharedPreferences("MySharedPrefLogin", MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", "defaultName")
        val password = sharedPreferences.getString("password", "defaultName")



        login_btn.setOnClickListener {
            if (checkForInternet(this)) {
                val txtname = txt_username_.text.toString()
                val txtpassword = pass.text.toString()
                login(txtname, txtpassword)
            } else {
                Toast.makeText(this, "Internet not available", Toast.LENGTH_SHORT).show()
            }

            /* var dateOfinstall = ""
             val txtname = txt_username_.text.toString()
             val txtpassword = pass.text.toString()
             if (txtname.equals(userName) && txtpassword.equals(password)) {
                 val myEdit = sharedPreferences.edit()
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                     val current = LocalDateTime.now()
                     val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                     var answer: String = current.format(formatter)
                     dateOfinstall = answer
                 } else {
                     var date = Date()
                     val formatter = SimpleDateFormat("dd/MM/yyyy")
                     val answer: String = formatter.format(date)
                     dateOfinstall = answer
                 }




                 myEdit.putString("loginDate", "01/10/2021")
                 myEdit.putString("islogin", "Yes")
                 myEdit.commit()
                 myEdit.apply()
                 var intent: Intent = Intent(this, LandingActivity::class.java)
                 startActivity(intent)
                 finish()
             } else if (txtname.equals("admin") && txtpassword.equals("12345")) {
                 // showAlertDialog("Username or Password is incorrect")
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                     val current = LocalDateTime.now()
                     val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                     var answer: String = current.format(formatter)
                     dateOfinstall = answer
                 } else {
                     var date = Date()
                     val formatter = SimpleDateFormat("dd/MM/yyyy")
                     val answer: String = formatter.format(date)
                     dateOfinstall = answer
                 }
                 val sharedPreferences = getSharedPreferences("MySharedPrefLogin", MODE_PRIVATE)
                 val myEdit = sharedPreferences.edit()
                 myEdit.putString("username", "admin")
                 myEdit.putString("password", "12345")
                 myEdit.putString("name", "Demo User")
                 myEdit.putString("visioncenter", "Pune")
                 myEdit.putString("Mobile no", "8855224466")
                 myEdit.putString("loginDate", "01/10/2021")
                 myEdit.commit()
                 myEdit.apply()
                 val myEdit2 = sharedPreferences.edit()
                 myEdit2.putString("islogin", "Yes")
                 myEdit2.commit()
                 myEdit2.apply()
                 var intent: Intent = Intent(this, LandingActivity::class.java)
                 startActivity(intent)
                 finish()
             }*/

        }
        txt_newreg.setOnClickListener {
            var intent: Intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }


        /* btn_login.setOnClickListener {
             if (txt_username_.text.equals("")){
                 Toast.makeText(applicationContext, "Enter Valid User Name",
                         Toast.LENGTH_SHORT).show()
             }
             else if (pass.text.equals("")){
                 Toast.makeText(applicationContext, "Enter Valid Password",
                         Toast.LENGTH_SHORT).show()
             }
             else{
                 var intent :Intent=Intent(this,LandingActivity::class.java)
                 startActivity(intent)
             }
         }*/
        3

    }

    private fun showAlertDialog(msg: String?) {
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

    fun login(userName: String, password: String) {
        var url = "https://digidrishti.digitalindiacorporation.in/api/auth/signin"
        val queue = Volley.newRequestQueue(this)
        val req = object : StringRequest(Request.Method.POST, url,
            Response.Listener { response ->
                Log.e("Aaaaaaaaaaaaaaa", "" + response)
                try {
                    val sharedPreferences = getSharedPreferences("MySharedPrefLogin", MODE_PRIVATE)
                    val myEdit = sharedPreferences.edit()
                    var strResp = response.toString()
                    val jsonObj: JSONObject = JSONObject(strResp)
                    var status = jsonObj.getBoolean("success")
                    if (status) {
                        myEdit.putString("username", jsonObj.getString("username"))
                        myEdit.putString("id", jsonObj.getString("id"))
                        myEdit.putString(
                            "name",
                            jsonObj.getString("firstname") + " " + jsonObj.getString("lastname")
                        )
                        myEdit.putString("visioncenter", jsonObj.getString("vision_center"))
                        myEdit.putString("Mobile no", jsonObj.getString("phone"))
                        myEdit.putString("islogin", "Yes")
                        myEdit.putString("accessToken", jsonObj.getString("accessToken"))
                        myEdit.commit()
                        myEdit.apply()
                        var intent: Intent = Intent(this, LandingActivity::class.java)
                        startActivity(intent)
                        finish()

                    }


                    /* if (status == 200) {

                         val jsonArray: JSONArray = jsonObj.getJSONArray("data")

                         for (i in 0 until jsonArray.length()) {
                             var jsonInner: JSONObject = jsonArray.getJSONObject(i)
                             var location_id = jsonInner.get("location_id")
                             var location = jsonInner.get("location")

                         }


                     }*/
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }

            }, Response.ErrorListener { e ->

            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                // Removed this line if you dont need it or Use application/json
                // params["Authorization"] = "Bearer $accesstoken"
                return params
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray? {
                val jsonObject = JSONObject()
                try {
                    jsonObject.put("username", userName)
                    jsonObject.put("password", password)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                Log.e("xxxxxxxxxxxxxx", "" + jsonObject.toString())
                val str = jsonObject.toString()
                return str.toByteArray()
            }

            override fun getBodyContentType(): String? {
                return "application/json; charset=utf-8"
            }
        }
        req.retryPolicy = DefaultRetryPolicy(
            60000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(req)

    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}


