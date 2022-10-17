package com.example.appmedicamentos.domain.repository

import androidx.lifecycle.MutableLiveData
import com.example.appmedicamentos.models.Medicamento

interface MedicationRepository {

    fun getAllMedicamentosInUse(): MutableLiveData<Medicamento>

    suspend fun getAllMedicamentosDosages()
}