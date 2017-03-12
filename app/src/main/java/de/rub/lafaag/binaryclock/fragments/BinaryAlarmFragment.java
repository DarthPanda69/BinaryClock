package de.rub.lafaag.binaryclock.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.rub.lafaag.binaryclock.R;

public class BinaryAlarmFragment extends Fragment {

    public static BinaryAlarmFragment newInstance() {
        return new BinaryAlarmFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_binary_alarm, container, false);
    }
}
