package com.example.news.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.news.domain.model.NewsDomainModel
import com.example.news.ui.tabs.bookmarks.BookmarksModel
import com.google.firebase.auth.FirebaseUser

interface NewsRepository {
    suspend fun fetchNews(): List<NewsDomainModel>
    suspend fun loadNews()

    fun addNewsToBookmarks(news: NewsDomainModel, resultLiveData: MutableLiveData<String>)
    fun deleteNewsFromBookmarks(id: String, resultLiveData: MutableLiveData<String>)
    fun getFirebaseUser(): MutableLiveData<FirebaseUser?>
    fun setFirebaseUser(user: FirebaseUser?)

    fun logOut()

    fun getListBookmarks(): LiveData<List<BookmarksModel>>
    fun removeBookmarksListener()
}