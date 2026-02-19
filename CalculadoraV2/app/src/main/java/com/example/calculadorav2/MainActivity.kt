package com.example.calculadorav2

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculadorav2.databinding.ActivityMainBinding
import com.example.calculadorav2.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val myViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        configurarBotones()

        myViewModel.datos.observe(this) {
            binding.txtHistorial.text = it.historial
            binding.txtResultado.text = it.num2
            if (it.error){
                mostrarSnackbar(it.mensajeError)
            }
        }

    }

    fun configurarBotones(){
        val btnNum = listOf(
            binding.btn0,
            binding.btn1,
            binding.btn2,
            binding.btn3,
            binding.btn4,
            binding.btn5,
            binding.btn6,
            binding.btn7,
            binding.btn8,
            binding.btn9
        )

        val btnOperaciones = listOf(
            binding.btnSumar,
            binding.btnRestar,
            binding.btnMultiplicar,
            binding.btnDividir,
        )

        binding.btnLimpiar.setOnClickListener {
            myViewModel.limpiar()
        }

        btnNum.forEach { boton ->
            boton.setOnClickListener {
                myViewModel.anyadirTexto(boton.text.toString())
            }
        }

        btnOperaciones.forEach { boton ->
            boton.setOnClickListener {
                myViewModel.mostrarHistorial(boton.text.toString())
            }
        }

        binding.btnCalcular.setOnClickListener {
            myViewModel.calcular()
        }
    }

    fun mostrarSnackbar(mensaje: String){
        Snackbar.make(
            binding.root,
            mensaje,
            Snackbar.LENGTH_LONG)
            .setBackgroundTint(Color.RED)
            .setTextColor(Color.WHITE)
            .show()
    }

    
}