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
    lateinit var plusBtn: AppCompatButton
    lateinit var minusBtn: AppCompatButton
    lateinit var makeBtn: AppCompatButton
    lateinit var recyclerView: RecyclerView
    lateinit var addAdapter: AddRecyclerViewAdapter
    var urlTextList = ArrayList<View>() // url 입력창의 생성 개수

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addAdapter.addItem(layoutInflater.inflate(R.layout.url_item, recyclerView, false))

        // + 이벤트 리스너
        plusBtn.setOnClickListener {
            if (urlTextList.size > 9) {
                Toast.makeText(context, "10개가 최대입니다.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Log.d("LogLog", addAdapter.itemCount.toString())
                addAdapter.addItem(layoutInflater.inflate(R.layout.url_item, recyclerView, false))
            }
        }

        // - 이벤트 리스너
        minusBtn.setOnClickListener {
            if (urlTextList.size <= 1) {
                Toast.makeText(context, "더 이상 없앨 수 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                addAdapter.removeItem()
            }
        }

        // 만들기 버튼 리스너
        makeBtn.setOnClickListener {
//            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_fragment, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        addAdapter = AddRecyclerViewAdapter(urlTextList, inflater)
        recyclerView.adapter = addAdapter
        plusBtn = view.findViewById(R.id.plus_button)
        minusBtn = view.findViewById(R.id.minus_button)
        makeBtn = view.findViewById(R.id.make_button)

        return view
    }
}