package com.example.android.roomyweather.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView

import androidx.activity.viewModels
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomyweather.BuildConfig
import com.example.android.roomyweather.R
import com.example.android.roomyweather.data.*
import com.google.android.material.navigation.NavigationView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar



/*
 * To use your own OpenWeather API key, create a file called `gradle.properties` in your
 * GRADLE_USER_HOME directory (this will usually be `$HOME/.gradle/` in MacOS/Linux and
 * `$USER_HOME/.gradle/` in Windows), and add the following line:
 *
 *   OPENWEATHER_API_KEY="<put_your_own_OpenWeather_API_key_here>"
 *
 * The Gradle build for this project is configured to automatically grab that value and store
 * it in the field `BuildConfig.OPENWEATHER_API_KEY` that's used below.  You can read more
 * about this setup on the following pages:
 *
 *   https://developer.android.com/studio/build/gradle-tips#share-custom-fields-and-resource-values-with-your-app-code
 *
 *   https://docs.gradle.org/current/userguide/build_environment.html#sec:gradle_configuration_properties
 *
 * Alternatively, you can just hard-code your API key below 🤷‍.
 */
const val OPENWEATHER_API_KEY = "711c192ac096e7940e096f8195e14c2f"
const val OPENWEATHER_APPID = "711c192ac096e7940e096f8195e14c2f"

//const val OPENWEATHER_APPID = BuildConfig.OPENWEATHER_API_KEY

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val databaseViewModel: SavedCitiesViewModel by viewModels()
    private lateinit var cityListRV: RecyclerView

    private lateinit var drawerCityAdapter: DrawerCityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("redCheck","OnCreate in MainActivity.kt")
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)

        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)


        cityListRV = findViewById(R.id.rv_city_list)
        cityListRV.layoutManager = LinearLayoutManager(this)
        cityListRV.setHasFixedSize(true)
        drawerCityAdapter = DrawerCityAdapter(::onCityNameClick)
        cityListRV.adapter = drawerCityAdapter
        databaseViewModel.savedCities.observe(this){savedCities ->
            this.drawerCityAdapter.updateCityList(savedCities)
        }

    }

    private fun onCityNameClick(forcastCityEntity: ForcastCityEntity) {
        Log.d("ClickCheck","CityName in the drawer being clicked.")
        //val directions = ForecastFetchFragmentDirections
           // .navigateToForecastDetail(forecastAdapter.forecastCity!!,forecastPeriod)
        //findNavController().navigate(directions)
//        val intent = Intent(this, ForecastDetailActivity::class.java).apply {
//            putExtra(EXTRA_FORECAST_PERIOD, forecastPeriod)
//            putExtra(EXTRA_FORECAST_CITY, forecastAdapter.forecastCity)
        //       }
        //       startActivity(intent)
    }
    override fun onSupportNavigateUp(): Boolean {

        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) ||
        super.onSupportNavigateUp()
    }
}