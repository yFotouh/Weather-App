package com.tests.newandroid.DI

import com.task.parenttechnicaltask.model.repository.CityRepository
import com.task.parenttechnicaltask.model.repository.CityRepositoryDummy
import com.task.parenttechnicaltask.model.repository.CityRepositoryImpl
import com.task.parenttechnicaltask.viewmodel.CityViewModel
import com.tests.newandroid.models.WeatherRepository
import com.tests.newandroid.models.WeatherRepositoryCoImpl
import com.tests.newandroid.models.WeatherRepositoryDummy
import com.tests.newandroid.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@JvmField
val appModule = module {

//    factory<BookRepository> {(appClass: AppClass) -> BookRepositoryImpl() }
    factory<WeatherRepository> { WeatherRepositoryCoImpl() }
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