package com.example.newlogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(){

    lateinit var btnRegister:Button
    lateinit var edtRegisterEmail:EditText
    lateinit var edtRegisterPassword:EditText
    lateinit var tvLogin:TextView
    lateinit var myFireBaseAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initProps()
    }

    fun initProps(){
        btnRegister = findViewById(R.id.btnRegister)
        edtRegisterEmail = findViewById(R.id.edtRegisterEmail)
        edtRegisterPassword = findViewById(R.id.edtRegisterPassword)
        tvLogin = findViewById(R.id.tvLogin)
    }
}
