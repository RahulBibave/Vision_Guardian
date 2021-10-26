package com.example.visionguardian

import android.app.Activity
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

class ReferralListAdapter(
    val context: Context,
    val array: ArrayList<ExaminationData>,
    val myDatabase: MyDatabase
) : RecyclerView.Adapter<ReferralListAdapter.PatientHolder>() {

    class PatientHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.txtName)
        var age: TextView = view.findViewById(R.id.txtAge)
        var dovisit: TextView = view.findViewById(R.id.txtDovisit)
        var mobile: TextView = view.findViewById(R.id.txtMobile)
        var card: LinearLayout = view.findViewById(R.id.linear_view)
        var lay_head: LinearLayout = view.findViewById(R.id.lay_up)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.paitent_list_item, parent, false)
        return PatientHolder(itemView)
    }

    override fun onBindViewHolder(holder: PatientHolder, position: Int) {
        if (array[position].status == "updated") {
            holder.lay_head.setBackgroundResource(R.color.green)
        } else {
            holder.lay_head.setBackgroundResource(R.color.blue)
        }
        val stuData = myDatabase.dao()!!.loadSingle(array[position].mPatientID)
        holder.name.text = stuData[0].mFirstName + " " + stuData[0].mLastName
        holder.age.text = stuData[0].mAge + "/" + stuData[0].mGender
        holder.dovisit.text = stuData[0].mDoVisit
        holder.mobile.text = stuData[0].mMobile


        holder.card.setOnClickListener {

            val intent = Intent(context, ReferralDetails::class.java)
            intent.putExtra("patientID", stuData.get(0).patientId)
            intent.putExtra("ExamID", array.get(position).mExamId)
            context.startActivity(intent)
            (context as Activity).finish()
        }

    }

    override fun getItemCount(): Int {
        return array.size
    }
}