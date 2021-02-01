package com.example.visionguardian

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.visionguardian.db.ExaminationData
import com.example.visionguardian.db.MyDatabase
import com.example.visionguardian.db.Patient
import jxl.Workbook
import jxl.WorkbookSettings
import jxl.format.Border
import jxl.format.BorderLineStyle
import jxl.format.Colour
import jxl.format.UnderlineStyle
import jxl.write.*
import jxl.write.biff.RowsExceededException
import kotlinx.android.synthetic.main.activity_referral.*
import java.io.File
import java.io.IOException
import java.util.*


class Referral : AppCompatActivity() {
    var visionName=""
    var userName=""
    var myDatabase: MyDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_referral)
        setUpDB()
        val sharedPreferences = getSharedPreferences("MySharedPrefLogin", MODE_PRIVATE)
        var VCName= sharedPreferences.getString("visioncenter", "defaultName").toString()
        visionName=VCName.take(2)
        var u_Name= sharedPreferences.getString("username", "defaultName").toString()
        userName=u_Name.take(2)
        ActivityCompat.requestPermissions(
                this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ), PackageManager.PERMISSION_GRANTED
        )
        val examData = myDatabase!!.examDao()!!.patientExam
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewPatientExam)
        val adapter = ReferalListAdapter(this, examData as ArrayList<ExaminationData>, myDatabase!!)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
        back_arrow.setOnClickListener {
            finish()
        }

        ic_export.setOnClickListener { createExcelSheet() }
    }

    private fun setUpDB() {
        myDatabase = Room.databaseBuilder(this, MyDatabase::class.java, "StudentDB")
                .allowMainThreadQueries().build()
    }


    private fun createExcelSheet() {


        var filename = "export.xls"

        var path = getExternalFilesDir(null)   //get file directory for this package
        //(Android/data/.../files | ... is your app package)

//create fileOut object
        var fileOut = File(path, filename)

//delete any file object with path and filename that already exists
        fileOut.delete()

//create a new file
        fileOut.createNewFile()

        val cellFonts = WritableFont(
                WritableFont.createFont("Calibri"), 11, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE, Colour.BLACK
        )
        val cellFormats = WritableCellFormat(cellFonts)
        cellFormats.setBorder(Border.ALL, BorderLineStyle.THIN)
        cellFormats.setBackground(Colour.WHITE)
        cellFormats.wrap = true

        val cellFormats2 = WritableCellFormat(cellFonts)
        cellFormats.setBorder(Border.ALL, BorderLineStyle.THIN)
        cellFormats.setBackground(Colour.GRAY_25)
        cellFormats.wrap = true


        val wbSettings = WorkbookSettings()
        wbSettings.locale = Locale("en", "EN")
        val workbook: WritableWorkbook
        try {
            val a = 1
            workbook = Workbook.createWorkbook(fileOut, wbSettings)
            val sheet = workbook.createSheet("First Sheet", 0)

            //workbook.createSheet("Report", 0);
            val examDataList = myDatabase!!.examDao()!!.patientExam as ArrayList<ExaminationData>

            sheet.addCell(Label(0, 0, "Sr. No", cellFormats))
            sheet.addCell(Label(1, 0, "Name", cellFormats))
            sheet.addCell(Label(2, 0, "Age", cellFormats))
            sheet.addCell(Label(3, 0, "Sex", cellFormats))
            sheet.addCell(Label(4, 0, "Date Of Visit", cellFormats))
            sheet.addCell(Label(5, 0, "Mobile", cellFormats))
            sheet.addCell(Label(6, 0, "Aa", cellFormats))

            sheet.addCell(Label(7, 0, "Complaint", cellFormats))
            sheet.mergeCells(7, 0, 8, 0)
            sheet.addCell(Label(7, 1, "1", cellFormats))
            sheet.addCell(Label(8, 1, "2", cellFormats))

            sheet.addCell(Label(9, 0, "Past Ocular history", cellFormats))
            sheet.mergeCells(9, 0, 10, 0)
            sheet.addCell(Label(9, 1, "Difficulty with glass", cellFormats))
            sheet.addCell(Label(10, 1, "Past History", cellFormats))

            sheet.addCell(Label(11, 0, "Systemic History", cellFormats))


            sheet.addCell(Label(12, 0, "Distant vision without glasses", cellFormats))
            sheet.mergeCells(12, 0, 13, 0)
            sheet.addCell(Label(12, 1, "Right eye", cellFormats))
            sheet.addCell(Label(13, 1, "Left eye", cellFormats))

            sheet.addCell(Label(14, 0, "Near vision without glasses", cellFormats))
            sheet.mergeCells(14, 0, 15, 0)
            sheet.addCell(Label(14, 1, "Right eye", cellFormats))
            sheet.addCell(Label(15, 1, "Left eye", cellFormats))

            sheet.addCell(Label(16, 0, "Distant vision with glasses", cellFormats))
            sheet.mergeCells(16, 0, 17, 0)
            sheet.addCell(Label(16, 1, "Right eye", cellFormats))
            sheet.addCell(Label(17, 1, "Left eye", cellFormats))

            sheet.addCell(Label(18, 0, "Near vision with glasses", cellFormats))
            sheet.mergeCells(18, 0, 19, 0)
            sheet.addCell(Label(18, 1, "Right eye", cellFormats))
            sheet.addCell(Label(19, 1, "Left eye", cellFormats))

            sheet.addCell(Label(20, 0, "click checkrefraction", cellFormats))
            sheet.mergeCells(20, 0, 25, 0)
            sheet.addCell(Label(20, 1, "Right eye", cellFormats))
            sheet.mergeCells(20, 1, 22, 1)
            sheet.addCell(Label(23, 1, "Left eye", cellFormats))
            sheet.mergeCells(23, 1, 25, 1)

            sheet.addCell(Label(20, 2, "Sphere", cellFormats))
            sheet.addCell(Label(21, 2, "Cylinder", cellFormats))
            sheet.addCell(Label(22, 2, "Axis", cellFormats))

            sheet.addCell(Label(23, 2, "Sphere", cellFormats))
            sheet.addCell(Label(24, 2, "Cylinder", cellFormats))
            sheet.addCell(Label(25, 2, "Axis", cellFormats))

            sheet.addCell(Label(26, 0, "Deviation of eyes", cellFormats))
            sheet.mergeCells(26, 0, 27, 0)
            sheet.addCell(Label(26, 1, "Right eye", cellFormats))
            sheet.addCell(Label(27, 1, "Left eye", cellFormats))

            sheet.addCell(Label(28, 0, "Size of eye", cellFormats))
            sheet.mergeCells(28, 0, 29, 0)
            sheet.addCell(Label(28, 1, "Right eye", cellFormats))
            sheet.addCell(Label(29, 1, "Left eye", cellFormats))

            sheet.addCell(Label(30, 0, "Eyelids", cellFormats))
            sheet.mergeCells(30, 0, 31, 0)
            sheet.addCell(Label(30, 1, "Right eye", cellFormats))
            sheet.addCell(Label(31, 1, "Left eye", cellFormats))

            sheet.addCell(Label(32, 0, "Eyelashes", cellFormats))
            sheet.mergeCells(32, 0, 33, 0)
            sheet.addCell(Label(32, 1, "Right eye", cellFormats))
            sheet.addCell(Label(33, 1, "Left eye", cellFormats))

            sheet.addCell(Label(34, 0, "White of the eyes", cellFormats))
            sheet.mergeCells(34, 0, 35, 0)
            sheet.addCell(Label(34, 1, "Right eye", cellFormats))
            sheet.addCell(Label(35, 1, "Left eye", cellFormats))

            sheet.addCell(Label(36, 0, "Black of the eyes", cellFormats))
            sheet.mergeCells(36, 0, 37, 0)
            sheet.addCell(Label(36, 1, "Right eye", cellFormats))
            sheet.addCell(Label(37, 1, "Left eye", cellFormats))

            sheet.addCell(Label(38, 0, "External injury", cellFormats))
            sheet.mergeCells(38, 0, 39, 0)
            sheet.addCell(Label(38, 1, "Right eye", cellFormats))
            sheet.addCell(Label(39, 1, "Left eye", cellFormats))

            sheet.addCell(Label(40, 0, "Foreign body", cellFormats))
            sheet.mergeCells(40, 0, 41, 0)
            sheet.addCell(Label(40, 1, "Right eye", cellFormats))
            sheet.addCell(Label(41, 1, "Left eye", cellFormats))

           // sheet.addCell(Label(42, 0, "", cellFormats))
            sheet.addCell(Label(42, 0, "Refer", cellFormats))


            for (item in examDataList.indices) {
                val x = item + 3
                val sr = item + 1
                val pID = examDataList.get(item).mPatientID
                val patientList = myDatabase!!.dao()!!.loadSingle(pID) as ArrayList<Patient>

                sheet.addCell(Label(0, x,visionName.toUpperCase()+userName.toUpperCase()+"-"+sr.toString()))
                sheet.addCell(Label(1, x, patientList.get(0).mFirstName + " " + patientList.get(0).mLastName))
                sheet.addCell(Label(2, x, patientList.get(0).mAge))
                sheet.addCell(Label(3, x, patientList.get(0).mGender))
                sheet.addCell(Label(4, x, examDataList.get(item).mDateOfExam))
                sheet.addCell(Label(5, x, patientList.get(0).mMobile))
                sheet.addCell(Label(6, x, patientList.get(0).mEmail))
                sheet.addCell(Label(7, x, "Blurry vision -${examDataList.get(item).mHistoryQ1},"+
                "Difficulty in Reading-${examDataList.get(item).mHistoryQ2}"))
                sheet.addCell(Label(8, x,examDataList.get(item).mHistoryQ3 +"-"+ examDataList.get(item).mHistoryQ5 +"Other - " +examDataList.get(item).mHistoryQ4))

                sheet.addCell(Label(9, x, examDataList.get(item).mHistoryQ9))
                sheet.addCell(Label(10, x, examDataList.get(item).mHistoryQ10+examDataList.get(item).mHistoryQ7))

                sheet.addCell(Label(11, x, examDataList.get(item).mHistoryQ6+examDataList.get(item).mHistoryQ11))
              //  sheet.addCell(Label(11, x, examDataList.get(item).mHistoryQ12))

                sheet.addCell(Label(12, x, examDataList.get(item).mdistanceVisionWithoutR))
                sheet.addCell(Label(13, x, examDataList.get(item).mdistanceVisionWithoutL))

                sheet.addCell(Label(14, x, examDataList.get(item).mNearWithOutGR))
                sheet.addCell(Label(15, x, examDataList.get(item).mNearWithOutGL))

                sheet.addCell(Label(16, x, examDataList.get(item).mdistanceGlassR))
                sheet.addCell(Label(17, x, examDataList.get(item).mdistanceGlassL))

                sheet.addCell(Label(18, x, examDataList.get(item).mNearWithGR))
                sheet.addCell(Label(19, x, examDataList.get(item).mNearWithGL))

                sheet.addCell(Label(20, x, examDataList.get(item).mSphereR))
                sheet.addCell(Label(21, x, examDataList.get(item).mCylinderR))
                sheet.addCell(Label(22, x, examDataList.get(item).mAxisR))
                sheet.addCell(Label(23, x, examDataList.get(item).mSphereL))
                sheet.addCell(Label(24, x, examDataList.get(item).mCylinderL))
                sheet.addCell(Label(25, x, examDataList.get(item).mAxisL))

                sheet.addCell(Label(26, x, examDataList.get(item).mDeviation))
                sheet.addCell(Label(27, x, examDataList.get(item).mDevL))

                sheet.addCell(Label(28, x, examDataList.get(item).mSameSize))
                sheet.addCell(Label(29, x, examDataList.get(item).mSameSize))

                sheet.addCell(Label(30, x, examDataList.get(item).mEyelidsR))
                sheet.addCell(Label(31, x, examDataList.get(item).mEyelidsL))

                sheet.addCell(Label(32, x, examDataList.get(item).mEyelashR))
                sheet.addCell(Label(33, x, examDataList.get(item).mEyelashL))

                sheet.addCell(Label(34, x, examDataList.get(item).mWhiteR))
                sheet.addCell(Label(35, x, examDataList.get(item).mWhiteL))

                sheet.addCell(Label(36, x, examDataList.get(item).mBlackR))
                sheet.addCell(Label(37, x, examDataList.get(item).mBlackL))

                sheet.addCell(Label(38, x, examDataList.get(item).mExInjuR))
                sheet.addCell(Label(39, x, examDataList.get(item).mExInjuL))

                sheet.addCell(Label(40, x, examDataList.get(item).mForeignR))
                sheet.addCell(Label(41, x, examDataList.get(item).mForeignL))
                sheet.addCell(Label(42, x, examDataList.get(item).mHistoryQ8))
              //  sheet.addCell(Label(43, x, patientList.get(0).mEmail))



            }


            try {


            } catch (e: RowsExceededException) {
                Log.e("xxxxxxxxxxxxxxxxxx", "" + e)
                e.printStackTrace()
            } catch (e: WriteException) {
                Log.e("xxxxxxxxxxxxxxxxxx", "" + e)
                e.printStackTrace()
            }
            workbook.write()
            try {
                workbook.close()
            } catch (e: WriteException) {
                Log.e("xxxxxxxxxxxxxxxxxx", "" + e)
                e.printStackTrace()
            }
            //createExcel(excelSheet);
        } catch (e: IOException) {
            Log.e("xxxxxxxxxxxxxxxxxx", "" + e)
            e.printStackTrace()
        }
        showAlertDialog()
    }


    private fun showAlertDialog() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Data export successfully completed")
        alertDialog.setMessage("please check Android/data/com.digidrishtri.visionguardian/files")
        alertDialog.setPositiveButton(
                "OK"
        ) { _, _ ->

        }

        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }
}