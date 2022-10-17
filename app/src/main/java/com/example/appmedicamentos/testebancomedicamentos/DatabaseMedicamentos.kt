package com.example.appmedicamentos.testebancomedicamentos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appmedicamentos.testebancomedicamentos.daos.MedicamentoDaoTeste
import com.example.appmedicamentos.testebancomedicamentos.entities.Doses
import com.example.appmedicamentos.testebancomedicamentos.entities.MedicamentoTeste


@Database(
    entities = [
        MedicamentoTeste::class,
        Doses::class
    ],
    version = 1
)
abstract class DatabaseMedicamentos: RoomDatabase() {

    abstract val medicamentoDaoTeste: MedicamentoDaoTeste

    companion object{
        @Volatile
        private var INSTANCE: DatabaseMedicamentos? = null

        fun getInstance(context: Context): DatabaseMedicamentos{
            synchronized(this){
                return INSTANCE?: Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseMedicamentos::class.java,
                    "school_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}