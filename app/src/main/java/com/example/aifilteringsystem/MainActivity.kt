package com.example.aifilteringsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.main_recyclerView)
        recyclerView.adapter = RecyclerViewAdapter(layoutInflater)

//        recyclerView.adapter.notifyDataSetChanged()

        findViewById<AppCompatButton>(R.id.plus_button).setOnClickListener {

        }

        findViewById<AppCompatButton>(R.id.minus_button).setOnClickListener {

        }
    }

    class RecyclerViewAdapter(
        val inflater: LayoutInflater
    ) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val urlEditText = itemView.findViewById<EditText>(R.id.url_edit)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(inflater.inflate(R.layout.url_item, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        }

        override fun getItemCount(): Int {
            return 0
        }
    }
}