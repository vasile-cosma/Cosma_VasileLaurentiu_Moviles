package com.example.recyclerview.recycler

import android.graphics.Color
import android.graphics.Color.parseColor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.model.Datos

class MiAdaptador(var misDatos: Datos) : RecyclerView.Adapter<MiVista>() {
    var posicionSeleccionada = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiVista {
        var miVista = LayoutInflater.from(parent.context).inflate(R.layout.my_row, parent, false)
        return MiVista(miVista)
    }

    override fun onBindViewHolder(holder: MiVista, position: Int) {
        holder.txtTitulo.text = misDatos.colores[position].nombre
        holder.txtHexadecimal.text = misDatos.colores[position].hexadecimal
        holder.fila.setBackgroundColor(misDatos.colores[position].hexadecimal.toColorInt())

        if (position == posicionSeleccionada){
            holder.txtTitulo.setTextColor(misDatos.colores[position].hexadecimal.toColorInt())
            holder.txtHexadecimal.setTextColor(misDatos.colores[position].hexadecimal.toColorInt())
            holder.fila.setBackgroundColor(Color.WHITE)
        } else {
            holder.txtTitulo.setTextColor(Color.BLACK)
            holder.txtHexadecimal.setTextColor(Color.BLACK)
            holder.fila.setBackgroundColor(misDatos.colores[position].hexadecimal.toColorInt())
        }

        holder.fila.setOnClickListener {
            notifyItemChanged(posicionSeleccionada)
            posicionSeleccionada = position
            notifyItemChanged(posicionSeleccionada)
        }
    }

    override fun getItemCount(): Int {
        return misDatos.colores.size
    }

}