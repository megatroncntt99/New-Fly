package com.vannv.train.newsfly.data.remote.base

enum class TypeError {
    NO_INTERNET,
    REDIRECT_RESPONSE_ERROR,
    CLIENT_REQUEST_ERROR,
    SERVER_RESPONSE_ERROR,
    ANOTHER_ERROR,
    UNAUTHORISED,
    INTERNAL_ERROR,
    NO_RESPONSE;
}