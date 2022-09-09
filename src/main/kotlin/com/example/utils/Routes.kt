package com.example.utils

object Routes {

    const val USERS_ROUTE = "/users"
    const val SELLERS_ROUTE = "/sellers"
    const val CUSTOMERS_ROUTE = "/customers"

    const val STATE_ROUTE = "/states"
    const val CITY_ROUTE = "/cities"
    const val LOCATION_ROUTE = "/locations"

    const val SELLER_CATEGORY_ROUTE = "/seller-categories"
    const val RESULT_CATEGORY_ROUTE = "/result-categories"
    const val FOOD_CATEGORY_ROUTE = "/food-categories"

    const val RESULT_ROUTE = "/results"

    const val CREATE_ROUTE = "/create"
    const val UPDATE_ROUTE = "/{id}/update"
    const val DELETE_ROUTE = "/{id}/delete"

    const val SELLER_RATE_ROUTE = "/{seller_id}/ratings/rate"
    const val RESULT_RATE_ROUTE = "/{result_id}/ratings/rate"

    const val COMMENT_SELLER_ADD_ROUTE = "/{seller_id}/comments/add"
    const val COMMENT_RESULT_ADD_ROUTE = "/{result_id}/comments/add"
    const val COMMENT_SELLER_GET_ROUTE = "/{seller_id}/comments"
    const val COMMENT_RESULT_GET_ROUTE = "/{result_id}/comments"

    const val SELLER_OPEN_STATUS_ADD_ROUTE = "/{seller_id}/open-hours/add"
    const val SELLER_CLOSE_STATUS_ADD_ROUTE = "/{seller_id}/close-hours/add"
    const val SELLER_OPEN_STATUS_GET_ROUTE = "/{seller_id}/open-status"
    const val SELLER_OPEN_STATUS_ROUTE = "/open-hours"
    const val SELLER_CLOSE_STATUS_ROUTE = "/close-hours"
    const val SELLER_OPEN_STATUS_DELETE_ROUTE = "/{seller_id}/open-hours/delete"
    const val SELLER_CLOSE_STATUS_DELETE_ROUTE = "/{seller_id}/close-hours/delete"
    const val SELLER_OPEN_STATUS_UPDATE_ROUTE = "/{seller_id}/open-hours/update"
    const val SELLER_CLOSE_STATUS_UPDATE_ROUTE = "/{seller_id}/close-hours/update"
}