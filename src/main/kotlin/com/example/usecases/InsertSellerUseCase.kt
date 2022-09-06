package com.example.usecases

import com.example.models.Seller
import com.example.repository.SellerRepository
import com.example.utils.ServiceResult
import com.example.utils.toDatabaseString
import java.time.LocalDateTime

class InsertSellerUseCase(private val sellerRepository: SellerRepository) {

    suspend operator fun invoke(seller: Seller): Seller {
        sellerRepository.insertSeller(seller.copy(
            date_created = LocalDateTime.now().toDatabaseString()
        )).let {
            return when (it) {
                is ServiceResult.Success -> it.data
                else -> seller
            }
        }
    }
}