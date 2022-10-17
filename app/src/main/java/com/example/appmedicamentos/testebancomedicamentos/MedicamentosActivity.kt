package com.example.appmedicamentos.testebancomedicamentos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.appmedicamentos.R
import com.example.appmedicamentos.testebancomedicamentos.entities.Doses
import com.example.appmedicamentos.testebancomedicamentos.entities.MedicamentoTeste
import com.example.appmedicamentos.testebancomedicamentos.relations.MedicamentoComDoses
import kotlinx.coroutines.launch

class MedicamentosActivity : AppCompatActivity() {
    // TODO: o jeito que eu fiz o meu banco de dados nesse teste esta pronto para ser testado em "produção"
    // TODO: agora alem de colocar isso no meu fluxo principal do meu app, eu tenho que descobrir como fazer update dessas tabelas, fazer update no jaTomouDose

    //eu passo uma lista para o adapter, cada item vai ser um medicamento e em cada bind eu vou fazer a logica de colocar a proxima dose
    //a ser tomada, para o usuario ter uma noção de quando ele deve tomar a proxima dose

    private lateinit var lista: List<MedicamentoComDoses>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicamentos)

        val dao = DatabaseMedicamentos.getInstance(this).medicamentoDaoTeste


        /*
        val medicamentoTestes = listOf(
            MedicamentoTeste("Losartana", "20:00")
        )

         */

        /*
        val doses = listOf(
            Doses(4, "Losartana", "2:00", false),
            Doses(5, "Losartana", "8:00", false),
            Doses(6, "Losartana", "14:00", false)

        )

         */
        lista = ArrayList()

        /*
        lifecycleScope.launch {
            medicamentoTestes.forEach { dao.insertMedicamento(it) }
            doses.forEach { dao.insertDose(it) }

            lista = dao.getAllMedicamentoWithDoses()
            lista.forEach {
                medicamentoComDoses->
                Log.d("testerelmed", "medicamento: ${medicamentoComDoses.medicamentoTeste.nomeMedicamento} dose as: ${medicamentoComDoses.medicamentoTeste.horaPrimeiraDose}")
                medicamentoComDoses.listaDoses.forEach {
                    doses->
                    Log.d("testerelmed", "medicamento: ${doses.nomeMedicamento} dose as: ${doses.horarioDose}")

                }
            }


        }

         */


    }
}