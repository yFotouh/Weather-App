package com.tests.newandroid.DI

import com.task.parenttechnicaltask.model.repository.*
import com.task.parenttechnicaltask.viewmodel.CityViewModel
import com.task.parenttechnicaltask.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@JvmField
val appModule = module {

//    factory<BookRepository> {(appClass: AppClass) -> BookRepositoryImpl() }
    factory<WeatherRepository> { WeatherRepositoryImpl() }
    factory<CityRepository> { CityRepositoryImpl() }
//    factory<CityRepository> { CityRepositoryImpl() }
//    factory { MainActivityViewModelKt(get()) }
    viewModel { WeatherViewModel(get()) }
    viewModel { CityViewModel(get()) }

//    single<AppPrefs>()
}


@JvmField
val testModule = module {

    factory<WeatherRepository> { WeatherRepositoryDummy() }
    viewModel { WeatherViewModel(get()) }

    factory<CityRepository> { CityRepositoryDummy() }
    viewModel { CityViewModel(get()) }
}