package com.vannv.train.newsfly.room.main

import androidx.room.TypeConverter
import com.vannv.train.newsfly.model.Source

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 1:49 PM
 */

class RoomConverter {
    @TypeConverter
    fun fromSource(source: Source): String = source.name

    @TypeConverter
    fun toSource(name: String) = Source(name, name)
}