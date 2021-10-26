package com.example.visionguardian

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

internal class AdapterAwareness(val context: Context, val array: ArrayList<AwarenessList>) :
    RecyclerView.Adapter<AdapterAwareness.AwarenessAdapter>() {
    internal inner class AwarenessAdapter(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.textViewName)
        var image: ImageView = view.findViewById(R.id.imageView)
        var card: CardView = view.findViewById(R.id.container_awarness)

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AwarenessAdapter {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.awarnes_item, parent, false)
        return AwarenessAdapter(itemView)
    }

    override fun onBindViewHolder(holder: AwarenessAdapter, position: Int) {
        holder.title.text = array[position].title
        holder.image.setImageResource(array[position].image)
        holder.card.setOnClickListener {
            val intent = Intent(context, Awareness::class.java)
            intent.putExtra("data", array[position].title)
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return array.size
    }
}