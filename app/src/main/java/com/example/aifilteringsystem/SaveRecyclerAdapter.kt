package com.example.aifilteringsystem

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.MotionEventCompat.getSource
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlin.math.max

class SaveRecyclerAdapter(
    val context: Context?,
    val inflater: LayoutInflater
) : RecyclerView.Adapter<SaveRecyclerAdapter.ViewHolder>() {
    private val resultList = arrayListOf<Data>()
    private val resultText = arrayListOf<String>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView
        val title: TextView

        init {
            thumbnail = itemView.findViewById(R.id.imageView)
            title = itemView.findViewById(R.id.title)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.save_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url = "https://img.youtube.com/vi/JngoTo1H8sY/default.jpg" //유튜브 썸네일 불러오는 방법
        Glide.with(context!!).load(url).into(holder.thumbnail)
        holder.title.text = "제목 테스트"
    }

    fun addItem(result: Data, text: String) {
        resultList.add(result)
        resultText.add(text)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return 3
    }
}