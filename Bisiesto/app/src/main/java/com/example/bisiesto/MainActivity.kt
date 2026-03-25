package com.example.bisiesto

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bisiesto.databinding.ActivityMainBinding
import com.example.bisiesto.fragments.FragmentoBisiesto
import com.example.bisiesto.fragments.FragmentoDivisible
import com.example.bisiesto.viewmodel.AppViewmodel
import com.google.android.material.tabs.TabLayout
import kotlin.getValue

class MainActivity : AppCompatActivity() {
    private val miViewModel: AppViewmodel by viewModels()
    private var pestanyaSeleccionada = 0
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.materialToolbar)


        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Bisiesto"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Divisible"))

        binding.btnGenerar.setOnClickListener {
            miViewModel.generarNumero()
        }

        miViewModel.datos.observe(this) {
            datos ->
            binding.numAleatorio.text = datos.numAleatorio.toString()
        }

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pestanyaSeleccionada = tab.position

                if (miViewModel.isNumeroGenerado()){
                    actualizarFragmento()
                } else {
                    Toast.makeText(this@MainActivity, "ERROR: No hay número generado", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        binding.btnGenerar.setOnClickListener {
            miViewModel.generarNumero();
            actualizarFragmento();
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return when (id) {
            R.id.salir -> {finish()
                true}
            else -> { super.onOptionsItemSelected(item)}
        }
    }

    fun actualizarFragmento(){
        miViewModel.reset()
            when (pestanyaSeleccionada){
                0 -> {
                    supportActionBar?.title = "Bisiesto"
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.miFrameLayout, FragmentoBisiesto())
                        .commit()
                }
                1 -> {
                    supportActionBar?.title = "Divisible"
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.miFrameLayout, FragmentoDivisible())
                        .commit()
                }
            }
    }
}