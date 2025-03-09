package com.example.trabajo08_gil_calvo_odin



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment

class CalculadoraFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculadora, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnOperar = view.findViewById<Button>(R.id.btnOperar)
        val etNum1 = view.findViewById<EditText>(R.id.etNum1)
        val etNum2 = view.findViewById<EditText>(R.id.etNum2)
        val tvResultado = view.findViewById<TextView>(R.id.tvResultado)
        val rbSumar = view.findViewById<RadioButton>(R.id.rbSumar)
        val rbRestar = view.findViewById<RadioButton>(R.id.rbRestar)
        val rbMultiplicar = view.findViewById<RadioButton>(R.id.rbMultiplicar)
        val rbDividir = view.findViewById<RadioButton>(R.id.rbDividir)


        btnOperar.setOnClickListener {
            val num1 = etNum1.text.toString().toDoubleOrNull()
            val num2 = etNum2.text.toString().toDoubleOrNull()

            if (num1 == null || num2 == null) {
                tvResultado.text = "Ingrese valores válidos"
                return@setOnClickListener
            }

            val resultado = when {
                rbSumar.isChecked -> num1 + num2
                rbRestar.isChecked -> num1 - num2
                rbMultiplicar.isChecked -> num1 * num2
                rbDividir.isChecked -> if (num2 != 0.0) num1 / num2 else "Error: Div. por 0"
                else -> "Seleccione una operación"
            }

            tvResultado.text = resultado.toString()
        }
    }
}