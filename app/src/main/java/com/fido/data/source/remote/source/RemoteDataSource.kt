package com.fido.data.source.remote.source

import com.fido.data.source.remote.api.NetworkApi
import com.fido.utils.constants.ApiConstants

class RemoteDataSource(private val networkApi: NetworkApi) {

    suspend fun getDataFromApi() = networkApi.getDataFromApi(ApiConstants.API_KEY)


}