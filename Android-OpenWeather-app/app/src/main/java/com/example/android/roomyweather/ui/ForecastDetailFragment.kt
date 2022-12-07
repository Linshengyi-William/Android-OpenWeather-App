package com.example.android.roomyweather.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.example.android.roomyweather.R
import com.example.android.roomyweather.data.ForecastCity
import com.example.android.roomyweather.data.ForecastPeriod
import com.example.android.roomyweather.util.getTempUnitsDisplay
import com.example.android.roomyweather.util.getWindSpeedUnitsDisplay
import com.example.android.roomyweather.util.openWeatherEpochToDate


class ForecastDetailFragment : Fragment(R.layout.forecast_detail) {
    //private var forecastCity: ForecastCity? = null
    //private var forecastPeriod: ForecastPeriod? = null

    private val args: ForecastDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        //if (intent != null && intent.hasExtra(EXTRA_FORECAST_CITY)) {
            //forecastCity = intent.getSerializableExtra(EXTRA_FORECAST_CITY) as ForecastCity
            view.findViewById<TextView>(R.id.tv_forecast_city).text = args.forecastCity!!.name
        //}


        //if (intent != null && intent.hasExtra(EXTRA_FORECAST_PERIOD)) {
            //forecastPeriod = intent.getSerializableExtra(EXTRA_FORECAST_PERIOD) as ForecastPeriod

            /*
             * Figure out the correct temperature and wind units to display for the current
             * setting of the units preference.
             */
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val units = sharedPrefs.getString(getString(R.string.pref_units_key), null)
            val tempUnitsDisplay = getTempUnitsDisplay(units, requireContext())
            val windUnitsDisplay = getWindSpeedUnitsDisplay(units, requireContext())

            Glide.with(this)
                .load(args.forecastPeriod.iconUrl)
                .into(view.findViewById(R.id.iv_forecast_icon))

            view.findViewById<TextView>(R.id.tv_forecast_date).text = getString(
                R.string.forecast_date_time,
                openWeatherEpochToDate(args.forecastPeriod.epoch, args.forecastCity!!.tzOffsetSec)
            )

            view.findViewById<TextView>(R.id.tv_low_temp).text = getString(
                R.string.forecast_temp,
                args.forecastPeriod.lowTemp,
                tempUnitsDisplay
            )

            view.findViewById<TextView>(R.id.tv_high_temp).text = getString(
                R.string.forecast_temp,
                args.forecastPeriod.highTemp,
                tempUnitsDisplay
            )

            view.findViewById<TextView>(R.id.tv_pop).text =
                getString(R.string.forecast_pop, args.forecastPeriod.pop)

            view.findViewById<TextView>(R.id.tv_clouds).text =
                getString(R.string.forecast_clouds, args.forecastPeriod.cloudCover)

            view.findViewById<TextView>(R.id.tv_wind).text = getString(
                R.string.forecast_wind,
                args.forecastPeriod.windSpeed,
                windUnitsDisplay
            )

            view.findViewById<ImageView>(R.id.iv_wind_dir).rotation =
                args.forecastPeriod.windDirDeg.toFloat()

            view.findViewById<TextView>(R.id.tv_forecast_description).text =
                args.forecastPeriod.description


    }

    override fun onCreateOptionsMenu(menu: Menu,menuInflater: MenuInflater){
        menuInflater.inflate(R.menu.activity_forecast_detail, menu)
        //return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                shareForecastText()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * This method constructs a simple string of text representing the current forecast period
     * and opens the Android Sharesheet to share that string.
     */
    private fun shareForecastText() {
        if (args.forecastCity != null && args.forecastPeriod != null) {
            /*
             * Figure out the correct temperature and wind units to display for the current
             * setting of the units preference.
             */
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val units = sharedPrefs.getString(getString(R.string.pref_units_key), null)
            val tempUnitsDisplay = getTempUnitsDisplay(units, requireContext())

            val date = openWeatherEpochToDate(args.forecastPeriod.epoch, args.forecastCity!!.tzOffsetSec)
            val shareText = getString(
                R.string.share_forecast_text,
                getString(R.string.app_name),
                args.forecastCity!!.name,
                getString(R.string.forecast_date_time, date),
                args.forecastPeriod.description,
                getString(R.string.forecast_temp, args.forecastPeriod.highTemp, tempUnitsDisplay),
                getString(R.string.forecast_temp, args.forecastPeriod.lowTemp, tempUnitsDisplay),
                getString(R.string.forecast_pop, args.forecastPeriod.pop)
            )

            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareText)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(intent, null))
        }
    }
}