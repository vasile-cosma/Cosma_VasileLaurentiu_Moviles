package com.example.quizmatematico

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var randomBtn: Button
    lateinit var resultadoBtn: Button
    lateinit var fondoSwitch: Switch
    lateinit var resultadoTxt: TextView;
    lateinit var anyoTxt: TextView;
    lateinit var siRadio: RadioButton
    lateinit var noRadio: RadioButton
    lateinit var radioGroup: RadioGroup
    lateinit var mainLayout: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        randomBtn = this.findViewById(R.id.randomBtn)
        resultadoBtn = this.findViewById(R.id.resultadoBtn)
        fondoSwitch = this.findViewById(R.id.fondoSwitch)
        resultadoTxt = this.findViewById(R.id.resultadoTxt)
        anyoTxt = this.findViewById(R.id.anyoTxt)
        siRadio = this.findViewById(R.id.siRadio)
        noRadio = this.findViewById(R.id.noRadio)
        radioGroup = this.findViewById(R.id.radioGroup)
        mainLayout = this.findViewById(R.id.main)
        var anyo: Int = 0
        var esBisiesto: Boolean

        fondoSwitch.setOnClickListener() {
            if (fondoSwitch.isChecked) {
                mainLayout.setBackgroundColor(Color.YELLOW)
            } else {
                mainLayout.setBackgroundColor(Color.WHITE)
            }
        }

        randomBtn.setOnClickListener {
            anyo = Random.nextInt(1900,2501)
            anyoTxt.text = anyo.toString()
            resultadoTxt.text = ""
        }

        resultadoBtn.setOnClickListener {
            esBisiesto = comprobarBisiesto(anyo)

            when (radioGroup.checkedRadioButtonId){
                siRadio.id -> {
                   if (esBisiesto) {
                       resultadoTxt.text = getString(R.string.resultadoCorrecto)
                       resultadoTxt.setTextColor(Color.GREEN)
                   } else {
                       resultadoTxt.text = getString(R.string.resultadoErroneo)
                       resultadoTxt.setTextColor(Color.RED)
                   }
                }

                noRadio.id -> {
                    if (!esBisiesto) {
                        resultadoTxt.text = getString(R.string.resultadoCorrecto)
                        resultadoTxt.setTextColor(Color.GREEN)
                    } else {
                        resultadoTxt.text = getString(R.string.resultadoErroneo)
                        resultadoTxt.setTextColor(Color.RED)
                    }
                }

                else -> {
                    resultadoTxt.text = getString(R.string.resultadoNulo)
                    resultadoTxt.setTextColor(Color.BLUE)
                }
            }
        }




    }

    fun comprobarBisiesto(anyo: Int): Boolean {
        return (anyo%4==0 && anyo%100 !=0) || (anyo%400 == 0)
    }
}