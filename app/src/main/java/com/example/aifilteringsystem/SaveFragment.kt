package com.example.aifilteringsystem

import android.content.Intent
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class SaveFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.save_fragment, container, false)

        val editText = view.findViewById<EditText>(R.id.edit)
        val button = view.findViewById<Button>(R.id.button)
        val outputText = view.findViewById<TextView>(R.id.text)

        button.setOnClickListener {
            outputText.text = "d"
        }

        return view
    }
}