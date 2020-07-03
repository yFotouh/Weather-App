package com.task.parenttechnicaltask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.task.parenttechnicaltask.R;
import com.task.parenttechnicaltask.model.dto.response.AList;
import com.task.parenttechnicaltask.wrappers.WeatherDay;

import java.util.List;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class WeatherDetailAdapter extends RecyclerView.Adapter<WeatherDetailAdapter.CityViewHolder> {

    private List<WeatherDay> weatherData;
    Context context;

    public WeatherDetailAdapter(List<WeatherDay> weatherData, Context context) {
        this.weatherData = weatherData;
        this.context = context;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_weather_detail, parent, false);

        return new CityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        WeatherDay weatherDay = weatherData.get(position);
        holder.tv_day_of_week.setText(weatherDay.day);
        holder.tv_tempreture.setText(weatherDay.temperature + " C");

//        holder.tv_contact_name.setText(jobContact.getName());
//        holder.tv_contact_phone.setText(Double.toString(jobContact.getNumber()));
    }

    @Override
    public int getItemCount() {
        return weatherData.size();
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {

        //        @BindView(R.id.tv_tempreture)
        public TextView tv_tempreture;

        //        @BindView(R.id.tv_day_of_week)
        public TextView tv_day_of_week;


        public CityViewHolder(View view) {
            super(view);
            tv_tempreture = view.findViewById(R.id.tv_tempreture);
            tv_day_of_week = view.findViewById(R.id.tv_day_of_week);
//            ButterKnife.bind(this, view);
        }
    }
}