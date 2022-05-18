package com.vannv.train.newsfly.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vannv.train.newsfly.data.local.storage.DataStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 2:34 PM
 */

@HiltViewModel
class MainViewModel @Inject constructor(private val dataStorage: DataStorage) : ViewModel() {

    val selectedTheme = dataStorage.selectedTheme()

    fun changeSelectedTheme(theme: String) {
        viewModelScope.launch {
            dataStorage.setSelectedTheme(theme)
        }
    }
}