package com.example.appmedicamentos.data.repository

import com.example.appmedicamentos.testebancomedicamentos.daos.MedicamentoDaoTeste
import com.example.appmedicamentos.testebancomedicamentos.entities.MedicamentoTeste
import com.example.appmedicamentos.testebancomedicamentos.relations.MedicamentoComDoses
import javax.inject.Inject

class MedicationRepositoryImpl @Inject constructor(
    private val medicamentoDao: MedicamentoDaoTeste,
){
    fun getMedicamentos(): List<MedicamentoComDoses>?{
        return medicamentoDao.getAllMedicamentoWithDoses()
    }

    fun insertMedicamento(medicamento: MedicamentoTeste){
        //medicamentoDao.insertMedicamento(medicamento)
    }

    // TODO: resolver o erro error: incompatible types: MedicamentoDaoTeste cannot be converted to MedicamentoDao
    //    return RoomModule_ProvideMedicamentosDaoFactory.provideMedicamentosDao(provideDBProvider.get());

}