package com.task.parenttechnicaltask.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.task.parenttechnicaltask.R;
import com.task.parenttechnicaltask.ui.wrappers.CityWeatherWrapper;
import com.task.parenttechnicaltask.ui.wrappers.WeatherDay;

import java.util.ArrayList;
import java.util.List;


public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private List<CityWeatherWrapper> cityWeatherWrappers;
    Context context;
    ICityAction iCityAction;

    public CityAdapter(List<CityWeatherWrapper> cityWeatherWrappers, Context context, ICityAction iCityAction) {
        this.cityWeatherWrappers = cityWeatherWrappers;
        this.context = context;
        this.iCityAction = iCityAction;
    }

    @Override
    public CityAdapter.CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_city_forcast, parent, false);

        return new CityAdapter.CityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.CityViewHolder holder, int position) {
        CityWeatherWrapper cityWeatherWrapper = cityWeatherWrappers.get(position);
        holder.tv_city_name.setText(cityWeatherWrapper.cityName);
        holder.refreshAdapter(cityWeatherWrapper.weatherDays);
        holder.iv_delete.setOnClickListener(view -> {
            iCityAction.removeCity(cityWeatherWrapper);

        });
//        holder.tv_contact_name.setText(jobContact.getName());
//        holder.tv_contact_phone.setText(Double.toString(jobContact.getNumber()));
    }

    @Override
    public int getItemCount() {
        return cityWeatherWrappers.size();
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {

        //        @BindView(R.id.tv_city_name)
        public TextView tv_city_name;

        //        @BindView(R.id.rv_forecast)
        public RecyclerView rv_forecast;
        public ImageView iv_delete;

        public CityViewHolder(View view) {
            super(view);
            tv_city_name = view.findViewById(R.id.tv_city_name);
            rv_forecast = view.findViewById(R.id.rv_forecast);
            iv_delete = view.findViewById(R.id.iv_delete);
            refreshAdapter(new ArrayList<WeatherDay>());

//            ButterKnife.bind(this, view);
        }

        public void refreshAdapter(List<WeatherDay> aLists) {
            rv_forecast.setHasFixedSize(true);

            // use a linear layout manager
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            rv_forecast.setLayoutManager(layoutManager);

            // specify an adapter (see also next example)
            WeatherDetailAdapter mAdapter = new WeatherDetailAdapter(aLists, context);
            rv_forecast.setAdapter(mAdapter);
        }
    }
}