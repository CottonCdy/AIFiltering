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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.model.CommentListResponse
import com.google.api.services.youtube.model.CommentThread
import com.google.api.services.youtube.model.CommentThreadListResponse
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RepliesCommentFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.replies_comment_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val back = view.findViewById<TextView>(R.id.back_comment)
        back.setOnClickListener {

        }

        var bundle = arguments

        // top 댓글 데이터
        val topCommentData =
            JacksonFactory().fromString(bundle?.getString("topComment"), CommentThread::class.java)

        // 답글 데이터
        var repliesComment = JacksonFactory().fromString(
            bundle?.getString("repliesComment"),
            CommentListResponse::class.java
        )

        var nextToken = bundle?.getString("token")

        val topProfile = view.findViewById<ImageView>(R.id.profile)
        Glide.with(requireContext()).load(
            topCommentData.snippet.topLevelComment.snippet.authorProfileImageUrl
        ).centerCrop().circleCrop().into(topProfile)

        val topAuthorName = view.findViewById<TextView>(R.id.nick_name)
        topAuthorName.text =
            topCommentData.snippet.topLevelComment.snippet.authorDisplayName

        val topComment = view.findViewById<TextView>(R.id.comment)
        topComment.text =
            topCommentData.snippet.topLevelComment.snippet.textDisplay

        // 답글 리사이클러뷰
        val repliesCommentRecyclerViewAdapter =
            view.findViewById<RecyclerView>(R.id.replies_comment_recycler)
        val adapter =
            RepliesCommentRecyclerViewAdapter(
                repliesComment,
                requireContext(),
                layoutInflater
            )
        repliesCommentRecyclerViewAdapter.adapter = adapter

        repliesCommentRecyclerViewAdapter.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastItem =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val lastItemCount = recyclerView.adapter!!.itemCount - 1

                if(lastItem == lastItemCount){
                    if(nextToken != null){
                        Thread {
                            (activity as RunActivity).repliesCommentAPI(
                                topCommentData,
                                nextToken,
                                true
                            )

                            bundle = arguments

                            repliesComment = JacksonFactory().fromString(
                                bundle?.getString("repliesComment"),
                                CommentListResponse::class.java
                            )

                            nextToken = bundle?.getString("token")

                            adapter.addItem(repliesComment) // 리사이클러뷰 아이템 추가
                            (activity as RunActivity).updateRepliesRecyclerView(adapter)
                            Log.d("testtest", "답글 갯수 " + adapter.itemCount.toString())
                        }.start()
                    } else{
                        Log.d("testtest", "답글의 끝입니다.")
                    }

                    Log.d("testtest", "답글 LAst")
                }
            }
        })
    }
}
