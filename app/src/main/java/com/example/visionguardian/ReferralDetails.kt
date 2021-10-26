package com.example.visionguardian

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.visionguardian.db.ExaminationData
import com.example.visionguardian.db.MyDatabase
import com.example.visionguardian.db.Patient
import kotlinx.android.synthetic.main.activity_refferal_details.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

internal class ReferralDetails : AppCompatActivity() {

    var myDatabase: MyDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refferal_details)
        setUpDB()
        val intent: Intent = intent
        val sharedPreferencesLogin = getSharedPreferences("MySharedPrefLogin", MODE_PRIVATE)
        var accesstoken = sharedPreferencesLogin.getString("accessToken", "defaultName").toString()
        var user_id = sharedPreferencesLogin.getString("id", "defaultName").toString()
        var id = intent.getIntExtra("patientID", 0)
        var examId = intent.getIntExtra("ExamID", 0)
        val patientData = myDatabase!!.dao()!!.loadSingle(id)
        val examData = myDatabase!!.examDao()!!.loadSingleExam(examId)
        val pData = patientData[0]
        txtName.text = pData.mFirstName + " " + pData.mLastName
        txtDateV.text = pData.mDoVisit
        txtDOB.text = pData.mDob
        txtAge.text = pData.mAge
        txtGender.text = pData.mGender
        txtAddress.text = pData.mAddress
        val data = examData[0]
        txtDistR.text = data.mdistanceVisionWithoutR
        txtDistL.text = data.mdistanceVisionWithoutL
        txtDistwithR.text = data.mdistanceGlassR
        txtDistwithL.text = data.mdistanceGlassL
        txtNearL.text = data.mNearWithOutGL
        txtNearR.text = data.mNearWithOutGR
        txtNearWithR.text = data.mNearWithGR
        txtNearWithL.text = data.mNearWithGL
        txtCate.text = data.mCateVI
        txtSphereR.text = data.mSphereR
        txtSphereL.text = data.mSphereL
        txtCylinderR.text = data.mCylinderR
        txtCylinderL.text = data.mCylinderL
        txtAxisL.text = data.mAxisL
        txtAxisR.text = data.mAxisR
        devEyeR.text = data.mDeviation
        devEyeL.text = data.mDevL
        txtSizeR.text = data.mSameSize
        txtEyelidsL.text = data.mEyelidsL
        txtEyelidsR.text = data.mEyelidsR
        txtEyelashesR.text = data.mEyelashR
        txtEyelashesL.text = data.mEyelashL
        txtWhiteR.text = data.mWhiteR
        txtWhiteL.text = data.mWhiteL
        txtBlackL.text = data.mBlackL
        txtBlackR.text = data.mBlackR
        txtExernalL.text = data.mExInjuL
        txtExernalR.text = data.mExInjuR
        txtForeignR.text = data.mForeignR
        txtForeignL.text = data.mForeignL
        txtPupilR.text = data.mPupilR
        txtPupilL.text = data.mPupilL
        txtQ7_op.text = data.mHistoryQ7_op
        txtQ7_op_duration.text = data.mHistoryQ7_op2
        if (data.mHistoryQ7_op == "Nil" || data.mHistoryQ7_op == "") {
            lin_q7_op.visibility = View.GONE
        }
        if (data.mHistoryQ1 == "No") {
            lay_bluryvision.visibility = View.GONE
        } else {
            lay_bluryvision.visibility = View.VISIBLE
            txtBlury.text = data.mHistoryQ1
        }

        if (data.mHistoryQ2 == "No") {
            lay_Dificulty.visibility = View.GONE
        } else {
            lay_Dificulty.visibility = View.VISIBLE
            txtDificulty.text = data.mHistoryQ2
        }

        if (data.mHistoryQ3 == "No") {
            lay_comp3.visibility = View.GONE
        } else {
            lay_comp3.visibility = View.VISIBLE
            txt3.text = data.mHistoryQ5
            txtcomp3.text = data.mHistoryQ3
        }
        if (data.mHistoryQ9.equals("Not wearing glasses")) {
            txtPast1.visibility = View.GONE
        } else {
            txtPast1.text = "History of Wearing glasses"
        }

        txtFamilyH.text = data.mHistoryQ12
        txtSystemic.text = data.mHistoryQ6
        txtSysduration.text = data.mHistoryQ11
        Log.e("mHistoryQ6", "" + data.mHistoryQ6)
        Log.e("mHistoryQ11", "" + data.mHistoryQ11)
        txtPastHistory.text = data.mHistoryQ10 + data.mHistoryQ7
        txtPastHistory_op.text = data.mHistoryQ6_op + data.mHistoryQ6_op2
        if (data.mHistoryQ6_op == "Nil" || data.mHistoryQ6_op == "") {
            txtPastHistory_op.visibility = View.GONE
        }
        if (data.mHistoryQ10.equals("Nil")) {
            txtHistr.visibility = View.GONE
        }
        txtOther.text = data.mHistoryQ4

        if (data.status.equals("updated")) {
            btn_refer.visibility = View.GONE
        }


        back_arrow.setOnClickListener { onBackPressed() }

        btn_refer.setOnClickListener {
            if (checkForInternet(this)) {
                uploadPatient(pData, data, accesstoken, examId, user_id)
            } else {
                Toast.makeText(this, "Internet not available", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun uploadPatient(
        pdata: Patient,
        mData: ExaminationData,
        accesstoken: String,
        examID: Int,
        userID: String
    ) {
        var url = "https://digidrishti.digitalindiacorporation.in/api/patient/newapp"
        val queue = Volley.newRequestQueue(this)
        val req = object : StringRequest(Request.Method.POST, url,
            Response.Listener { response ->
                try {
                    Log.e("Aaaaaaaaaaaaaaa", "" + response)
                    var strResp = response.toString()
                    val jsonObj: JSONObject = JSONObject(strResp)
                    val success: Boolean = jsonObj.getBoolean("success")
                    if (success) {
                        val jsonObj: JSONObject = jsonObj.getJSONObject("data")
                        val id = jsonObj.getString("_id")
                        Log.e("Aaaaaaaaaaaaaaa", "" + id)
                        uploadExam(id, mData, accesstoken, examID)
                    }

                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }

            }, Response.ErrorListener { e ->
                Log.e("ssssssss", "" + e)
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {

                val params: MutableMap<String, String> = HashMap()
                // Removed this line if you dont need it or Use application/json
                //params.put("Content-Type", "application/json; charset=UTF-8");
                // params.put("accessToken", accesstoken);
                // params["Authorization"] = "Bearer $accesstoken"
                params.put("Content-Type", "application/json")
                params.put("x-access-token", accesstoken)
                return params
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray? {
                val jsonObject = JSONObject()
                try {
                    jsonObject.put("user_id", userID)
                    jsonObject.put("addressline1", pdata.mAddress)
                    jsonObject.put("age", pdata.mAge)
                    jsonObject.put("city", "")
                    jsonObject.put("date_of_birth", pdata.mDob)
                    jsonObject.put("district", "")
                    jsonObject.put("firstname", pdata.mFirstName)
                    jsonObject.put("gender", pdata.mGender)
                    jsonObject.put("identity_number", pdata.mAddress)
                    jsonObject.put("lastname", pdata.mLastName)
                    jsonObject.put("middlename", pdata.mMiddle)
                    jsonObject.put("phone", pdata.mMobile)
                    jsonObject.put("pincode", "")


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

    private fun uploadExam(id: String, mData: ExaminationData, accesstoken: String, examID: Int) {
        var url = "https://digidrishti.digitalindiacorporation.in/api/patient/newappque"
        val queue = Volley.newRequestQueue(this)
        val req = object : StringRequest(Request.Method.POST, url,
            Response.Listener { response ->
                try {
                    Log.e("yyyyyyyyyyy", "" + response)
                    var strResp = response.toString()
                    val jsonObj: JSONObject = JSONObject(strResp)
                    val success: Boolean = jsonObj.getBoolean("success")
                    if (success) {
                        val msg = jsonObj.getString("message")
                        //  Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
                        myDatabase!!.examDao()!!.updateExam("updated", examID)
                        showDialog()
                        /* val obj = Referral()
                         obj.onUpdateExam()*/

                    }

                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }

            }, Response.ErrorListener { e ->
                Log.e("ssssssss", "" + e)
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                // Removed this line if you dont need it or Use application/json
                //params.put("Content-Type", "application/json; charset=UTF-8");
                // params.put("accessToken", accesstoken);
                // params["Authorization"] = "Bearer $accesstoken"
                params.put("Content-Type", "application/json")
                params.put("x-access-token", accesstoken)
                return params
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray? {
                val jsonObject = JSONObject()
                try {
                    jsonObject.put("patient_id", id)
                    jsonObject.put("question_1", mData.mHistoryQ1)
                    jsonObject.put("question_2", mData.mHistoryQ2)
                    jsonObject.put("question_3", mData.mHistoryQ5)
                    jsonObject.put("red", mData.mHistoryQ3)

                    jsonObject.put("difficult", mData.mHistoryQ4)
                    jsonObject.put("diff_in_see", mData.mHistoryQ9)

                    // jsonObject.put("question_6", mData.mHistoryQ12+mData.mHistoryQ6)
                    if (mData.mHistoryQ6.equals("Nil")) {
                        Log.e("Nilaaaaaaaaaaaaaa", "nil")
                        jsonObject.put("question_6", "Nil")
                    } else {
                        jsonObject.put("question_6", mData.mHistoryQ11 + mData.mHistoryQ6)
                    }

                    if (mData.mHistoryQ6_op.equals("Nil")) {
                        jsonObject.put("addmore", "Nil")
                    } else {
                        jsonObject.put("addmore", mData.mHistoryQ6_op + mData.mHistoryQ6_op2)
                    }


                    // jsonObject.put("question_7", mData.mHistoryQ10 + mData.mHistoryQ7)
                    if (mData.mHistoryQ10.equals("Nil")) {
                        jsonObject.put("question_7", "Nil")
                    } else {
                        jsonObject.put("question_7", mData.mHistoryQ10 + mData.mHistoryQ7)
                    }

                    if (mData.mHistoryQ7_op == "Nil" || mData.mHistoryQ7_op == "") {
                        jsonObject.put("add_more", "Nil")
                    } else {
                        jsonObject.put("add_more", mData.mHistoryQ7_op + mData.mHistoryQ7_op2)
                    }



                    jsonObject.put("without_r", mData.mdistanceVisionWithoutR)
                    jsonObject.put("without_l", mData.mdistanceVisionWithoutL)
                    jsonObject.put("with_r", mData.mdistanceGlassR)
                    jsonObject.put("with_l", mData.mdistanceGlassL)
                    jsonObject.put("near_r", mData.mNearWithOutGR)
                    jsonObject.put("near_l", mData.mNearWithOutGL)
                    jsonObject.put("vision_r", mData.mNearWithGR)
                    jsonObject.put("vision_l", mData.mNearWithGL)



                    jsonObject.put("re_sph1", mData.mSphereR)
                    //jsonObject.put("re_sph2", "")
                    //jsonObject.put("re_sph3", "")
                    jsonObject.put("re_cyl", mData.mCylinderR)
                    jsonObject.put("re_axis", mData.mAxisR)
                    jsonObject.put("le_sph1", mData.mSphereL)
                    //jsonObject.put("le_sph2", "")
                    // jsonObject.put("le_sph3", "")
                    jsonObject.put("le_cyl", mData.mCylinderL)
                    jsonObject.put("le_axis", mData.mAxisL)
                    jsonObject.put("remark", mData.mHistoryQ12)


                    jsonObject.put("auto_re1", mData.mSpAuto_R)
                    jsonObject.put("auto_re2", mData.mCyAuto_R)
                    jsonObject.put("auto_re3", mData.mAxisAuto_R)
                    jsonObject.put("auto_re_cyl", mData.mVisionR)
                    jsonObject.put("auto_re_axis", mData.mNVR)
                    jsonObject.put("auto_le1", mData.mSpAuto_L)
                    jsonObject.put("auto_le2", mData.mCyAuto_L)
                    jsonObject.put("auto_le3", mData.mAxisAuto_L)
                    jsonObject.put("auto_le_cyl", mData.mVisionL)
                    jsonObject.put("auto_le_axis", mData.mNVL)
                    jsonObject.put("remark1", mData.mHistoryQ12)


                    jsonObject.put("devi_r", mData.mDeviation)
                    jsonObject.put("devi_l", mData.mDevL)
                    jsonObject.put("size_r", mData.mSameSize)
                    jsonObject.put("size_l", mData.mSameSize)
                    jsonObject.put("inju_r", mData.mExInjuR)
                    jsonObject.put("inju_l", mData.mExInjuL)
                    jsonObject.put("fore_r", mData.mForeignR)
                    jsonObject.put("fore_l", mData.mForeignL)
                    jsonObject.put("pup_r", mData.mPupilR)
                    jsonObject.put("pup_l", mData.mPupilL)
                    jsonObject.put("eye_r", mData.mEyelashR)
                    jsonObject.put("eye_l", mData.mEyelashL)
                    jsonObject.put("bla_r", mData.mBlackR)
                    jsonObject.put("bla_l", mData.mBlackL)
                    jsonObject.put("whi_r", mData.mWhiteR)
                    jsonObject.put("whi_l", mData.mWhiteL)
                    jsonObject.put("eyelids_r", mData.mEyelidsR)
                    jsonObject.put("eyelids_l", mData.mEyelidsL)
                    jsonObject.put("status", mData.mHistoryQ8)


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


    private fun setUpDB() {
        myDatabase = Room.databaseBuilder(this, MyDatabase::class.java, "StudentDB")
            .allowMainThreadQueries().build()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, Referral::class.java)
        startActivity(intent)
        finish()
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


    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("DigiDrishti")
        builder.setMessage("Data added successfully")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("Ok") { dialogInterface, which ->
            onBackPressed()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }
}