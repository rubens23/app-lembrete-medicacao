package com.example.appmedicamentos.models

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

@Entity
data class Medicamento(
    //todo medicamentos com tratamento finalizado devem ser excluidos dessa tabela e ir para outra(tabela historico de tratamento)
    @PrimaryKey
    @ColumnInfo(name = "nome_medicamento") val nomeMedicamento: String,
    @ColumnInfo(name = "quantidade_doses") val quantidadeDoses: Int,
    @ColumnInfo(name = "qnt_total_dias_tratamento")val QntDiasTratamento: Int,
    @ColumnInfo(name = "horario_primeira_dose") val horarioPrimeiraDose: String,
    @ColumnInfo(name = "tratamento_finalizado") var tratamentoFinalizado: Boolean = false,
    @ColumnInfo(name = "horarios_doses") val horariosDoses: List<HorarioDose>

): Serializable{
    @SuppressLint("NotConstructor")
    fun Medicamento(){

    }
}
class HorarioDosesTypeConverter {

    @TypeConverter
    fun listToJson(value: List<HorarioDose>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<HorarioDose>::class.java).toList()

}
/*
class HorarioDosesTypeConverter{
    @TypeConverter
    fun fromString(value: String?): ArrayList<String>{

        val listType = object: TypeToken<ArrayList<String>>(){}.type
        return Gson().fromJson(value, listType)

    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>): String{
        return Gson().toJson(list)


    }
}

 */
