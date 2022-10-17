package com.example.aifilteringsystem

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

// Todo url_item 아이디값
class AddRecyclerViewAdapter(
    val urlTextList: ArrayList<View>,
    val inflater: LayoutInflater
) : RecyclerView.Adapter<AddRecyclerViewAdapter.ViewHolder>() {

    // url_item xml 인플레이트
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.url_item, parent, false))
    }

    // 뷰의 포지션마다 View를 준다
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = urlTextList.get(position).findViewById<EditText>(R.id.url_edit)
        holder.urlText.hint = view.hint
    }

    // urlTextList 크기
    override fun getItemCount(): Int {
        return urlTextList.size
    }

    fun addItem(view: View) {
        urlTextList.add(view)
        notifyDataSetChanged()
    }

    fun removeItem() {
        urlTextList.removeLast()
        notifyDataSetChanged()
    }

    // 뷰홀더 -> url 입력창 생성
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val urlText = itemView.findViewById<EditText>(R.id.url_edit)

        init {
            urlText.setOnClickListener {
                Log.d("LogLog", "" + it.id)
            }
        }
    }
}