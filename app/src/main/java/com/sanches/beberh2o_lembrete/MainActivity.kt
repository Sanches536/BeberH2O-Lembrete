package com.sanches.beberh2o_lembrete

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.sanches.beberh2o_lembrete.databinding.ActivityMainBinding
import com.sanches.beberh2o_lembrete.model.CalcularIngestaoDiaria
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var calcularIngestaoDiaria: CalcularIngestaoDiaria

    lateinit var timePickerDialog: TimePickerDialog
    lateinit var calendar: Calendar
    var horaAtual = 0
    var minutoAtual = 0

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

        binding.btnLembrete.setOnClickListener{
            calendar = Calendar.getInstance()
            horaAtual = calendar.get(Calendar.HOUR_OF_DAY)
            minutoAtual = calendar.get(Calendar.MINUTE)
            timePickerDialog = TimePickerDialog(this,{ timePicker: TimePicker, hourOfDay: Int, minutes: Int ->
                binding.txtHora.text = String.format("%02d", hourOfDay)
                binding.txtMinuto.text = String.format("%02d", minutes)
            },horaAtual, minutoAtual, true)
            timePickerDialog.show()
        }

        binding.btnAlarme.setOnClickListener {

                if (binding.txtHora.toString().isNotEmpty() && binding.txtMinuto.toString().isNotEmpty()){
                val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                intent.putExtra(AlarmClock.EXTRA_HOUR, binding.txtHora.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MINUTES, binding.txtMinuto.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.alarme_mensagem))
                startActivity(intent)

                if (intent.resolveActivity(packageManager) != null){
                    startActivity(intent)
                }
            }
        }


    }
}