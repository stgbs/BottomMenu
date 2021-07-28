package com.anroid.bottommenu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

class LogoutFragment : Fragment() {

    lateinit var btn_yes : Button
    lateinit var btn_no : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_logout, container, false)

        btn_yes = view.findViewById(R.id.btn_yes)
        btn_no = view.findViewById(R.id.btn_no)

        btn_yes.setOnClickListener {
            val intent = Intent(getActivity(), MainActivity::class.java)
            startActivity(intent)
            //Toast.makeText(MainActivity(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show() -> 안 뜸
        }

        return view
    }
}