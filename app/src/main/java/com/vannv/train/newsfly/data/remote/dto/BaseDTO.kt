package com.vannv.train.newsfly.data.remote.dto

import com.google.gson.JsonElement
import java.util.*

/**
 * Author: vannv8@fpt.com.vn
 * Date: 20/05/2022
 */

class BaseDTO(
    val data: JsonElement? = null,
    val message: String = "",
    val code: Int = -1,
    val status: String = ""
) {
    fun data(): String {
        return Objects.toString(data)
    }
}