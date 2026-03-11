package com.example.apiperros.recycler

import android.media.Image
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.apiperros.R

class MiVista(fila : View) : RecyclerView.ViewHolder(fila) {
    var imagen = fila.findViewById<ImageView>(R.id.imageView)
}