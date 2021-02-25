package com.amgdeveloper.cookingapp

import android.app.Application
import com.amgdeveloper.cookingapp.data.server.Spoonacular
import com.amgdeveloper.cookingapp.di.AppModule
import com.amgdeveloper.cookingapp.di.CookingComponent
import com.amgdeveloper.cookingapp.di.DataModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import java.lang.NullPointerException
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by amgdeveloper on 16/02/2021
 */

@Singleton
@Component(modules = [AppModule::class, DataModule::class, TestServerModule::class])
interface UITestComponent : CookingComponent{

    val spoonacular : Spoonacular
    val mockWebServer : MockWebServer

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance app : Application): UITestComponent
    }
}

@Module
class TestServerModule {

    @Provides
    @Singleton
    @Named("baseUrl")
    fun baseUrlProvider() = "http://127.0.0.1:8080"

    @Provides
    @Singleton
    fun mockWebServerProvider(): MockWebServer {
        var mockWebServer: MockWebServer? = null
        val thread = Thread(Runnable {
            mockWebServer = MockWebServer()
            mockWebServer?.start(8080)
        })

        thread.start()
        thread.join()
        return mockWebServer ?: throw NullPointerException()
    }

    @Provides
    @Singleton
    fun SpooncularTestServiceProvider(
            @Named("baseUrl") baseUrl: String
    ): Spoonacular = Spoonacular(baseUrl)
}


