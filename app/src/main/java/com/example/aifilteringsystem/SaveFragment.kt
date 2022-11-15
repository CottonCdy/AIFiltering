package com.example.aifilteringsystem

import android.content.Intent
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const private val naverApiKeyId = "w7cnrl8nd6"
const private val naverApiKey = "UkIu6ENB91Ldl3pXyD0aSjnqTQAVZRZg3UjMs468"
const private val youtubeApiKey = "AIzaSyBK2sZ7-erhVTkTqNy7i6KWYyfxCnS_0xo"

class SaveFragment : Fragment() {
    private var content: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.save_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://naveropenapi.apigw.ntruss.com/sentiment-analysis/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val saveRecyclerView =
            view.findViewById<RecyclerView>(R.id.save_recycler)

        val saveAdapter = SaveRecyclerAdapter(context, layoutInflater)
        saveRecyclerView.adapter = saveAdapter

        val button = view.findViewById<Button>(R.id.button)
        button.setOnClickListener {
            startActivity(Intent(context, RunActivity::class.java))
            /*
            val contentString = HashMap<String, String>()
            contentString.put("content", content)
            if (content.equals("")) {
                Toast.makeText(context, "텍스트가 비었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                val retrofitService = retrofit.create(RetrofitService::class.java)
                retrofitService.sentimentAPI(apiKeyId, apiKey, contentString)
                    .enqueue(
                        object : Callback<Data> {
                            override fun onResponse(
                                call: Call<Data>,
                                response: Response<Data>
                            ) {
                                if (response.isSuccessful) {
                                    val result = response.body()!!

                                    saveAdapter.addItem(result, content)
                                }
                            }

                            override fun onFailure(
                                call: Call<Data>,
                                t: Throwable
                            ) {
                                Log.d("testt", "" + t)
                            }
                        })
            }*/
        }
        super.onViewCreated(view, savedInstanceState)
    }
}
