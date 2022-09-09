package com.example.repository

import com.example.models.SellerCloseHour
import com.example.models.SellerOpenHour
import com.example.models.responses.SellerOpenStatusResponse
import com.example.utils.ServiceResult

interface SellerOpenStatusRepository {
    suspend fun insertSellerOpenHours(openHours: SellerOpenHour): ServiceResult<SellerOpenHour>
    suspend fun insertSellerCloseHours(closeHours: SellerCloseHour): ServiceResult<SellerCloseHour>

    suspend fun getSellerOpenHoursStatusById(sellerId: Long): ServiceResult<SellerOpenStatusResponse?>

    suspend fun updateSellerOpenHours(sellerId: Long, openHours: SellerOpenHour): ServiceResult<SellerOpenHour>
    suspend fun updateSellerCloseHours(sellerId: Long, closeHours: SellerCloseHour): ServiceResult<SellerCloseHour>

    suspend fun deleteSellerOpenHoursById(sellerId: Long): ServiceResult<List<SellerOpenHour?>>
    suspend fun deleteSellersOpenHours(): ServiceResult<List<SellerOpenHour?>>
    suspend fun deleteSellerCloseHoursById(sellerId: Long): ServiceResult<List<SellerCloseHour?>>
    suspend fun deleteSellersCloseHours(): ServiceResult<List<SellerCloseHour?>>
}