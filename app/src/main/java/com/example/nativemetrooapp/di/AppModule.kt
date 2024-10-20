package com.example.nativemetrooapp.di

import android.app.Application
import androidx.room.Room
import com.example.nativemetrooapp.data.local.StationDao
import com.example.nativemetrooapp.data.local.StationDatabase
import com.example.nativemetrooapp.data.local.StationRepository
import com.example.nativemetrooapp.data.remote.MapDirectionsService
import com.example.nativemetrooapp.data.remote.MapsRepositoryImpl
import com.example.nativemetrooapp.helper.LocationHelper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFusedLocationProviderClient(application: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(application)
    }

    @Provides
    fun provideLocationHelper(
        fusedLocationProviderClient: FusedLocationProviderClient,
        application: Application
    ): LocationHelper {
        return LocationHelper(fusedLocationProviderClient, application)
    }
    @Provides
    @Singleton
    fun provideStationDatabase(application: Application): StationDatabase {
        return Room.databaseBuilder(
            application,
            StationDatabase::class.java,
            "station_database"
        ).fallbackToDestructiveMigration() // Optional: handle migrations if needed
            .build()
    }

    @Provides
    @Singleton
    fun provideStationDao(database: StationDatabase): StationDao {
        return database.stationDao()
    }
    @Provides
    @Singleton
    fun provideStationRepository(stationDao: StationDao): StationRepository {
        return StationRepository(stationDao)
    }
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
    @Provides
    @Singleton
    fun providesMapDirectionsService(
        retrofit: Retrofit
    ): MapDirectionsService = retrofit.create(MapDirectionsService::class.java)

    @Provides
    @Singleton
    fun provideMapsRepository(
        directionsService: MapDirectionsService
    ): MapsRepositoryImpl {
        return MapsRepositoryImpl(directionsService)
    }
}