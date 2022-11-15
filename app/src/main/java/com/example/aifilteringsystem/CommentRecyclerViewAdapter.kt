package com.example.aifilteringsystem

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CommentRecyclerViewAdapter(
    val commentsArrayList: ArrayList<String>,
    val context: Context,
    val inflater: LayoutInflater
) : RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder>() {
    lateinit var listener: OnItemClickListener

    // private val commentItem = commentsList.getJSONArray("items")
    private val commentItemsList = arrayListOf<CommentSnippet>()

    interface OnItemClickListener {
        //클릭시 동작할 함수
        fun onItemClick(v: View?, pos: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profile: ImageView = itemView.findViewById(R.id.profile)
        val authorName: TextView = itemView.findViewById(R.id.nick_name)
        val comment: TextView = itemView.findViewById(R.id.comment)
        val repliesComment: TextView = itemView.findViewById(R.id.replies_comment)

        init {
            repliesComment.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(it, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.comment_result_item, parent, false))
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

        // Log.d("testtest", commentsArrayList.get(0))
/*
        val topProfile = commentItem.getJSONObject(position)
            .getJSONObject("snippet")
            .getJSONObject("topLevelComment")
            .getJSONObject("snippet")
            .getString("authorProfileImageUrl")

        val topComment = commentItem.getJSONObject(position)
            .getJSONObject("snippet")
            .getJSONObject("topLevelComment")
            .getJSONObject("snippet")
            .getString("textDisplay")

        val topAuthorName = commentItem.getJSONObject(position)
            .getJSONObject("snippet")
            .getJSONObject("topLevelComment")
            .getJSONObject("snippet")
            .getString("authorDisplayName")

        // 답글 있으면 표시, 없으면 숨기기
        if (commentItem.getJSONObject(position).getJSONObject("snippet")
                .getInt("totalReplyCount") > 0
        ) {
            holder.repliesComment.visibility = View.VISIBLE
            holder.repliesComment.text =
                "답글 " + commentItem.getJSONObject(position).getJSONObject("snippet")
                    .getInt("totalReplyCount").toString() + "개 보기"
        } else {
            holder.repliesComment.visibility = View.GONE
        }

        // 프로필
        Glide.with(context)
            .load(topProfile)
            .centerCrop()
            .circleCrop()
            .into(holder.profile)

        // 닉네임
        holder.authorName.text = topAuthorName

        // 댓글
        holder.comment.text = topComment

 */
    }

    override fun getItemCount(): Int {
        //return commentItem.length()
        return 0
    }
/*
    fun getRepliesItemCount(): Int {
        var size: Int = 0

        for (i in 0 until commentItem.length()) {
            size += commentItem.getJSONObject(i).getJSONObject("snippet").getInt("totalReplyCount")
        }

        return size
    }*/

    fun settingData() {
        for (i in 0 until commentsArrayList.size) {
            val commentItems = Gson().fromJson(JSONObject(commentsArrayList.get(i)).toString(), CommentItems::class.java)

            for (j in 0 until commentItems.items.size) {
                commentItemsList.add(commentItems.items.get(j))
            }
        }

        var count = 0

        for (i in 0 until commentItemsList.size) {
            count += commentItemsList.get(i).snippet.totalReplyCount
        }

        Log.d("testtest", commentItemsList.size.toString() + count)
    }
}