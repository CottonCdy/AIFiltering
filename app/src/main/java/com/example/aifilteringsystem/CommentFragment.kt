package com.example.aifilteringsystem

import android.content.Context
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject


class CommentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.comment_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = arguments
        val commentsArrayList = bundle?.getStringArrayList("commentsArrayList")

        // 댓글 리사이클러뷰
        val commentRecyclerView = view.findViewById<RecyclerView>(R.id.comment_recycler)
        val adapter = CommentRecyclerViewAdapter(commentsArrayList!!, requireContext(), layoutInflater)
        commentRecyclerView.adapter = adapter
        adapter.settingData() // 데이터 세팅
        adapter.setOnItemClickListener(object : CommentRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(v: View?, pos: Int) {
                // childFragmentManager.beginTransaction().replace(R.id.comment_container, RepliesCommentFragment()).commit()
                val repliesCommentFragment = RepliesCommentFragment()
                bundle?.putInt("position", pos)
                repliesCommentFragment.arguments = bundle
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.container, repliesCommentFragment)
                    addToBackStack(null)
                    commit()
                }
            }
        })

        val commentCountText = view.findViewById<TextView>(R.id.comment_count)

        //val commentCount = adapter.itemCount
        //val repliesCommentCount = adapter.getRepliesItemCount()

        //commentCountText.text = "댓글 " + commentCount + "개, " + "답글 " + repliesCommentCount + "개"
    }
/*
    fun commentsListOrganize() {
        val gson = Gson()
        val commentItemsList = arrayListOf<CommentItems>()

        for (i in 0 until commentsArrayList!!.size) {
            val commentItems = gson.fromJson(commentsArrayList?.get(i), CommentItems::class.java)

            for (j in 0 until commentItems.items.size) {
                val topProfile =
                    commentItems.items.get(j).snippet.topLevelComment.snippet.authorProfileImageUrl
                val topAuthorName =
                    commentItems.items.get(j).snippet.topLevelComment.snippet.authorDisplayName
                val topComment =
                    commentItems.items.get(j).snippet.topLevelComment.snippet.textDisplay

                Log.d("testtest", topProfile + " " + topAuthorName + " " + topComment)/*
                val repliesProfile = commentItems.items.get(j).replies.comments.
                val repliesAuthorName = commentItems.items.get(j).snippet.topLevelComment.snippet.authorDisplayName
                val repliesComment = commentItems.items.get(j).snippet.topLevelComment.snippet.textDisplay*/
            }
        }
    }*/
}