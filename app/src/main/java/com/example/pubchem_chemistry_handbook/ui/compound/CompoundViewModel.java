package com.example.pubchem_chemistry_handbook.ui.compound;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CompoundViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CompoundViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Compound fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}