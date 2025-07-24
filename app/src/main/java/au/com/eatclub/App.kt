package au.com.eatclub

import android.app.Application
import au.com.eatclub.data.repo.RestaurantListRepository
import au.com.eatclub.data.repo.RestaurantListRepositoryImpl
import au.com.eatclub.data.service.getRetrofitInstance
import au.com.eatclub.presentation.screens.restaurant.list.RestaurantListScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}

val appModule = module {
    viewModel { RestaurantListScreenViewModel(get(), get()) }
    single { getRetrofitInstance() }
    single { RestaurantListRepositoryImpl(get()) as RestaurantListRepository }
}
