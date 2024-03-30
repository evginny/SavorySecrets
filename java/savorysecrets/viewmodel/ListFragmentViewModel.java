package com.zybooks.savorysecrets.viewmodel;

import androidx.lifecycle.ViewModel;

public class ListFragmentViewModel extends ViewModel {
    private String mCategory;

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }
}
