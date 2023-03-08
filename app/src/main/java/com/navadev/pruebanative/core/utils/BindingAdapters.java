package com.navadev.pruebanative.core.utils;

import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.navadev.pruebanative.R;
import com.navadev.pruebanative.feature.add.presenter.AddViewModel;

public class BindingAdapters {


    @BindingAdapter("updateText")
    public static void updateText(AppCompatTextView textView, @NonNull LiveData<String> livedata) {
        Observer<String> observer = textView::setText;
        livedata.observeForever(observer);
    }


    @BindingAdapter("stateBackground")
    public static void setStateBackground(View view, @NonNull LiveData<Boolean> livedata) {

        Observer<Boolean> observer = value->{
            if (value) {
                view.setBackgroundColor(Color.parseColor("#00C853"));
            } else {
                view.setBackgroundColor(Color.parseColor("#FF5733"));
            }
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(Color.parseColor("#FFFFFF"));
            }
        };

        livedata.observeForever(observer);


    }

    @BindingAdapter("setCheckedBolean")
    public static void setCheckedBolean(CheckBox check, @NonNull LiveData<Boolean> livedata) {
        Observer<Boolean> observer = check::setChecked;

        livedata.observeForever(observer);
    }



}
