package com.example.appmedicamentos.data.repository

import com.example.appmedicamentos.testebancomedicamentos.daos.MedicamentoDaoTeste
import com.example.appmedicamentos.testebancomedicamentos.entities.Doses
import com.example.appmedicamentos.testebancomedicamentos.entities.MedicamentoTeste
import javax.inject.Inject

class AddMedicineRepositoryImpl @Inject constructor(
    private val medicamentoDao: MedicamentoDaoTeste
) {
    fun insertMedicamento(medicamento: MedicamentoTeste): Long{
        return medicamentoDao.insertMedicamento(medicamento)

    }

    fun insertDoses(doses: Doses): Long{
        return medicamentoDao.insertDose(doses)
    }


}