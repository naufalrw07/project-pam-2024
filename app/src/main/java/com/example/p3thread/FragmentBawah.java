package com.example.p3thread;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FragmentBawah extends Fragment {
    private RecyclerView rvRiwayat;
    private AirAdapter adapterAir;
    private List<Air> daftar;
    private RequestQueue antrian;

    public FragmentBawah() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        daftar = new ArrayList<>();
        adapterAir = new AirAdapter(requireContext(), daftar);

        antrian = Volley.newRequestQueue(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bawah, container, false);
        rvRiwayat = v.findViewById(R.id.rvRiwayat);
        rvRiwayat.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRiwayat.setAdapter(adapterAir);

        Button btRiwayat = v.findViewById(R.id.btRiwayat);
        btRiwayat.setOnClickListener(this::tampilkanRiwayat);
        return v;
    }

    public void tampilkanRiwayat(View view) {
        String url = "http://172.16.153.202/thread/library.php";

        StringRequest req = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    Gson gson = new Gson();
                    Air[] data = gson.fromJson(response, Air[].class);

                    daftar.clear();
                    for (Air air : data) {
                        daftar.add(air);
                    }
                    adapterAir.notifyDataSetChanged();
                },
                error -> {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );

        antrian.add(req);
    }
}
