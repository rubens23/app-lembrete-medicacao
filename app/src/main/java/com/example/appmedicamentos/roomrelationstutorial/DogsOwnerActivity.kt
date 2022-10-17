package com.example.appmedicamentos.roomrelationstutorial


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.appmedicamentos.databinding.ActivityDogsOwnerBinding
import com.example.appmedicamentos.localstorage.AppDatabase
import com.example.appmedicamentos.roomrelationstutorial.entities.Dog
import kotlinx.coroutines.launch

class DogsOwnerActivity : AppCompatActivity() {

    private val binding: ActivityDogsOwnerBinding by lazy{
        ActivityDogsOwnerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val dbReference = AppDatabase.getAppDatabase(this)
        //val daoReference = dbReference?.dogsAndOwnersDao()

        /*
        lifecycleScope.launch {
            daoReference?.insertNewDog(Dog(
                1,
                1,
                "Sparky",
                3,
                4,
                "Dead Dog Revived"
            ))
        }

         */
    }
}