package com.example.appmedicamentos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.appmedicamentos.databinding.ActivityAddMedicineBinding
import com.example.appmedicamentos.testebancomedicamentos.entities.Doses
import com.example.appmedicamentos.testebancomedicamentos.entities.MedicamentoTeste
import com.example.appmedicamentos.ui.viewmodels.AddMedicineViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMedicineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMedicineBinding
    private var tratamentoDuraMeses = false
    private var listaHorarioDoses = ArrayList<Doses>()
    lateinit var viewModel: AddMedicineViewModel
    private lateinit var  horarioPrimeiraDose: String
    private lateinit var  nomeRemedio: String
    private lateinit var  qntDosesStr: String
    private var qntDoses: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMedicineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClickListeners()

        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[AddMedicineViewModel::class.java]
        viewModel.insertResponse.observe(this){
            Log.d("observerlong", "consegui uma resposta aqui no observer do insert: ${it}")
            if(it > -1){
                //pode colocar as doses na tabela de doses
                dealWithDosageTime(nomeRemedio, qntDoses, horarioPrimeiraDose)
                Toast.makeText(this, "${nomeRemedio} cadastrado com sucesso!", Toast.LENGTH_LONG)
                    .show()
                finish()
            }else{
                Toast.makeText(this, "Esse medicamento já está cadastrado", Toast.LENGTH_LONG).show()
            }
        }
        viewModel.insertDosesResponse.observe(this){
            Log.d("observerdosesinserter", "resultado do insert ddas doses: ${it}")
        }
    }

    private fun onClickListeners() {
        binding.btnDuracaoDias.setOnClickListener {
            Toast.makeText(this, "dias", Toast.LENGTH_LONG).show()
            binding.containerButtons.visibility = View.INVISIBLE
            binding.tilMedicineTimeTreatment.hint = "Quantos dias?"
            binding.tilMedicineTimeTreatment.visibility = View.VISIBLE
        }
        binding.btnDuracaoMeses.setOnClickListener {
            Toast.makeText(this, "meses", Toast.LENGTH_LONG).show()
            binding.containerButtons.visibility = View.INVISIBLE
            binding.tilMedicineTimeTreatment.hint = "Quantos meses?"
            binding.tilMedicineTimeTreatment.visibility = View.VISIBLE
            tratamentoDuraMeses = true

        }
    }

    override fun onStart() {
        super.onStart()

        //val medicamentoDao = db?.medicamentoDaoTeste

        binding.btnConfirmNewMedication.setOnClickListener {
            val qntDiasTrat: Int?
            nomeRemedio = binding.tilMedicineName.editText?.text.toString()
            qntDosesStr = binding.tilMedicineQntPerDay.editText?.text.toString()
            val qntTratamentoStr = binding.tilMedicineQntPerDay.editText?.text.toString()
            val qntDuracaoTratamentoStr = binding.tilMedicineTimeTreatment.editText?.text.toString()
            if (qntDuracaoTratamentoStr.isNotEmpty()) {
                if(tratamentoDuraMeses){
                    qntDiasTrat = qntDuracaoTratamentoStr.toInt() * 30
                }else{
                    qntDiasTrat = qntDuracaoTratamentoStr.toInt()
                }
            }else{
                qntDiasTrat = null
            }


            qntDoses = 0
            var sucesso = 0

            if (qntDosesStr != "") {
                qntDoses = qntDosesStr.toInt()
            }
            horarioPrimeiraDose = binding.tilTimeFirstTake.editText?.text.toString()
            val primeiroDigitoPrimeiraDose = horarioPrimeiraDose[0].toString().toInt()
            val segundoDigitoPrimeiraDose = horarioPrimeiraDose[1].toString().toInt()




            if (nomeRemedio != null
                &&
                qntDoses != null &&
                qntDoses > 0 &&
                horarioPrimeiraDose != null &&
                nomeRemedio.isNotEmpty() &&
                horarioPrimeiraDose.isNotEmpty()
                && horarioPrimeiraDose.length == 5
                && horarioPrimeiraDose[2].toString() == ":"
                && qntDiasTrat != null
                && primeiroDigitoPrimeiraDose <= 2
                && segundoDigitoPrimeiraDose <= 4
            ) {

                viewModel.insertMedicamento(
                    MedicamentoTeste(
                        nomeMedicamento = nomeRemedio,
                        totalDiasTratamento = qntDiasTrat,
                        horaPrimeiraDose = horarioPrimeiraDose,
                        qntDoses = qntDoses,
                        tratamentoFinalizado = false,
                        diasRestantesDeTratamento = qntDiasTrat
                    )
                )




            } else {
                Toast.makeText(
                    this,
                    "Erro ao cadastrar medicamento. Verifique o preenchimento dos dados.",
                    Toast.LENGTH_LONG
                ).show()

            }

        }
    }

    private fun dealWithDosageTime(nomeMedicamento: String, qntDoses: Int, horarioPrimeiraDose: String) {
        //todo aqui eu ja tenho que passar para a tabela doses
        val horaDigitada = horarioPrimeiraDose
        var hora = 0
        var minutos = "00"
        if(horaDigitada[1].toString() == ":"){//se hora tiver 1 digito
            if(horaDigitada[0].toString().toInt() in 1..9){
                //hora recebe horaDigitada[0].toString().toInt()
                hora = horaDigitada[0].toString().toInt()
                //minutos recebe (horaDigitada[2].toString() +  horaDigitada[2].toString()).toInt()
                minutos = horaDigitada[2].toString() +  horaDigitada[3].toString()

            }


        }else if(horaDigitada[2].toString() == ":"){//se hora tiver 2 digitos
            if(horaDigitada[0].toString().toInt() == 0){
                if(horaDigitada[1].toString().toInt() == 0){
                    //é meia noite
                    //hora recebe 0
                    hora = 0
                    //minutos recebe indice3 e 4 para int
                    minutos = horaDigitada[3].toString() +  horaDigitada[4].toString()

                }else{
                    if(horaDigitada[1].toString().toInt() > 0){
                        //hora recebe horaDigitada[1]
                        hora = horaDigitada[1].toString().toInt()
                        //minutos recebe  indice3 e 4 para int
                        minutos = horaDigitada[3].toString() +  horaDigitada[4].toString()
                    }
                }


            }else{
                if(horaDigitada[0].toString().toInt() in 1..2){
                    if((horaDigitada[0].toString() + horaDigitada[1].toString()).toInt() in 10..23){
                        //hora recebe indice 0 e 1
                        hora = (horaDigitada[0].toString() + horaDigitada[1].toString()).toInt()
                        //minutos recebe indice 3 e 4
                        minutos = horaDigitada[3].toString() +  horaDigitada[4].toString()
                    }
                }else if(horaDigitada[0].toString().toInt() == 2 && horaDigitada[1].toString().toInt() == 4){
                    //hora recebe 0
                    hora = 0
                    //minutos recebe indice 3 e 4
                    minutos = horaDigitada[3].toString() +  horaDigitada[4].toString()
                }
            }
        }

        getAllDosages(nomeMedicamento, hora, qntDoses, minutos)

    }

    private fun getAllDosages(nomeMed: String, horaIni: Int, qntDoses: Int, min: String) {
        var hora = horaIni
        Log.d("controlelistadoses", "hora inicial: ${horaIni}")
        //passar a hora inicial que equivale a primeira para a lista
        val medicamentoPrimeiraDose = Doses(
            nomeMedicamento = nomeMed,
            horarioDose = horaIni.toString()+":"+min+"h",
            jaTomouDose = false
        )

        //listaHorarioDoses.add(HorarioDose(horaIni.toString()+":"+min+"h", false))
        listaHorarioDoses.add(medicamentoPrimeiraDose)
        Log.d("controlelistadoses", "horario de dose adicionado na lista: ${horaIni.toString()+":"+min+"h"}")

        //listaDoses.add(medicamentoPrimeiraDose)
        val intervaloEntreDoses = 24/qntDoses
        for(i in 1..qntDoses-1){
            if(hora + intervaloEntreDoses > 24){
                hora = hora + intervaloEntreDoses -24
                val medicamentoDose = Doses(
                    nomeMedicamento = nomeMed,
                    horarioDose = hora.toString()+":"+min+"h",
                    jaTomouDose = false
                )
                Log.d("controlelistadoses", "segunda dose: ${hora.toString()+":"+min+"h"}")
                //listaDoses.add(medicamentoPrimeiraDose)
                //listaHorarioDoses.add(HorarioDose(hora.toString()+":"+min+"h", false))
                listaHorarioDoses.add(medicamentoDose)

            }
            else if(hora + intervaloEntreDoses < 24){
                hora = hora + intervaloEntreDoses
                val medicamentoDose = Doses(
                    nomeMedicamento = nomeMed,
                    horarioDose = hora.toString()+":"+min+"h",
                    jaTomouDose = false
                )
                //listaDoses.add(medicamentoPrimeiraDose)
                //listaHorarioDoses.add(HorarioDose(hora.toString()+":"+min+"h", false))
                listaHorarioDoses.add(medicamentoDose)
                Log.d("controlelistadoses", "terceira dose: ${hora.toString()+":"+min+"h"}")
            }
            else if(hora + intervaloEntreDoses == 24){
                hora = 0
                val medicamentoDose = Doses(
                    nomeMedicamento = nomeMed,
                    horarioDose = hora.toString()+":"+min+"h",
                    jaTomouDose = false
                )
                //listaDoses.add(medicamentoPrimeiraDose)
                //listaHorarioDoses.add(HorarioDose(hora.toString()+":"+min+"h", false))
                listaHorarioDoses.add(medicamentoDose)
                Log.d("controlelistadoses", "horario de dose adicionado na lista: ${horaIni.toString()+":"+min+"h"}")
            }

        }
        listaHorarioDoses.forEach { dose->
            viewModel.insertDose(dose)

        }




    }

    private fun showResponseFromInsert(returnInsert: Long?, nomeMedicamento: String) {
        if (returnInsert != null) {
            if(returnInsert > -1){
                Toast.makeText(this, "${nomeMedicamento} cadastrado com sucesso!", Toast.LENGTH_LONG)
                    .show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()


            }else{
                Toast.makeText(this, "erro ao cadastrar medicamento", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }
}


//todo - se o user escolheu dias, eu n preciso fazer nenhuma conversao
//todo - se o user escolheu meses, eu pego o a quantidade e multiplico por 30
//todo - e preencho a quantidade de dias do tratamentyo

//todo - coloco a quantidade no parametro que é passado quando eu clico no
//todo botão de confirmar
