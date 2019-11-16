package com.example.quizlet.activity

import com.example.quizlet.model.Question
import com.example.quizlet.model.StudentResult
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


interface QuizletRmoteService {
    @POST("{testId}/studentResult")
    fun sendResult(@Body studentResult: StudentResult, @Path(value = "testId") testId: String): Single<Unit>

    @GET("{testId}/questions")
    fun getQuestions(@Path(value = "testId") testId: String): Single<List<Question>>

    companion object {
        fun get(): QuizletRmoteService {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.0.194:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
            return retrofit.create(QuizletRmoteService::class.java)
        }
    }
}