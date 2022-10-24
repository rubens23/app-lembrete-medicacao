package com.example.appmedicamentos.localstorage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appmedicamentos.testebancomedicamentos.daos.MedicamentoDaoTeste
import com.example.appmedicamentos.testebancomedicamentos.entities.Doses
import com.example.appmedicamentos.testebancomedicamentos.entities.HistoricoMedicamentos
import com.example.appmedicamentos.testebancomedicamentos.entities.MedicamentoTeste

@Database(
    entities = [MedicamentoTeste::class,
        Doses::class,
        HistoricoMedicamentos::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    //todo: resolver esse erro do n reconhecimento da entity


    abstract val medicamentoDaoTeste: MedicamentoDaoTeste

    companion object {

        private var INSTANCE: AppDatabase? = null


        fun getAppDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {

                INSTANCE = Room.databaseBuilder<AppDatabase>(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "banco-app-medicamentos"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build()
            }
            return INSTANCE
        }

    }
    }