package com.vannv.train.newsfly.presentation.jetpackcomposepaging.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.room.Query
import com.vannv.train.newsfly.domain.usecase.HomeUseCase
import com.vannv.train.newsfly.presentation.base.BaseViewModel
import com.vannv.train.newsfly.presentation.jetpackcomposepaging.model.UnsplashImage
import com.vannv.train.newsfly.presentation.search.SearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * Author: vannv8@fpt.com.vn
 * Date: 03/06/2022
 */
@HiltViewModel
class HomeViewModel @Inject constructor(private val homeUseCase: HomeUseCase) :
    BaseViewModel<HomeRepo>() {
    val getAllImages = homeUseCase.getAllImages.invoke()

    fun getSearchImages(query: String) = homeUseCase.getSearchImages.invoke(query)

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _searchImages = MutableStateFlow<PagingData<UnsplashImage>>(PagingData.empty())
    val searchImages: StateFlow<PagingData<UnsplashImage>> = _searchImages

    fun searchHeroes(query: String) {
        viewModelScope {
            homeUseCase.getSearchImages(query = query).cachedIn(viewModelScope).collect {
                _searchImages.value = it
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}