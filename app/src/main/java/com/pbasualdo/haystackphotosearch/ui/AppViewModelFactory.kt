package com.pbasualdo.haystackphotosearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pbasualdo.haystackphotosearch.network.NetworkService
import com.pbasualdo.haystackphotosearch.ui.main.MainViewModel
import com.pbasualdo.haystackphotosearch.ui.search.SearchViewModel

class AppViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when(modelClass){
        MainViewModel::class.java -> MainViewModel(NetworkService())
        SearchViewModel::class.java -> SearchViewModel(NetworkService())
        else -> throw IllegalArgumentException("Unknown ViewModel class")
    } as T
}