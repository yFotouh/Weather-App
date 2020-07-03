package com.tests.newandroid.DI

import com.task.parenttechnicaltask.AppClass
import com.task.parenttechnicaltask.repository.CityRepository
import com.task.parenttechnicaltask.repository.CityRepositoryImpl
import com.task.parenttechnicaltask.utils.AppPrefs
import com.tests.newandroid.models.WeatherRepository
import com.tests.newandroid.models.WeatherRepositoryCoImpl
import com.tests.newandroid.models.WeatherRepositoryDummy
import com.tests.newandroid.viewmodel.WeatherFragmentViewModelKt
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
@JvmField
val appModule = module {

//    factory<BookRepository> {(appClass: AppClass) -> BookRepositoryImpl() }
    factory<WeatherRepository> { WeatherRepositoryCoImpl() }
    factory<CityRepository> { CityRepositoryImpl() }
//    factory { MainActivityViewModelKt(get()) }
    viewModel { WeatherFragmentViewModelKt(get()) }

//    single<AppPrefs>()
}


@JvmField
val testModule = module {

    factory<WeatherRepository> { WeatherRepositoryDummy() }
    viewModel { WeatherFragmentViewModelKt(get()) }
}