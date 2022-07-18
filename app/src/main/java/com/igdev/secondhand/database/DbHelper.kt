package com.igdev.secondhand.database

import com.igdev.secondhand.model.PagingProduct.Product


class DbHelper(
    private val searchHistoryDao: SearchHistoryDao,
    private val productDao: ProductDao,
    private val remoteKeysDao: RemoteKeysDao ) {

    suspend fun getSearchHistory() = searchHistoryDao.getSearchHistory()

    suspend fun insertSearchHistory(searchHistoryEntity: SearchHistoryEntity) =
        searchHistoryDao.insertSearchHistory(searchHistoryEntity)

    suspend fun clearHistory(searchHistoryEntity: SearchHistoryEntity) =
        searchHistoryDao.clearHistory(searchHistoryEntity)

    suspend fun clearAllHistory() = searchHistoryDao.clearAllHistory()

    suspend fun insertAllProduct(product : List<Product>)= productDao.insertAll(product)
    suspend fun getProduct()= productDao.getProduct()
    suspend fun clearProduct()= productDao.clearProducts()

    suspend fun insertAllRemoteKeys(remoteKeys : List<RemoteKeys>)= remoteKeysDao.insertAll(remoteKeys)
    suspend fun remoteKeysProductId(productId:Int)= remoteKeysDao.remoteKeysProductId(productId)
    suspend fun clearRemoteKeys()= remoteKeysDao.clearRemoteKeys()


}