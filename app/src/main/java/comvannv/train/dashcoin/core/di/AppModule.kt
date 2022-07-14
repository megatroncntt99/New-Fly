package comvannv.train.dashcoin.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import comvannv.train.dashcoin.core.utils.Constants
import comvannv.train.dashcoin.data.database.DashCoinDao
import comvannv.train.dashcoin.data.database.DashCoinDatabase
import comvannv.train.dashcoin.data.remote.DashCoinApi
import comvannv.train.dashcoin.domain.usecase.DashCoinUseCase
import comvannv.train.dashcoin.domain.usecase.local.DeleteCoinUseCase
import comvannv.train.dashcoin.domain.usecase.local.GetAllCoinsUseCase
import comvannv.train.dashcoin.domain.usecase.local.InsertCoinUseCase
import comvannv.train.dashcoin.domain.usecase.remote.GetChartUseCase
import comvannv.train.dashcoin.domain.usecase.remote.GetCoinUseCase
import comvannv.train.dashcoin.domain.usecase.remote.GetCoinsUseCase
import comvannv.train.dashcoin.domain.usecase.remote.GetNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDashCoinApi(): DashCoinApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient().newBuilder().also { client ->
                client.connectTimeout(120, TimeUnit.SECONDS)
                client.writeTimeout(120, TimeUnit.SECONDS)
                client.addNetworkInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            }.build())
            .build()
            .create(DashCoinApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDashCoinDatabase(@ApplicationContext context: Context): DashCoinDatabase {
        return Room.databaseBuilder(context, DashCoinDatabase::class.java, Constants.DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun provideDashCoinDao(database: DashCoinDatabase): DashCoinDao {
        return database.dashCoinDao()
    }

    @Provides
    @Singleton
    fun provideDashCoinUseCase(api: DashCoinApi, dao: DashCoinDao): DashCoinUseCase {
        return DashCoinUseCase(
            getCoin = GetCoinUseCase(api),
            getCoins = GetCoinsUseCase(api),
            getChart = GetChartUseCase(api),
            getNews = GetNewsUseCase(api),
            addCoin = InsertCoinUseCase(dao),
            deleteCoin = DeleteCoinUseCase(dao),
            getAllCoins = GetAllCoinsUseCase(dao)
        )
    }
}