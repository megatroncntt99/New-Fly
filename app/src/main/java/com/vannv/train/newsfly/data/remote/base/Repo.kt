package com.vannv.train.newsfly.data.remote.base

/**
 * Author: vannv8@fpt.com.vn
 * Date: 20/05/2022
 */

class Repo(
    val headers: Map<String, String>,
    val url: String,
    val message: Any? = null,
    val codeRequired: Any? = null,
    val typeRepo: TypeRepo = TypeRepo.GET,
)

enum class TypeRepo {
    GET, POST, PUT, DELETE
}