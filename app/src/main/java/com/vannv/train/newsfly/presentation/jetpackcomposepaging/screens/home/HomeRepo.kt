package com.vannv.train.newsfly.presentation.jetpackcomposepaging.screens.home


import com.vannv.train.newsfly.data.remote.base.Repo
import com.vannv.train.newsfly.data.remote.base.TypeRepo
import com.vannv.train.newsfly.data.remote.repo.BaseRepo
import com.vannv.train.newsfly.utils.Constant
import com.vannv.train.newsfly.utils.Urls


/**
 * Author: vannv8@fpt.com.vn
 * Date: 03/06/2022
 */
interface HomeRepo : BaseRepo {
    fun repoGetAllImages(page: Int, perPage: Int = Constant.ITEMS_PER_PAGE): Repo {
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Client-ID ${Urls.API_KEY}"
        return Repo(
            url = "photos?page=$page&perPage=$perPage",
            headers = headers,
            typeRepo = TypeRepo.GET,
            codeRequired = HashMap<String, String>(),
        )
    }

    fun repoGetSearchImages(query: String, perPage: Int = Constant.ITEMS_PER_PAGE): Repo
    {
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Client-ID ${Urls.API_KEY}"
        return Repo(
            url = "search/photos?query=$query&perPage=$perPage",
            headers = headers,
            typeRepo = TypeRepo.GET,
            codeRequired = HashMap<String, String>(),
        )
    }
}