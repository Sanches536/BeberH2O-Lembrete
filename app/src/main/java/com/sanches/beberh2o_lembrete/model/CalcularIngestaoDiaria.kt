package com.sanches.beberh2o_lembrete.model

class CalcularIngestaoDiaria{

    private var resultado = 0.0

    fun calcular(peso: Double, idade: Int): Double{
        if(idade <= 17){
            resultado = peso * 40
        }else if (idade <= 55){
            resultado = peso * 35
        }else if (idade <= 65){
            resultado = peso * 30
        }else{
            resultado = peso * 25
        }
        return  resultado
    }

}