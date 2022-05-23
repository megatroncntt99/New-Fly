package com.vannv.train.newsfly.data.datasource.news

import com.vannv.train.newsfly.data.local.room.NewDao
import com.vannv.train.newsfly.domain.entity.New
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Author: vannv8@fpt.com.vn
 * Date: 23/05/2022
 */

class NewLocalDataSourceImpl @Inject constructor(private val newDao: NewDao) : NewLocalDataSource {

    override fun getNews(): Flow<List<New>> = newDao.getAllNews()


    override suspend fun insertNews(news: List<New>) {
        newDao.insertNews(news)
    }

    override suspend fun deleteNews() {
        newDao.deleteAllNews()
    }
}