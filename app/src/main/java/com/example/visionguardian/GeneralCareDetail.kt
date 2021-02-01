package com.example.visionguardian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class GeneralCareDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val stringGK = getIntent().getStringExtra("GKdata")
        if (stringGK=="Hygiene and eye care")setContentView(R.layout.view_hygiene)
        else if (stringGK=="Eat healthy for good vision")  setContentView(R.layout.view_eat_healthy)
        else if (stringGK=="Caring your spectacle") setContentView(R.layout.view_caring)
        else if (stringGK=="Instillation of eye drops") setContentView(R.layout.view_instillation)
        else if (stringGK=="Postoperative care")setContentView(R.layout.view_postoperative)
        else if (stringGK=="First aid") setContentView(R.layout.view_firstaid)
        else if (stringGK=="Eye care solutions of digital eye strain") setContentView(R.layout.view_eyecare)
        else if (stringGK=="Contact lens care") setContentView(R.layout.view_contactlens)
       // else if (stringGK=="Regular physician check-up after 40 years") setContentView(R.layout.view_regularphysician)











       // "Hygiene and eye care"
       // "Eat healthy for good vision"
        //"Caring your spectacle"
       // "Instillation of eye drops"
       // "Postoperative care"
       // "First aid"
       // "Eye care solutions of digital eye strain"
      //  "Contact lens care"
       // "Regular physician check-up after 40 years"
    }
}