package com.example.aifilteringsystem

import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.model.CommentThreadListResponse


class CommentFragment : Fragment() {
    private val videoId = "OlZveif37Z4"
    private val DEVELOPER_KEY = "AIzaSyBK2sZ7-erhVTkTqNy7i6KWYyfxCnS_0xo"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.comment_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var bundle = arguments

        var dataList = JacksonFactory().fromString(
            bundle?.getString("data"),
            CommentThreadListResponse::class.java
        )

        var nextToken = bundle?.getString("token")

        // 댓글 리사이클러뷰
        val commentRecyclerView = view.findViewById<RecyclerView>(R.id.comment_recycler)
        val adapter = CommentRecyclerViewAdapter(dataList, requireContext(), layoutInflater)
        commentRecyclerView.adapter = adapter

        commentRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // 마지막 스크롤된 항목 위치
                val lastItem =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val lastItemCount = recyclerView.adapter!!.itemCount - 1

                if (lastItem == lastItemCount) {
                    if (nextToken != null) {
                        Thread {
                            (activity as RunActivity).commentAPI(nextToken, true)

                            bundle = arguments

                            dataList = JacksonFactory().fromString(
                                bundle?.getString("data"),
                                CommentThreadListResponse::class.java
                            )

                            nextToken = bundle?.getString("token")

                            adapter.addItem(dataList) // 기존 리사이클러뷰에 아이템 추가
                            (activity as RunActivity).updateCommentRecyclerView(adapter)

                            Log.d("testtest", adapter.itemCount.toString())
                        }.start()
                    } else {
                        Log.d("testtest", "댓글의 끝입니다.")
                    }

                    Log.d("testtest", "last")
                }
            }
        })

        adapter.setOnItemClickListener(
            object : CommentRecyclerViewAdapter.OnItemClickListener {
                override fun onItemClick(v: View?, pos: Int) {
                    val commentTotalDataList = adapter.getItem()

                    Thread {
                        (activity as RunActivity).repliesCommentAPI(commentTotalDataList.items.get(pos))
                    }.start()
                }
            })

        val commentCountText = view.findViewById<TextView>(R.id.comment_count)
        commentCountText.text = "댓글 " + (activity as RunActivity).commentCount.toString() + "개"
    }
}