package com.moose.foodies.domain.repositories

import com.moose.foodies.data.local.ItemsDao
import com.moose.foodies.data.local.UserDao
import com.moose.foodies.data.remote.ApiEndpoints
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface HomeRepository {
    val profile: Flow<Profile>
    val feed: Flow<List<Recipe>>

    suspend fun fetchData()
}

class HomeRepositoryImpl @Inject constructor(val api: ApiEndpoints, val userDao: UserDao, val itemsDao: ItemsDao): HomeRepository {
    override val profile: Flow<Profile>
        get() = userDao.getProfile()

    override val feed: Flow<List<Recipe>>
        get() = itemsDao.getFeedRecipes()

    override suspend fun fetchData() {
        val feed = api.getFeed()
        itemsDao.updateFeedRecipes(feed)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeRepositoryBinding {

    @Binds
    abstract fun provideRepository(impl: HomeRepositoryImpl): HomeRepository
}
