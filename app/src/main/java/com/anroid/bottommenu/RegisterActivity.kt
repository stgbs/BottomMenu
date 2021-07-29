package com.anroid.bottommenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {

    lateinit var btn_register2:Button
    lateinit var et_email:EditText
    lateinit var et_name:EditText
    lateinit var et_pass:EditText
    lateinit var et_passck:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var dbHelper: DBHelper = DBHelper(applicationContext, "MEMBER.db", null, 1)

        btn_register2=findViewById(R.id.btn_register2)
        et_email=findViewById(R.id.et_email)
        et_name=findViewById(R.id.et_name)
        et_pass=findViewById(R.id.et_pass)
        et_passck=findViewById(R.id.et_passck)

        btn_register2.setOnClickListener {
            // 회원 정보를 저장하는 부분. 데이터베이스에~
            var email: String = et_email.getText().toString()
            var id: String = et_name.getText().toString()
            var password: String = et_pass.getText().toString()
            var password_ck: String = et_passck.getText().toString()


            if (email.length == 0 || id.length == 0 || password.length == 0
                || password_ck.length == 0) {
                var t1 = Toast.makeText(this, "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT)
                t1.show()
            }
            if(!password.equals(password_ck)){
                var t2 = Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT)
                t2.show()
            }
            else {
                dbHelper.insert(email, id, password, password_ck)
                var t3 = Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT)
                t3.show()
                val intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}