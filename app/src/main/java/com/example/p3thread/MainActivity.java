package com.example.p3thread;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Air> daftar;
    private RecyclerView rvDaftar;
    private Button btRiwayat;
    private AirAdapter adapterAir;
    private RequestQueue antrian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btRiwayat = findViewById(R.id.btRiwayat);
        this.rvDaftar = findViewById(R.id.rvDaftar);

        this.btRiwayat.setOnClickListener(this);

        this.daftar = new ArrayList<>();
        this.adapterAir = new AirAdapter(this, this.daftar);

        this.rvDaftar.setLayoutManager(new LinearLayoutManager(this));
        this.rvDaftar.setAdapter(this.adapterAir);

        this.antrian = Volley.newRequestQueue(this);
    }

    @Override
    public void onClick(View view) {
        String url = "http://172.16.153.202/thread/library.php";

        StringRequest req = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    Gson gson = new Gson();
                    Air[] data = gson.fromJson(response, Air[].class);

                    this.daftar.clear();
                    for (Air air : data) {
                        this.daftar.add(air);
                    }
                    this.adapterAir.notifyDataSetChanged();
                },
                error -> {
                    Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );

        this.antrian.add(req);
    }
}
