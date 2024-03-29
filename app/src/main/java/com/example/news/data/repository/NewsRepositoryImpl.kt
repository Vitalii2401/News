package com.example.news.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.news.data.datasource.NewsDataSource
import com.example.news.domain.model.NewsDomainModel
import com.example.news.domain.repository.NewsRepository
import com.example.news.ui.tabs.bookmarks.BookmarksModel
import com.google.firebase.auth.FirebaseUser

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsDataSource.Remote,
    private val newsLocalDataSource: NewsDataSource.Local,
    private val firebaseDataSource: NewsDataSource.Firebase
) : NewsRepository {

    /* Local data source */
    override suspend fun fetchNewsFromDatabase(): List<NewsDomainModel> {
        return newsLocalDataSource.fetchNewsFromDatabase()
    }

    /* Remote data source */
    override suspend fun loadNews(): List<NewsDomainModel> {
        newsRemoteDataSource.loadNews().let { list ->
            if (list.isNotEmpty()) {
                newsLocalDataSource.clearDatabase()
                newsLocalDataSource.addNewsToDatabase(list)
            }
        }

        return newsLocalDataSource.fetchNewsFromDatabase()
    }

    /* Firebase data source */
    override fun getListBookmarks(): LiveData<List<BookmarksModel>> {
        return firebaseDataSource.getListBookmarks()
    }

    override fun getCountBookmarks(): LiveData<Long> {
        return firebaseDataSource.getCountBookmarks()
    }

    override fun addNewsToBookmarks(
        news: NewsDomainModel,
        resultLiveData: MutableLiveData<String>
    ) {
        firebaseDataSource.addNewsToBookmarks(news, resultLiveData)
    }

    override fun deleteNewsFromBookmarks(
        id: String,
        resultLiveData: MutableLiveData<String>
    ) {
        firebaseDataSource.deleteNewsFromBookmarks(id, resultLiveData)
    }

    override fun setFirebaseUser(user: FirebaseUser?) {
        firebaseDataSource.setUser(user)
    }

    override fun getFirebaseUser(): MutableLiveData<FirebaseUser?> {
        return firebaseDataSource.getUser()
    }

    override fun logOut() {
        firebaseDataSource.logOut()
    }

    override fun removeBookmarksListener() {
        firebaseDataSource.removeBookmarksListener()
    }
}