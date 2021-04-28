package com.example.visionguardian

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_landing.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.demo.*
import kotlinx.android.synthetic.main.demo.toolbar
import kotlinx.android.synthetic.main.lay_navigation_drawer_header.*
import kotlinx.android.synthetic.main.setcamp_dialog.view.*
import java.util.*


class LandingActivity : AppCompatActivity() {
    lateinit var toggle:ActionBarDrawerToggle
    var city=""
    var district=""
    var state=""
    var pin=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

        loadLocate()
        setContentView(R.layout.demo)

        val heder=nav_view.getHeaderView(0)
        val txtname=heder.findViewById<TextView>(R.id.nav_header_textView)
       // loadLocate()
        val sharedPreferencesData = getSharedPreferences("MySharedPrefCamp", MODE_PRIVATE)
        city= sharedPreferencesData.getString("city", "").toString()
        district= sharedPreferencesData.getString("district", "").toString()
        state= sharedPreferencesData.getString("state", "").toString()
        pin= sharedPreferencesData.getString("pincode", "").toString()

        /*toggle= ActionBarDrawerToggle(this,rootLayouts,toolbar,R.string.open,R.string.close)
        rootLayouts.addDrawerListener(toggle)
        toggle.syncState()
        toggle.isDrawerIndicatorEnabled = true
        supportActionBar?.setDisplayHomeAsUpEnabled(true)*/

        val sharedPreferences = getSharedPreferences("MySharedPrefLogin", MODE_PRIVATE)
        val Name= sharedPreferences.getString("name", "defaultName")
        txtname.text=Name

        if (intent.getBooleanExtra("EXIT", false)) {
           // finish();
        }
        card_examin.setOnClickListener{
            val intent =Intent(this, ListOfPaitent::class.java)
            startActivity(intent)
        }

        //

        card_ref.setOnClickListener {
            val intent =Intent(this, Referral::class.java)
            startActivity(intent)
        }
        card_reg.setOnClickListener {
            val intent =Intent(this, Registration::class.java)
            startActivity(intent)
        }


        card_gc.setOnClickListener{
            val intent =Intent(this, GeneralCare::class.java)
            startActivity(intent)
        }
        card_scheme.setOnClickListener {
            val intent=Intent(this, Scheme::class.java)
            startActivity(intent)
        }
        card_awareness.setOnClickListener {
            val intent =Intent(this, Awarness_List::class.java)
            startActivity(intent)
        }
        navigation()

    }

    fun setCamp(){
        val sharedPreferencescamp = getSharedPreferences("MySharedPrefCamp", MODE_PRIVATE)
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.setcamp_dialog, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Set Screening Locations")
        //show dialog
        val  mAlertDialog = mBuilder.show()
        //login button click of custom layout
        mDialogView.dialogLoginBtn.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
            //get text from EditTexts of custom layout
           /* mDialogView.dialogCity.setText(city)
            mDialogView.dialogDis.setText(district)
            mDialogView.dialogState.setText(state)
            mDialogView.dialogPinCode.setText(pin)*/

            val city = mDialogView.dialogCity.text.toString()
            val district = mDialogView.dialogDis.text.toString()
            val state = mDialogView.dialogState.text.toString()
            val pincode = mDialogView.dialogPinCode.text.toString()
            val myEdit = sharedPreferencescamp.edit()
            myEdit.putString("city", city)
            myEdit.putString("district", district)
            myEdit.putString("state", state)
            myEdit.putString("pincode", pincode)
            myEdit.commit()
            myEdit.apply()
            //set the input text in TextView
          /*  mainInfoTv.setText("Name:"+ name +"\nEmail: "+ email +"\nPassword: "+ password)*/
        }
        //cancel button click of custom layout
        mDialogView.dialogCancelBtn.setOnClickListener {
            sharedPreferencescamp.edit().clear().commit()
            mAlertDialog.dismiss()
        }
    }

    fun navigation() {
        // Initialize the action bar drawer toggle instance
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
                this,
                rootLayouts,
                toolbar,
                R.string.open,
                R.string.close
        ) {

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                linear_main.translationX = slideOffset * drawerView.width
                rootLayouts.bringChildToFront(drawerView)
                rootLayouts.requestLayout()
            }
        }
        drawerToggle.isDrawerIndicatorEnabled = true

        rootLayouts.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        nav_view.setNavigationItemSelectedListener {
            when (it.itemId){
                R.id.mRegistration -> {
                    val intent = Intent(this, Registration::class.java)
                    startActivity(intent)
                }
                R.id.mExamination -> {
                    val intent = Intent(this, ListOfPaitent::class.java)
                    startActivity(intent)
                }
                R.id.mRefer -> {
                    val intent = Intent(this, Referral::class.java)
                    startActivity(intent)
                }
                R.id.mAwarness -> {
                    val intent = Intent(this, Awarness_List::class.java)
                    startActivity(intent)
                }
                R.id.mScheme -> {
                    val intent = Intent(this, Scheme::class.java)
                    startActivity(intent)
                }
                R.id.mGk -> {
                    val intent = Intent(this, GeneralCare::class.java)
                    startActivity(intent)
                }

                R.id.mCamp -> {
                    setCamp()
                }
                R.id.mSelectLang -> {
                    chooseLang()
                }
            }
           rootLayouts.closeDrawer(GravityCompat.START)
            true
        }

    }

    private fun chooseLang() {
        val listItmes = arrayOf( "English","मराठी")

        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle("Choose Language")
        mBuilder.setSingleChoiceItems(listItmes, -1) { dialog, which ->
            if (which == 0) {
                setLocate("en")
                recreate()
            } else if (which == 1) {
                setLocate("hi")
                recreate()
            }
           /* else if (which == 2) {
                setLocate("hi")
                recreate()
            }*/
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()

        mDialog.show()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
             if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
          /*  return when (item.itemId) {
                R.id.mRegistration -> {
                    Toast.makeText(applicationContext, "click on setting", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.mExamination -> {
                    Toast.makeText(applicationContext, "click on setting", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.mCamp -> {
                    setCamp()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }*/

        }


  /*  private fun setLocate(Lang: String) {

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

    }*/

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

   /* fun loadlang(){
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", language)
        editor.apply()
    }*/


}