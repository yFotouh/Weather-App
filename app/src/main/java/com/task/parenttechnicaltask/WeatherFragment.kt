package com.task.parenttechnicaltask

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
import com.task.parenttechnicaltask.adapter.CityAdapter
import com.task.parenttechnicaltask.adapter.ICityAction
import com.task.parenttechnicaltask.repository.CityRepository
import com.task.parenttechnicaltask.utils.LocationHelper
import com.task.parenttechnicaltask.utils.PermissionHelper
import com.task.parenttechnicaltask.wrappers.CityWeatherWrapper
import com.tests.newandroid.viewmodel.WeatherFragmentViewModelKt
import io.nlopez.smartlocation.OnLocationUpdatedListener
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.include_weather_forecast.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : Fragment() {


    val myViewModel: WeatherFragmentViewModelKt by viewModel()
    lateinit var myViewModel1: WeatherFragmentViewModelKt
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    //    private lateinit var viewManager: RecyclerView.LayoutManager
    private var cityWeatherWrappers = ArrayList<CityWeatherWrapper>()

    // evaluates dependency lazily
    val cityRepository: CityRepository by inject()
//    val weatherRepository: WeatherRepository by inject()

    // TODO: Rename and change types of parameters
    private var city: String? = null
    lateinit var cities: List<String>
    lateinit var citySet: HashSet<String>

    //    private var param2: String? = null
//    @BindView(R.id.autoCompleteTextView)
//    lateinit  var autoCompleteTextView: AutoCompleteTextView
//    private val button by bind<Button>(R.id.button)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            city = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
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
//        ButterKnife.bind(this, view)
        citySet = HashSet<String>()
        refreshAdapter()
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
//        var factory = MainActivityViewModelFactory(weatherRepository)
//        myViewModel1 = ViewModelProvider(this, factory).get(WeatherFragmentViewModelKt::class.java)
        myViewModel.getCachedWeatherData().observe(viewLifecycleOwner, Observer {
            cityWeatherWrappers = it
            refreshAdapter()
        })


        GlobalScope.launch(Dispatchers.Main) {
            cities = fetchCities()
            val adapter =
                ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, cities)
            //Getting the instance of AutoCompleteTextView
            //Getting the instance of AutoCompleteTextView

            autoCompleteTextView.threshold = 1 //will start working from first character

            autoCompleteTextView.setAdapter(adapter) //setting the adapter data into the AutoCompleteTextView

            autoCompleteTextView.setTextColor(Color.RED)
            autoCompleteTextView.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
                val selected =
                    parent.getItemAtPosition(position) as String
                if (citySet.add(selected) == false) {
                    autoCompleteTextView.setText("")
                    Toast.makeText(activity, "City already entered", Toast.LENGTH_LONG).show()
                    return@OnItemClickListener Unit
                }
                if (cityWeatherWrappers.size >= 5) {
                    Toast.makeText(activity, "Max. number of cities reached", Toast.LENGTH_LONG)
                        .show()
                } else {

                    callWebApi(selected)
                    autoCompleteTextView.setText("")
                }

            })

        }


    }

    private fun refreshAdapter() {

        viewAdapter = CityAdapter(cityWeatherWrappers, activity, ICityAction {
            cityWeatherWrappers.remove(it)
            citySet.remove(it.cityName)
            myViewModel.cacheWeatherData(cityWeatherWrappers)
            refreshAdapter()
        })
        var linearLayoutManager = LinearLayoutManager(activity)
        rv_cities.layoutManager = linearLayoutManager
        rv_cities.adapter = viewAdapter
    }

    private fun callWebApi(city: String) {

        myViewModel.getForecast(city).observe(viewLifecycleOwner, Observer {
            updateListAndAdapter(it)
        })
    }

    private fun callWebApiByCordiante(lat: Float, lon: Float) {

        myViewModel.getForecast(lat, lon).observe(viewLifecycleOwner, Observer {
            updateListAndAdapter(it)
        })
    }

    private fun updateListAndAdapter(it: CityWeatherWrapper) {
        cityWeatherWrappers.add(it)
        refreshAdapter()
        myViewModel.cacheWeatherData(cityWeatherWrappers)
    }

    suspend fun fetchCities(): List<String> {
        return withContext(Dispatchers.IO) {

            cities = cityRepository.getAllCities().citiesName
            return@withContext cities

        }
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
        fun newInstance(city: String) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, city)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
