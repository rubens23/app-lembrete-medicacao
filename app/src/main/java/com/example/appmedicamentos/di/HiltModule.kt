package com.example.appmedicamentos.di

import android.content.Context
import com.example.appmedicamentos.data.repository.AddMedicineRepositoryImpl
import com.example.appmedicamentos.data.repository.MedicationRepositoryImpl
import com.example.appmedicamentos.localstorage.AppDatabase
import com.example.appmedicamentos.testebancomedicamentos.daos.MedicamentoDaoTeste
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context): AppDatabase? {
        return AppDatabase.getAppDatabase(context)
    }

    @Singleton
    @Provides
    fun provideMedicamentosDao(db: AppDatabase?): MedicamentoDaoTeste {
        return db!!.medicamentoDaoTeste
    }

    @Provides
    @Singleton
    fun providesMedicamentoRepository(dao: MedicamentoDaoTeste): MedicationRepositoryImpl {
        return MedicationRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun providesAddMedicamentosRepository(dao: MedicamentoDaoTeste): AddMedicineRepositoryImpl{
        return AddMedicineRepositoryImpl(dao)
    }

}