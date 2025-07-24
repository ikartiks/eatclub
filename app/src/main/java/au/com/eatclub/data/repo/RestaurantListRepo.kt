package au.com.eatclub.data.repo

import au.com.eatclub.data.model.Restaurants
import au.com.eatclub.data.service.EatClubService
import org.koin.core.component.KoinComponent
import retrofit2.Retrofit

interface RestaurantListRepository {
  suspend fun fetchAllRestaurants(): Restaurants?
}

class RestaurantListRepositoryImpl(val retrofit: Retrofit) : RestaurantListRepository,
  KoinComponent {
  override suspend fun fetchAllRestaurants(): Restaurants? {
    val eatClubService = retrofit.create(EatClubService::class.java)
    return eatClubService.getRestaurants()
  }
}