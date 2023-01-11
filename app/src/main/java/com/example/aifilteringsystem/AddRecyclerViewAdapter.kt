package com.example.aifilteringsystem

import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView

class AddRecyclerViewAdapter(
    val list: ArrayList<String>,
    val inflater: LayoutInflater
) : RecyclerView.Adapter<AddRecyclerViewAdapter.ViewHolder>() {
    val editTextList = arrayListOf<EditText>()

    // url_item xml 인플레이트
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.url_item, parent, false))
    }

    // 뷰의 포지션마다 View를 준다
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.urlText.text = list.get(position)
    }

    // urlTextList 크기
    override fun getItemCount(): Int {
        return list.size
    }

    fun addItem() {
        notifyDataSetChanged()
    }

    fun removeItem() {
        editTextList.removeLast()
        notifyDataSetChanged()
    }

    /*
    fun getItem() : String{
        return strings.get(position)
    }*/

    // 뷰홀더 -> url 입력창 생성
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var urlText = itemView.findViewById<EditText>(R.id.url_edit)
    }
}