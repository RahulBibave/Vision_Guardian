package com.example.visionguardian

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

internal class AdapterGCare (val context: Context, val array: ArrayList<CareList>) : RecyclerView.Adapter<AdapterGCare.careAdapter>() {
    internal inner class careAdapter(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.textViewNameCare)
        var carecard: LinearLayout = view.findViewById(R.id.container_gk)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): careAdapter {
        val itemView=LayoutInflater.from(context).inflate(R.layout.care_item,parent,false)
        return careAdapter(itemView)
    }

    override fun onBindViewHolder(holder: careAdapter, position: Int) {
        holder.title.text=array.get(position).title
        holder.carecard.setOnClickListener {
            val intent=Intent(context,GeneralCareDetail::class.java)
            intent.putExtra("GKdata",array.get(position).title)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return array.size
    }

}