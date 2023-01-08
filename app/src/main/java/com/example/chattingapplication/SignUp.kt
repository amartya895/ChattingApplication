package com.example.chattingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    lateinit var userName:EditText
    lateinit var userMail: EditText
    lateinit var userPassword: EditText
    lateinit var btnSignup: Button
    lateinit var mAuth: FirebaseAuth
    lateinit var mDbRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()

        userMail = findViewById(R.id.et_user)
        userPassword = findViewById(R.id.et_password)//password - 12345678
        userName = findViewById(R.id.et_name)
        btnSignup = findViewById(R.id.btn_signup)

        mAuth = FirebaseAuth.getInstance()

        btnSignup.setOnClickListener {
            val name = userName.text.toString()
            val email = userMail.text.toString()
            val password = userPassword.text.toString()

            signUp(name,email,password)


            userName.text.clear()
            userMail.text.clear()
            userPassword.text.clear()

        }

    }

    private fun signUp(userName:String,email:String,password:String)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(userName,email, mAuth.currentUser?.uid!!)
                    Toast.makeText(this@SignUp,"SignUp Successful",Toast.LENGTH_LONG).show()
                    val intent = Intent(this,MainActivity::class.java)
                    finish()
                    startActivity(intent)



                } else {
                    Toast.makeText(this@SignUp, "Some Error occured while Signingup", Toast.LENGTH_LONG).show()
                }
            }

    }

    private fun addUserToDatabase(userName: String,email: String,uid:String){
        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(userModel(userName,email,uid))

    }
}