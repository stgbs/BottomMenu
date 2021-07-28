package com.anroid.bottommenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.ToggleButton

class ForumActivity : AppCompatActivity() {

    /*    lateinit var dbManager: DBManager
    lateinit var sqLiteDatabase: SQLiteDatabase*/

    lateinit var btn_edit: Button

    lateinit var textView_content_1: TextView
    lateinit var editText_content_1: EditText
    lateinit var textView_content_2: TextView
    lateinit var editText_content_2: EditText
    lateinit var textView_content_3: TextView
    lateinit var editText_content_3: EditText
    lateinit var textView_content_4: TextView
    lateinit var editText_content_4: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum)


/*        dbManager = DBManager(this, "forum", null, 1)*/
        btn_edit = findViewById<Button>(R.id.btn_edit)

        textView_content_1 = findViewById<TextView>(R.id.textView_content_1)
        editText_content_1 = findViewById<EditText>(R.id.editText_content_1)
        textView_content_2 = findViewById<TextView>(R.id.textView_content_2)
        editText_content_2 = findViewById<EditText>(R.id.editText_content_2)
        textView_content_3 = findViewById<TextView>(R.id.textView_content_3)
        editText_content_3 = findViewById<EditText>(R.id.editText_content_3)
        textView_content_4 = findViewById<TextView>(R.id.textView_content_4)
        editText_content_4 = findViewById<EditText>(R.id.editText_content_4)

        btn_edit.setOnClickListener {
            if (btn_edit.text == "수정") {
                btn_edit.setText("저장")

                textView_content_1.visibility = View.GONE
                editText_content_1.visibility = View.VISIBLE
                textView_content_2.visibility = View.GONE
                editText_content_2.visibility = View.VISIBLE
                textView_content_3.visibility = View.GONE
                editText_content_3.visibility = View.VISIBLE
                textView_content_4.visibility = View.GONE
                editText_content_4.visibility = View.VISIBLE

            } else {
                btn_edit.setText("수정")

                textView_content_1.visibility = View.VISIBLE
                editText_content_1.visibility = View.GONE
                textView_content_2.visibility = View.VISIBLE
                editText_content_2.visibility = View.GONE
                textView_content_3.visibility = View.VISIBLE
                editText_content_3.visibility = View.GONE
                textView_content_4.visibility = View.VISIBLE
                editText_content_4.visibility = View.GONE

            }
        }
    }
}
