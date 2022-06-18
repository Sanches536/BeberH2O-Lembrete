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
    private lateinit var calculateIngestionDiary: CalcularIngestaoDiaria

    private lateinit var timePickerDialog: TimePickerDialog
    private lateinit var calendar: Calendar
    private var horaActual = 0
    private var minuteActual = 0

    @SuppressLint("SetTextI18n", "QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        calculateIngestionDiary = CalcularIngestaoDiaria()

        binding.btnCalcular.setOnClickListener{

            if(binding.editPeso.text.toString().isEmpty()){
                Toast.makeText(this,R.string.toast_informe_peso, Toast.LENGTH_SHORT).show()
            }else if(binding.editIdade.text.toString().isEmpty()){
                Toast.makeText(this, R.string.toast_informe_idade, Toast.LENGTH_SHORT).show()
            }else{
                val peso = binding.editPeso.text.toString().toDouble()
                val age = binding.editIdade.text.toString().toInt()
                val result = calculateIngestionDiary.calcular(peso, age)
                val format = NumberFormat.getNumberInstance(Locale("pt", "BR"))
                format.isGroupingUsed = false
                binding.txtResultadoMl.text = format.format(result).toString() + " " + "ml"
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
                    binding.txtHora.text = "00"
                    binding.txtMinuto.text = "00"
                }
            alertDialog.setNegativeButton("Cancel") { _, _ ->

            }
            alertDialog.create().show()

        }

        binding.btnLembrete.setOnClickListener{
            calendar = Calendar.getInstance()
            horaActual = calendar.get(Calendar.HOUR_OF_DAY)
            minuteActual = calendar.get(Calendar.MINUTE)
            timePickerDialog = TimePickerDialog(this,{ _: TimePicker, hourOfDay: Int, minutes: Int ->
                binding.txtHora.text = String.format("%02d", hourOfDay)
                binding.txtMinuto.text = String.format("%02d", minutes)
            },horaActual, minuteActual, true)
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