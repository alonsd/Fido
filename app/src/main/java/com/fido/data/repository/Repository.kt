package com.fido.data.repository

import com.fido.data.source.remote.source.RemoteDataSource

class Repository(
    private val remoteDataSource: RemoteDataSource,
) {

    suspend fun getDataFromApi() = remoteDataSource.getDataFromApi()

}

