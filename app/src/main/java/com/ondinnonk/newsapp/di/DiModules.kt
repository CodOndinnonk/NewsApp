package com.ondinnonk.newsapp.di

import androidx.room.Room
import com.ondinnonk.newsapp.MainActivityViewModel
import com.ondinnonk.newsapp.SharedViewModel
import com.ondinnonk.newsapp.features.details.NewsDetailsViewModel
import com.ondinnonk.newsapp.features.list.NewsListViewModel
import com.ondinnonk.newsapp.repository.DataStore
import com.ondinnonk.newsapp.repository.Repository
import com.ondinnonk.newsapp.repository.RepositoryImpl
import com.ondinnonk.newsapp.repository.local.RepositoryLocal
import com.ondinnonk.newsapp.repository.local.db.RoomDb
import com.ondinnonk.newsapp.repository.remote.RepositoryRemote
import com.ondinnonk.newsapp.repository.remote.ServerRepositorySetup
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { MainActivityViewModel() }
    viewModel { NewsListViewModel(get()) }
    viewModel { NewsDetailsViewModel() }
    viewModel { SharedViewModel() }
}

val repositoryModule = module {
    single { RepositoryRemote(get()) }
    single { RepositoryLocal(get()) }
    single { ServerRepositorySetup(get()) }
    factory {
        val server: ServerRepositorySetup = get()
        return@factory server.create()
    }
    factory<Repository> { RepositoryImpl(get(), get(), get()) }
    single<RoomDb> {
        Room.databaseBuilder(get(), RoomDb::class.java, RoomDb.DB_NAME)
            .allowMainThreadQueries()
            .build()
    }
    factory { get<RoomDb>().newsDao() }
    single { DataStore(get()) }
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
