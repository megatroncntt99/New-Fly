package com.vannv.train.newsfly.network

/**
 * Creator: Nguyen Van Van
 * Date: 25,April,2022
 * Time: 8:59 AM
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