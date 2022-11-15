package com.example.aifilteringsystem

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.gson.Gson
import kr.co.prnd.YouTubePlayerView
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RunActivity : AppCompatActivity() {
    private val videoId = "8o6TwNHI0BQ"
    private val DEVELOPER_KEY = "AIzaSyBK2sZ7-erhVTkTqNy7i6KWYyfxCnS_0xo"
    private val commentsArrayList = arrayListOf<String>()
    private val commentFragment = CommentFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.run_activity)

        val youtubeView = findViewById<YouTubePlayerView>(R.id.youtube_player_view)
        youtubeView.play(videoId, object : YouTubePlayerView.OnInitializedListener {
            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider,
                result: YouTubeInitializationResult
            ) {
                Toast.makeText(applicationContext, "YoutubePlayer Error", Toast.LENGTH_SHORT).show()
            }

            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider,
                player: YouTubePlayer,
                wasRestored: Boolean
            ) {
                player.fullscreenControlFlags = YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
            }
        })

        val retrofit = Retrofit.Builder()
            .baseUrl("https://youtube.googleapis.com/youtube/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService = retrofit.create(RetrofitService::class.java)
        commentRetrofit(retrofitService)
    }

    fun commentRetrofit(retrofitService: RetrofitService) {
        val gson = Gson()
        retrofitService.commentThreadsAPI(DEVELOPER_KEY, videoId)
            .enqueue(object : Callback<Any> {
                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ) {
                    if (response.isSuccessful) {
                        // Any -> Json 변환
                        val commentsList = gson.toJson(response.body())
                        commentsArrayList.add(commentsList)

                        // 다음 토큰을 가지고 있다면
                        if (JSONObject(commentsList).has("nextPageToken")) {
                            val nextToken = JSONObject(commentsList).getString("nextPageToken")
                            commentRetrofitNext(retrofitService, nextToken)
                            Log.d("testtest", "첫번째")
                        } else {
                            val bundle = Bundle()
                            bundle.putStringArrayList("commentsArrayList", commentsArrayList)
                            commentFragment.arguments = bundle
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.container, commentFragment).commit()
                        }

//                        Log.d("testtest", commentsList.toString())
//                        Log.d("testtest", commentsArrayList.get(commentsArrayList.size-1).toString())
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.d("testtest", "실패")
                }
            })
    }

    fun commentRetrofitNext(retrofitService: RetrofitService, nextToken: String) {
        val gson = Gson()

        retrofitService.commentNextAPI(DEVELOPER_KEY, videoId, nextToken)
            .enqueue(object : Callback<Any> {
                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ) {
                    if (response.isSuccessful) {
                        // Any -> Json 변환
                        val commentsList = gson.toJson(response.body())
                        commentsArrayList.add(commentsList)

                        //Log.d("testtest", commentsList.toString())
                        // 프래그먼트 새로고침
                        // supportFragmentManager.beginTransaction().detach(commentFragment).attach(commentFragment).commit()

                        // 다음 토큰을 가지고 있다면
                        if (JSONObject(commentsList).has("nextPageToken")) {
                            val nextToken = JSONObject(commentsList).getString("nextPageToken")
                            commentRetrofitNext(retrofitService, nextToken)
                        } else {
                            val bundle = Bundle()
                            bundle.putStringArrayList("commentsArrayList", commentsArrayList)
                            commentFragment.arguments = bundle
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.container, commentFragment).commit()
                        }
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.d("testtest", "실패")
                }
            })
    }
}