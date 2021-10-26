package com.example.visionguardian

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.demo.*
import kotlinx.android.synthetic.main.setcamp_dialog.view.*
import java.util.*


class LandingActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    var city = ""
    var district = ""
    var state = ""
    var pin = ""
    var loginDate = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

        loadLocate()
        //setContentView(R.layout.demo_tab)
        setContentView(R.layout.demo)

        val header = nav_view.getHeaderView(0)
        val txtname = header.findViewById<TextView>(R.id.nav_header_textView)
        // loadLocate()
        val sharedPreferencesData = getSharedPreferences("MySharedPrefCamp", MODE_PRIVATE)
        val sharedPreferencesLogin = getSharedPreferences("MySharedPrefLogin", MODE_PRIVATE)
        loginDate = sharedPreferencesLogin.getString("loginDate", "").toString()
        city = sharedPreferencesData.getString("city", "").toString()
        district = sharedPreferencesData.getString("district", "").toString()
        state = sharedPreferencesData.getString("state", "").toString()
        pin = sharedPreferencesData.getString("pincode", "").toString()

        /* var dateStr = ""
         var dateStrMonth = ""
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             val current = LocalDateTime.now()
             val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
             val formatterM = DateTimeFormatter.ofPattern("MM")
             dateStrMonth = current.format(formatterM)
             var answer: String = current.format(formatter)
             dateStr = answer
         } else {
             var date = Date()
             val formatter = SimpleDateFormat("dd/MM/yyyy")
             val form = SimpleDateFormat("MM")
             dateStrMonth = form.format(date)
             val answer: String = formatter.format(date)
             dateStr = answer
         }

         //  Log.e("aaaaaaaaaaa",""+dateStrMonth)

         val sdf = SimpleDateFormat("dd/MM/yyyy")
         val date1: Date = sdf.parse(dateStr)

         //loginDate add for every month code
         val date2: Date = sdf.parse(loginDate)
         val diff: Long = date1.getTime() - date2.getTime()
         val seconds = diff / 1000
         val minutes = seconds / 60
         val hours = minutes / 60
         val days = hours / 24*/

        //Log.e("sssssssssss",""+days)

        /*toggle= ActionBarDrawerToggle(this,rootLayouts,toolbar,R.string.open,R.string.close)
        rootLayouts.addDrawerListener(toggle)
        toggle.syncState()
        toggle.isDrawerIndicatorEnabled = true
        supportActionBar?.setDisplayHomeAsUpEnabled(true)*/

        // val sharedPreferences = getSharedPreferences("MySharedPrefLogin", MODE_PRIVATE)
        var Name = sharedPreferencesLogin.getString("name", "defaultName")
        txtname.text = Name

        if (intent.getBooleanExtra("EXIT", false)) {
            // finish();
        }
        card_examin.setOnClickListener {
            val intent = Intent(this, ListOfPatent::class.java)
            startActivity(intent)

        }

        //

        card_ref.setOnClickListener {
            val intent = Intent(this, Referral::class.java)
            startActivity(intent)
        }
        card_reg.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
            /* val userName = Name!!.take(2)
             if (days < 1) {
                 val intent = Intent(this, Registration::class.java)
                 startActivity(intent)
                 finish()
             } else {
                 val mDialogView = LayoutInflater.from(this).inflate(R.layout.enter_code_dialog, null)
                 val mBuilder = AlertDialog.Builder(this)
                         .setView(mDialogView)
                         .setTitle("Trial period expired")
                 val mAlertDialog = mBuilder.show()

                 mDialogView.dialogSubmitBtn.setOnClickListener {
                     //dismiss dialog
                     mAlertDialog.dismiss()
                     val code = mDialogView.dialogCode.text.toString()
                     if (code == userName + dateStrMonth + "98" || code == userName + dateStrMonth + "80" || code == userName + dateStrMonth + "95") {
                         val myEdit = sharedPreferencesLogin.edit()
                         myEdit.putString("loginDate", "15/10/2021")
                         myEdit.commit()
                         myEdit.apply()
                         val intent = Intent(this, Registration::class.java)
                         startActivity(intent)
                         finish()

                     }


                 }
                 //cancel button click of custom layout
                 *//* mDialogView.dialogCancelBtn.setOnClickListener {
                     mAlertDialog.dismiss()
                 }*//*
            }*/

        }


        card_gc.setOnClickListener {
            val intent = Intent(this, GeneralCare::class.java)
            startActivity(intent)
        }
        card_scheme.setOnClickListener {
            val intent = Intent(this, Scheme::class.java)
            startActivity(intent)
        }
        card_awareness.setOnClickListener {
            val intent = Intent(this, Awareness_List::class.java)
            startActivity(intent)
        }
        navigation()

    }

    private fun setCamp() {
        val sharedPreferencescamp = getSharedPreferences("MySharedPrefCamp", MODE_PRIVATE)
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.setcamp_dialog, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Set Screening Locations")
        //show dialog
        val mAlertDialog = mBuilder.show()
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

    private fun navigation() {
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
            when (it.itemId) {
                R.id.mRegistration -> {
                    val intent = Intent(this, Registration::class.java)
                    startActivity(intent)
                }
                R.id.mExamination -> {
                    val intent = Intent(this, ListOfPatent::class.java)
                    startActivity(intent)
                }
                R.id.mRefer -> {
                    val intent = Intent(this, Referral::class.java)
                    startActivity(intent)
                }
                R.id.mAwarness -> {
                    val intent = Intent(this, Awareness_List::class.java)
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
                R.id.mHelp -> {
                    val intent = Intent(this, ActivityHelp::class.java)
                    startActivity(intent)
                }

                R.id.mCamp -> {
                    setCamp()
                }
                R.id.mSelectLang -> {
                    chooseLang()
                }
                R.id.mLogOut -> {
                    val sharedPreferences = getSharedPreferences("MySharedPrefLogin", MODE_PRIVATE)
                    val myEdit = sharedPreferences.edit()
                    myEdit.putString("username", "")
                    myEdit.putString("id", "")
                    myEdit.putString("name", "")
                    myEdit.putString("visioncenter", "")
                    myEdit.putString("Mobile no", "")
                    myEdit.putString("islogin", "")
                    myEdit.putString("accessToken", "")
                    myEdit.commit()
                    myEdit.apply()
                    var intent: Intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            rootLayouts.closeDrawer(GravityCompat.START)
            true
        }

    }

    private fun chooseLang() {
        val listItems = arrayOf("English", "मराठी")

        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle("Choose Language")
        mBuilder.setSingleChoiceItems(listItems, -1) { dialog, which ->
            if (which == 0) {
                setLocate("en")
                recreate()
            } else if (which == 1) {
                setLocate("mr")
                recreate()
            } else if (which == 2) {
                setLocate("hi")
                recreate()
            }
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()

        mDialog.show()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
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