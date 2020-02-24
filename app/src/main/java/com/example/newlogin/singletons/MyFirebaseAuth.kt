package com.example.newlogin.singletons

import com.google.firebase.auth.FirebaseAuth

class MyFirebaseAuth {


    companion object{
        private var MyFirebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()

        fun getMyAuth():FirebaseAuth{
            return MyFirebaseAuth
        }
    }
}