package com.vannv.train.newsfly.data.local.sqldelight

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.vannv.train.newsfly.PersonDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import persondb.PersonEntity


/**
 * Author: vannv8@fpt.com.vn
 * Date: 19/07/2022
 */
class PersonDataSource(db: PersonDatabase) {
    private val queries = db.personEntityQueries
    suspend fun getPersonById(id: Long): PersonEntity? {
        return withContext(Dispatchers.IO) {
            queries.getPersonById(id).executeAsOneOrNull()
        }
    }

    fun getAllPersons(): Flow<List<PersonEntity>> {
        return queries.getAllPersons().asFlow().mapToList()
    }

    suspend fun deletePersonById(id: Long) {
        withContext(Dispatchers.IO) {
            queries.deletePersonById(id)
        }
    }

    suspend fun insertPerson(id: Long? = null, firstName: String, lastName: String) {
        withContext(Dispatchers.IO) {
            queries.insertPerson(id, firstName, lastName)
        }
    }
}