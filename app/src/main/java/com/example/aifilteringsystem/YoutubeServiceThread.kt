package com.example.aifilteringsystem

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import java.io.IOException
import java.security.GeneralSecurityException

class YoutubeServiceThread(fragmentManager: FragmentManager) : Thread() {
    private val videoId = "OlZveif37Z4"
    private val DEVELOPER_KEY = "AIzaSyBK2sZ7-erhVTkTqNy7i6KWYyfxCnS_0xo"
    private val APPLICATION_NAME = "Youtube API"
    private var nextToken: String? = null
    private val JSON_FACTORY: JsonFactory = JacksonFactory.getDefaultInstance()
    private val fManager = fragmentManager

    val youtubeService: YouTube?

    init {
        youtubeService = getService()
    }

    // 유튜브 서비스 API
    @Throws(GeneralSecurityException::class, IOException::class)
    fun getService(): YouTube? {
        val httpTransport = com.google.api.client.http.javanet.NetHttpTransport()
        return YouTube.Builder(httpTransport, JSON_FACTORY, null)
            .setApplicationName(APPLICATION_NAME)
            .build()
    }

    override fun run() {
        super.run()

        // 요청 보내기
        val request = youtubeService!!.commentThreads()
            .list("snippet, replies")

        val response = if (nextToken == null) {
            request.setKey(DEVELOPER_KEY)
                .setVideoId(videoId)
                .setPrettyPrint(true)
                .setMaxResults(100)
                .setTextFormat("plainText")
                .execute()
        } else {
            request.setKey(DEVELOPER_KEY)
                .setVideoId(videoId)
                .setPrettyPrint(true)
                .setPageToken(nextToken)
                .setMaxResults(100)
                .setTextFormat("plainText")
                .execute()
        }

        nextToken = response.nextPageToken
        val data = JSON_FACTORY.toString(response)

        val bundle = Bundle()
        bundle.putString("data", data)
        val commentFragment = CommentFragment()
        commentFragment.arguments = bundle

        fManager.beginTransaction()
            .replace(R.id.container, commentFragment)
            .commit()

    }
}
