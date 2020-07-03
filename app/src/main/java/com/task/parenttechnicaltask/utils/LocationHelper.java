package com.task.parenttechnicaltask.utils;

import android.content.Context;
import android.location.Location;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import rx.Single;

public class LocationHelper {
    public static SingleLiveEvent<Location> getSingleLocation(Context context) {
        SingleLiveEvent<Location> liveEvent = new SingleLiveEvent<>();
        SmartLocation.with(context).location()
                .oneFix()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        liveEvent.setValue(location);
                    }
                });
        return liveEvent;
    }
}
