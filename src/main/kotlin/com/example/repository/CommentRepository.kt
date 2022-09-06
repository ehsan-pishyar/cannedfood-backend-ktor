package com.example.repository

import com.example.models.ResultComment
import com.example.models.SellerComment
import com.example.models.responses.ResultCommentResponse
import com.example.models.responses.SellerCommentResponse
import com.example.utils.ServiceResult

interface CommentRepository {

    suspend fun addCommentForSeller(sellerId: Long, sellerComment: SellerComment): ServiceResult<SellerComment>
    suspend fun addCommentForResult(resultId: Long, resultComment: ResultComment): ServiceResult<ResultComment>

    suspend fun getSellerComments(sellerId: Long): ServiceResult<List<SellerCommentResponse?>>
    suspend fun getResultComments(resultId: Long): ServiceResult<List<ResultCommentResponse?>>
}