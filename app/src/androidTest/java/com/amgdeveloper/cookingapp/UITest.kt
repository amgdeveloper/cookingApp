package com.amgdeveloper.cookingapp

import android.Manifest
import android.app.Application
import android.content.Intent
import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.amgdeveloper.cookingapp.ui.main.MainActivity
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by amgdeveloper on 17/02/2021
 */
class UITest {


    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    @get:Rule
    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private lateinit var mockWebServer: MockWebServer
    private lateinit var resource: OkHttp3IdlingResource

    @Before
    fun setUp() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as Application
        val component = DaggerUITestComponent.factory().create(app)

        mockWebServer = component.mockWebServer

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(recipes)
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(recipeSummary)
        )


        resource = OkHttp3IdlingResource.create("OkHttp", component.spoonacular.okHttpClient)
        IdlingRegistry.getInstance().register(resource)

        val intent = Intent(instrumentation.targetContext, MainActivity::class.java)

        activityTestRule.launchActivity(intent)
    }

    @Test
    fun clickARecipeNavigatesToDetail() {

        SystemClock.sleep(1000)

        onView(withId(R.id.recipeListFragmentRv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                4, ViewActions.click()
            )
        )
        onView(withId(R.id.toolbar))
            .check(matches(hasDescendant(withText("Roma Tomato Bruschetta"))))
    }

    @After
    fun tearDown() {
        mockWebServer.close()
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(resource)
    }
}

