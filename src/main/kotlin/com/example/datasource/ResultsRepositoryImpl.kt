package com.example.datasource

import com.example.models.Results
import com.example.repository.ResultsRepository
import com.example.tables.DatabaseFactory.dbQuery
import com.example.tables.FoodCategoryTable
import com.example.tables.ResultsTable
import org.jetbrains.exposed.sql.*

class ResultsRepositoryImpl : ResultsRepository {

    override suspend fun insertResult(result: Results) {
        dbQuery {
            ResultsTable.insert {
                it[title] = result.title
                it[description] = result.description
                it[sellerId] = result.seller_id
                it[sellerCategoryId] = result.seller_category_id
                it[resultCategoryId] = result.result_category_id
                it[foodCategoryId] = result.food_category_id!!
                it[imagePath] = result.image_path
                it[price] = result.price
                it[rating] = result.rating
                it[voteCount] = result.vote_count
                it[discount] = result.discount
                it[dateAdded] = result.date_added
                it[prepareDuration] = result.prepare_duration
            }
        }
    }

    override suspend fun getResults(): List<Results?> {
        val results = dbQuery {
            ResultsTable.selectAll()
                .orderBy(FoodCategoryTable.id to SortOrder.ASC)
                .limit(20, offset = 1)
                .map {
                    rowToResults(it)
                }
        }
        return results
    }

    override suspend fun getResultById(resultId: Int): Results {
        val result = dbQuery {
            ResultsTable.select {
                ResultsTable.id.eq(resultId)
            }
                .orderBy(FoodCategoryTable.id to SortOrder.ASC)
                .map {
                    rowToResults(it)
                }
                .singleOrNull()
        }
        return result!!
    }

    override suspend fun getResultsByTitle(resultTitle: String): List<Results?> {
        val results = dbQuery {
            ResultsTable.select {
                ResultsTable.title.eq(resultTitle)
            }
                .orderBy(FoodCategoryTable.id to SortOrder.ASC)
                .map {
                    rowToResults(it)
                }
        }
        return results
    }

    override suspend fun getResultsByDescription(description: String?): List<Results?> {
        val results = dbQuery {
            ResultsTable.select {
                ResultsTable.title.like(description!!)
            }
                .orderBy(FoodCategoryTable.id to SortOrder.ASC)
                .limit(20, offset = 1)
                .map {
                    rowToResults(it)
                }
        }
        return results
    }

    override suspend fun getResultsBySellerId(sellerId: Int): List<Results?> {
        val results = dbQuery {
            ResultsTable.select {
                ResultsTable.sellerId.eq(sellerId)
            }
                .orderBy(FoodCategoryTable.id to SortOrder.ASC)
                .limit(20, offset = 1)
                .map {
                    rowToResults(it)
                }
        }
        return results
    }

    override suspend fun getResultsBySellerCategoryId(sellerCategoryId: Int): List<Results?> {
        val results = dbQuery {
            ResultsTable.select {
                ResultsTable.sellerCategoryId.eq(sellerCategoryId)
            }
                .orderBy(FoodCategoryTable.id to SortOrder.ASC)
                .limit(20, offset = 1)
                .map {
                    rowToResults(it)
                }
        }
        return results
    }

    override suspend fun getResultsByResultCategoryId(resultCategoryId: Int): List<Results?> {
        val results = dbQuery {
            ResultsTable.select {
                ResultsTable.resultCategoryId.eq(resultCategoryId)
            }
                .orderBy(FoodCategoryTable.id to SortOrder.ASC)
                .limit(20, offset = 1)
                .map {
                    rowToResults(it)
                }
        }
        return results
    }

    override suspend fun getResultsByFoodCategoryId(foodCategoryId: Int): List<Results?> {
        val results = dbQuery {
            ResultsTable.select {
                ResultsTable.foodCategoryId.eq(foodCategoryId)
            }
                .orderBy(FoodCategoryTable.id to SortOrder.ASC)
                .limit(20, offset = 1)
                .map {
                    rowToResults(it)
                }
        }
        return results
    }

    override suspend fun getResultsByPrice(price: Int): List<Results?> {
        val results = dbQuery {
            ResultsTable.select {
                ResultsTable.price.eq(price)
            }
                .orderBy(FoodCategoryTable.id to SortOrder.ASC)
                .limit(20, offset = 1)
                .map {
                    rowToResults(it)
                }
        }
        return results
    }

    override suspend fun getResultsByRating(rating: Double): List<Results?> {
        val results = dbQuery {
            ResultsTable.select {
                ResultsTable.rating.eq(rating)
            }
                .orderBy(FoodCategoryTable.id to SortOrder.ASC)
                .limit(20, offset = 1)
                .map {
                    rowToResults(it)
                }
        }
        return results
    }

    override suspend fun getResultsByVoteCount(voteCount: Int): List<Results?> {
        val results = dbQuery {
            ResultsTable.select {
                ResultsTable.voteCount.eq(voteCount)
            }
                .orderBy(FoodCategoryTable.id to SortOrder.ASC)
                .limit(20, offset = 1)
                .map {
                    rowToResults(it)
                }
        }
        return results
    }

    override suspend fun getResultsByDiscount(discount: Int): List<Results?> {
        val results = dbQuery {
            ResultsTable.select {
                ResultsTable.discount.eq(discount)
            }
                .orderBy(FoodCategoryTable.id to SortOrder.ASC)
                .limit(20, offset = 1)
                .map {
                    rowToResults(it)
                }
        }
        return results
    }

    override suspend fun getResultsByDateAdded(dateAdded: String): List<Results?> {
        val results = dbQuery {
            ResultsTable.select {
                ResultsTable.dateAdded.eq(dateAdded)
            }
                .orderBy(FoodCategoryTable.id to SortOrder.ASC)
                .limit(20, offset = 1)
                .map {
                    rowToResults(it)
                }
        }
        return results
    }

    override suspend fun getResultsByPrepareDuration(minutes: Int): List<Results?> {
        val results = dbQuery {
            ResultsTable.select {
                ResultsTable.prepareDuration.eq(minutes)
            }
                .orderBy(FoodCategoryTable.id to SortOrder.ASC)
                .limit(20, offset = 1)
                .map {
                    rowToResults(it)
                }
        }
        return results
    }

    override suspend fun updateResult(resultId: Int, results: Results) {
        dbQuery {
            ResultsTable.update({ResultsTable.id.eq(resultId)}) {
                it[id] = results.id
                it[title] = results.title
                it[description] = results.description
                it[sellerId] = results.seller_id
                it[sellerCategoryId] = results.seller_category_id
                it[resultCategoryId] = results.result_category_id
                it[foodCategoryId] = results.food_category_id!!
                it[imagePath] = results.image_path
                it[price] = results.price
                it[rating] = results.rating
                it[voteCount] = results.vote_count
                it[discount] = results.discount
                it[dateAdded] = results.date_added
                it[prepareDuration] = results.prepare_duration
            }
        }
    }

    override suspend fun deleteResultById(resultId: Int) {
        dbQuery {
            ResultsTable.deleteWhere {
                ResultsTable.id.eq(resultId)
            }
        }
    }

    override suspend fun deleteResults() {
        dbQuery {
            ResultsTable.deleteAll()
        }
    }

    private fun rowToResults(row: ResultRow?): Results? {
        if (row == null) return null

        return Results(
            id = row[ResultsTable.id],
            title = row[ResultsTable.title],
            description = row[ResultsTable.description]!!,
            seller_id = row[ResultsTable.sellerId],
            seller_category_id = row[ResultsTable.sellerCategoryId],
            result_category_id = row[ResultsTable.resultCategoryId],
            food_category_id = row[ResultsTable.foodCategoryId],
            image_path = row[ResultsTable.imagePath],
            price = row[ResultsTable.price],
            rating = row[ResultsTable.rating],
            vote_count = row[ResultsTable.voteCount],
            discount = row[ResultsTable.discount],
            date_added = row[ResultsTable.dateAdded],
            prepare_duration = row[ResultsTable.prepareDuration],
        )
    }
}