package com.example.visionguardian

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

internal class AdapterGCare(val context: Context, val array: ArrayList<CareList>) :
    RecyclerView.Adapter<AdapterGCare.AdapterCare>() {
    internal inner class AdapterCare(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.textViewNameCare)
        var carecard: CardView = view.findViewById(R.id.container_care)
        var image: ImageView = view.findViewById(R.id.imageViewCare)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCare {
        val itemView = LayoutInflater.from(context).inflate(R.layout.care_item, parent, false)
        return AdapterCare(itemView)
    }

    override fun onBindViewHolder(holder: AdapterCare, position: Int) {
        holder.title.text = array[position].title
        holder.image.setImageResource(array[position].image)
        holder.carecard.setOnClickListener {
            val intent = Intent(context, GeneralCareDetail::class.java)
            intent.putExtra("GKdata", array[position].title)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return array.size
    }

}