package com.example.appmedicamentos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmedicamentos.databinding.ItemDoseBinding
import com.example.appmedicamentos.localstorage.AppDatabase
import com.example.appmedicamentos.models.Medicamento
import com.example.appmedicamentos.testebancomedicamentos.daos.MedicamentoDaoTeste
import com.example.appmedicamentos.testebancomedicamentos.entities.Doses
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DosesAdapter(
    val dose: Medicamento,
    context: Context,
    val listaComDoses: MutableList<Doses>
): RecyclerView.Adapter<DosesAdapter.ViewHolder>(){

    private var db: AppDatabase? = null
    private lateinit var medicamentoDoseDao: MedicamentoDaoTeste
    private lateinit var adapter: DosesTomadasAdapter

    //todo java.util.ConcurrentModificationException esta acontecendo
    //todo tentar resolver isso



    val listaDoses: MutableList<Doses> = mutableListOf()

    init {
        db = AppDatabase.getAppDatabase(context)
        medicamentoDoseDao = db!!.medicamentoDaoTeste
        GlobalScope.launch {
            listaComDoses.clear()
            val listaDosesTomadas = medicamentoDoseDao.getAllDoses(dose.nomeMedicamento)
            listaDosesTomadas.forEach {
                listaComDoses.add(it)
            }
            adapter = DosesTomadasAdapter(dose, context, listaComDoses)

            //Log.d("testeadapterlista", "tamanho da lista: ${listaComDoses.size}")
            listaComDoses.forEach { //Log.d("testeadapterlista", "${it.nomeMedicamento} ${it.horarioDose} tomou? ${it.TomouDose}")

            }


        }


        //aqui tem que retornar só 4 doses
        //pegar o nome do medicamento
        //chamar o banco de dados
        //atraves da instancia do banco de dados chamar a dao
        //chamar o metodos de getAllDoses dentro de um GlobalLifeCycle scope
        //fazer um forEach na lista recebida do banco de dados
        //pra cada item da lista, checar se tomouMedicamento é true
        //se for, coloca a bolinha verde

    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.rvDosesTomadas.adapter = adapter
            //Log.d("testeadapterlista", "${holder.lista[1].nomeMedicamento} ${holder.lista[0].horarioDose} tomou? ${holder.lista[0].TomouDose}")
        //Log.d("testeadapterlista", "tamanho da lista: ${holder.lista.size}")
        //Log.d("testeadapterlista", "tamanho da lista: ${listaComDoses.size}")









        //Log.d("testeadapterlista", "${listaDoses[1].nomeMedicamento} ${listaDoses[1].horarioDose} tomou? ${listaDoses[0].TomouDose}")
        for(i in 0..listaDoses.size-1){
            if(listaDoses[i].nomeMedicamento == "dipirona"){
                /*
                if(listaDoses[i].TomouDose){
                    //Log.d("testeadapterlista", "${listaDoses[i].nomeMedicamento} ${listaDoses[i].horarioDose} tomou? ${listaDoses[i].TomouDose}")

                }

                 */

            }
            //Log.d("testemainactivitylista", "${todosMedicamentosComDose[i].nomeMedicamento} ${todosMedicamentosComDose[i].horarioDose} tomou? ${todosMedicamentosComDose[i].TomouDose}")
        }


    }


    class ViewHolder(val binding: ItemDoseBinding, val lista: MutableList<Doses>): RecyclerView.ViewHolder(binding.root){


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemDoseBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view, listaComDoses)
    }


    /*
    como ele esta dentro do forEach, quando ele
    toma 1 dose, o loop é repetido pelo total
    de doses
    - eu tenho que colocar a bolinha verde só uma vez
     */

    override fun getItemCount(): Int = dose.quantidadeDoses
}

/*
size no init


tamanho da lista: 4
tamanho da lista: 2
tamanho da lista: 2
tamanho da lista: 4
tamanho da lista: 6

size no onBindViewHolder

tamanho da lista: 4
tamanho da lista: 4
tamanho da lista: 4
tamanho da lista: 4
tamanho da lista: 2
tamanho da lista: 2
tamanho da lista: 2
tamanho da lista: 2
tamanho da lista: 4
tamanho da lista: 4
tamanho da lista: 4
tamanho da lista: 4
tamanho da lista: 6
tamanho da lista: 6
tamanho da lista: 6
tamanho da lista: 6
tamanho da lista: 6
tamanho da lista: 6
 */
