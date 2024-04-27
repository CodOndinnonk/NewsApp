package com.ondinnonk.newsapp.di

import com.ondinnonk.newsapp.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { MainActivityViewModel() }
}

val repositoryModule = module {
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
