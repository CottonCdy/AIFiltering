package com.example.aifilteringsystem

import android.media.Image
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File
import java.io.Serializable

class Data(
    val document: Document, val sentences: List<Content>
)

class Document(
    val sentiment: String, val confidence: Confidence
)

class Confidence(
    val negative: Float, val positive: Float, val neutral: Float
)

class Content(
    val content: String,
    val offset: Int,
    val length: Int,
    val sentiment: String,
    val confidence: Confidence,
    val highlights: List<Highlights>
)

class Highlights(
    val offset: Int, val length: Int
)

interface RetrofitService {
    @Headers("Content-Type: application/json")
    @POST("analyze")
    fun sentimentAPI(
        @Header("X-NCP-APIGW-API-KEY-ID") apiKeyId: String,
        @Header("X-NCP-APIGW-API-KEY") apiKey: String,
        @Body content: HashMap<String, String>
    ): Call<Data>

    @GET("commentThreads")
    fun commentThreadsAPI(
        @Query("key") key: String,
        @Query("videoId") videoId: String,
        @Query("maxResults") maxResults: Int = 100,
        @Query("part") part: String = "snippet, replies",
        @Query("textFormat") textFormat: String = "plainText",
        @Query("fields") fields: String = "nextPageToken, items(snippet(topLevelComment(snippet(authorProfileImageUrl, likeCount, updatedAt, authorDisplayName, textDisplay)), totalReplyCount), replies(comments(snippet(authorProfileImageUrl, likeCount, updatedAt, authorDisplayName, textDisplay))))",
    ): Call<Any>

    @GET("commentThreads")
    fun commentNextAPI(
        @Query("key") key: String,
        @Query("videoId") videoId: String,
        @Query("pageToken") pageToken: String,
        @Query("maxResults") maxResults: Int = 100,
        @Query("part") part: String = "snippet, replies",
        @Query("textFormat") textFormat: String = "plainText",
        @Query("fields") fields: String = "nextPageToken, items(snippet(topLevelComment(snippet(authorProfileImageUrl, likeCount, updatedAt, authorDisplayName, textDisplay)), totalReplyCount), replies(comments(snippet(authorProfileImageUrl, likeCount, updatedAt, authorDisplayName, textDisplay))))",
    ): Call<Any>
}

