package com.task.parenttechnicaltask.ui

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.task.parenttechnicaltask.R
import com.task.parenttechnicaltask.ui.adapter.CityAdapter
import com.task.parenttechnicaltask.ui.adapter.ICityAction
import com.task.parenttechnicaltask.utils.LocationHelper
import com.task.parenttechnicaltask.utils.PermissionHelper
import com.task.parenttechnicaltask.viewmodel.CityViewModel
import com.task.parenttechnicaltask.ui.wrappers.CityWeatherWrapper
import com.tests.newandroid.viewmodel.WeatherFragmentViewModelKt
import kotlinx.android.synthetic.main.fragment_weather.*
import org.koin.androidx.viewmodel.ext.android.viewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : Fragment() {


    val weatherViewModel: WeatherFragmentViewModelKt by viewModel()
    val cityViewModel: CityViewModel by viewModel()
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private var cityWeatherWrappers = ArrayList<CityWeatherWrapper>()
    lateinit var cities: List<String>
    lateinit var citySet: HashSet<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_weather, container, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        citySet = HashSet<String>()
        getAllCities()
        refreshAdapter()
        getCachedWeatherData()
        getCurrentLocationForecastOrLondon()
    }

    private fun getAllCities() {
        cityViewModel.getCities().observe(viewLifecycleOwner, Observer {
            cities = it.citiesName
            manageAutoCompleteAdapter()

        })
    }

    private fun getCurrentLocationForecastOrLondon() {
        PermissionHelper.getPermission(activity, object : MultiplePermissionsListener {
            override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    LocationHelper.getSingleLocation(activity)
                        .observe(viewLifecycleOwner, Observer {
                            if (cityWeatherWrappers.size == 0)
                                callWebApiByCordiante(it.latitude.toFloat(), it.longitude.toFloat())

                        })
                } else {

                    callWebApi("London")
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                list: List<PermissionRequest>,
                permissionToken: PermissionToken
            ) {
            }
        }, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun getCachedWeatherData() {
        weatherViewModel.getCachedWeatherData().observe(viewLifecycleOwner, Observer {
            cityWeatherWrappers = it
            for ((index, value) in cityWeatherWrappers.withIndex()) {
                citySet.add(value.cityName)
            }
            refreshAdapter()
        })
    }

    private fun WeatherFragment.manageAutoCompleteAdapter() {
        val adapter =
            ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, cities)
        autoCompleteTextView.threshold = 1 //will start working from first character

        autoCompleteTextView.setAdapter(adapter) //setting the adapter data into the AutoCompleteTextView

        autoCompleteTextView.setTextColor(Color.RED)
        autoCompleteTextView.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            val selected =
                parent.getItemAtPosition(position) as String
            if (getCityFromApi(selected)) return@OnItemClickListener Unit

        })
    }

    private fun getCityFromApi(selected: String): Boolean {
        if (citySet.contains(selected) == true) {
            autoCompleteTextView.setText("")
            Toast.makeText(activity, getString(R.string.city_already), Toast.LENGTH_LONG).show()
            return true
        }
        if (cityWeatherWrappers.size >= 5) {
            Toast.makeText(activity, getString(R.string.max_cities), Toast.LENGTH_LONG)
                .show()
        } else {

            callWebApi(selected)
            autoCompleteTextView.setText("")
        }
        return false
    }

    private fun refreshAdapter() {

        viewAdapter = CityAdapter(
            cityWeatherWrappers,
            activity,
            ICityAction {
                cityWeatherWrappers.remove(it)
                citySet.remove(it.cityName)
                weatherViewModel.cacheWeatherData(cityWeatherWrappers)
                refreshAdapter()
            })
        var linearLayoutManager = LinearLayoutManager(activity)
        rv_cities.layoutManager = linearLayoutManager
        rv_cities.adapter = viewAdapter
    }

    private fun callWebApi(city: String) {

        weatherViewModel.getForecast(city).observe(viewLifecycleOwner, Observer {
            updateListAndAdapter(it)
        })
    }

    private fun callWebApiByCordiante(lat: Float, lon: Float) {

        weatherViewModel.getForecast(lat, lon).observe(viewLifecycleOwner, Observer {
//            if (cityWeatherWrappers.size == 0)
            updateListAndAdapter(it)

        })
    }

    private fun updateListAndAdapter(it: CityWeatherWrapper) {
        if (citySet.add(it.cityName) == false)
            return
        cityWeatherWrappers.add(it)
        refreshAdapter()
        weatherViewModel.cacheWeatherData(cityWeatherWrappers)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param city Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WeatherFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            WeatherFragment().apply {
            }
    }
}
