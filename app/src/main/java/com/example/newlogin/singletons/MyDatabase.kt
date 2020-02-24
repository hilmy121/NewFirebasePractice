package com.example.newlogin.singletons

import com.google.firebase.database.FirebaseDatabase

class MyDatabase {
    companion object{
        private var myDatabase:FirebaseDatabase = FirebaseDatabase.getInstance()

        fun getDatabase():FirebaseDatabase{
            return myDatabase
        }
    }
}