package com.bostonhacks.text2map;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class ResultsActivityFragment extends Fragment {
    View inflated;

    public ResultsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflated = inflater.inflate(R.layout.fragment_results, container, false);
        return inflated;
    }
}
