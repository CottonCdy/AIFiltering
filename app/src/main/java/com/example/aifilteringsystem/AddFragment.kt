package com.example.aifilteringsystem

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

// TODO: 2022-10-16 : 만들기 버튼에 리스너 달기, 해당 url 영상 가져오기

class AddFragment : Fragment() {
    val list = arrayListOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.add("")

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = AddRecyclerViewAdapter(list, layoutInflater)
        recyclerView.adapter = adapter



        val plusBtn = view.findViewById<AppCompatButton>(R.id.plus_button)
        // + 이벤트 리스너
        plusBtn.setOnClickListener {
            if (adapter.editTextList.size > 9) {
                Toast.makeText(context, "10개가 최대입니다.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Log.d("testtest", adapter.itemCount.toString())
                adapter.addItem(layoutInflater.inflate(R.layout.url_item, null, false))
            }
        }

        val minusBtn = view.findViewById<AppCompatButton>(R.id.minus_button)
        // - 이벤트 리스너
        minusBtn.setOnClickListener {
            if (adapter.editTextList.size <= 1) {
                Toast.makeText(context, "더 이상 없앨 수 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                adapter.removeItem()
            }
        }

        val makeBtn = view.findViewById<AppCompatButton>(R.id.make_button)
        // 만들기 버튼 리스너
        makeBtn.setOnClickListener {
            // Todo: make btn save
            //for (i in 0 until adapter.itemCount) {
                Log.d("testtest", "" + adapter.editTextList.get(0).text)
            //}
//            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_fragment, container, false)
    }
}