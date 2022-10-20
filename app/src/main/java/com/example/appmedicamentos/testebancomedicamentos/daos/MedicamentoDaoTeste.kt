package com.example.appmedicamentos.testebancomedicamentos.daos

import androidx.room.*
import com.example.appmedicamentos.testebancomedicamentos.entities.Doses
import com.example.appmedicamentos.testebancomedicamentos.entities.HistoricoMedicamentos
import com.example.appmedicamentos.testebancomedicamentos.entities.MedicamentoTeste
import com.example.appmedicamentos.testebancomedicamentos.relations.MedicamentoComDoses

@Dao
interface MedicamentoDaoTeste {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMedicamento(medicamentoTeste: MedicamentoTeste): Long


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDose(dose: Doses): Long




    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNaTabelaHistoricoMedicamentos(novoMedicamentoFinalizado: HistoricoMedicamentos): Long




    @Transaction
    @Query("SELECT * FROM MedicamentoTeste WHERE nomeMedicamento = :nomeMedicamento")
    suspend fun getMedicamentoWithDoses(nomeMedicamento: String): List<MedicamentoComDoses>

    @Transaction
    @Query("SELECT * FROM MedicamentoTeste")
    fun getAllMedicamentoWithDoses(): List<MedicamentoComDoses>

    @Query("SELECT * FROM medicamentoTeste WHERE tratamentoFinalizado = 0")
    fun getAllMedicamentosInUse(): List<MedicamentoTeste>

    @Query("SELECT * FROM Doses WHERE nomeMedicamento = :nomeMedicamento")
    suspend fun getAllDoses(nomeMedicamento: String): List<Doses>

    @Query("UPDATE doses SET jaTomouDose=:tomou WHERE horarioDose=:horaDose")
    suspend fun tomarDoseMedicamento(tomou: Boolean, horaDose: String)

    @Query("UPDATE medicamentoteste SET tratamentoFinalizado=:finalizado WHERE nomeMedicamento=:nomeRemedio")
    suspend fun finalizarMedicamento(finalizado: Boolean, nomeRemedio: String)

    @Query("UPDATE medicamentoteste SET diasRestantesDeTratamento=:diasRestantes WHERE nomeMedicamento=:nomeRemedio")
    suspend fun diaConcluido(diasRestantes: Int, nomeRemedio: String)

    @Query("UPDATE doses SET jaTomouDose=:naoTomou WHERE nomeMedicamento=:nomeRemedio")
    suspend fun resetarDosesTomadasParaDiaNovoDeTratamento(naoTomou: Boolean, nomeRemedio: String)

    @Query("DELETE FROM medicamentoteste WHERE nomeMedicamento =:nomeRemedio")
    fun deleteMedicamentoFromMedicamentoTeste(nomeRemedio: String)

    @Query("DELETE FROM Doses WHERE nomeMedicamento = :nomeRemedio")
    fun deleteDosesDoMedicamentoFinalizado(nomeRemedio: String)



}