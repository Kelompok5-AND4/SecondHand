package com.igdev.secondhand.database

import androidx.paging.PagingSource
import androidx.room.*
import com.igdev.secondhand.model.PagingProduct.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products:List<Product>)
    @Query("SELECT * FROM product ORDER BY productId ASC")
    fun getProduct(): PagingSource<Int,Product>
    @Query("DELETE FROM product")
    suspend fun clearProducts()
}