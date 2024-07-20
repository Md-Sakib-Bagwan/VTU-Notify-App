package com.adityamshidlyali.vtunotify.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adityamshidlyali.vtunotify.endpoint.ApiService
import com.adityamshidlyali.vtunotify.endpoint.ServiceGenerator
import com.adityamshidlyali.vtunotify.model.NotificationItem
import com.adityamshidlyali.vtunotify.scraper.VtuScraper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationRepository(private val context: Context) {

    private var scraper: VtuScraper = VtuScraper()

    private var _notificationList = MutableLiveData<List<NotificationItem>>()

    val notifications: LiveData<List<NotificationItem>>
        get() = _notificationList!!

    suspend fun getNotifications(VTU_URL: String) {

        val retrofit = ServiceGenerator.generateService(context, VTU_URL)

        val apiService = retrofit.create(ApiService::class.java)

        val stringCall = apiService.response
        stringCall!!.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>?, response: Response<String?>) {
                if (response.isSuccessful) {
                    val responseString: String? = response.body()
                    _notificationList.postValue(scraper.scrapeNotifications(responseString.toString()))
                }
            }

            override fun onFailure(call: Call<String?>?, t: Throwable?) {
            }
        })
    }
}