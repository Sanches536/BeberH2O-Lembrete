package com.sanches.beberh2o_lembrete

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.sanches.beberh2o_lembrete.databinding.ActivityMainBinding
import com.sanches.beberh2o_lembrete.model.CalcularIngestaoDiaria
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var calcularIngestaoDiaria: CalcularIngestaoDiaria

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        calcularIngestaoDiaria = CalcularIngestaoDiaria()

        binding.btnCalcular.setOnClickListener{

            if(binding.editPeso.text.toString().isEmpty()){
                Toast.makeText(this,R.string.toast_informe_peso, Toast.LENGTH_SHORT).show()
            }else if(binding.editIdade.text.toString().isEmpty()){
                Toast.makeText(this, R.string.toast_informe_idade, Toast.LENGTH_SHORT).show()
            }else{
                val peso = binding.editPeso.text.toString().toDouble()
                val idade = binding.editIdade.text.toString().toInt()
                val resultado = calcularIngestaoDiaria.calcular(peso, idade)
                val formatar = NumberFormat.getNumberInstance(Locale("pt", "BR"))
                formatar.isGroupingUsed = false
                binding.txtResultadoMl.text = formatar.format(resultado).toString() + " " + "ml"
            }
        }

        binding.icRefresh.setOnClickListener{
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(R.string.dialog_titulo)
                .setMessage(R.string.dialog_desc)
                .setPositiveButton("ok") { _, _ ->
                    binding.editPeso.setText("")
                    binding.editIdade.setText("")
                    binding.txtResultadoMl.text = ""
                }
            alertDialog.setNegativeButton("Cancela") { _, _ ->

            }
            val dialog = alertDialog.create()
            dialog.show()
        }

    }
}