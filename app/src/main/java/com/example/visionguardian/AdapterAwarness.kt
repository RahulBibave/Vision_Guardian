package com.example.visionguardian

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

internal class AdapterAwarness(val context: Context,val array: ArrayList<AwarnessList>) : RecyclerView.Adapter<AdapterAwarness.AwarnessAdapter>() {
    internal inner class AwarnessAdapter(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.textViewName)
        var image: ImageView = view.findViewById(R.id.imageView)
        var card :CardView= view.findViewById(R.id.container_awarness)

    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AwarnessAdapter {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.awarnes_item, parent, false)
        return AwarnessAdapter(itemView)
    }
    override fun onBindViewHolder(holder: AwarnessAdapter, position: Int) {
        holder.title.text=array.get(position).title
        holder.image.setImageResource(array.get(position).image)
        holder.card.setOnClickListener {
            val intent=Intent(context,Awarness::class.java)
            intent.putExtra("data",array.get(position).title)
            context.startActivity(intent)
        }


    }
    override fun getItemCount(): Int {
        return array.size
    }
}