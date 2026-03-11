package com.example.apiperros

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apiperros.databinding.ActivityMainBinding
import com.example.apiperros.model.Datos
import com.example.apiperros.model.PerrosRespuesta
import com.example.apiperros.recycler.MiAdaptador
import com.example.apiperros.viewmodel.PerrosViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import kotlin.getValue

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var  misDatos: Datos
    private val myViewModel : PerrosViewModel by viewModels()

    private lateinit var miAdaptador : MiAdaptador
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val mLayout = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = mLayout

        binding.btnBuscar.setOnClickListener {
            myViewModel.recuperaFotosPaginacion(binding.txtBuscar.text.toString())
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var finalScroll = false
                if (mLayout.findLastVisibleItemPosition()%10>=9&&
                    mLayout.findLastVisibleItemPosition()/10==(misDatos.paginaActual!!-1)){
                    finalScroll = true
                }
                if (finalScroll && misDatos.paginaActual!! < misDatos.numPaginas!!){
                    Snackbar.make(binding.main, "Si desea recuperar más fotos pulse: ", Snackbar.LENGTH_LONG)
                        .setAction("Cargar más fotos", {
                            myViewModel.scrollFotos()
                        }).show()
                }
            }
        })

        myViewModel.datos.observe(this@MainActivity){
            when(it.status){
                "success" -> {
                    if (it.paginaActual==1){
                        misDatos = it
                        miAdaptador = MiAdaptador(PerrosRespuesta(it.status, it.message))
                        binding.recyclerView.adapter = miAdaptador
                    } else {
                        miAdaptador.notifyItemRangeInserted(it.paginaActual!!*10, it.message!!.size)
                    }
                }
                "error" -> Toast.makeText(this@MainActivity,"No hay fotos de esa raza", Toast.LENGTH_LONG).show()
            }
        }

        /* Intento con state flow... not working
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                myViewModel.datos.collect {
                    when(it.status){
                        "success" -> {
                            if (it.paginaActual==1){
                                misDatos = it
                                miAdaptador = MiAdaptador(PerrosRespuesta(it.status, it.message))
                                binding.recyclerView.adapter = miAdaptador
                            } else {
                                miAdaptador.notifyItemRangeInserted(it.paginaActual!!*10, it.message!!.size)
                            }
                        }
                        "error" -> Toast.makeText(this@MainActivity,"No hay fotos de esa raza", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }*/
    }


}