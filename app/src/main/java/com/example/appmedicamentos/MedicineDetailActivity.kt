package com.example.appmedicamentos

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.appmedicamentos.adapters.DetalhesAdapter
import com.example.appmedicamentos.databinding.ActivityMedicineDetailBinding
import com.example.appmedicamentos.localstorage.AppDatabase
import com.example.appmedicamentos.testebancomedicamentos.entities.Doses
import com.example.appmedicamentos.testebancomedicamentos.relations.MedicamentoComDoses

class MedicineDetailActivity : AppCompatActivity() {

    private var listaDoses  = arrayListOf<Doses>()
    private lateinit var adapter : DetalhesAdapter
    private lateinit var binding: ActivityMedicineDetailBinding
    private var db: AppDatabase? = null

    @SuppressLint("NotifyDataSetChanged", "ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicineDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getAppDatabase(this)


        val intent = intent
        val extra = intent.getSerializableExtra("medicamento")

        //dealWithDosageTime(extra as MedicamentoComDoses)
        adapter = DetalhesAdapter(extra as MedicamentoComDoses)
        binding.medDetalhesRecyclerView.adapter = adapter


    }

    private fun dealWithDosageTime(extra: MedicamentoComDoses) {
        val horaDigitada = extra.medicamentoTeste.horaPrimeiraDose
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

        getAllDosages(extra.medicamentoTeste.nomeMedicamento, hora, extra.medicamentoTeste.qntDoses, minutos)//eu preciso tbm da qnt de doses

        }

    private fun getAllDosages(nomeMed: String, horaIni: Int, qntDoses: Int, min: String) {
        var hora = horaIni
        //passar a hora inicial que equivale a primeira para a lista
        val medicamentoPrimeiraDose = Doses(
            nomeMedicamento = nomeMed,
            horarioDose = horaIni.toString()+":"+min+"h",
            jaTomouDose = false
        )
        listaDoses.add(medicamentoPrimeiraDose)
        val intervaloEntreDoses = 24/qntDoses
        for(i in 1..qntDoses-1){
            Log.d("testehora", "hora da dose: ${hora}:${min}h")
            if(hora + intervaloEntreDoses > 24){
                hora = hora + intervaloEntreDoses -24
                val medicamentoPrimeiraDose = Doses(
                    nomeMedicamento = nomeMed,
                    horarioDose = hora.toString()+":"+min+"h",
                    jaTomouDose = false
                )
                listaDoses.add(medicamentoPrimeiraDose)
            }
            else if(hora + intervaloEntreDoses < 24){
                hora = hora + intervaloEntreDoses
                val medicamentoPrimeiraDose = Doses(
                    nomeMedicamento = nomeMed,
                    horarioDose = hora.toString()+":"+min+"h",
                    jaTomouDose = false
                )
                listaDoses.add(medicamentoPrimeiraDose)
            }
            else if(hora + intervaloEntreDoses == 24){
                hora = 0
                val medicamentoPrimeiraDose = Doses(
                    nomeMedicamento = nomeMed,
                    horarioDose = hora.toString()+":"+min+"h",
                    jaTomouDose = false
                )
                listaDoses.add(medicamentoPrimeiraDose)
            }

        }
        listaDoses.forEach {
            Log.d("testelista", "cada horario salvo na lista: ${it.horarioDose}")
        }

        insertNovaDosagemAndSetListInAdapter(nomeMed)


    }

    private fun insertNovaDosagemAndSetListInAdapter(nomeMedicamento: String) {



    }


}



@Entity
data class MedicamentoDose(
    @PrimaryKey(autoGenerate = true) val idDoseMedicamento: Int = 0,
    val nomeMedicamento: String,
    val horarioDose: String,
    var TomouDose: Boolean = false
)

//todo será que a minha estrutura dos models esta errada?

