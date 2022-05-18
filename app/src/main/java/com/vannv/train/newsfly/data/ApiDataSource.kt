package com.vannv.train.newsfly.data

import com.vannv.train.newsfly.data.remote.dto.PopularNewsDTO
import com.vannv.train.newsfly.data.remote.ApiService
import com.vannv.train.newsfly.data.remote.Repo
import com.vannv.train.newsfly.data.remote.TypeRepo

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 3:26 PM
 */

class ApiDataSource (private val apiService: ApiService) {
    //get all news
    suspend fun getRecentNews(page: Int?, pageSize: Int) =
        apiService.getRecentNews(page = page, pageSize = pageSize)

    //get popular news
    suspend fun getPopularNews() = apiService.getPopularNews()

    //search for news
    suspend fun searchForNews(q: String) = apiService.searchForNews(q)

    suspend fun request(repo: Repo){
        when(repo.typeRepo){
            TypeRepo.GET ->{
                apiService.get<PopularNewsDTO>(repo.url,repo.headers,
                    (repo.message?:  HashMap<String,String>()) as Map<String, String>,)
            }
            TypeRepo.POST ->{

            }
            TypeRepo.PUT ->{

            }
            TypeRepo.DELETE ->{

            }
        }
    }
}