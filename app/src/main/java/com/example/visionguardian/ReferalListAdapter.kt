package com.example.visionguardian

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.visionguardian.db.ExaminationData
import com.example.visionguardian.db.MyDatabase
import com.example.visionguardian.db.Patient

class ReferalListAdapter(val context: Context, val array: ArrayList<ExaminationData>,val myDatabase: MyDatabase) : RecyclerView.Adapter<ReferalListAdapter.PaitentHolder>(){

    class PaitentHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.txtName)
        var age: TextView = view.findViewById(R.id.txtAge)
        var dovisit: TextView = view.findViewById(R.id.txtDovisit)
        var mobile: TextView = view.findViewById(R.id.txtMobile)
        var card: LinearLayout = view.findViewById(R.id.linear_view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaitentHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.paitent_list_item, parent, false)
        return PaitentHolder(itemView)
    }

    override fun onBindViewHolder(holder: PaitentHolder, position: Int) {
        val stuData =myDatabase.dao()!!.loadSingle(array.get(position).mPatientID)
        holder.name.text=stuData.get(0).mFirstName + " "+stuData.get(0).mLastName
        holder.age.text=stuData.get(0).mAge+"/"+stuData.get(0).mGender
        holder.dovisit.text=stuData.get(0).mDoVisit
        holder.mobile.text=stuData.get(0).mMobile


        holder.card.setOnClickListener {

            val intent = Intent(context,RefferalDetails::class.java)
            intent.putExtra("patientID",stuData.get(0).patientId)
            intent.putExtra("ExamID",array.get(position).mExamId)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return array.size
    }
}