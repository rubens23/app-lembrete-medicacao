package com.example.appmedicamentos.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmedicamentos.data.repository.MedicationRepositoryImpl
import com.example.appmedicamentos.domain.repository.MedicationRepository
import com.example.appmedicamentos.models.Medicamento
import com.example.appmedicamentos.testebancomedicamentos.entities.MedicamentoTeste
import com.example.appmedicamentos.testebancomedicamentos.relations.MedicamentoComDoses
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val medicationRepository: MedicationRepositoryImpl
): ViewModel(){

    lateinit var medicamentos: MutableLiveData<List<MedicamentoComDoses>>

    init{
        medicamentos = MutableLiveData()
        loadMedications()
    }

    //fun getMedicamentosObserver(): MutableLiveData<List<MedicamentoComDoses>> = medicamentos

    fun loadMedications(){
        val list = medicationRepository.getMedicamentos()

        medicamentos.postValue(list)
    }

    fun insertMedicamentos(medicamento: MedicamentoTeste){
        medicationRepository.insertMedicamento(medicamento)
    }


}