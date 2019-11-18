package com.example.pubchem_chemistry_handbook.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewsViewModel extends ViewModel {

    private MutableLiveData<String> textDay;
    private MutableLiveData<String> textDate;

    public NewsViewModel() {
        String currentDay = new SimpleDateFormat("EEE", Locale.getDefault()).format(new Date());
        String currentDate = new SimpleDateFormat("MMM. d, yyyy", Locale.getDefault()).format(new Date());
        textDate = new MutableLiveData<>();
        textDate.setValue(currentDate);
        switch (currentDay) {
            case "Sun":
                currentDay = "Sunday";
                break;
            case "Mon":
                currentDay = "Monday";
                break;
            case "Tue":
                currentDay = "Tuesday";
                break;
            case "Wed":
                currentDay = "Wednesday";
                break;
            case "Thu":
                currentDay = "Thursday";
                break;
            case "Fri":
                currentDay = "Friday";
                break;
            case "Sat":
                currentDay = "Saturday";
                break;
                default:
                    currentDay = "NULL";
        }
        textDay = new MutableLiveData<>();
        textDay.setValue(currentDay);
    }

    LiveData<String> getDate() {
        return textDate;
    }
    LiveData<String> getDay() {
        return textDay;
    }
}