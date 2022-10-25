package com.example.appmedicamentos.adapters

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.appmedicamentos.*
import com.example.appmedicamentos.databinding.ItemMedicamentoBinding
import com.example.appmedicamentos.localstorage.AppDatabase
import com.example.appmedicamentos.testebancomedicamentos.daos.MedicamentoDaoTeste
import com.example.appmedicamentos.testebancomedicamentos.entities.Doses
import com.example.appmedicamentos.testebancomedicamentos.entities.HistoricoMedicamentos
import com.example.appmedicamentos.testebancomedicamentos.relations.MedicamentoComDoses
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MedicamentosAdapter(private val list: ArrayList<MedicamentoComDoses>, val context: Context): RecyclerView.Adapter<MedicamentosAdapter.ViewHolder>() {
    //todo o que o usuario deve fazer se ele esquecer de tomar a dose?
    //todo ele n pode tomar duas
    //todo se eu colocar uns view pagers com algumas dicas?
    //todo view pager: esqueceu de tomar dose?
    //todo colocar um botao no viewPager bem claro
    //todo para o usuario mais leigo conseguir entrar no app
    var idNotificacao = 1

    private var db: AppDatabase? = null
    private lateinit var medicamentoDoseDao: MedicamentoDaoTeste
    var listaComDosesToast: MutableLiveData<List<Doses>> = MutableLiveData()

    init{
        db = AppDatabase.getAppDatabase(context)
        //medicamentoDoseDao = db!!.medicamentoDoseDao()
    }








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
                           val sdf = SimpleDateFormat("dd/MM/yyyy")
                            val c = Calendar.getInstance()
                            val date = sdf.format(c.time)


                            db?.insertNaTabelaHistoricoMedicamentos(HistoricoMedicamentos(
                                medicamento.medicamentoTeste.nomeMedicamento,
                                medicamento.medicamentoTeste.totalDiasTratamento,
                                date
                            ))


                            db?.deleteMedicamentoFromMedicamentoTeste(medicamento.medicamentoTeste.nomeMedicamento)
                            db?.deleteDosesDoMedicamentoFinalizado(medicamento.medicamentoTeste.nomeMedicamento)
                            Log.d("terminoumedicamento", "Voce terminou de tomar esse medicamento!")
                        }

                    }

                }

            }


            binding.horaProximaDose.text = proxDose


            db = AppDatabase.getAppDatabase(binding.root.context)
            medicamentoDoseDao = db!!.medicamentoDaoTeste



            //aqui começa o codigo de tentativa de criação das notificações
            createNotificationChannel()
            scheduleNotification(proxDose, medicamento.medicamentoTeste.nomeMedicamento)


            binding.medicamento.setOnClickListener {
                //se eu conseguir passar um objeto aqui na intent
                val intent = Intent(binding.root.context, MedicineDetailActivity::class.java)
                intent.putExtra("medicamento", medicamento)
                binding.root.context.startActivity(intent)

            }
            binding.medName.text = medicamento?.medicamentoTeste?.nomeMedicamento

        }

    }

    private fun scheduleNotification(proxDose: String?, nomeMedicamento: String) {
        idNotificacao++
        val intent = Intent(context, Notification::class.java)
        val title = "Tome sua Dose de ${nomeMedicamento}"
        val message = "${nomeMedicamento} dose das ${proxDose!!}, pra ir tomar a dose clique aqui."
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        //mas a cada iteraçao do bind ele tras um medicamento com horario diferente
        //ou seja, a iteração entre os itens da lista ja esta sendo feita



        val pendingIntent = PendingIntent.getBroadcast(
            context,
            idNotificacao,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime(proxDose)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )


    }

    private fun getTime(proxDose: String): Long {
        var minute: Int = 0
        var hour: Int = 0
        Log.d("testehoranot", "proxDose length: ${proxDose.length}")
        Log.d("testehoranot", "proxDose: ${proxDose}")
        if(proxDose.length == 6){
            minute = (proxDose[3].toString()+proxDose[4].toString()).toInt()
            hour = (proxDose[0].toString()+proxDose[1].toString()).toInt()
            Log.d("testehoranot", "to no primeiro if")
        }
        if(proxDose.length == 5){
            minute = (proxDose[2].toString()+proxDose[3].toString()).toInt()
            hour = (proxDose[0].toString()).toInt()
            Log.d("testehoranot", "to no segundo if")
        }
        //por enquanto a data vai estar hardcoded aqui
        val day = 25
        val month = 9
        val year = 2022
        Log.d("testehoranot", "hora: ${hour}  minutos: ${minute}")



        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        Log.d("testehoranot", "hora: ${hour}  minutos: ${minute}")
        return calendar.timeInMillis

    }

    private fun createNotificationChannel() {
        val name = "Canal Notificação"
        val desc = "testando notifications aqui dentro do adapter"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc

        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)




    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMedicamentoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val medicamento = list?.get(position)
        holder.bind(medicamento)

    }



    override fun getItemCount(): Int{
        Log.d("testehoranot", "list size: ${list.size}")
        return list.size
    }

}

//testar com dois medicamentos diferentes com dos minutos de diferença entre as doses deles

//a notificação de 9:27 n chegou
//vamos ver a de 9:29

//a de 9:29 chegou, ou seja, provavelmente a segunda notificação está sobreescrevendo a primeira

/*
Eu tenho que achar um jeito de criar notificacoes de acordo com o numero de medicamentos, assim
talvez eu assegure que todas as notificações necessárias apareçam

por exemplo: se a pessoa n tomou a dose das 10 vai ficar aparecendo ate ela tomar?
se tiver muito tempo, é melhor ela tomar a proxima
 */
