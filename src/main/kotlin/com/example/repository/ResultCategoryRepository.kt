package com.example.repository

import com.example.models.ResultCategory
import com.example.models.responses.ResultsCategoryResponse
import com.example.utils.ServiceResult

interface ResultCategoryRepository {

    suspend fun insertResultCategory(
        resultCategory: ResultCategory
    ): ServiceResult<ResultCategory>

    suspend fun getResultCategories(
        sellerCategoryId: Int
    ): ServiceResult<List<ResultsCategoryResponse?>>

    suspend fun getResultCategoryById(
        resultsCategoryId: Int
    ): ServiceResult<ResultsCategoryResponse>
    suspend fun getResultCategoriesByTitle(
        resultsCategoryTitle: String?
    ): ServiceResult<List<ResultsCategoryResponse?>> // for search result

    suspend fun updateResultCategory(
        resultsCategoryId: Int,
        resultCategory: ResultCategory
    ): ServiceResult<ResultCategory>

    suspend fun deleteResultCategoryById(
        resultsCategoryId: Int
    ): ServiceResult<List<ResultsCategoryResponse?>>
    suspend fun deleteResultCategoriesOfSellerCategory(
        sellerCategoryId: Int
    ): ServiceResult<List<ResultsCategoryResponse?>>
    suspend fun deleteResultCategories(): ServiceResult<List<ResultsCategoryResponse?>>
}