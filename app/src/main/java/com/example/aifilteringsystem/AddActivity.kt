package com.example.aifilteringsystem

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: 2022-09-10 : View ID 생성 및 문자열 ID

class AddActivity : AppCompatActivity() {
    var itemSize = 0 // urlText 개수
    var itemId = 0
    lateinit var view: View
    lateinit var urlText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val layout = findViewById<LinearLayoutCompat>(R.id.linear)

        getUrlTextView() // View(EditText) 생성
        layout.addView(view)

        // + 이벤트 리스너
        findViewById<AppCompatButton>(R.id.plus_button).setOnClickListener {
            if (itemSize > 8) {
                Toast.makeText(this, "10개가 최대입니다.", Toast.LENGTH_SHORT).show()
            } else {
                getUrlTextView() // View(EditText) 생성
                layout.addView(view)
                itemSize++
            }

//            Log.d("LogLog", "plus:" + itemSize)
        }

        // - 이벤트 리스너
        findViewById<AppCompatButton>(R.id.minus_button).setOnClickListener {
//            Log.d("LogLog", "minus:" + itemSize)
            if (itemSize < 1) {
                Toast.makeText(this, "더 이상 없앨 수 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                layout.removeViewAt(itemSize)
                itemId--
                itemSize--
            }
        }

        findViewById<AppCompatButton>(R.id.make_button).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    // url input text 동적 생성
    fun getUrlTextView(): View {
        view = layoutInflater.inflate(R.layout.url_item, null, false) // url_item 레이아웃 생성
        urlText = view.findViewById(R.id.url_edit) // EditText 생성
        urlText.id = ++itemId
//        urlText.id = View.generateViewId() // id 생성
        Log.d("LogLog", "" + urlText.id)
        urlText.hint = "input url" + (urlText.id)
        return view
    }
}

/*
    class RecyclerViewAdapter(
        val inflater: LayoutInflater
    ) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
        var itemSize: Int = 0

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val urlEditText = itemView.findViewById<EditText>(R.id.url_edit)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(inflater.inflate(R.layout.url_item, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        }

        override fun getItemCount(): Int {
            return itemSize
        }
    }
*/