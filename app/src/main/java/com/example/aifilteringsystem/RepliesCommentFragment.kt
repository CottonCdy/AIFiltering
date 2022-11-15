package com.example.aifilteringsystem

import android.content.Intent
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RepliesCommentFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.replies_comment_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val back = view.findViewById<TextView>(R.id.back_comment)
        back.setOnClickListener {

        }

        val bundle = arguments
        val commentsPosition = bundle?.getInt("position")
        val commentsList = JSONObject(bundle?.getString("commentsList"))
            .getJSONArray("items")
            .getJSONObject(commentsPosition!!)

        val topProfile = view.findViewById<ImageView>(R.id.profile)
        Glide.with(requireContext())
            .load(commentsList
                .getJSONObject("snippet")
                .getJSONObject("topLevelComment")
                .getJSONObject("snippet")
                .getString("authorProfileImageUrl"))
            .centerCrop()
            .circleCrop()
            .into(topProfile)

        val topAuthorName = view.findViewById<TextView>(R.id.nick_name)
        topAuthorName.text = commentsList
            .getJSONObject("snippet")
            .getJSONObject("topLevelComment")
            .getJSONObject("snippet")
            .getString("authorDisplayName")

        val topComment = view.findViewById<TextView>(R.id.comment)
        topComment.text = commentsList
            .getJSONObject("snippet")
            .getJSONObject("topLevelComment")
            .getJSONObject("snippet")
            .getString("textDisplay")

        val repliesComment = commentsList.getJSONObject("replies").getJSONArray("comments")

        // 답글 리사이클러뷰
        val RepliesCommentRecyclerViewAdapter = view.findViewById<RecyclerView>(R.id.replies_comment_recycler)
        val adapter = RepliesCommentRecyclerViewAdapter(repliesComment, requireContext(), layoutInflater)
        RepliesCommentRecyclerViewAdapter.adapter = adapter

        /*
        adapter.setOnItemClickListener(object : CommentRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(v: View?, pos: Int) {
                // childFragmentManager.beginTransaction().replace(R.id.comment_container, RepliesCommentFragment()).commit()
                val repliesCommentFragment = RepliesCommentFragment()
                repliesCommentFragment.arguments = bundle
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.container, repliesCommentFragment)
                    addToBackStack(null)
                    commit()
                }
            }
        })
        */
    }
}
