package com.example.appmedicamentos.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmedicamentos.databinding.ItemDoseTomadaBinding
import com.example.appmedicamentos.models.Medicamento
import com.example.appmedicamentos.testebancomedicamentos.entities.Doses

class DosesTomadasAdapter(
    val dose: Medicamento,
    context: Context,
    val listaComDoses: MutableList<Doses>
): RecyclerView.Adapter<DosesTomadasAdapter.ViewHolder>(){


    init {



    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



        //Log.d("testeadapterlista", "${listaDoses[1].nomeMedicamento} ${listaDoses[1].horarioDose} tomou? ${listaDoses[0].TomouDose}")
            for(i in 0..listaComDoses.size-1){
                if(listaComDoses[i].nomeMedicamento == dose.nomeMedicamento){
                    /*
                    if(listaComDoses[i].TomouDose){
                        Log.d("testeadapterlista", "${listaComDoses[i].nomeMedicamento} ${listaComDoses[i].horarioDose} tomou? ${listaComDoses[i].TomouDose}")

                    }

                     */

                }
                //Log.d("testemainactivitylista", "${todosMedicamentosComDose[i].nomeMedicamento} ${todosMedicamentosComDose[i].horarioDose} tomou? ${todosMedicamentosComDose[i].TomouDose}")
            }


    }


    class ViewHolder(val binding: ItemDoseTomadaBinding): RecyclerView.ViewHolder(binding.root){


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemDoseTomadaBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view)
    }


    /*
    como ele esta dentro do forEach, quando ele
    toma 1 dose, o loop é repetido pelo total
    de doses
    - eu tenho que colocar a bolinha verde só uma vez
     */

    override fun getItemCount(): Int = 0
        ///todo no getitemcount retornar só o valor certo de bolinhas verdes para cada remedio
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
