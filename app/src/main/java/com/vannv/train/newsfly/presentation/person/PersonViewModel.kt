package com.vannv.train.newsfly.presentation.person

import androidx.lifecycle.viewModelScope
import com.vannv.train.newsfly.data.local.sqldelight.PersonDataSource
import com.vannv.train.newsfly.presentation.base.BaseViewModel
import com.vannv.train.newsfly.presentation.search.SearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import persondb.PersonEntity
import javax.inject.Inject

/**
 * Author: vannv8@fpt.com.vn
 * Date: 19/07/2022
 */
@HiltViewModel
class PersonViewModel @Inject constructor(private val personDataSource: PersonDataSource) : BaseViewModel<SearchRepo>() {

    val persons = personDataSource.getAllPersons()
    private val _personDetail: MutableStateFlow<PersonEntity?> = MutableStateFlow(null)
    val personDetail: StateFlow<PersonEntity?> = _personDetail
    fun onInsertPerson(firstName: String, lastName: String) {
        if (firstName.isBlank() || lastName.isBlank()) return
        viewModelScope.launch(Dispatchers.IO) {
            personDataSource.insertPerson(firstName = firstName, lastName = lastName)
        }
    }

    fun onDeletePerson(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            personDataSource.deletePersonById(id)
        }
    }

    fun getPersonById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _personDetail.value = personDataSource.getPersonById(id)
        }
    }

    fun onPersonDialogDismiss() {
        _personDetail.value = null
    }
}