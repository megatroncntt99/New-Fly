package com.vannv.train.newsfly.data.remote.repo

import com.vannv.train.newsfly.presentation.jetpackcomposepaging.screens.home.HomeRepo
import com.vannv.train.newsfly.presentation.search.SearchRepo

/**
 * Author: vannv8@fpt.com.vn
 * Date: 23/05/2022
 */

class RepoManager : SearchRepo, HomeRepo

interface BaseRepo {
}