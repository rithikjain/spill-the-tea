package `in`.rithikjain.spillthetea.di

import `in`.rithikjain.spillthetea.data.local.Database
import `in`.rithikjain.spillthetea.data.repository.DataStoreRepository
import `in`.rithikjain.spillthetea.data.repository.DataStoreRepositoryImpl
import `in`.rithikjain.spillthetea.data.repository.PostRepository
import `in`.rithikjain.spillthetea.data.repository.PostRepositoryImpl
import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): Database {
        return Room.databaseBuilder(
            app,
            Database::class.java,
            "tea_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePostRepository(db: Database): PostRepository {
        return PostRepositoryImpl(db.postDao)
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext app: Context): DataStoreRepository {
        return DataStoreRepositoryImpl(app)
    }
}