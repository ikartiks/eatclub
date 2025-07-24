package au.com.eatclub.data.service

import au.com.eatclub.data.model.Restaurants
import retrofit2.http.GET

interface EatClubService {
  @GET("misc/challengedata.json")
  suspend fun getRestaurants(): Restaurants?
}