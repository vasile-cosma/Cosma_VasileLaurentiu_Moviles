package com.example.calculadora

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var num1Txt: EditText;
    lateinit var num2Txt: EditText;
    lateinit var btnSumar: Button;
    lateinit var btnRestar: Button;
    lateinit var btnMultiplicar: Button;
    lateinit var btnDividir: Button;
    lateinit var resultadoTxt: TextView;
    lateinit var resultado: TextView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        num1Txt = this.findViewById(R.id.num1)
        num2Txt = this.findViewById(R.id.num2)
        btnSumar = this.findViewById(R.id.btnSumar)
        btnRestar = this.findViewById(R.id.btnRestar)
        btnMultiplicar = this.findViewById(R.id.btnMultiplicar)
        btnDividir = this.findViewById(R.id.btnDividir)
        resultadoTxt = this.findViewById(R.id.resultadoTxt)
        resultado = this.findViewById(R.id.resultado)

        var resultado: Double;

        btnSumar.setOnClickListener {
            val num1 = num1Txt.text.toString().toDouble();
            val num2 = num2Txt.text.toString().toDouble();
            resultado = num1+num2
            resultadoTxt.text = resultado.toString()
        }
        btnRestar.setOnClickListener {
            val num1 = num1Txt.text.toString().toDouble();
            val num2 = num2Txt.text.toString().toDouble();
            resultado = num1-num2
            resultadoTxt.text = resultado.toString()
        }
        btnMultiplicar.setOnClickListener {
            val num1 = num1Txt.text.toString().toDouble();
            val num2 = num2Txt.text.toString().toDouble();
            resultado = num1*num2
            resultadoTxt.text = resultado.toString()
        }
        btnDividir.setOnClickListener {
            val num1 = num1Txt.text.toString().toDouble();
            val num2 = num2Txt.text.toString().toDouble();
            resultado = num1/num2
            resultadoTxt.text = resultado.toString()
        }

    }
}