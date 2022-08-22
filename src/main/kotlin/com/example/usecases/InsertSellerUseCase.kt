package com.example.usecases

import com.example.models.Seller
import com.example.repository.SellerRepository
import com.example.utils.ServiceResult
import com.example.utils.toDatabaseString
import java.time.LocalDateTime

class InsertSellerUseCase(
    private val sellerRepository: SellerRepository
) {

    suspend operator fun invoke(seller: ServiceResult<Seller>): Seller? {
        return when(seller) {
            is ServiceResult.Success -> {
                val response = sellerRepository.insertSeller(seller.data.copy(
                    date_created = LocalDateTime.now().toDatabaseString()
                ))
                when (response) {
                    is ServiceResult.Success -> response.data
                    else -> null
                }
            }
            is ServiceResult.Error -> null
        }
    }
}