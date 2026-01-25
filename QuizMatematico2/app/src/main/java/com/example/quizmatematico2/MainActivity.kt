package com.example.quizmatematico2

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var randomBtn: Button
    lateinit var randomTxt: TextView
    lateinit var preguntaTxt: TextView
    lateinit var entre2Check: CheckBox
    lateinit var entre3Check: CheckBox
    lateinit var entre5Check: CheckBox
    lateinit var entre10Check: CheckBox
    lateinit var entre0Check: CheckBox
    lateinit var comprobarBtn: Button
    lateinit var resultadoTxt: TextView
    lateinit var imageView: ImageView
    lateinit var linearLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        randomBtn = findViewById(R.id.randomBtn)
        randomTxt = findViewById(R.id.randomTxt)
        preguntaTxt = findViewById(R.id.preguntaTxt)
        entre2Check = findViewById(R.id.checkBoxEntre2)
        entre3Check = findViewById(R.id.checkBoxEntre3)
        entre5Check = findViewById(R.id.checkBoxEntre5)
        entre10Check = findViewById(R.id.checkBoxEntre10)
        entre0Check = findViewById(R.id.checkBoxNinguno)
        comprobarBtn = findViewById(R.id.resultadoBtn)
        resultadoTxt = findViewById(R.id.resultadoTxt)
        imageView = findViewById(R.id.imageView)
        linearLayout= findViewById(R.id.linearLayout)
        linearLayout.visibility = View.INVISIBLE
        var num: Int = 1

        randomBtn.setOnClickListener {
            num = Random.nextInt(1000,2000)
            randomTxt.text = num.toString()
            linearLayout.visibility = View.VISIBLE
        }

        comprobarBtn.setOnClickListener {
            val correctas = ArrayList<Int>()
            val marcadas = ArrayList<Int>()

            if (num%2==0) correctas.add(2)
            if (num%3==0) correctas.add(3)
            if (num%5==0) correctas.add(5)
            if (num%10==0) correctas.add(10)
            if (correctas.isEmpty()) correctas.add(-1)

            if (entre2Check.isChecked) marcadas.add(2)
            if (entre3Check.isChecked) marcadas.add(3)
            if (entre5Check.isChecked) marcadas.add(5)
            if (entre10Check.isChecked) marcadas.add(10)
            if (entre0Check.isChecked) marcadas.add(-1)

            if (marcadas.isEmpty()){
                resultadoTxt.text = getString(R.string.resultadoNulo)
                resultadoTxt.setTextColor(Color.BLUE)
                imageView.visibility = View.INVISIBLE

            } else if (marcadas == correctas){
                resultadoTxt.text = getString(R.string.resultadoOk)
                resultadoTxt.setTextColor(Color.GREEN)
                imageView.setImageResource(R.drawable.ok)
                imageView.visibility = View.VISIBLE
            } else {
                resultadoTxt.text = getString(R.string.resultadoKo)
                resultadoTxt.setTextColor(Color.RED)
                imageView.setImageResource(R.drawable.ko)
                imageView.visibility = View.VISIBLE
            }

        }
    }

}