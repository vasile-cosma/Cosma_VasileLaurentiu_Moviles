package com.example.apiperros.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apiperros.R
import com.example.apiperros.model.Datos
import com.example.apiperros.model.PerrosRespuesta

class MiAdaptador(var respuesta : PerrosRespuesta) : RecyclerView.Adapter<MiVista>() {
    lateinit var miContexto: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiVista {
        var vista = LayoutInflater.from(parent.context).inflate(R.layout.fila, parent, false)
        miContexto = parent.context
        return MiVista(vista)
    }

    override fun onBindViewHolder( holder: MiVista, position: Int) {
        val urlImagen: String = respuesta.message!![position]

        Glide.with(miContexto)
            .load(urlImagen)
            .into(holder.imagen)

    }

    override fun getItemCount(): Int {
        return respuesta.message!!.size
    }
}