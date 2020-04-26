package com.example.newlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.newlogin.Model.ModelUser
import com.example.newlogin.singletons.MyDatabase
import com.example.newlogin.singletons.MyFirebaseAuth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(){

    lateinit var cvRegister:CardView
    lateinit var edtRegisterEmail:EditText
    lateinit var edtRegisterPassword:EditText
    lateinit var tvLogin:TextView
    lateinit var myFireBaseAuth:FirebaseAuth
    lateinit var myFirebaseDatabase:FirebaseDatabase
    lateinit var edtRegisterPekerjaan:EditText
    lateinit var edtRegisterGaji:EditText
    lateinit var edtRegisterNamaLengkap : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myFireBaseAuth = MyFirebaseAuth.getMyAuth()
        myFirebaseDatabase = MyDatabase.getDatabase()
        initProps()
        cvRegister.setOnClickListener {
            var email = edtRegisterEmail.text.trim().toString()
            var password = edtRegisterPassword.text.trim().toString()
            var noHp = edtRegisterPekerjaan.text.trim().toString()
            var alamat =  edtRegisterGaji.text.trim().toString()
            var namaLengkap = edtRegisterNamaLengkap .text.trim().toString()
            createAccount(namaLengkap,email,noHp,alamat,password,myFireBaseAuth,myFirebaseDatabase)
        }
        tvLogin.setOnClickListener {
            val intent: Intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            MainActivity@this.onStop()
        }


    }

    fun createAccount(namaLengkap:String,email:String,noHp:String,alamat:String,password:String,firebaseAuth: FirebaseAuth,firebaseDatabase: FirebaseDatabase){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                val user = firebaseAuth.currentUser
                val userID = user!!.uid
                val userMap = HashMap<String,ModelUser>()
                userMap.put(userID,ModelUser(namaLengkap,email,alamat,noHp,password))
                firebaseDatabase.
                    getReference("Users")
                    .setValue(userMap).addOnCompleteListener {
                        if (it.isSuccessful){
                            user.sendEmailVerification().addOnCompleteListener {
                                if (it.isSuccessful){
                                    Toast.makeText(MainActivity@this,"Check Your Email",Toast.LENGTH_LONG).show()
                                }else{
                                    Toast.makeText(MainActivity@this,"Something Went Wrong",Toast.LENGTH_LONG).show()
                                }
                            }
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
        edtRegisterGaji=findViewById(R.id.edtRegisterPassword)
        edtRegisterPekerjaan=findViewById(R.id.edtRegisterAlamat)
        cvRegister = findViewById(R.id.cvRegister)
        edtRegisterEmail = findViewById(R.id.edtRegisterEmail)
        edtRegisterPassword = findViewById(R.id.edtRegisterNoHp)
        edtRegisterNamaLengkap = findViewById(R.id.edtRegisterNamaLengkap)
        tvLogin = findViewById(R.id.tvLogin)
    }
}
