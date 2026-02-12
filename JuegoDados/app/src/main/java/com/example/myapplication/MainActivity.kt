package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var txtSaldo: TextView
    lateinit var saldo: TextView
    lateinit var toggleGroup: MaterialButtonToggleGroup
    lateinit var btnParImpar: MaterialButton
    lateinit var btnMayorMenor: MaterialButton
    lateinit var spinner: Spinner
    lateinit var btnLanzar: MaterialButton
    lateinit var txtApuesta: TextInputEditText
    lateinit var txtDado1: TextView
    lateinit var txtDado2: TextView
    lateinit var imagen: ImageView
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var corrutina: Job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtSaldo = findViewById(R.id.txtSaldo)
        saldo = findViewById(R.id.saldo)
        toggleGroup = findViewById(R.id.toggleButton)
        btnParImpar = findViewById(R.id.btnParImpar)
        btnMayorMenor = findViewById(R.id.btnMayorMenor)
        spinner = findViewById(R.id.spinner)
        btnLanzar = findViewById(R.id.btnLanzar)
        txtApuesta = findViewById(R.id.txtApuesta)
        txtDado1 = findViewById(R.id.txtDado1)
        txtDado2 = findViewById(R.id.txtDado2)
        imagen = findViewById(R.id.imgView)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)

        toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener
            when (checkedId){
                R.id.btnParImpar -> {
                    llenarSpinner(listOf("Par","Impar"))
                }
                R.id.btnMayorMenor -> {
                    llenarSpinner(listOf("Mayor/Igual","Menor"))
                }
            }
        }

        btnLanzar.setOnClickListener {
            val apuesta = txtApuesta.text.toString().toIntOrNull()

            if (saldo.text.toString().toInt() == 0){
                AlertDialog.Builder(this)
                    .setTitle("Jugando a los dados")
                    .setMessage("Estás arruinado. Debes dejar el juego.")
                    .setCancelable(false)
                    .setPositiveButton("Salir del juego") {
                        _, _ -> finish()
                    }
                    .show()
                imagen.setImageResource(R.drawable.bancarrota)
                imagen.visibility = View.VISIBLE
            } else if (apuesta == null || apuesta <= 0) {
                mostrarSnackbar("La apuesta no es válida.")
            } else if (apuesta > saldo.text.toString().toInt()) {
               mostrarSnackbar("La apuesta debe ser menor al saldo disponible.")
            } else if (spinner.adapter == null || spinner.adapter.count == 0){
                mostrarSnackbar("Debe elegir una opción.")
            } else {
                val opcion = spinner.selectedItem.toString()
                corrutina = lifecycleScope.launch {
                    lanzarDados(opcion,apuesta)
                }
            }
        }
    }


    fun llenarSpinner(opciones: List<String>){
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    fun comprobarResultado(opcion: String, apuesta: Int, resultado: Int){
        val saldoInt: Int = saldo.text.toString().toInt()
        when (opcion.uppercase()){
            "PAR" -> {
                if (resultado%2 ==0) {
                    victoria(saldoInt, apuesta)
                } else {
                    derrota(saldoInt, apuesta)
                }
            }
            "IMPAR" -> {
                if (resultado%2 ==0) {
                    derrota(saldoInt, apuesta)
                } else {
                    victoria(saldoInt, apuesta)
                }
            }
            "MAYOR/IGUAL" -> {
                if (resultado>=7) {
                    victoria(saldoInt, apuesta)
                } else {
                    derrota(saldoInt, apuesta)
                }
            }
            "MENOR" -> {
                if (resultado>=7) {
                    derrota(saldoInt, apuesta)
                } else {
                    victoria(saldoInt, apuesta)
                }
            }
        }
    }

    fun victoria(saldoInt: Int, apuesta: Int) {
        saldo.text = (saldoInt + apuesta).toString()
        imagen.setImageResource(R.drawable.ganar_dados)
        imagen.visibility = View.VISIBLE
        corrutina = lifecycleScope.launch {
            preguntaSeguirJugando()
        }
    }

    fun derrota(saldoInt: Int, apuesta: Int) {
        saldo.text = (saldoInt - apuesta).toString()
        imagen.setImageResource(R.drawable.perder_dados)
        imagen.visibility = View.VISIBLE
        corrutina = lifecycleScope.launch {
            preguntaSeguirJugando()
        }
    }

    suspend fun preguntaSeguirJugando(){
        delay(1000)
        AlertDialog.Builder(this)
            .setTitle("Jugando a los dados")
            .setMessage("¿Desea seguir jugando?")
            .setCancelable(false)
            .setPositiveButton("Seguir jugando") { alerta, _ -> alerta.dismiss() }
            .setNegativeButton("Salir del juego") { _, _ -> finish() }
            .show()
    }

    fun mostrarSnackbar(mensaje: String) {
        Snackbar.make(
            coordinatorLayout,
            mensaje,
            Snackbar.LENGTH_LONG)
            .setBackgroundTint(Color.RED)
            .setTextColor(Color.WHITE)
            .show()
    }

    suspend fun lanzarDados(opcion: String, apuesta: Int) {
        Glide.with(this).load(R.drawable.dado_animado).into(imagen)
        imagen.visibility = View.VISIBLE
        delay(3000)
        val dado1 = (1..6).random()
        txtDado1.text = dado1.toString()
        val dado2 = (1..6).random()
        txtDado2.text = dado2.toString()
        val resultado = dado1+dado2
        comprobarResultado(opcion,apuesta, resultado)
    }
}