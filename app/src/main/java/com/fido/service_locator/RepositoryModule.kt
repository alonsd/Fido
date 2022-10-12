package com.fido.service_locator

import com.fido.data.repository.Repository
import org.koin.dsl.module


val repositoryModule = module {

    single { Repository(get()) }
}