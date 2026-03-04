package com.example.recyclerview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.databinding.ActivityMainBinding
import com.example.recyclerview.recycler.MiAdaptador
import com.example.recyclerview.viewmodel.ColoresViewModel
import kotlinx.coroutines.launch
import kotlin.getValue

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val myViewModel : ColoresViewModel by viewModels()
    private lateinit var miAdaptador : MiAdaptador

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
        myViewModel.retornarLista()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                myViewModel.datos.collect {
                    miAdaptador = MiAdaptador(it)
                    binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity,
                        LinearLayoutManager.VERTICAL, false)
                    binding.recyclerView.adapter = miAdaptador
                }
            }
        }
    }
}