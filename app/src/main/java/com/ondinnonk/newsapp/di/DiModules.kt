package com.ondinnonk.newsapp.di

import com.ondinnonk.newsapp.MainActivityViewModel
import com.ondinnonk.newsapp.features.details.NewsDetailsViewModel
import com.ondinnonk.newsapp.features.list.NewsListViewModel
import com.ondinnonk.newsapp.repository.Repository
import com.ondinnonk.newsapp.repository.RepositoryImpl
import com.ondinnonk.newsapp.repository.remote.ServerRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { MainActivityViewModel() }
    viewModel { NewsListViewModel(get()) }
    viewModel { NewsDetailsViewModel() }
}

val repositoryModule = module {
    single { ServerRepository(get()) }
    factory {
        val server: ServerRepository = get()
        return@factory server.create()
    }
    factory<Repository> { RepositoryImpl(get()) }
//    single { LocalRepository(get(), get(), get(), get(), get(), get()) }
//    single { RemoteDataStore() }
}

val utilsModule = module {
//    factory { SoundRecognitionUseCase(get(), get()) }
}

val qualityModule = module {
//    single { DetailedLogger() }
//    single { CrashlyticsLogger() }
}

val networkModule = module {
//    factory {
//        val service: WebApiService = get()
//        return@factory service.retrofit.create(AuthApi::class.java)
//    }
}


//val useCaseModule = module {
//    factory { CheckUpdateUseCase(get(), get()) }
//    factory { TakeMedicationUseCase(get(), get()) }
//}

val validatorsModule = module {
}
