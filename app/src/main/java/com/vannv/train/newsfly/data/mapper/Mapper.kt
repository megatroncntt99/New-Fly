package com.vannv.train.newsfly.data.mapper

import com.vannv.train.newsfly.data.remote.dto.NewDTO
import com.vannv.train.newsfly.domain.entity.New

/**
 * Author: vannv8@fpt.com.vn
 * Date: 23/05/2022
 */

fun NewDTO.mapToNew() = New(
    author = author ?: "",
    content = content ?: "",
    description = description ?: "",
    publishedAt = publishedAt ?: "",
    source = source?.name ?: "",
    title = title ?: "",
    url = url ?: "",
    urlToImage = urlToImage ?: ""
)