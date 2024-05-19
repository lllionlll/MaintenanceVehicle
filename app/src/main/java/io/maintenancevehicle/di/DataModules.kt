package io.maintenancevehicle.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.maintenancevehicle.data.source.local.MaintenanceVehicleDatabase
import io.maintenancevehicle.data.repository.MaintenanceVehicleRepository
import io.maintenancevehicle.data.source.local.MaintenanceVehicleDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideMaintenanceVehicleLocalDataSource(
        maintenanceVehicleDatabase: MaintenanceVehicleDatabase
    ): MaintenanceVehicleDataSource {
        return MaintenanceVehicleDataSource(maintenanceVehicleDatabase.maintenanceVehicleDao())
    }

}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMaintenanceVehicleRepository(
        maintenanceVehicleDataSource: MaintenanceVehicleDataSource
    ): MaintenanceVehicleRepository {
        return MaintenanceVehicleRepository(maintenanceVehicleDataSource)
    }

}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): MaintenanceVehicleDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MaintenanceVehicleDatabase::class.java,
            "maintenance_vehicle.db"
        ).build()
    }
}