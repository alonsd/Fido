package com.fido.service_locator

import com.fido.data.source.remote.source.RemoteDataSource
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single { RemoteDataSource(get()) }
}