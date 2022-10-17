package com.example.appmedicamentos.localstorage.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.appmedicamentos.MedicamentoDose
import com.example.appmedicamentos.models.HorarioDose
import com.example.appmedicamentos.models.Medicamento

@Dao
interface MedicamentoDoseDao {

    @Query("SELECT * FROM medicamentodose")
    suspend fun getAll(): List<MedicamentoDose>

    @Query("SELECT * FROM medicamentodose WHERE nomeMedicamento = :nomeMedicamento")
    suspend fun getAllDoses(nomeMedicamento: String): List<MedicamentoDose>

    @Insert
    suspend fun insertNovaDose(medicamentoDose: List<MedicamentoDose>)

    @Query("UPDATE medicamentodose SET TomouDose=:tomou WHERE horarioDose=:horaDose")
    suspend fun tomarDoseMedicamento(tomou: Boolean, horaDose: String)

    //todo como faço pra atualizar uma lista de objetos dentro de uma coluna da tabela
    //todo xafurdar naquele post do stackoverflow para ver como fazer isso
    //é provavel que minha query tenha que ser maior do que uma linha

    @Query("DELETE FROM medicamentodose")
    suspend fun deleteAllFromMedicamentoDose()

    /*
    - colocar os dados na tabela
    - dar um toast com o horario que a pessoa apertou a bolinha vermelha
    - Você confirma que tomou a dose de tal hora? SIM, CANCEL
    se sim:
    -mudar tomouDose desse horario para true
    -mudar bolinha para a cor verde.

     */
}