package com.vannv.train.newsfly.utils

import com.google.gson.GsonBuilder
import org.json.JSONObject

/**
 * Author: vannv8@fpt.com.vn
 * Date: 20/05/2022
 */

object JSON {
    private val parseGson = GsonBuilder().disableHtmlEscaping().create()

    @Throws(Exception::class)
    fun encode(obj: Any?): String {
        return try {
            parseGson.toJson(obj)
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }


    fun <T> decodeToList(jsonElement: String?, clazz: Class<Array<T>>): List<T> {
        return try {
            val array: Array<T> = parseGson.fromJson(jsonElement, clazz)
            array.toList()
        } catch (e: Exception) {
            ArrayList()
        }
    }

    fun <T> decodeToArrayList(jsonElement: String?, clazz: Class<Array<T>>): MutableList<T> {
        return try {
            val array: Array<T> = parseGson.fromJson(jsonElement, clazz)
            array.toMutableList()
        } catch (e: Exception) {
            ArrayList()
        }
    }

    fun <T> decode(json: String?, tClass: Class<T>?): T? {
        return try {
            parseGson.fromJson(json, tClass)
        } catch (e: Exception) {
            null
        }
    }

    fun evertStringToJSON(json: String?): JSONObject? {
        return try {
            JSONObject(json)
        } catch (e: Exception) {
            null
        }
    }
}