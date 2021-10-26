package com.example.visionguardian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class GetRefferal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_refferal)
        data()
    }

    /* fun getData() {
         var url = "https://digidrishti.digitalindiacorporation.in/api/patient/allappque"
         var queue = Volley.newRequestQueue(this)
         var req = object : StringRequest(Request.Method.GET, url,
             Response.Listener { response ->
                 Log.e("Aaaaaaaaaaaaaaa", "" + response)
                 try {
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
                }

            }, Response.ErrorListener { e ->

            })

    }*/


    fun data() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://digidrishti.digitalindiacorporation.in/api/patient/allappque"

        // Request a string response from the provided URL.
        val stringReq = StringRequest(Request.Method.GET, url,
            { response ->


                var strResp = response.toString()
                val jsonObj: JSONObject = JSONObject(strResp)
                val success: Boolean = jsonObj.getBoolean("success")
                if (success) {

                    val jsonArray: JSONArray = jsonObj.getJSONArray("data")

                    for (i in 0 until jsonArray.length()) {
                        var jsonInner: JSONObject = jsonArray.getJSONObject(i)
                        val str_user = jsonInner.get("str_user")
                        val _id = jsonInner.get("_id")
                        val age = jsonInner.get("age")
                        val gender = jsonInner.get("gender")
                        val mobile = jsonInner.get("mobile")
                        val name = jsonInner.get("name")
                        val auto_le1 = jsonInner.get("auto_le1")
                        val auto_le2 = jsonInner.get("auto_le2")
                        val auto_le3 = jsonInner.get("auto_le3")
                        val auto_le_axis = jsonInner.get("auto_le_axis")
                        val auto_le_cyl = jsonInner.get("auto_le_cyl")
                        val auto_re1 = jsonInner.get("auto_re1")
                        val auto_re2 = jsonInner.get("auto_re2")
                        val auto_re3 = jsonInner.get("auto_re3")
                        val auto_re_axis = jsonInner.get("auto_re_axis")
                        val auto_re_cyl = jsonInner.get("auto_re_cyl")

                        val bla_l = jsonInner.get("bla_l")
                        val bla_r = jsonInner.get("bla_r")
                        val blurry_vision = jsonInner.get("blurry_vision")
                        val blurry_vision_yes = jsonInner.get("blurry_vision_yes")
                        val checkboxs = jsonInner.get("checkboxs")
                        val devi_l = jsonInner.get("devi_l")
                        val devi_r = jsonInner.get("devi_r")
                        val diff_in_see = jsonInner.get("diff_in_see")
                        val disease = jsonInner.get("disease")
                        val dura_opt = jsonInner.get("dura_opt")
                        val duration = jsonInner.get("duration")
                        val duration_bv = jsonInner.get("duration_bv")
                        val duration_news = jsonInner.get("duration_news")
                        val duration_news_1 = jsonInner.get("duration_news_1")
                        val duration_redness = jsonInner.get("duration_redness")

                        val eye_l = jsonInner.get("eye_l")
                        val eye_r = jsonInner.get("eye_r")
                        val eyelids_l = jsonInner.get("eyelids_l")
                        val eyelids_r = jsonInner.get("eyelids_r")
                        val fore_l = jsonInner.get("fore_l")
                        val fore_r = jsonInner.get("fore_r")
                        val inju_l = jsonInner.get("inju_l")
                        val inju_r = jsonInner.get("inju_r")
                        val le_axis = jsonInner.get("le_axis")
                        val le_cyl = jsonInner.get("le_cyl")
                        val le_sph1 = jsonInner.get("le_sph1")
                        val le_sph2 = jsonInner.get("le_sph2")
                        val le_sph3 = jsonInner.get("le_sph3")
                        val near_l = jsonInner.get("near_l")
                        val near_r = jsonInner.get("near_r")
                        val news = jsonInner.get("news")
                        val news_yes = jsonInner.get("news_yes")
                        val past_duration = jsonInner.get("past_duration")
                        val past_history = jsonInner.get("past_history")
                        val past_num = jsonInner.get("past_num")
                        val patient_id = jsonInner.get("patient_id")
                        val pup_l = jsonInner.get("pup_l")
                        val pup_r = jsonInner.get("pup_r")
                        val quanity_options = jsonInner.get("quanity_options")
                        val re_axis = jsonInner.get("re_axis")
                        val re_cyl = jsonInner.get("re_cyl")
                        val re_sph1 = jsonInner.get("re_sph1")
                        val re_sph2 = jsonInner.get("re_sph2")
                        val re_sph3 = jsonInner.get("re_sph3")
                        val rednesss = jsonInner.get("rednesss")
                        val rednesss_yes = jsonInner.get("rednesss_yes")
                        val remark = jsonInner.get("remark")
                        val remark1 = jsonInner.get("remark1")
                        val size_l = jsonInner.get("size_l")
                        val size_r = jsonInner.get("size_r")
                        val systematic = jsonInner.get("systematic")
                        val vision_l = jsonInner.get("vision_l")
                        val vision_r = jsonInner.get("vision_r")
                        val whi_l = jsonInner.get("whi_l")
                        val whi_r = jsonInner.get("whi_r")
                        val with_l = jsonInner.get("with_l")
                        val with_r = jsonInner.get("with_r")
                        val without_l = jsonInner.get("without_l")
                        val without_r = jsonInner.get("without_r")
                        val registered_on = jsonInner.get("name")
                        Log.e("dddddddddddddd", "" + str_user)

                    }
                }


            },
            { e -> Log.e("dddddddddddddd", "" + e) })
        queue.add(stringReq)
    }


}