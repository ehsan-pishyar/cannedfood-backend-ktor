package com.example.repository.impl

import com.example.models.ResultComment
import com.example.models.SellerComment
import com.example.repository.CommentRepository
import com.example.utils.ServiceResult

class CommentRepositoryImpl : CommentRepository {
    override suspend fun addCommentForSeller(
        sellerId: Long,
        sellerComment: SellerComment
    ): ServiceResult<SellerComment?> {
        TODO("Not yet implemented")
    }

    override suspend fun addCommentForResult(
        resultId: Long,
        resultComment: ResultComment
    ): ServiceResult<ResultComment?> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellerComments(sellerId: Long): ServiceResult<List<SellerComment>> {
        TODO("Not yet implemented")
    }
}