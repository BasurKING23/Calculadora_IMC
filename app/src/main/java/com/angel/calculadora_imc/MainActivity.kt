package com.angel.calculadora_imc

import android.annotation.SuppressLint
import android.content.ClipDescription
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.slider.Slider
import java.util.Locale
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    companion object {
        const val INITIAL_HEIGHT = 170f
        const val INITIAL_WEIGHT = 75f
        const val INITIAL_DESCRIPTION = ("Description")
    }


    // importar //
    lateinit var heightTextView: TextView
    lateinit var heightSlider: Slider
    lateinit var weightEditText: EditText
    lateinit var weightAddButton: Button
    lateinit var weightMinusButton: Button
    lateinit var calButton: Button
    lateinit var resultTextView: TextView
    lateinit var descriptionTextView: TextView
    lateinit var restar: Button

    // importar //
    var height = INITIAL_HEIGHT
    var weight = INITIAL_WEIGHT
    var description = INITIAL_DESCRIPTION

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // llamadas a botones//

        heightTextView = findViewById(R.id.heightTextView)
        heightSlider = findViewById(R.id.heightSlider)
        weightEditText = findViewById(R.id.weightEditText)
        weightAddButton = findViewById(R.id.weightAddButton)
        weightMinusButton = findViewById(R.id.weightMinusButton)
        calButton = findViewById(R.id.calButton)
        resultTextView = findViewById(R.id.resultTextView)
        descriptionTextView = findViewById(R.id.description)
        restar = findViewById(R.id.restar)
        // fin de llamada a botones//

        heightSlider.addOnChangeListener { slider, value, fromUser ->
            height = value
            heightTextView.text = "${height.toInt()}"

        }
        weightAddButton.setOnClickListener {
            weight++
            weightEditText.setText("${weight.toInt()}")
            hideKeyboard()
        }
        weightMinusButton.setOnClickListener {
            weight--
            weightEditText.setText("${weight.toInt()}")
            hideKeyboard()
        }

        weightEditText.addTextChangedListener {
            val text = weightEditText.text.toString()
            if (text.isNotEmpty()) {
                if (text.toFloat() > 300) {
                    weightEditText.setText("300")
                }
                if (text.toFloat() < 1) {
                    weightEditText.setText("1")
                }
                weight = weightEditText.text.toString().toFloat()
            }
        }

        calButton.setOnClickListener {
            val result = weight / (height / 100).pow(2)

            resultTextView.text = String.format(Locale.getDefault(), "%.2f", result)

            var color = 0
            var text = 0

        // hola
            when (result) {
                in 0f..<18.5f -> {
                    color = R.color.bajo
                    text = R.string.bajo
                }

                in 18.5f..<25f -> {
                    color = R.color.normal
                    text = R.string.normal
                }

                in 25f..<30f -> {
                    color = R.color.sobrepeso
                    text = R.string.sobrepeso
                }

                in 30f..<35f -> {
                    color = R.color.obesidad1
                    text = R.string.obesidad1
                }

                in 35f..<40f -> {
                    color = R.color.obesidad2
                    text = R.string.obesidad2
                }

                in 35f..<40f -> {
                    color = R.color.obesidad3
                    text = R.string.obesidad3
                }

            }
            resultTextView.setTextColor(getColor(color))
            descriptionTextView.setTextColor(getColor(color))
            descriptionTextView.text = getString(text)




            hideKeyboard()
        }

        restar.setOnClickListener {
            heightSlider.value = INITIAL_HEIGHT
            weightEditText.setText("$INITIAL_WEIGHT")
            descriptionTextView.text = INITIAL_DESCRIPTION
            resultTextView.setTextColor(getColor(R.color.white))
            resultTextView.text = "00.00"
            descriptionTextView.setTextColor(getColor(R.color.white))



            AlertDialog.Builder(this)
                .setTitle("se ha reiniciado")
                .setMessage("Se ha colocado los valores por defecto")
                .setPositiveButton(android.R.string.yes, null)
                .setNegativeButton(android.R.string.no, null)
                .show()
        }

    }

    fun hideKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}