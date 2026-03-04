package com.example.recyclerview.recycler

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R

class MiVista(miFila : View) : RecyclerView.ViewHolder(miFila) {
    var txtTitulo = miFila.findViewById<TextView>(R.id.txtTitulo)
    var txtHexadecimal = miFila.findViewById<TextView>(R.id.txtHexadecimal)
    var fila = miFila.findViewById<LinearLayout>(R.id.fila)
}