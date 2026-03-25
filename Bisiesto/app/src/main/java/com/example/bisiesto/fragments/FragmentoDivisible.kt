package com.example.bisiesto.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.bisiesto.R
import com.example.bisiesto.databinding.FragmentFragmentoDivisibleBinding
import com.example.bisiesto.viewmodel.AppViewmodel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentoDivisible.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentoDivisible : Fragment() {
    private lateinit var binding : FragmentFragmentoDivisibleBinding
    private val viewModel: AppViewmodel by viewModels({requireActivity()})

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFragmentoDivisibleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val checkboxes = setOf(
            binding.checkEntre2,
            binding.checkEntre3,
            binding.checkEntre5,
            binding.checkEntre10,
            binding.checkNoDivisible
        )

        binding.btnValidarDivisibles.setOnClickListener {
            val seleccionados = checkboxes.filter { it.isChecked }

            if (seleccionados.isEmpty()) {
                Toast.makeText(
                    view.context,
                    "ERROR: No hay opción seleccionada",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val respuesta = seleccionados.map { it.tag.toString().toInt() }.toSet()
                println("RESPUESTA: $respuesta")
                viewModel.comprobarDivisible(respuesta)
            }
        }

        viewModel.datos.observe(viewLifecycleOwner) { datos ->
            when (datos.estado) {
                0 -> {
                    binding.txtResultadoDivisibles.text = "Correcto"
                    binding.txtResultadoDivisibles.setTextColor(Color.GREEN)
                }

                1 -> {
                    binding.txtResultadoDivisibles.text = "Pendiente"
                    binding.txtResultadoDivisibles.setTextColor(Color.CYAN)
                }

                -1 -> {
                    binding.txtResultadoDivisibles.text = "Incorrecto"
                    binding.txtResultadoDivisibles.setTextColor(Color.RED)
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentoDivisible.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentoDivisible().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}