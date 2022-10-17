package com.example.appmedicamentos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.appmedicamentos.adapters.MedicamentosAdapter
import com.example.appmedicamentos.databinding.ActivityMainBinding
import com.example.appmedicamentos.localstorage.AppDatabase
import com.example.appmedicamentos.models.Medicamento
import com.example.appmedicamentos.testebancomedicamentos.entities.MedicamentoTeste
import com.example.appmedicamentos.testebancomedicamentos.relations.MedicamentoComDoses
import com.example.appmedicamentos.ui.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //todo estudar sobre relacionamento entre tabelas utilizando o room
    //https://medium.com/androiddevelopers/database-relations-with-room-544ab95e4542






    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MedicamentosAdapter
    lateinit var viewModel: MainActivityViewModel





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)








        val db = AppDatabase.getAppDatabase(this)
       // lifecycleScope.launch { db?.medicamentoDoseDao()?.deleteAllFromMedicamentoDose() }
       // lifecycleScope.launch { db?.medicamentoDao()?.deleteAll() }

        lifecycleScope.launch {
           // val listaDoses = db?.medicamentoDoseDao()!!.getAll()
            //listaDoses.forEach {
                //todosMedicamentosComDose.add(it)
           // }
            //getList(todosMedicamentosComDose)
        }

        /*
        lifecycleScope.launch {
            val listaDoses = db?.medicamentoDoseDao()!!.getAllDoses("aspirina")
            listaDoses.forEach {
                todosMedicamentosComDose.add(it)
            }
            getList(todosMedicamentosComDose)
        }

         */
        //lifecycleScope.launch { db?.medicamentoDao()?.deleteAll() }




        lifecycleScope.launch {
            //allMedicamentosInUse.postValue(db?.medicamentoDao()!!.getAllMedicamentosInUse())


        }
        /*
        esse observer que eu estou fazendo é correto, mas o acesso ao banco de dados deve ser feito
        atraves de viewmodels e repositories
         */

        /*
        allMedicamentosInUse.observe(this, Observer {
            if (it.isNotEmpty()) {

                binding.txtNoData.visibility = View.INVISIBLE
                binding.caixaVazia.visibility = View.INVISIBLE
                adapter = MedicamentosAdapter(it as ArrayList<Medicamento>, this)
                binding.recyclerView.adapter = adapter


            }

        })

         */



        binding.fab.setOnClickListener { view ->
            startActivity(Intent(this, AddMedicineActivity::class.java))
        }

        initViewModel()
    }
    //todo o medicamento n esta atualizando automaticamente quando eu saio da addmedicine activity(n adiciona o novo medicamento na recycler view)

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.medicamentos.observe(this){
            //recebe lista de medicamentos que usuario esta utilizando
            //a partir dessa lista eu preciso de uma lista de cada medicamento com suas doses
            if(it != null){
                Log.d("testenullex", "tamanho da lista ${it.size}")
                setAdapter(it)
            }


        }
    }
    fun setAdapter(medicamentos: List<MedicamentoComDoses>?) {
        binding.txtNoData.visibility = View.INVISIBLE
        binding.caixaVazia.visibility = View.INVISIBLE
        adapter = MedicamentosAdapter(medicamentos as ArrayList<MedicamentoComDoses>, this)
        adapter.listaComDosesToast.observe(this){
            it.forEach {
                Log.d("dosesmainactivity", "dose de ${it.nomeMedicamento} as ${it.horarioDose}")
            }
        }
        binding.recyclerView.adapter = adapter

    }

    private fun getList(todosMedicamentosComDose: MutableList<MedicamentoDose>) {
        //Log.d("testemainactivitylista", "${todosMedicamentosComDose[0].nomeMedicamento} ${todosMedicamentosComDose[0].horarioDose} tomou? ${todosMedicamentosComDose[0].TomouDose}")

        for(i in 0..todosMedicamentosComDose.size-1){
            if(todosMedicamentosComDose[i].nomeMedicamento == "aspirina"){
                if(todosMedicamentosComDose[i].TomouDose){
                    Log.d("testemainactivitylista", "${todosMedicamentosComDose[i].nomeMedicamento} ${todosMedicamentosComDose[i].horarioDose} tomou? ${todosMedicamentosComDose[i].TomouDose}")

                }

            }
            //Log.d("testemainactivitylista", "${todosMedicamentosComDose[i].nomeMedicamento} ${todosMedicamentosComDose[i].horarioDose} tomou? ${todosMedicamentosComDose[i].TomouDose}")
        }




    }


}

/*


agora eu vou começar a checar se tem alguma coisa no banco de dados
se tiver, eu mostro a recyclerview e escondo os elementos de quando n tem nenhum medicamento cadastrado




 */

/*
a pessoa pode tomar um mesmo medicamento por 6 dias ou a pessoa pode tomar por alguns meses


se ela colocar 2, pode significar 2 dias ou 2 meses
 */


/*

3, 4, 5
 */