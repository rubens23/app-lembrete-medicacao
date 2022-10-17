package com.example.appmedicamentos.localstorage.daos

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appmedicamentos.models.Medicamento

@Dao
interface MedicamentoDao {
    @Query("SELECT * FROM medicamento")
    fun getAllMedicamentosPaciente(): List<Medicamento>

    @Query("SELECT * FROM medicamento WHERE tratamento_finalizado = 0")
    fun getAllMedicamentosInUse(): List<Medicamento>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNovoMedicamento(medicamento: Medicamento): Long

    @Query("DELETE FROM medicamento")
    suspend fun deleteAll()


}