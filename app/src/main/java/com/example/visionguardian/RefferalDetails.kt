package com.example.visionguardian

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.room.Room
import com.example.visionguardian.R
import com.example.visionguardian.db.MyDatabase
import kotlinx.android.synthetic.main.activity_refferal_details.*

internal class RefferalDetails : AppCompatActivity() {

    var myDatabase: MyDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refferal_details)
        setUpDB()
        val intent: Intent = intent
        var id = intent.getIntExtra("patientID",0)
        var examId = intent.getIntExtra("ExamID",0)
        val patientData =myDatabase!!.dao()!!.loadSingle(id)
        val examData=myDatabase!!.examDao()!!.loadSingleExam(examId)
        val pData=patientData[0]
        txtName.text= pData.mFirstName +" "+ pData.mLastName
        txtDateV.text=pData.mDoVisit
        txtDOB.text=pData.mDob
        txtAge.text=pData.mAge
        txtGender.text=pData.mGender
        txtAddress.text=pData.mAddress
        val data=examData[0]

        txtDistR.text=data.mdistanceVisionWithoutR
        txtDistL.text=data.mdistanceVisionWithoutL
        txtDistwithR.text=data.mdistanceGlassR
        txtDistwithL.text=data.mdistanceGlassL
        txtNearL.text=data.mNearWithOutGL
        txtNearR.text=data.mNearWithOutGR
        txtNearWithR.text=data.mNearWithGR
        txtNearWithL.text=data.mNearWithGL
        txtCate.text=data.mCateVI
        txtSphereR.text=data.mSphereR
        txtSphereL.text=data.mSphereL
        txtCylinderR.text=data.mCylinderR
        txtCylinderL.text=data.mCylinderL
        txtAxisL.text=data.mAxisL
        txtAxisR.text=data.mAxisR
        devEyeR.text=data.mDeviation
        devEyeL.text=data.mDevL
        txtSizeR.text=data.mSameSize
        txtEyelidsL.text=data.mEyelidsL
        txtEyelidsR.text=data.mEyelidsR
        txtEyelashesR.text=data.mEyelashR
        txtEyelashesL.text=data.mEyelashL
        txtWhiteR.text=data.mWhiteR
        txtWhiteL.text=data.mWhiteL
        txtBlackL.text=data.mBlackL
        txtBlackR.text=data.mBlackR
        txtExernalL.text=data.mExInjuL
        txtExernalR.text=data.mExInjuR
        txtForeignR.text=data.mForeignR
        txtForeignL.text=data.mForeignL

        if (data.mHistoryQ1=="No"){
            lay_bluryvision.visibility= View.GONE
        }else{
            lay_bluryvision.visibility= View.VISIBLE
            txtBlury.text=data.mHistoryQ1
        }

        if (data.mHistoryQ2=="No"){
            lay_Dificulty.visibility= View.GONE
        }else{
            lay_Dificulty.visibility= View.VISIBLE
            txtDificulty.text=data.mHistoryQ2
        }

        if (data.mHistoryQ3=="No"){
            lay_comp3.visibility=View.GONE
        }else{
            lay_comp3.visibility=View.VISIBLE
            txt3.text=data.mHistoryQ5
            txtcomp3.text=data.mHistoryQ3
        }
        if (data.mHistoryQ9.equals("Not wearing glasses")){

        }else{
            txtPast1.text="History of Wearing glasses"
        }

        txtFamilyH.text=data.mHistoryQ12
        txtSystemic.text=data.mHistoryQ6
        txtSysduration.text=data.mHistoryQ11
        txtPastHistory.text=data.mHistoryQ10+data.mHistoryQ7
        txtOther.text=data.mHistoryQ4



        back_arrow.setOnClickListener { finish() }
    }
    private fun setUpDB() {
        myDatabase = Room.databaseBuilder(this, MyDatabase::class.java, "StudentDB")
            .allowMainThreadQueries().build()
    }



}