package com.task.parenttechnicaltask.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tests.newandroid.models.WeatherRepository;

public class MainActivityViewModel extends AndroidViewModel {
    private WeatherRepository weatherRepository;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
//        bookRepository =new BookRepositoryImpl(application);
//        bookRepository =new BookRepositoryDummy();

    }

//    public LiveData<List<Book>> getAllBooks() {
//        return bookRepository.getMutableLiveData();
//    }
}
