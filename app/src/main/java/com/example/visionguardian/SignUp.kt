package com.example.visionguardian

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btn_signUp.setOnClickListener {
            signup()
            /* if (Validate()) {
                 val sharedPreferences = getSharedPreferences("MySharedPrefLogin", MODE_PRIVATE)
                 val myEdit = sharedPreferences.edit()
                 myEdit.putString("username", txt_username.text.toString())
                 myEdit.putString("password", edt_password.text.toString())
                 myEdit.putString("name", edt_name.text.toString() + " " + edt_lastname.text.toString())
                 myEdit.putString("visioncenter", text_visionCente.text.toString())
                 myEdit.putString("Mobile no", edt_mobile.text.toString())
                 myEdit.commit()
                 myEdit.apply()
                 var intent = Intent(this, MainActivity::class.java)
                 startActivity(intent)
                 finish()
             }*/

        }

        dob_vg.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            var dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    val mmMonth = mMonth + 1
                    val date = "$mDay/$mmMonth/$mYear"

                    dob_vg.text = date
                },
                year,
                month,
                day
            )
            dpd.show()
        }


    }

    private fun showAlertDialog(msg: String?) {
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
        if (!hasText(txt_username, "Enter Username")) return false
        if (!hasText(edt_password, "Enter Password")) return false
        if (!hasText(edt_name, "Enter First Name")) return false
        if (!hasText(edt_lastname, "Enter Last Name")) return false
        if (!hasText(edt_mobile, "Enter Mobile Number")) return false
        if (!hasText(text_visionCente, "Enter Vision Center Name")) return false
        return true
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


    fun signup() {
        var url = "https://digidrishti.dic.gov.in/api/auth/signup"
        val queue = Volley.newRequestQueue(this)
        val req = object : StringRequest(Request.Method.POST, url,
            Response.Listener { response ->
                Log.e("Aaaaaaaaaaaaaaa", "" + response)
                /*  try {
                      var strResp = response.toString()
                      val jsonObj: JSONObject = JSONObject(strResp)
                      var status = jsonObj.getString("accessToken")
                      Log.e("Aaaaaaaaaaaaaaa", "" + status)
                      *//* if (status == 200) {

                         val jsonArray: JSONArray = jsonObj.getJSONArray("data")

                         for (i in 0 until jsonArray.length()) {
                             var jsonInner: JSONObject = jsonArray.getJSONObject(i)
                             var location_id = jsonInner.get("location_id")
                             var location = jsonInner.get("location")

                         }


                     }*//*
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }*/

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
                    jsonObject.put("username", "Rahul123")
                    jsonObject.put("email", "rahul@gmail.com")
                    jsonObject.put("firstname", "Rahul")
                    jsonObject.put("middlename", "")
                    jsonObject.put("lastname", "Bibave")
                    jsonObject.put("phone", "8055277545")
                    jsonObject.put("gender", "Male")
                    jsonObject.put("date_of_birth", "01/01/2000")
                    jsonObject.put("password", "12345678")
                    jsonObject.put("organization", "")
                    jsonObject.put("vision_center", "HV")
                    jsonObject.put("addressline1", "pune")
                    jsonObject.put("addressline2", "")
                    jsonObject.put("city", "pune")
                    jsonObject.put("district", "pune")
                    jsonObject.put("state", "mh")
                    jsonObject.put("pincode", "123455")
                    jsonObject.put("country", "in")


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

}
