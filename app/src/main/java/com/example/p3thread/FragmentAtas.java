package com.example.p3thread;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentAtas extends Fragment {

    private TextView tvJumlah;
    private TextView tvTarget;
    private ProgressBar progressBar;
    private int currentIntake = 0;
    private int targetIntake = 1500;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_atas, container, false);

        tvJumlah = v.findViewById(R.id.tvJumlah);
        tvTarget = v.findViewById(R.id.tvTarget);
        progressBar = v.findViewById(R.id.progressBar);

        tvTarget.setText(targetIntake + "ML");
        progressBar.setMax(targetIntake);

        ImageButton btTambah = v.findViewById(R.id.btTambah);
        btTambah.setOnClickListener(view -> addWater(150));

        return v;
    }

    private void addWater(int amount) {
        currentIntake += amount;
        if (currentIntake > targetIntake) {
            currentIntake = targetIntake;
        }
        tvJumlah.setText(currentIntake + "ML");
        progressBar.setProgress(currentIntake);
    }
}
