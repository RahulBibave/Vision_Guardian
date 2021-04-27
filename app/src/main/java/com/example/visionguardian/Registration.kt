
package com.example.visionguardian

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.visionguardian.db.MyDatabase
import com.example.visionguardian.db.Patient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_examination.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_registration.back_arrow
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val PERMISSION_REQUEST = 10
class Registration : AppCompatActivity() {


    var myDatabase: MyDatabase? = null
    var gender="Male"
    var currentYear=0
    var dobYear=0
    var currentAge=0

    lateinit var mFusedLocationClient: FusedLocationProviderClient

    private var permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        setUpDB()
        loadLocate()
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        back_arrow.setOnClickListener { finish() }

        val sharedPreferences = getSharedPreferences("MySharedPrefCamp", MODE_PRIVATE)
        val city= sharedPreferences.getString("city", "")
        edt_city.setText(city)
        val district= sharedPreferences.getString("district", "")
        txt_dist.setText(district)
        val state= sharedPreferences.getString("state", "")
        text_state.setText(state)
        val pin= sharedPreferences.getString("pincode", "")
        edt_pin.setText(pin)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(permissions)) {

                mFusedLocationClient.lastLocation.addOnCompleteListener { task ->
                    val location = task.result
                    if (location == null) {
                        // requestNewLocationData()
                    } else {

                        Log.e("aaaaaaaaaaa",""+location.latitude.toString() )
                        Log.e("aaaaaaaaaaa",""+location.longitude.toString())
                        GetAddress(location.latitude.toString() ,  location.longitude.toString())

                    }
                }


                // getLocation()
            } else {
                requestPermissions(permissions, PERMISSION_REQUEST)
            }
        } else {

            // enableView()
        }

        txt_dob.setOnClickListener {
            val c= Calendar.getInstance()
            val year= c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            var dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, mYear,mMonth , mDay ->
                val mmMonth = mMonth+1
                val date = "$mDay/$mmMonth/$mYear"
                dobYear=mYear
                txt_dob.text = date
            },year,month,day)
            dpd.show()
        }
        txt_dob.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentAge=currentYear-dobYear

                text_age1.setText(""+currentAge)

            }
        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            currentYear= current.year
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            var answer: String =  current.format(formatter)
            txt_dov.text=answer
        } else {
            var date = Date()
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            val answer: String = formatter.format(date)
            txt_dov.text=answer
        }






        radioGender.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio_langange: RadioButton = findViewById(checkedId)
                    gender=radio_langange.text.toString()
                }
        )

        btn_add_patiant.setOnClickListener {
            if (Validate()){
                val name = txt_name.text.toString().trim()

                val middle = middle_name.text.toString().trim()
                val last = last_name.text.toString().trim()

                val mobile = text_mobile.text.toString().trim()
                val dovisit = txt_dov.text.toString().trim()
                val dob = txt_dob.text.toString().trim()
                val age = text_age1.text.toString().trim()
                val email = text_Email.text.toString().trim()
                val address_line = text_Addresss.text.toString().trim()
                val city=edt_city.text.toString().trim()
                val dist=txt_dist.text.toString().trim()
                val state=text_state.text.toString().trim()
                val pin=edt_pin.text.toString().trim()

                val address= "$address_line,$city,$dist,$state,$pin"





                val paitent = Patient(name, last, middle, dovisit, dob, age, gender, mobile, email, address)
                myDatabase!!.dao()!!.patientInsertion(paitent)
                finish()
            }




        }


    }

    private fun setUpDB() {
        myDatabase = Room.databaseBuilder(this, MyDatabase::class.java, "StudentDB")
            .allowMainThreadQueries().build()
    }

    private fun Validate(): Boolean {
        if (!hasText(txt_name,"Enter First Name")) return false
        if (!hasText(last_name,"Enter Last Name")) return false
        if (!hasText(text_mobile,"Enter Mobile Number")) return false
        if (!hasText(text_age1,"Enter Age")) return false
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



    private fun checkPermission(permissionArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionArray.indices) {
            if (checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        return allSuccess
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            var allSuccess = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false
                    val requestAgain =
                            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                                    permissions[i]
                            )
                    if (requestAgain) {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                                this,
                                "Go to settings and enable the permission",
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }
    }



    fun GetAddress(latitude: String, longitude: String){
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(this, Locale.getDefault())

        addresses = geocoder.getFromLocation(
                latitude.toDouble(),
                longitude.toDouble(),
                1
        ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        val address =
                addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        val city = addresses[0].locality
        val state = addresses[0].adminArea
        val country = addresses[0].countryName
        val postalCode = addresses[0].postalCode
        txt_dist.setText(city)
        text_state.setText(state)
        edt_pin.setText(postalCode)
       // val knownName = addresses[0].getFeatureName() // Only if available else return NULL

        Log.e("sssssssssssssssss", "" + address + "   " + city)
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

}