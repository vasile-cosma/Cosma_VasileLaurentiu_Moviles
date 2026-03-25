package com.example.bisiesto.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.bisiesto.R
import com.example.bisiesto.databinding.FragmentFragmentoBisiestoBinding
import com.example.bisiesto.databinding.FragmentFragmentoDivisibleBinding
import com.example.bisiesto.viewmodel.AppViewmodel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentoBisiesto.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentoBisiesto : Fragment() {
    private lateinit var binding: FragmentFragmentoBisiestoBinding

    private val viewModel: AppViewmodel by viewModels ({requireActivity()})
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentFragmentoBisiestoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnValidarBisiesto.setOnClickListener {
            val radioSeleccionado = binding.radioGroup.checkedRadioButtonId
            if (radioSeleccionado != -1){
                var respuesta = binding.radioGroup.findViewById<RadioButton>(radioSeleccionado).text.toString()
                viewModel.comprobarBisiesto(respuesta)
            } else {
                Toast.makeText(view.context, "ERROR: Debe seleccionar una opción", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.datos.observe(viewLifecycleOwner) { datos ->
            when (datos.estado) {
                0 -> {
                    binding.txtResultadoBisiesto.text = "Correcto"
                    binding.txtResultadoBisiesto.setTextColor(Color.GREEN)
                }
                1 -> {
                    binding.txtResultadoBisiesto.text = "Pendiente"
                    binding.txtResultadoBisiesto.setTextColor(Color.CYAN)
                }
                -1 -> {
                    binding.txtResultadoBisiesto.text = "Incorrecto"
                    binding.txtResultadoBisiesto.setTextColor(Color.RED)
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
         * @return A new instance of fragment FragmentoBisiesto.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentoBisiesto().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}