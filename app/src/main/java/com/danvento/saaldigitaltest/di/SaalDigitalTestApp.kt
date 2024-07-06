package com.danvento.saaldigitaltest.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.di
* 
* Created by Dan Vento. 
*/

class SaalDigitalTestApp : Application() {
    override fun onCreate() {
        super.onCreate()
//        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
//        }
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@SaalDigitalTestApp)
            modules(
                listOf(
                    roomModule,
                    dataModule,
                    viewModelModule,
                    useCaseModule,
                )
            )
        }
    }
}