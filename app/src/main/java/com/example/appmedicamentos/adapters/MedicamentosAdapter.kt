package com.example.appmedicamentos.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.appmedicamentos.MedicamentoDose
import com.example.appmedicamentos.MedicineDetailActivity
import com.example.appmedicamentos.databinding.ItemMedicamentoBinding
import com.example.appmedicamentos.localstorage.AppDatabase
import com.example.appmedicamentos.localstorage.daos.MedicamentoDoseDao
import com.example.appmedicamentos.models.Medicamento
import com.example.appmedicamentos.testebancomedicamentos.daos.MedicamentoDaoTeste
import com.example.appmedicamentos.testebancomedicamentos.entities.Doses
import com.example.appmedicamentos.testebancomedicamentos.entities.HistoricoMedicamentos
import com.example.appmedicamentos.testebancomedicamentos.entities.MedicamentoTeste
import com.example.appmedicamentos.testebancomedicamentos.relations.MedicamentoComDoses
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MedicamentosAdapter(private val list: ArrayList<MedicamentoComDoses>, context: Context): RecyclerView.Adapter<MedicamentosAdapter.ViewHolder>() {
    private var db: AppDatabase? = null
    private lateinit var medicamentoDoseDao: MedicamentoDaoTeste
    private var listaComDoses: MutableList<MedicamentoDose> = mutableListOf()
    var listaComDosesToast: MutableLiveData<List<Doses>> = MutableLiveData()

    init{
        db = AppDatabase.getAppDatabase(context)
        //medicamentoDoseDao = db!!.medicamentoDoseDao()
    }

    //todo o tratamento do medicamento n acaba quando ele termina de tomar as doses do dia. Ele continua nos dias subsequentes ate acabar a quantidade de dias






    inner class ViewHolder(val binding: ItemMedicamentoBinding): RecyclerView.ViewHolder(binding.root) {




        fun bind(medicamento: MedicamentoComDoses){

            var proxDose: String? = null
            var definiuProxDose = false
            for (i in 0..medicamento?.listaDoses?.size-1){
                val horarioDose = medicamento?.listaDoses?.get(i)
                if(!horarioDose.jaTomouDose && !definiuProxDose){
                    proxDose = horarioDose.horarioDose
                    definiuProxDose = true
                }
                if(horarioDose.jaTomouDose && i != medicamento?.listaDoses?.size-1){
                    definiuProxDose = false
                }else if(i == medicamento?.listaDoses?.size-1 && horarioDose.jaTomouDose){
                    proxDose = medicamento.medicamentoTeste.horaPrimeiraDose
                    //aqui ele tem que verificar se ainda tem dias restantes de tratamento e
                    //se sim, diminuir um na quantidade de dias restantes de tratamento
                    //todo alterar o valor do if para > 1 e testar
                    GlobalScope.launch {
                        val db = AppDatabase.getAppDatabase(binding.root.context)?.medicamentoDaoTeste
                        if(medicamento.medicamentoTeste.diasRestantesDeTratamento > 1){
                            db?.diaConcluido(medicamento.medicamentoTeste.diasRestantesDeTratamento - 1, medicamento.medicamentoTeste.nomeMedicamento)
                            db?.resetarDosesTomadasParaDiaNovoDeTratamento(false, medicamento.medicamentoTeste.nomeMedicamento)
                        }else{
                            //tiro da lista de medicamento e passo para a lista de historico de medicamentos
                            //como eu estou dentro da coroutine eu ja posso chamar o metodo daqui
                            //vai ser um metodo de insert para a tabela historico medicamentos
                            //e depois um metodo de delete para o medicamento que foi finalizado


                            db?.insertNaTabelaHistoricoMedicamentos(HistoricoMedicamentos(
                                medicamento.medicamentoTeste.nomeMedicamento,
                                medicamento.medicamentoTeste.totalDiasTratamento,
                                "18/10/2022"
                            ))




                            db?.deleteMedicamentoFromMedicamentoTeste(medicamento.medicamentoTeste.nomeMedicamento)
                            db?.deleteDosesDoMedicamentoFinalizado(medicamento.medicamentoTeste.nomeMedicamento)
                            Log.d("terminoumedicamento", "Voce terminou de tomar esse medicamento!")
                        }

                    }

                }

            }
            /*
            medicamento?.listaDoses?.forEach {
                horariodose->
                if(!horariodose.jaTomouDose && !definiuProxDose){
                    proxDose = horariodose.horarioDose
                    definiuProxDose = true
                }
                if(horariodose.jaTomouDose){
                    definiuProxDose = false
                }
            }

             */


            binding.horaProximaDose.text = proxDose


            db = AppDatabase.getAppDatabase(binding.root.context)
            medicamentoDoseDao = db!!.medicamentoDaoTeste
            /*
            GlobalScope.launch {
                val lista = medicamentoDoseDao.getAllDoses(medicamento.nomeMedicamento)
                listaComDosesToast.postValue(lista)
            }

             */


            binding.medicamento.setOnClickListener {
                //se eu conseguir passar um objeto aqui na intent
                val intent = Intent(binding.root.context, MedicineDetailActivity::class.java)
                intent.putExtra("medicamento", medicamento)
                binding.root.context.startActivity(intent)

            }
            binding.medName.text = medicamento?.medicamentoTeste?.nomeMedicamento

        }
        /*
        eu tenho o dipirona por exemplo, no bind que ele ta selecionado eu quero printar todas as doses dele
         */


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMedicamentoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val medicamento = list?.get(position)
        holder.bind(medicamento)

    }



    override fun getItemCount(): Int = list.size


}
/*

xml bolinhas
 */