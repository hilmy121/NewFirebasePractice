package com.example.newlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.newlogin.singletons.MyDatabase
import com.example.newlogin.singletons.MyFirebaseAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    lateinit var tvRegister:TextView
    lateinit var cvbtnLogin:CardView
    lateinit var edtEmailLogin:EditText
    lateinit var edtPasswordLogin:EditText
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth=MyFirebaseAuth.getMyAuth()
        firebaseDatabase=MyDatabase.getDatabase()
        initProps()
        tvRegister.setOnClickListener {
            val intent:Intent = Intent(LoginActivity@this,MainActivity::class.java)
            startActivity(intent)
            LoginActivity@this.onStop()

        }
        cvbtnLogin.setOnClickListener {
            login(edtEmailLogin.text.trim().toString(),
                edtPasswordLogin.text.trim().toString(),
                firebaseAuth,
                firebaseDatabase)

        }
    }


    fun initProps(){
        cvbtnLogin = findViewById(R.id.cvbtnLogin)
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin)
        edtEmailLogin = findViewById(R.id.edtLoginEmail)
        tvRegister = findViewById(R.id.tvRegister)
    }

    fun login(email:String,password:String,firebaseAuth: FirebaseAuth,firebaseDatabase: FirebaseDatabase){

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            val user = firebaseAuth.currentUser

            if (it.isSuccessful){
                if (user!=null && user.isEmailVerified){
                val intent:Intent = Intent(LoginActivity@this,HomeActivity::class.java)
                startActivity(intent)
                LoginActivity@this.onStop()
                }
            }else{
                it.addOnFailureListener {
                    Log.i("Fail Cause",it.message.toString())
                    //Toast.makeText(LoginActivity@this,it.message.toString(),Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
