package com.example.repository

import com.example.models.SellerCloseHours
import com.example.models.SellerOpenHours
import com.example.models.responses.SellerOpenStatusResponse
import com.example.utils.ServiceResult

interface SellerOpenStatusRepository {
    suspend fun insertSellerOpenHours(openHours: SellerOpenHours): ServiceResult<SellerOpenHours>
    suspend fun insertSellerCloseHours(closeHours: SellerCloseHours): ServiceResult<SellerCloseHours>

    suspend fun getSellerOpenHoursStatusById(sellerId: Long): ServiceResult<SellerOpenStatusResponse?>

    suspend fun updateSellerOpenHours(sellerId: Long, openHours: SellerOpenHours): ServiceResult<SellerOpenHours>
    suspend fun updateSellerCloseHours(sellerId: Long, closeHours: SellerCloseHours): ServiceResult<SellerCloseHours>

    suspend fun deleteSellerOpenHoursById(sellerId: Long): ServiceResult<List<SellerOpenHours?>>
    suspend fun deleteSellersOpenHours(): ServiceResult<List<SellerOpenHours?>>
    suspend fun deleteSellerCloseHoursById(sellerId: Long): ServiceResult<List<SellerCloseHours?>>
    suspend fun deleteSellersCloseHours(): ServiceResult<List<SellerCloseHours?>>
}