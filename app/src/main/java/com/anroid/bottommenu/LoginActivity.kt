package com.anroid.bottommenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    lateinit var btn_login:Button
    lateinit var btn_register:Button
    lateinit var et_id:EditText
    lateinit var et_pass:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var dbHelper: DBHelper = DBHelper(applicationContext, "MEMBER.db", null, 1)

        btn_login=findViewById(R.id.btn_login)
        btn_register=findViewById(R.id.btn_register)
        et_id=findViewById(R.id.et_id)
        et_pass=findViewById(R.id.et_pass)

        btn_login.setOnClickListener {
            if(dbHelper.getResult1(et_id.getText().toString(), et_pass.getText().toString()) == true){
                val intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                var login_t= Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT)
                login_t.show()
            }
        }

        btn_register.setOnClickListener {
            val intent= Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}