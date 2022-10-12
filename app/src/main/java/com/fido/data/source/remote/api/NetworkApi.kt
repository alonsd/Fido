package com.fido.data.source.remote.api

import com.fido.model.server_models.ServerErrorResponseModel
import com.fido.model.server_models.TeslaNewsResponseModel
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {

    @GET("v2/everything?q=tesla&from=2022-09-12&sortBy=publishedAt")
    suspend fun getDataFromApi(
        @Query("apiKey") apiKey: String,
    ): NetworkResponse<TeslaNewsResponseModel, ServerErrorResponseModel>
}