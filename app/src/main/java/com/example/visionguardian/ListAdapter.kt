package com.example.visionguardian

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.visionguardian.db.Patient


class ListAdapter(val context: Context, val array: ArrayList<Patient>) : RecyclerView.Adapter<ListAdapter.PaitentHolder>(){

    class PaitentHolder(view: View) :RecyclerView.ViewHolder(view) {
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
        holder.name.text=array.get(position).mFirstName+" "+array.get(position).mLastName
        holder.age.text=array.get(position).mAge+"/"+array.get(position).mGender
        holder.dovisit.text="Date Of Visit :- "+array.get(position).mDoVisit
        holder.mobile.text="Mobile no :- "+array.get(position).mMobile

        holder.card.setOnClickListener {

            val intent = Intent(context,Examination::class.java)
            intent.putExtra("patientID",array.get(position).patientId)
            intent.putExtra("patientName",array.get(position).mFirstName)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
       return array.size
    }
}