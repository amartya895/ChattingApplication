package com.example.chattingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {
    lateinit var userMail:EditText
    lateinit var userPassword:EditText
    lateinit var btnLogin:Button
    lateinit var btnSignup:Button
    lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        supportActionBar?.hide()

        userMail = findViewById(R.id.et_user)
        userPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        btnSignup = findViewById(R.id.btn_signup)

        mAuth = FirebaseAuth.getInstance()

        btnSignup.setOnClickListener {
            var  intent = Intent(this,SignUp::class.java)
            finish()
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            var usermail = userMail.text.toString()
            var password = userPassword.text.toString()

            login(usermail,password)

            userMail.text.clear()
            userPassword.text.clear()
        }


    }

    private fun login(usermail:String,password:String)
    {
        mAuth.signInWithEmailAndPassword(usermail, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(this@LogIn, "Some Error occurred while SigningIn", Toast.LENGTH_LONG).show()
                }
            }
    }
}