package com.example.visionguardian

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.visionguardian.db.Patient


class ListAdapter(val context: Context, val array: ArrayList<Patient>) :
    RecyclerView.Adapter<ListAdapter.PatentHolder>() {

    class PatentHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.txtName)
        var age: TextView = view.findViewById(R.id.txtAge)
        var dovisit: TextView = view.findViewById(R.id.txtDovisit)
        var mobile: TextView = view.findViewById(R.id.txtMobile)
        var card: LinearLayout = view.findViewById(R.id.linear_view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatentHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.paitent_list_item, parent, false)
        return PatentHolder(itemView)
    }

    override fun onBindViewHolder(holder: PatentHolder, position: Int) {
        holder.name.text = array[position].mFirstName + " " + array[position].mLastName
        holder.age.text = array[position].mAge + "/" + array[position].mGender
        holder.dovisit.text = "Date Of Visit :- " + array[position].mDoVisit
        holder.mobile.text = "Mobile no :- " + array[position].mMobile

        holder.card.setOnClickListener {

            val intent = Intent(context, Examination::class.java)
            intent.putExtra("patientID", array[position].patientId)
            intent.putExtra("patientName", array[position].mFirstName)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return array.size
    }
}