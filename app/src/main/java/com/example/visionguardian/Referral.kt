package com.example.visionguardian

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import jxl.Workbook
import jxl.WorkbookSettings
import jxl.format.Border
import jxl.format.BorderLineStyle
import jxl.format.Colour
import jxl.format.UnderlineStyle
import jxl.write.*
import jxl.write.biff.RowsExceededException
import kotlinx.android.synthetic.main.activity_referral.*
import kotlinx.android.synthetic.main.datefilter_dialog.view.*
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class Referral : AppCompatActivity() {
    var visionName = ""
    var userName = ""
    var myDatabase: MyDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_referral)
        loadLocate()
        setUpDB()
        val sharedPreferences = getSharedPreferences("MySharedPrefLogin", MODE_PRIVATE)
        var VCName = sharedPreferences.getString("visioncenter", "defaultName").toString()
        var accesstoken = sharedPreferences.getString("accessToken", "defaultName").toString()
        var userId = sharedPreferences.getString("id", "defaultName").toString()
        visionName = VCName.take(2)
        var u_Name = sharedPreferences.getString("username", "defaultName").toString()
        userName = u_Name.take(2)
        /*  ActivityCompat.requestPermissions(
                  this, arrayOf(
                  Manifest.permission.WRITE_EXTERNAL_STORAGE,
                  Manifest.permission.READ_EXTERNAL_STORAGE
          ), PackageManager.PERMISSION_GRANTED
          )*/


        val examData = myDatabase!!.examDao()!!.patientExam
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewPatientExam)
        val adapter =
            ReferralListAdapter(this, examData as ArrayList<ExaminationData>, myDatabase!!)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
        back_arrow.setOnClickListener {
            finish()
        }



        ic_export.setOnClickListener {
            // createExcelSheet()
            dateFilter()
        }
        ic_upload.setOnClickListener {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Digidrishti")
            progressDialog.setMessage("Data is uploading, please wait")

            if (checkForInternet(this)) {
                for (i in examData.indices) {

                    if (examData[i].status.equals("")) {
                        progressDialog.show()
                        val stuData = myDatabase!!.dao()!!.loadSingle(examData[i].mPatientID)
                        val pData = stuData[0]
                        val examDatass =
                            myDatabase!!.examDao()!!.loadSingleExam(examData[i].mExamId)
                        val data = examDatass[0]
                        uploadPatient(pData, data, accesstoken, examData[i].mExamId, userId)
                    }
                }
                progressDialog.dismiss()
                showDialog()

            } else {
                Toast.makeText(this, "Internet not available", Toast.LENGTH_SHORT).show()
            }


        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("DigiDrishti")
        builder.setMessage("Data added successfully")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("Ok") { dialogInterface, which ->
            finish()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }

    private fun setUpDB() {
        myDatabase = Room.databaseBuilder(this, MyDatabase::class.java, "StudentDB")
            .allowMainThreadQueries().build()
    }

    //examDataList:ArrayList<ExaminationData>
    private fun createExcelSheet(examDataList: ArrayList<ExaminationData>) {

        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        var filename = "export$currentDate.xls"

        var path = getExternalFilesDir(null)   //get file directory for this package
        // var newPath=Environment.getExternalStoragePublicDirectory()
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
            // val examDataList = myDatabase!!.examDao()!!.patientExam as ArrayList<ExaminationData>

            sheet.addCell(Label(0, 0, "Sr. No", cellFormats))
            sheet.addCell(Label(1, 0, "Name", cellFormats))
            sheet.addCell(Label(2, 0, "Age", cellFormats))
            sheet.addCell(Label(3, 0, "Sex", cellFormats))
            sheet.addCell(Label(4, 0, "Date Of Visit", cellFormats))
            sheet.addCell(Label(5, 0, "Mobile", cellFormats))
            sheet.addCell(Label(6, 0, "Adhar", cellFormats))

            sheet.addCell(Label(7, 0, "Complaint", cellFormats))
            sheet.mergeCells(7, 0, 10, 0)
            sheet.addCell(Label(7, 1, "Blurry Vision", cellFormats))
            sheet.addCell(Label(8, 1, "Difficulty in Reading", cellFormats))
            sheet.addCell(Label(9, 1, "Uncomfortable Eyes", cellFormats))
            sheet.addCell(Label(10, 1, "Other", cellFormats))





            sheet.addCell(Label(11, 0, "Past Ocular history", cellFormats))
            sheet.mergeCells(11, 0, 12, 0)
            sheet.addCell(Label(11, 1, "Difficulty with glass", cellFormats))
            sheet.addCell(Label(12, 1, "Past History", cellFormats))

            sheet.addCell(Label(13, 0, "Systemic History", cellFormats))


            sheet.addCell(Label(14, 0, "Distant vision without glasses", cellFormats))
            sheet.mergeCells(14, 0, 15, 0)
            sheet.addCell(Label(14, 1, "Right eye", cellFormats))
            sheet.addCell(Label(15, 1, "Left eye", cellFormats))

            sheet.addCell(Label(16, 0, "Near vision without glasses", cellFormats))
            sheet.mergeCells(16, 0, 17, 0)
            sheet.addCell(Label(16, 1, "Right eye", cellFormats))
            sheet.addCell(Label(17, 1, "Left eye", cellFormats))

            sheet.addCell(Label(18, 0, "Distant vision with glasses", cellFormats))
            sheet.mergeCells(18, 0, 19, 0)
            sheet.addCell(Label(18, 1, "Right eye", cellFormats))
            sheet.addCell(Label(19, 1, "Left eye", cellFormats))

            sheet.addCell(Label(20, 0, "Near vision with glasses", cellFormats))
            sheet.mergeCells(20, 0, 21, 0)
            sheet.addCell(Label(20, 1, "Right eye", cellFormats))
            sheet.addCell(Label(21, 1, "Left eye", cellFormats))


            sheet.addCell(Label(22, 0, "click checkrefraction", cellFormats))
            sheet.mergeCells(22, 0, 27, 0)
            sheet.addCell(Label(22, 1, "Right eye", cellFormats))
            sheet.mergeCells(22, 1, 24, 1)
            sheet.addCell(Label(25, 1, "Left eye", cellFormats))
            sheet.mergeCells(25, 1, 27, 1)

            sheet.addCell(Label(22, 2, "Sphere", cellFormats))
            sheet.addCell(Label(23, 2, "Cylinder", cellFormats))
            sheet.addCell(Label(24, 2, "Axis", cellFormats))

            sheet.addCell(Label(25, 2, "Sphere", cellFormats))
            sheet.addCell(Label(26, 2, "Cylinder", cellFormats))
            sheet.addCell(Label(27, 2, "Axis", cellFormats))





            sheet.addCell(Label(28, 0, "Deviation of eyes", cellFormats))
            sheet.mergeCells(26, 0, 27, 0)
            sheet.addCell(Label(28, 1, "Right eye", cellFormats))
            sheet.addCell(Label(29, 1, "Left eye", cellFormats))

            sheet.addCell(Label(30, 0, "Size of eye", cellFormats))
            sheet.mergeCells(28, 0, 29, 0)
            sheet.addCell(Label(30, 1, "Right eye", cellFormats))
            sheet.addCell(Label(31, 1, "Left eye", cellFormats))

            sheet.addCell(Label(32, 0, "Eyelids", cellFormats))
            sheet.mergeCells(30, 0, 31, 0)
            sheet.addCell(Label(32, 1, "Right eye", cellFormats))
            sheet.addCell(Label(33, 1, "Left eye", cellFormats))

            sheet.addCell(Label(34, 0, "Eyelashes", cellFormats))
            sheet.mergeCells(32, 0, 33, 0)
            sheet.addCell(Label(34, 1, "Right eye", cellFormats))
            sheet.addCell(Label(35, 1, "Left eye", cellFormats))

            sheet.addCell(Label(36, 0, "White of the eyes", cellFormats))
            sheet.mergeCells(34, 0, 35, 0)
            sheet.addCell(Label(36, 1, "Right eye", cellFormats))
            sheet.addCell(Label(37, 1, "Left eye", cellFormats))

            sheet.addCell(Label(38, 0, "Black of the eyes", cellFormats))
            sheet.mergeCells(36, 0, 37, 0)
            sheet.addCell(Label(38, 1, "Right eye", cellFormats))
            sheet.addCell(Label(39, 1, "Left eye", cellFormats))

            sheet.addCell(Label(40, 0, "External injury", cellFormats))
            sheet.mergeCells(38, 0, 39, 0)
            sheet.addCell(Label(40, 1, "Right eye", cellFormats))
            sheet.addCell(Label(41, 1, "Left eye", cellFormats))

            sheet.addCell(Label(42, 0, "Foreign body", cellFormats))
            sheet.mergeCells(42, 0, 43, 0)
            sheet.addCell(Label(42, 1, "Right eye", cellFormats))
            sheet.addCell(Label(43, 1, "Left eye", cellFormats))

            sheet.addCell(Label(44, 0, "Pupil", cellFormats))
            sheet.mergeCells(44, 0, 45, 0)
            sheet.addCell(Label(44, 1, "Right eye", cellFormats))
            sheet.addCell(Label(45, 1, "Left eye", cellFormats))


            // sheet.addCell(Label(42, 0, "", cellFormats))
            sheet.addCell(Label(46, 0, "Subjective Refraction", cellFormats))
            sheet.mergeCells(46, 0, 57, 0)
            sheet.addCell(Label(46, 1, "Right Eye", cellFormats))
            sheet.mergeCells(46, 1, 51, 1)
            sheet.addCell(Label(46, 2, "Sphere", cellFormats))
            sheet.addCell(Label(47, 2, "Cylinder", cellFormats))
            sheet.addCell(Label(48, 2, "Axis", cellFormats))
            sheet.addCell(Label(49, 2, "Vision", cellFormats))
            sheet.addCell(Label(50, 2, "Add", cellFormats))
            sheet.addCell(Label(51, 2, "NV", cellFormats))

            sheet.addCell(Label(52, 1, "Left Eye", cellFormats))
            sheet.mergeCells(52, 1, 57, 1)
            sheet.addCell(Label(52, 2, "Sphere", cellFormats))
            sheet.addCell(Label(53, 2, "Cylinder", cellFormats))
            sheet.addCell(Label(54, 2, "Axis", cellFormats))
            sheet.addCell(Label(55, 2, "Vision", cellFormats))
            sheet.addCell(Label(56, 2, "Add", cellFormats))
            sheet.addCell(Label(57, 2, "NV", cellFormats))




            sheet.addCell(Label(58, 0, "Refer", cellFormats))
            sheet.addCell(Label(59, 0, "Note", cellFormats))


            for (item in examDataList.indices) {
                val x = item + 3
                val sr = item + 1
                val pID = examDataList.get(item).mPatientID
                val patientList = myDatabase!!.dao()!!.loadSingle(pID) as ArrayList<Patient>

                sheet.addCell(
                    Label(
                        0,
                        x,
                        visionName.toUpperCase() + userName.toUpperCase() + "-" + sr.toString()
                    )
                )
                sheet.addCell(
                    Label(
                        1,
                        x,
                        patientList.get(0).mFirstName + " " + patientList.get(0).mLastName
                    )
                )
                sheet.addCell(Label(2, x, patientList.get(0).mAge))
                sheet.addCell(Label(3, x, patientList.get(0).mGender))
                sheet.addCell(Label(4, x, examDataList.get(item).mDateOfExam))
                sheet.addCell(Label(5, x, patientList.get(0).mMobile))
                sheet.addCell(Label(6, x, patientList.get(0).mEmail))
                sheet.addCell(Label(7, x, examDataList.get(item).mHistoryQ1))






                sheet.addCell(Label(8, x, examDataList.get(item).mHistoryQ2))
                sheet.addCell(
                    Label(
                        9,
                        x,
                        examDataList.get(item).mHistoryQ3 + examDataList.get(item).mHistoryQ5
                    )
                )
                sheet.addCell(Label(10, x, examDataList.get(item).mHistoryQ4))


                //sheet.addCell(Label(8, x,examDataList.get(item).mHistoryQ3 +"-"+  +"Other - " +examDataList.get(item).mHistoryQ4))

                sheet.addCell(Label(11, x, examDataList.get(item).mHistoryQ9))
                sheet.addCell(
                    Label(
                        12, x, examDataList.get(item).mHistoryQ10 + examDataList.get(
                            item
                        ).mHistoryQ7
                    )
                )

                sheet.addCell(
                    Label(
                        13,
                        x,
                        examDataList.get(item).mHistoryQ6 + examDataList.get(item).mHistoryQ11
                    )
                )
                //  sheet.addCell(Label(11, x, examDataList.get(item).mHistoryQ12))

                sheet.addCell(Label(14, x, examDataList.get(item).mdistanceVisionWithoutR))
                sheet.addCell(Label(15, x, examDataList.get(item).mdistanceVisionWithoutL))

                sheet.addCell(Label(16, x, examDataList.get(item).mNearWithOutGR))
                sheet.addCell(Label(17, x, examDataList.get(item).mNearWithOutGL))

                sheet.addCell(Label(18, x, examDataList.get(item).mdistanceGlassR))
                sheet.addCell(Label(19, x, examDataList.get(item).mdistanceGlassL))

                sheet.addCell(Label(20, x, examDataList.get(item).mNearWithGR))
                sheet.addCell(Label(21, x, examDataList.get(item).mNearWithGL))

                sheet.addCell(Label(22, x, examDataList.get(item).mSphereR))
                sheet.addCell(Label(23, x, examDataList.get(item).mCylinderR))
                sheet.addCell(Label(24, x, examDataList.get(item).mAxisR))
                sheet.addCell(Label(25, x, examDataList.get(item).mSphereL))
                sheet.addCell(Label(26, x, examDataList.get(item).mCylinderL))
                sheet.addCell(Label(27, x, examDataList.get(item).mAxisL))

                sheet.addCell(Label(28, x, examDataList.get(item).mDeviation))
                sheet.addCell(Label(29, x, examDataList.get(item).mDevL))

                sheet.addCell(Label(30, x, examDataList.get(item).mSameSize))
                sheet.addCell(Label(31, x, examDataList.get(item).mSameSize))

                sheet.addCell(Label(32, x, examDataList.get(item).mEyelidsR))
                sheet.addCell(Label(33, x, examDataList.get(item).mEyelidsL))

                sheet.addCell(Label(34, x, examDataList.get(item).mEyelashR))
                sheet.addCell(Label(35, x, examDataList.get(item).mEyelashL))

                sheet.addCell(Label(36, x, examDataList.get(item).mWhiteR))
                sheet.addCell(Label(37, x, examDataList.get(item).mWhiteL))

                sheet.addCell(Label(38, x, examDataList.get(item).mBlackR))
                sheet.addCell(Label(39, x, examDataList.get(item).mBlackL))

                sheet.addCell(Label(40, x, examDataList.get(item).mExInjuR))
                sheet.addCell(Label(41, x, examDataList.get(item).mExInjuL))

                sheet.addCell(Label(42, x, examDataList.get(item).mForeignR))
                sheet.addCell(Label(43, x, examDataList.get(item).mForeignL))
                sheet.addCell(Label(44, x, examDataList.get(item).mPupilR))
                sheet.addCell(Label(45, x, examDataList.get(item).mPupilL))


                sheet.addCell(Label(46, x, examDataList.get(item).mSpAuto_R))
                sheet.addCell(Label(47, x, examDataList.get(item).mCyAuto_R))
                sheet.addCell(Label(48, x, examDataList.get(item).mAxisAuto_R))
                sheet.addCell(Label(49, x, examDataList.get(item).mVisionR))
                sheet.addCell(Label(50, x, examDataList.get(item).mAddR))
                sheet.addCell(Label(51, x, examDataList.get(item).mNVR))

                sheet.addCell(Label(52, x, examDataList.get(item).mSpAuto_L))
                sheet.addCell(Label(53, x, examDataList.get(item).mCyAuto_L))
                sheet.addCell(Label(54, x, examDataList.get(item).mAxisAuto_L))
                sheet.addCell(Label(55, x, examDataList.get(item).mVisionL))
                sheet.addCell(Label(56, x, examDataList.get(item).mAddL))
                sheet.addCell(Label(57, x, examDataList.get(item).mNVL))



                sheet.addCell(Label(58, x, examDataList.get(item).mHistoryQ8))
                sheet.addCell(Label(59, x, examDataList.get(item).mHistoryQ12))
                //  sheet.addCell(Label(43, x, patientList.get(0).mEmail))


            }


            try {


            } catch (e: RowsExceededException) {
                // Log.e("xxxxxxxxxxxxxxxxxx", "" + e)
                e.printStackTrace()
            } catch (e: WriteException) {
                //  Log.e("xxxxxxxxxxxxxxxxxx", "" + e)
                e.printStackTrace()
            }
            workbook.write()
            try {
                workbook.close()
            } catch (e: WriteException) {
                //  Log.e("xxxxxxxxxxxxxxxxxx", "" + e)
                e.printStackTrace()
            }
            //createExcel(excelSheet);
        } catch (e: IOException) {
            // Log.e("xxxxxxxxxxxxxxxxxx", "" + e)
            e.printStackTrace()
        }
        showAlertDialog("Please check Android/data/com.digidrishtri.visionguardian/files")
    }


    private fun showAlertDialog(msg: String) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Data export successfully completed")
        alertDialog.setMessage(msg)
        alertDialog.setPositiveButton(
            "OK"
        ) { _, _ ->

        }

        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
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

    private fun dateFilter() {

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.datefilter_dialog, null)
        //AlertDialogBuilder
        val mBuilder = android.app.AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Select Date")
        //show dialog
        val mAlertDialog = mBuilder.show()
        //login button click of custom layout
        var cal = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd/MM/yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                mDialogView.dialog_enddate.text = sdf.format(cal.time)

            }
        val dateSetListener_start =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd/MM/yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                mDialogView.dialog_start.text = sdf.format(cal.time)

            }
        mDialogView.dialog_enddate.setOnClickListener {
            DatePickerDialog(
                this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        mDialogView.dialog_start.setOnClickListener {
            DatePickerDialog(
                this, dateSetListener_start,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        mDialogView.dialog_import.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
            val startDate = mDialogView.dialog_start.text.toString()
            val endDate = mDialogView.dialog_enddate.text.toString()
            FilterDate(startDate, endDate)


        }
        mDialogView.import_all.setOnClickListener {
            val examDataList = myDatabase!!.examDao()!!.patientExam as ArrayList<ExaminationData>
            mAlertDialog.dismiss()
            createExcelSheet(examDataList)
            // createExelNew(examDataList)

        }
        //cancel button click of custom layout

    }


    fun FilterDate(startDate: String, endDate: String) {
        val datewise =
            myDatabase!!.examDao()!!.collectItems(startDate, endDate) as ArrayList<ExaminationData>
        createExcelSheet(datewise)
        // createExelNew(datewise)
        for (i in datewise.indices) {
            // Log.e("dddddddddddddddd",""+datewise.get(i).mDateOfExam)
        }

    }

    fun createExelNew(examDataList: ArrayList<ExaminationData>) {
        val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        var filename = "export$currentDate.xls"

        //  var path = getExternalFilesDir(null)   //get file directory for this package
        // var newPath=Environment.getExternalStoragePublicDirectory()
        //(Android/data/.../files | ... is your app package)
        var fileOut: File? = null
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            val path =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/Vision Guardian/")
            if (!path.exists()) {
                path.mkdirs()
            }
            Log.e("ppppp", "" + path)
            fileOut = File(path, filename)
        }
//create fileOut object

//delete any file object with path and filename that already exists
        fileOut!!.delete()

//create a new file
        fileOut!!.createNewFile()

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
            // val examDataList = myDatabase!!.examDao()!!.patientExam as ArrayList<ExaminationData>

            sheet.addCell(Label(0, 0, "Sr. No", cellFormats))
            sheet.addCell(Label(1, 0, "Name", cellFormats))
            sheet.addCell(Label(2, 0, "Age", cellFormats))
            sheet.addCell(Label(3, 0, "Sex", cellFormats))
            sheet.addCell(Label(4, 0, "Date Of Visit", cellFormats))
            sheet.addCell(Label(5, 0, "Mobile", cellFormats))
            sheet.addCell(Label(6, 0, "Adhar", cellFormats))

            sheet.addCell(Label(7, 0, "Complaint", cellFormats))
            sheet.mergeCells(7, 0, 10, 0)
            sheet.addCell(Label(7, 1, "Blurry Vision", cellFormats))
            sheet.addCell(Label(8, 1, "Difficulty in Reading", cellFormats))
            sheet.addCell(Label(9, 1, "Uncomfortable Eyes", cellFormats))
            sheet.addCell(Label(10, 1, "Other", cellFormats))





            sheet.addCell(Label(11, 0, "Past Ocular history", cellFormats))
            sheet.mergeCells(11, 0, 12, 0)
            sheet.addCell(Label(11, 1, "Difficulty with glass", cellFormats))
            sheet.addCell(Label(12, 1, "Past History", cellFormats))

            sheet.addCell(Label(13, 0, "Systemic History", cellFormats))


            sheet.addCell(Label(14, 0, "Distant vision without glasses", cellFormats))
            sheet.mergeCells(14, 0, 15, 0)
            sheet.addCell(Label(14, 1, "Right eye", cellFormats))
            sheet.addCell(Label(15, 1, "Left eye", cellFormats))

            sheet.addCell(Label(16, 0, "Near vision without glasses", cellFormats))
            sheet.mergeCells(16, 0, 17, 0)
            sheet.addCell(Label(16, 1, "Right eye", cellFormats))
            sheet.addCell(Label(17, 1, "Left eye", cellFormats))

            sheet.addCell(Label(18, 0, "Distant vision with glasses", cellFormats))
            sheet.mergeCells(18, 0, 19, 0)
            sheet.addCell(Label(18, 1, "Right eye", cellFormats))
            sheet.addCell(Label(19, 1, "Left eye", cellFormats))

            sheet.addCell(Label(20, 0, "Near vision with glasses", cellFormats))
            sheet.mergeCells(20, 0, 21, 0)
            sheet.addCell(Label(20, 1, "Right eye", cellFormats))
            sheet.addCell(Label(21, 1, "Left eye", cellFormats))


            sheet.addCell(Label(22, 0, "click checkrefraction", cellFormats))
            sheet.mergeCells(22, 0, 27, 0)
            sheet.addCell(Label(22, 1, "Right eye", cellFormats))
            sheet.mergeCells(22, 1, 24, 1)
            sheet.addCell(Label(25, 1, "Left eye", cellFormats))
            sheet.mergeCells(25, 1, 27, 1)

            sheet.addCell(Label(22, 2, "Sphere", cellFormats))
            sheet.addCell(Label(23, 2, "Cylinder", cellFormats))
            sheet.addCell(Label(24, 2, "Axis", cellFormats))

            sheet.addCell(Label(25, 2, "Sphere", cellFormats))
            sheet.addCell(Label(26, 2, "Cylinder", cellFormats))
            sheet.addCell(Label(27, 2, "Axis", cellFormats))






            sheet.addCell(Label(28, 0, "Deviation of eyes", cellFormats))
            sheet.mergeCells(26, 0, 27, 0)
            sheet.addCell(Label(28, 1, "Right eye", cellFormats))
            sheet.addCell(Label(29, 1, "Left eye", cellFormats))

            sheet.addCell(Label(30, 0, "Size of eye", cellFormats))
            sheet.mergeCells(28, 0, 29, 0)
            sheet.addCell(Label(30, 1, "Right eye", cellFormats))
            sheet.addCell(Label(31, 1, "Left eye", cellFormats))

            sheet.addCell(Label(32, 0, "Eyelids", cellFormats))
            sheet.mergeCells(30, 0, 31, 0)
            sheet.addCell(Label(32, 1, "Right eye", cellFormats))
            sheet.addCell(Label(33, 1, "Left eye", cellFormats))

            sheet.addCell(Label(34, 0, "Eyelashes", cellFormats))
            sheet.mergeCells(32, 0, 33, 0)
            sheet.addCell(Label(34, 1, "Right eye", cellFormats))
            sheet.addCell(Label(35, 1, "Left eye", cellFormats))

            sheet.addCell(Label(36, 0, "White of the eyes", cellFormats))
            sheet.mergeCells(34, 0, 35, 0)
            sheet.addCell(Label(36, 1, "Right eye", cellFormats))
            sheet.addCell(Label(37, 1, "Left eye", cellFormats))

            sheet.addCell(Label(38, 0, "Black of the eyes", cellFormats))
            sheet.mergeCells(36, 0, 37, 0)
            sheet.addCell(Label(38, 1, "Right eye", cellFormats))
            sheet.addCell(Label(39, 1, "Left eye", cellFormats))

            sheet.addCell(Label(40, 0, "External injury", cellFormats))
            sheet.mergeCells(38, 0, 39, 0)
            sheet.addCell(Label(40, 1, "Right eye", cellFormats))
            sheet.addCell(Label(41, 1, "Left eye", cellFormats))

            sheet.addCell(Label(42, 0, "Foreign body", cellFormats))
            sheet.mergeCells(42, 0, 43, 0)
            sheet.addCell(Label(42, 1, "Right eye", cellFormats))
            sheet.addCell(Label(43, 1, "Left eye", cellFormats))

            sheet.addCell(Label(44, 0, "Pupil", cellFormats))
            sheet.mergeCells(44, 0, 45, 0)
            sheet.addCell(Label(44, 1, "Right eye", cellFormats))
            sheet.addCell(Label(45, 1, "Left eye", cellFormats))


            // sheet.addCell(Label(42, 0, "", cellFormats))
            sheet.addCell(Label(46, 0, "Subjective Refraction", cellFormats))
            sheet.mergeCells(46, 0, 57, 0)
            sheet.addCell(Label(46, 1, "Right Eye", cellFormats))
            sheet.mergeCells(46, 1, 51, 1)
            sheet.addCell(Label(46, 2, "Sphere", cellFormats))
            sheet.addCell(Label(47, 2, "Cylinder", cellFormats))
            sheet.addCell(Label(48, 2, "Axis", cellFormats))
            sheet.addCell(Label(49, 2, "Vision", cellFormats))
            sheet.addCell(Label(50, 2, "Add", cellFormats))
            sheet.addCell(Label(51, 2, "NV", cellFormats))

            sheet.addCell(Label(52, 1, "Left Eye", cellFormats))
            sheet.mergeCells(52, 1, 57, 1)
            sheet.addCell(Label(52, 2, "Sphere", cellFormats))
            sheet.addCell(Label(53, 2, "Cylinder", cellFormats))
            sheet.addCell(Label(54, 2, "Axis", cellFormats))
            sheet.addCell(Label(55, 2, "Vision", cellFormats))
            sheet.addCell(Label(56, 2, "Add", cellFormats))
            sheet.addCell(Label(57, 2, "NV", cellFormats))




            sheet.addCell(Label(58, 0, "Refer", cellFormats))
            sheet.addCell(Label(59, 0, "Note", cellFormats))


            for (item in examDataList.indices) {
                val x = item + 3
                val sr = item + 1
                val pID = examDataList.get(item).mPatientID
                val patientList = myDatabase!!.dao()!!.loadSingle(pID) as ArrayList<Patient>

                sheet.addCell(
                    Label(
                        0,
                        x,
                        visionName.toUpperCase() + userName.toUpperCase() + "-" + sr.toString()
                    )
                )
                sheet.addCell(
                    Label(
                        1,
                        x,
                        patientList.get(0).mFirstName + " " + patientList.get(0).mLastName
                    )
                )
                sheet.addCell(Label(2, x, patientList.get(0).mAge))
                sheet.addCell(Label(3, x, patientList.get(0).mGender))
                sheet.addCell(Label(4, x, examDataList.get(item).mDateOfExam))
                sheet.addCell(Label(5, x, patientList.get(0).mMobile))
                sheet.addCell(Label(6, x, patientList.get(0).mEmail))
                sheet.addCell(Label(7, x, examDataList.get(item).mHistoryQ1))






                sheet.addCell(Label(8, x, examDataList.get(item).mHistoryQ2))
                sheet.addCell(
                    Label(
                        9,
                        x,
                        examDataList.get(item).mHistoryQ3 + examDataList.get(item).mHistoryQ5
                    )
                )
                sheet.addCell(Label(10, x, examDataList.get(item).mHistoryQ4))


                //sheet.addCell(Label(8, x,examDataList.get(item).mHistoryQ3 +"-"+  +"Other - " +examDataList.get(item).mHistoryQ4))

                sheet.addCell(Label(11, x, examDataList.get(item).mHistoryQ9))
                sheet.addCell(
                    Label(
                        12, x, examDataList.get(item).mHistoryQ10 + examDataList.get(
                            item
                        ).mHistoryQ7
                    )
                )

                sheet.addCell(
                    Label(
                        13,
                        x,
                        examDataList.get(item).mHistoryQ6 + examDataList.get(item).mHistoryQ11
                    )
                )
                //  sheet.addCell(Label(11, x, examDataList.get(item).mHistoryQ12))

                sheet.addCell(Label(14, x, examDataList.get(item).mdistanceVisionWithoutR))
                sheet.addCell(Label(15, x, examDataList.get(item).mdistanceVisionWithoutL))

                sheet.addCell(Label(16, x, examDataList.get(item).mNearWithOutGR))
                sheet.addCell(Label(17, x, examDataList.get(item).mNearWithOutGL))

                sheet.addCell(Label(18, x, examDataList.get(item).mdistanceGlassR))
                sheet.addCell(Label(19, x, examDataList.get(item).mdistanceGlassL))

                sheet.addCell(Label(20, x, examDataList.get(item).mNearWithGR))
                sheet.addCell(Label(21, x, examDataList.get(item).mNearWithGL))

                sheet.addCell(Label(22, x, examDataList.get(item).mSphereR))
                sheet.addCell(Label(23, x, examDataList.get(item).mCylinderR))
                sheet.addCell(Label(24, x, examDataList.get(item).mAxisR))
                sheet.addCell(Label(25, x, examDataList.get(item).mSphereL))
                sheet.addCell(Label(26, x, examDataList.get(item).mCylinderL))
                sheet.addCell(Label(27, x, examDataList.get(item).mAxisL))

                sheet.addCell(Label(28, x, examDataList.get(item).mDeviation))
                sheet.addCell(Label(29, x, examDataList.get(item).mDevL))

                sheet.addCell(Label(30, x, examDataList.get(item).mSameSize))
                sheet.addCell(Label(31, x, examDataList.get(item).mSameSize))

                sheet.addCell(Label(32, x, examDataList.get(item).mEyelidsR))
                sheet.addCell(Label(33, x, examDataList.get(item).mEyelidsL))

                sheet.addCell(Label(34, x, examDataList.get(item).mEyelashR))
                sheet.addCell(Label(35, x, examDataList.get(item).mEyelashL))

                sheet.addCell(Label(36, x, examDataList.get(item).mWhiteR))
                sheet.addCell(Label(37, x, examDataList.get(item).mWhiteL))

                sheet.addCell(Label(38, x, examDataList.get(item).mBlackR))
                sheet.addCell(Label(39, x, examDataList.get(item).mBlackL))

                sheet.addCell(Label(40, x, examDataList.get(item).mExInjuR))
                sheet.addCell(Label(41, x, examDataList.get(item).mExInjuL))

                sheet.addCell(Label(42, x, examDataList.get(item).mForeignR))
                sheet.addCell(Label(43, x, examDataList.get(item).mForeignL))
                sheet.addCell(Label(44, x, examDataList.get(item).mPupilR))
                sheet.addCell(Label(45, x, examDataList.get(item).mPupilL))


                sheet.addCell(Label(46, x, examDataList.get(item).mSpAuto_R))
                sheet.addCell(Label(47, x, examDataList.get(item).mCyAuto_R))
                sheet.addCell(Label(48, x, examDataList.get(item).mAxisAuto_R))
                sheet.addCell(Label(49, x, examDataList.get(item).mVisionR))
                sheet.addCell(Label(50, x, examDataList.get(item).mAddR))
                sheet.addCell(Label(51, x, examDataList.get(item).mNVR))

                sheet.addCell(Label(52, x, examDataList.get(item).mSpAuto_L))
                sheet.addCell(Label(53, x, examDataList.get(item).mCyAuto_L))
                sheet.addCell(Label(54, x, examDataList.get(item).mAxisAuto_L))
                sheet.addCell(Label(55, x, examDataList.get(item).mVisionL))
                sheet.addCell(Label(56, x, examDataList.get(item).mAddL))
                sheet.addCell(Label(57, x, examDataList.get(item).mNVL))








                sheet.addCell(Label(58, x, examDataList.get(item).mHistoryQ8))
                sheet.addCell(Label(59, x, examDataList.get(item).mHistoryQ12))
                //  sheet.addCell(Label(43, x, patientList.get(0).mEmail))


            }


            try {


            } catch (e: RowsExceededException) {
                // Log.e("xxxxxxxxxxxxxxxxxx", "" + e)
                e.printStackTrace()
            } catch (e: WriteException) {
                //  Log.e("xxxxxxxxxxxxxxxxxx", "" + e)
                e.printStackTrace()
            }
            workbook.write()
            try {
                workbook.close()
            } catch (e: WriteException) {
                //  Log.e("xxxxxxxxxxxxxxxxxx", "" + e)
                e.printStackTrace()
            }
            //createExcel(excelSheet);
        } catch (e: IOException) {
            // Log.e("xxxxxxxxxxxxxxxxxx", "" + e)
            e.printStackTrace()
        }
        showAlertDialog("Please check Documents/Vision Guardian folder")
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
                        //  Toast.makeText(this,msg, Toast.LENGTH_LONG).show()
                        myDatabase!!.examDao()!!.updateExam("updated", examID)
                        // onBackPressed()
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