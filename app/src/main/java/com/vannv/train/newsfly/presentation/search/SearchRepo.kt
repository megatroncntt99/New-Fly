package com.vannv.train.newsfly.presentation.search

import com.vannv.train.newsfly.data.remote.base.Repo
import com.vannv.train.newsfly.data.remote.base.TypeRepo
import com.vannv.train.newsfly.data.remote.repo.BaseRepo
import com.vannv.train.newsfly.utils.Urls

/**
 * Author: vannv8@fpt.com.vn
 * Date: 23/05/2022
 */

interface SearchRepo : BaseRepo {
    fun repoGetNews(key: String): Repo {
        return Repo(
            url = "everything?q=$key&apiKey=${Urls.API_KEY}",
            headers = HashMap(),
            typeRepo = TypeRepo.GET,
            codeRequired = HashMap<String,String>(),
        )
    }
}