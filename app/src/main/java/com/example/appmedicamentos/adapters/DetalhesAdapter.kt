package com.example.appmedicamentos.adapters

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmedicamentos.R
import com.example.appmedicamentos.databinding.ItemDetalhesMedicamentosBinding
import com.example.appmedicamentos.localstorage.AppDatabase
import com.example.appmedicamentos.testebancomedicamentos.daos.MedicamentoDaoTeste
import com.example.appmedicamentos.testebancomedicamentos.entities.Doses
import com.example.appmedicamentos.testebancomedicamentos.relations.MedicamentoComDoses
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DetalhesAdapter(private val listaDosagemMedicamento: MedicamentoComDoses): RecyclerView.Adapter<DetalhesAdapter.ViewHolder>() {
    private var db: AppDatabase? = null
    private lateinit var medicamentoDoseDao: MedicamentoDaoTeste





    inner class ViewHolder(val binding: ItemDetalhesMedicamentosBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ServiceCast", "SetTextI18n")
        fun bind(doses: Doses){
            db = AppDatabase.getAppDatabase(binding.root.context)
            medicamentoDoseDao = db!!.medicamentoDaoTeste

            Log.d("tentandoachardoses","tomou a dose das ${doses.horarioDose}")

            if(doses.jaTomouDose){
                Log.d("controlebolinhas","tomou a dose das ${doses.horarioDose}")
                binding.ivStatusDosage.setImageResource(R.drawable.med_taken)


            }







            binding.itemMedicamento.setOnClickListener {
                if(!doses.jaTomouDose){
                    //mostrar o dialog confirmando a dose a ser tomada
                    val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(binding.root.context)
                    alert.setTitle("Tomar ${doses.nomeMedicamento}")
                    alert.setMessage("Você quer tomar a dose de ${doses.horarioDose}h agora?")
                    alert.setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, which ->

                        GlobalScope.launch {
                            medicamentoDoseDao.tomarDoseMedicamento(true, doses.horarioDose)
                        }
                        binding.ivStatusDosage.setImageResource(R.drawable.med_taken)

                        dialog.dismiss()
                    })

                    alert.setNegativeButton("Não",
                        DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss() })

                    alert.show()
                }else{
                    //mostrar o dialog confirmando a dose a ser tomada
                    val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(binding.root.context)
                    alert.setTitle("Dose Tomada!")
                    alert.setMessage("Você já tomou a dose das ${doses.horarioDose}h!")
                    alert.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->


                        dialog.dismiss()
                    })


                    alert.show()

                }

            }
            binding.ivStatusDosage.setOnClickListener {
                if(!doses.jaTomouDose){
                    //mostrar o dialog confirmando a dose a ser tomada
                    val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(binding.root.context)
                    alert.setTitle("Tomar ${doses.nomeMedicamento}")
                    alert.setMessage("Você quer tomar a dose de ${doses.horarioDose}h agora?")
                    alert.setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, which ->

                        GlobalScope.launch {
                            medicamentoDoseDao.tomarDoseMedicamento(true, doses.horarioDose)
                        }
                        binding.ivStatusDosage.setImageResource(R.drawable.med_taken)

                        dialog.dismiss()
                    })

                    alert.setNegativeButton("Não",
                        DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss() })

                    alert.show()
                }else{
                    //mostrar o dialog confirmando a dose a ser tomada
                    val alert: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(binding.root.context)
                    alert.setTitle("Dose Tomada!")
                    alert.setMessage("Você já tomou a dose das ${doses.horarioDose}h!")
                    alert.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->


                        dialog.dismiss()
                    })


                    alert.show()

                }


            }





            binding.tvDetailMedicineName.text = doses.nomeMedicamento
            doses.horarioDose[0]
            if(doses.horarioDose[0].toString() == "2" && doses.horarioDose[1].toString() == "4"){
                binding.timeDosage.text = "00:"+doses.horarioDose[3]+doses.horarioDose[4]
            }else{
                binding.timeDosage.text = doses.horarioDose
            }





        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDetalhesMedicamentosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val medicamento = listaDosagemMedicamento.listaDoses[position]
        holder.bind(medicamento)

    }

    override fun getItemCount(): Int = listaDosagemMedicamento.listaDoses.size

}