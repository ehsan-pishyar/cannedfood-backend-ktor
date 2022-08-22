package com.example

import com.example.datasource.*
import com.example.plugins.*
import com.example.repository.*
import com.example.routings.*
import com.example.routings.location.cityRouting
import com.example.routings.location.locationRoutes
import com.example.routings.location.stateRoutes
import com.example.routings.user.userRoutes
import com.example.tables.DatabaseFactory.init
import com.example.usecases.InsertResultUseCase
import com.example.usecases.InsertSellerUseCase
import com.example.usecases.InsertUserUseCase
import io.ktor.server.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    init()

//    val stateRepository: StateRepository = StateRepositoryImpl()
//    val cityRepository: CityRepository = CityRepositoryImpl()
//    val foodCategoryRepository: FoodCategoryRepository = FoodCategoryRepositoryImpl()
//    val resultCategoryRepository: ResultCategoryRepository = ResultCategoryRepositoryImpl()
//    val sellerRepository: SellerRepository = SellerRepositoryImpl()
//    val insertSellerUseCase = InsertSellerUseCase(sellerRepository)
//    val resultsRepository: ResultsRepository = ResultsRepositoryImpl()
//    val insertResultUseCase = InsertResultUseCase(resultsRepository)
//    val userRepository: UserRepository = UserRepositoryImpl()
//    val sellerCategoryRepository: SellerCategoryRepository = SellerCategoryRepositoryImpl()
//    val locationRepository: LocationRepository = LocationRepositoryImpl()

    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRouting()

//    stateRoutes(stateRepository)
//    cityRouting(cityRepository)
//    typeRoutes(foodCategoryRepository)
//    categoryRoutes(resultCategoryRepository)
//    sellerRoutes(sellerRepository, insertSellerUseCase)
//    resultsRoutes(resultsRepository, insertResultUseCase)
//    userRoutes(userRepository)
//    sellerCategoryRoutes(sellerCategoryRepository)
//    locationRoutes(locationRepository)
}
