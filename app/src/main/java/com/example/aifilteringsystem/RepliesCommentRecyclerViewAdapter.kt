package com.example.aifilteringsystem

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONArray
import org.json.JSONObject

class RepliesCommentRecyclerViewAdapter(
    val repliesCommentList: JSONArray,
    val context: Context,
    val inflater: LayoutInflater
) : RecyclerView.Adapter<RepliesCommentRecyclerViewAdapter.ViewHolder>() {
    // lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        //클릭시 동작할 함수
        fun onItemClick(v: View?, pos: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repliesProfile: ImageView = itemView.findViewById(R.id.profile)
        val repliesAuthorName: TextView = itemView.findViewById(R.id.nick_name)
        val repliesComment: TextView = itemView.findViewById(R.id.comment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.replies_comment_result_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*
        val positive = "긍정 : " + String.format(
            "%.2f",
            resultList.get(position).document.confidence.positive
        ) + "%"
        val negative = "부정 : " + String.format(
            "%.2f",
            resultList.get(position).document.confidence.negative
        ) + "%"
        val neutral = "중립 : " + String.format(
            "%.2f",
            resultList.get(position).document.confidence.neutral
        ) + "%"

        holder.outputTextView.text = positive + "\n" + negative + "\n" + neutral
        holder.textOut.text = resultText.get(position)

        val pos = resultList.get(position).document.confidence.positive
        val neg = resultList.get(position).document.confidence.negative
        val neu = resultList.get(position).document.confidence.neutral

        val max = maxOf(pos, neg, neu)

        if(max == pos) holder.textBackground.setBackgroundColor(Color.parseColor("#B2CCFF"))
        else if(max == neg) holder.textBackground.setBackgroundColor(Color.parseColor("#FFD8D8"))
        else holder.textBackground.setBackgroundColor(Color.parseColor("FFFFFF"))
        */

        val repliesProfile = repliesCommentList
            .getJSONObject(position)
            .getJSONObject("snippet")
            .getString("authorProfileImageUrl")

        val repliesAuthorName = repliesCommentList
            .getJSONObject(position)
            .getJSONObject("snippet")
            .getString("authorDisplayName")

        val repliesComment = repliesCommentList
            .getJSONObject(position)
            .getJSONObject("snippet")
            .getString("textDisplay")

        Glide.with(context)
            .load(repliesProfile)
            .centerCrop()
            .circleCrop()
            .into(holder.repliesProfile)

        holder.repliesAuthorName.text = repliesAuthorName
        holder.repliesComment.text = repliesComment
    }

    override fun getItemCount(): Int {
        return repliesCommentList.length()
    }
}