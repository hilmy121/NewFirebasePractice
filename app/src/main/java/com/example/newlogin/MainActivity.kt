package com.example.newlogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.newlogin.Model.ModelUser
import com.example.newlogin.singletons.MyDatabase
import com.example.newlogin.singletons.MyFirebaseAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(){

    lateinit var btnRegister:Button
    lateinit var edtRegisterEmail:EditText
    lateinit var edtRegisterPassword:EditText
    lateinit var tvLogin:TextView
    lateinit var myFireBaseAuth:FirebaseAuth
    lateinit var myFirebaseDatabase:FirebaseDatabase
    lateinit var edtRegisterPekerjaan:EditText
    lateinit var edtRegisterGaji:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myFireBaseAuth = MyFirebaseAuth.getMyAuth()
        myFirebaseDatabase = MyDatabase.getDatabase()
        initProps()
        btnRegister.setOnClickListener {
            var email = edtRegisterEmail.text.trim().toString()
            var password = edtRegisterPassword.text.trim().toString()
            var pekerjaan = edtRegisterPekerjaan.text.trim().toString()
            var gaji =  edtRegisterGaji.text.trim().toString().toInt()
            createAccount(email,password,pekerjaan,gaji,myFireBaseAuth,myFirebaseDatabase)
        }


    }

    fun createAccount(email:String,password:String,pekerjaan:String,gaji:Int,firebaseAuth: FirebaseAuth,firebaseDatabase: FirebaseDatabase){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                val user = firebaseAuth.currentUser
                val userID = user!!.uid
                var userMap = HashMap<String,ModelUser>()
                userMap.put(userID,ModelUser(email,password,pekerjaan,gaji))
                firebaseDatabase.
                    getReference("Users")
                    .setValue(userMap).addOnCompleteListener {
                        if (it.isSuccessful){

                        }else{
                            Log.i("Result",it.exception.toString())
                        }
                    }

            }else{
                Log.i("Error",it.exception.toString())
            }
        }
    }

    fun initProps(){
        edtRegisterGaji=findViewById(R.id.edtRegisterGaji)
        edtRegisterPekerjaan=findViewById(R.id.edtRegisterPekerjaan)
        btnRegister = findViewById(R.id.btnRegister)
        edtRegisterEmail = findViewById(R.id.edtRegisterEmail)
        edtRegisterPassword = findViewById(R.id.edtRegisterPassword)
        tvLogin = findViewById(R.id.tvLogin)
    }
}
