package com.example.appmedicamentos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.appmedicamentos.adapters.MedicamentosAdapter
import com.example.appmedicamentos.databinding.ActivityMainBinding
import com.example.appmedicamentos.localstorage.AppDatabase
import com.example.appmedicamentos.testebancomedicamentos.relations.MedicamentoComDoses
import com.example.appmedicamentos.ui.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //estudar sobre relacionamento entre tabelas utilizando o room
    //https://medium.com/androiddevelopers/database-relations-with-room-544ab95e4542

    //todo: fazer o insert novo aparecer na main activity sem precisar reiniciar o app
    //quando eu tento cadastrar um medicamento com dose inicial as 9:00 ele n deixa cadastrar o medicamento, pq isso esta acontecendo?

    //todo: quando o medicamento termina ele atualiza a hora mesmo que a pessoa n precise tomar mais o medicamento no dia seguinte






    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MedicamentosAdapter
    lateinit var viewModel: MainActivityViewModel





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("ciclodevida", "to no oncreate")

        binding.fab.setOnClickListener { view ->
            startActivity(Intent(this, AddMedicineActivity::class.java))
        }

        initViewModel()




    }
    //todo colocar a quantidade de dias que faltam para a pessoa parar de tomar o medicamento

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.medicamentos.observe(this){
            //recebe lista de medicamentos que usuario esta utilizando
            //a partir dessa lista eu preciso de uma lista de cada medicamento com suas doses
            Log.d("medicamentocomdoses", "tamanho da lista ${it.size}")
            if(it != null){
                if(it.size > 0){
                    binding.txtNoData.visibility = View.INVISIBLE
                    binding.caixaVazia.visibility = View.INVISIBLE
                }
                setAdapter(it)
            }



        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("ciclodevida", "to no onstart")


    }
    fun setAdapter(medicamentos: List<MedicamentoComDoses>?) {
        adapter = MedicamentosAdapter(medicamentos as ArrayList<MedicamentoComDoses>, this)
        adapter.listaComDosesToast.observe(this){

        }
        binding.recyclerView.adapter = adapter

    }



    override fun onResume() {
        super.onResume()
        Log.d("ciclodevida", "to no onResume")
        //ele chama loadmedications que retorna uma lista com doses, só que a lista com doses era pra estar vazia
        viewModel.loadMedications()


    }




}

