package com.example.visionguardian

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_landing.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.demo.*
import kotlinx.android.synthetic.main.lay_navigation_drawer_header.*
import kotlinx.android.synthetic.main.setcamp_dialog.view.*

class LandingActivity : AppCompatActivity() {
    lateinit var toggle:ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo)
        val heder=nav_view.getHeaderView(0)
        val txtname=heder.findViewById<TextView>(R.id.nav_header_textView)

        /*toggle= ActionBarDrawerToggle(this,rootLayouts,toolbar,R.string.open,R.string.close)
        rootLayouts.addDrawerListener(toggle)
        toggle.syncState()
        toggle.isDrawerIndicatorEnabled = true
        supportActionBar?.setDisplayHomeAsUpEnabled(true)*/

        val sharedPreferences = getSharedPreferences("MySharedPrefLogin", MODE_PRIVATE)
        val Name= sharedPreferences.getString("name", "defaultName")
        txtname.text=Name

        if (getIntent().getBooleanExtra("EXIT", false)) {
           // finish();
        }
        card_examin.setOnClickListener{
            val intent =Intent(this,ListOfPaitent::class.java)
            startActivity(intent)
        }
        card_awareness.setOnClickListener {
            val intent =Intent(this,Awarness_List::class.java)
            startActivity(intent)
        }
        card_ref.setOnClickListener {
            val intent =Intent(this,Referral::class.java)
            startActivity(intent)
        }
        card_reg.setOnClickListener {
            val intent =Intent(this,Registration::class.java)
            startActivity(intent)
        }
        card_gc.setOnClickListener{
            val intent =Intent(this,GeneralCare::class.java)
            startActivity(intent)
        }
        card_scheme.setOnClickListener {
            val intent=Intent(this,Scheme::class.java)
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
            sharedPreferencescamp.edit().clear().commit();
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
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
                //toast("Drawer closed")
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                //toast("Drawer opened")
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                linear_main.setTranslationX(slideOffset * drawerView.getWidth())
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
                    val intent =Intent(this,Registration::class.java)
                    startActivity(intent)
                }
                R.id.mExamination -> {
                    val intent =Intent(this,ListOfPaitent::class.java)
                    startActivity(intent)
                }
                R.id.mRefer -> {
                    val intent =Intent(this,Referral::class.java)
                    startActivity(intent)
                }
                R.id.mAwarness -> {
                    val intent =Intent(this,Awarness_List::class.java)
                    startActivity(intent)
                }
                R.id.mScheme -> {
                    val intent=Intent(this,Scheme::class.java)
                    startActivity(intent)
                }
                R.id.mGk -> {
                    val intent =Intent(this,GeneralCare::class.java)
                    startActivity(intent)
                }

                R.id.mCamp -> {
                    setCamp()
                }
            }
           rootLayouts.closeDrawer(GravityCompat.START)
            true
        }

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

}