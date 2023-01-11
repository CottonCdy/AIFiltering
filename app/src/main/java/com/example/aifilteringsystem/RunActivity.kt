package com.example.aifilteringsystem

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.CommentListResponse
import com.google.api.services.youtube.model.CommentThread
import com.google.api.services.youtube.model.CommentThreadListResponse
import com.google.api.services.youtube.model.VideoListResponse
import kr.co.prnd.YouTubePlayerView
import java.io.IOException
import java.math.BigInteger
import java.security.GeneralSecurityException


class RunActivity : AppCompatActivity() {
    private val videoId = "l0CemDDBDUU"

    // private val youtubeServiceThread = YoutubeServiceThread(supportFragmentManager) // 유튜브 서비스 생성
    private val DEVELOPER_KEY = "AIzaSyBK2sZ7-erhVTkTqNy7i6KWYyfxCnS_0xo"
    private val APPLICATION_NAME = "Youtube API"
    private val JSON_FACTORY: JsonFactory = JacksonFactory.getDefaultInstance()
    private val commentFragment = CommentFragment()
    private val repliesCommentFragment = RepliesCommentFragment()
    var commentCount: BigInteger = BigInteger.valueOf(0)

    @Throws(GeneralSecurityException::class, IOException::class)
    fun getService(): YouTube? {
        val httpTransport = com.google.api.client.http.javanet.NetHttpTransport()
        return YouTube.Builder(httpTransport, JSON_FACTORY, null)
            .setApplicationName(APPLICATION_NAME).build()
    }

    @Throws(GeneralSecurityException::class, IOException::class, GoogleJsonResponseException::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.run_activity)

        val youtubeView = findViewById<YouTubePlayerView>(R.id.youtube_player_view)
        youtubeView.play(videoId, object : YouTubePlayerView.OnInitializedListener {
            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider, result: YouTubeInitializationResult
            ) {
                Toast.makeText(applicationContext, "YoutubePlayer Error", Toast.LENGTH_SHORT).show()
            }

            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider, player: YouTubePlayer, wasRestored: Boolean
            ) {
                player.fullscreenControlFlags = YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
            }
        })

        Thread {
            videoAPI()
            commentAPI()
        }.start()
    }

    fun videoAPI() {
        val youtubeService = getService()

        // 요청 보내기
        val request = youtubeService!!.videos().list("statistics")

        val response = request.setKey(DEVELOPER_KEY).setId(videoId).setPrettyPrint(true).execute()
        commentCount = commentCount(response)
    }

    fun commentCount(response: VideoListResponse): BigInteger {
        return response.items.get(0).statistics.commentCount
    }

    fun commentAPI(token: String? = null, firstCheck: Boolean = false) {
        var nextToken = token // comment token

        val youtubeService = getService()

        // 요청 보내기
        val request = youtubeService!!.commentThreads().list("snippet, replies")

        if (firstCheck == false) {
            val response =
                request.setKey(DEVELOPER_KEY).setVideoId(videoId).setPrettyPrint(true)
                    .setMaxResults(100).setTextFormat("plainText").execute()
            commentDataSetting(response)
        } else {
            if (nextToken != null) {
                val response =
                    request.setKey(DEVELOPER_KEY).setVideoId(videoId).setPrettyPrint(true)
                        .setPageToken(nextToken).setMaxResults(100).setTextFormat("plainText")
                        .execute()
                commentDataSetting(response)
            } else {
                Log.d("testtest", "다음 토큰이 없습니다.")
                return
            }
        }
    }

    fun commentDataSetting(response: CommentThreadListResponse) {
        val nextToken = response.nextPageToken
        val data = JSON_FACTORY.toString(response)

        val bundle = Bundle()
        bundle.putString("data", data)
        bundle.putString("token", nextToken)
        commentFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.container, commentFragment).commit()
    }

    fun repliesDataSetting(response: CommentListResponse, topComment: CommentThread) {
        val nextToken = response.nextPageToken
        val repliesData = JSON_FACTORY.toString(response)

        val bundle = Bundle()
        bundle.putString("repliesComment", repliesData)
        bundle.putString("topComment", JacksonFactory().toString(topComment))
        bundle.putString("token", nextToken)

        repliesCommentFragment.arguments = bundle

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, repliesCommentFragment)
            addToBackStack(null)
            commit()
        }
    }

    fun updateCommentRecyclerView(adapter: CommentRecyclerViewAdapter) {
        runOnUiThread {
            adapter.notifyDataSetChanged()
        }
    }

    fun updateRepliesRecyclerView(adapter: RepliesCommentRecyclerViewAdapter) {
        runOnUiThread {
            adapter.notifyDataSetChanged()
        }
    }

    fun repliesCommentAPI(
        topComment: CommentThread,
        token: String? = null,
        firstCheck: Boolean = false
    ) {
        var nextToken = token // replies token

        val youtubeService = getService()

        val request = youtubeService!!.comments().list("snippet")

        if (firstCheck == false) {
            val response = request.setKey(DEVELOPER_KEY)
                .setParentId(topComment.id)
                .setMaxResults(100)
                .setTextFormat("plainText")
                .execute()
            repliesDataSetting(response, topComment)
        } else {
            if (nextToken != null) {
                val response = request.setKey(DEVELOPER_KEY)
                    .setParentId(topComment.id)
                    .setPageToken(nextToken)
                    .setMaxResults(100)
                    .setTextFormat("plainText")
                    .execute()
                repliesDataSetting(response, topComment)
            } else {
                Log.d("testtest", "다음 토큰이 없습니다.")
                return
            }
        }

        Log.d("testtest", topComment.id)
    }
}