package com.example.datasource

import com.example.models.Type
import com.example.repository.TypeRepository

class TypeRepositoryImpl : TypeRepository {

    override suspend fun insertType(typeName: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun getTypes(): List<Type> {
        TODO("Not yet implemented")
    }

    override suspend fun getTypeById(typeId: Int): Type {
        TODO("Not yet implemented")
    }

    override suspend fun getTypeByTitle(typeTitle: String): Type? {
        TODO("Not yet implemented")
    }

    override suspend fun updateTypeById(typeId: Int): Type {
        TODO("Not yet implemented")
    }

    override suspend fun updateTypeByTitle(typeTitle: String): Type {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTypeById(typeId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTypeByTitle(typeTitle: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTypes() {
        TODO("Not yet implemented")
    }
}