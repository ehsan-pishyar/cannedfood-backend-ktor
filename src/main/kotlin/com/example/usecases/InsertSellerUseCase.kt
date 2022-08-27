package com.example.usecases

import com.example.models.Seller
import com.example.models.responses.SellerResponse
import com.example.repository.SellerRepository
import com.example.utils.ServiceResult
import com.example.utils.toDatabaseString
import java.time.LocalDateTime

class InsertSellerUseCase(
    private val sellerRepository: SellerRepository
) {

    suspend operator fun invoke(seller: Seller): Seller? {
        val response = sellerRepository.insertSeller(seller.copy(
            date_created = LocalDateTime.now().toDatabaseString()
        ))
        return when (response) {
            is ServiceResult.Success -> response.data
            else -> null
        }
    }
}