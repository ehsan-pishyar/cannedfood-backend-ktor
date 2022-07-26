package com.example

import com.example.datasource.*
import com.example.plugins.*
import com.example.repository.*
import com.example.routings.*
import com.example.tables.DatabaseFactory.init
import io.ktor.server.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    init()

    val stateRepository: StateRepository = StateRepositoryImpl()
    val cityRepository: CityRepository = CityRepositoryImpl()
    val typeRepository: TypeRepository = TypeRepositoryImpl()
    val categoryRepository: CategoryRepository = CategoryRepositoryImpl()
    val sellerRepository: SellerRepository = SellerRepositoryImpl()
    val resultsRepository: ResultsRepository = ResultsRepositoryImpl()

    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRouting()

    stateRoutes(stateRepository)
    cityRouting(cityRepository)
    typeRoutes(typeRepository)
    categoryRoutes(categoryRepository)
    sellerRoutes(sellerRepository)
    resultsRoutes(resultsRepository)
}
