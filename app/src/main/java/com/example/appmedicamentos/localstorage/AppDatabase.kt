package com.example.appmedicamentos.localstorage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.appmedicamentos.MedicamentoDose
import com.example.appmedicamentos.localstorage.daos.MedicamentoDao
import com.example.appmedicamentos.localstorage.daos.MedicamentoDoseDao
import com.example.appmedicamentos.models.HorarioDosesTypeConverter
import com.example.appmedicamentos.models.Medicamento
import com.example.appmedicamentos.philliplacknerrelationtutorial.entities.relations.StudentSubjectCrossRef
import com.example.appmedicamentos.roomrelationstutorial.daos.DogsAndOwnersDao
import com.example.appmedicamentos.roomrelationstutorial.entities.Dog
import com.example.appmedicamentos.roomrelationstutorial.entities.Owner
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

    fun destroyInstance() {
        INSTANCE = null
    }
}