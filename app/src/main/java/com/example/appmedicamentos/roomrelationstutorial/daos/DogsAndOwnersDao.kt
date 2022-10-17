package com.example.appmedicamentos.roomrelationstutorial.daos

import androidx.room.*
import com.example.appmedicamentos.models.Medicamento
import com.example.appmedicamentos.roomrelationstutorial.entities.Dog
import com.example.appmedicamentos.roomrelationstutorial.entities.DogAndOwner

@Dao
interface DogsAndOwnersDao {
    @Transaction
    @Query("SELECT * FROM Owner")
    fun getDogsAndOwners(): List<DogAndOwner>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNewDog(dog: Dog)
}