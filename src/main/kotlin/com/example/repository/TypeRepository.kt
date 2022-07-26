package com.example.repository

import com.example.models.Type

interface TypeRepository {

    suspend fun insertType(type: Type): Type?

    suspend fun getTypes(): List<Type?>
    suspend fun getTypeById(typeId: Int): Type?
    suspend fun getTypeByTitle(typeTitle: String): Type?

    suspend fun updateTypeById(typeId: Int): Type
    suspend fun updateTypeByTitle(typeTitle: String): Type

    suspend fun deleteTypeById(typeId: Int)
    suspend fun deleteTypeByTitle(typeTitle: String)
    suspend fun deleteTypes()
}