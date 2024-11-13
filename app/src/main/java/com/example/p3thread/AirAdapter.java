package com.example.p3thread;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AirAdapter extends RecyclerView.Adapter<AirAdapter.VH> {

    private Context ctx;
    private List<Air> daftar;

    public AirAdapter(Context ctx, List<Air> daftar) {
        this.ctx = ctx;
        this.daftar = daftar;
    }

    public class VH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvWaktu;
        private final TextView tvJumlahAir;
        private final ImageView gambar_gelas;
        private final ImageButton menu;
        private Air airItem;

        public VH(@NonNull View itemView) {
            super(itemView);
            this.tvWaktu = itemView.findViewById(R.id.tvWaktu);
            this.tvJumlahAir = itemView.findViewById(R.id.tvJumlahAir);
            this.gambar_gelas = itemView.findViewById(R.id.ivGelas);
            this.menu = itemView.findViewById(R.id.btMenu);

            this.menu.setOnClickListener(view -> showPopUpMenu(view));
        }

        private void showPopUpMenu(View view) {
            PopupMenu popup = new PopupMenu(ctx, view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.popup_menu, popup.getMenu());
            popup.show();

            popup.setOnMenuItemClickListener(item -> {
                int position = getAdapterPosition();
                if (item.getItemId() == R.id.btUbah) {
                    showNumberInputDialog();
                    return true;
                } else if (item.getItemId() == R.id.btHapus) {
                    if (position != RecyclerView.NO_POSITION) {
                        daftar.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, daftar.size());
                        Toast.makeText(ctx, "Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            });
        }

        private void showNumberInputDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle("Edit Jumlah Air");

            final EditText input = new EditText(ctx);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, which) -> {
                String jumlahAir = input.getText().toString();
                Toast.makeText(ctx, "Jumlah air diubah menjadi: " + jumlahAir + "ML", Toast.LENGTH_SHORT).show();
            });

            builder.setNegativeButton("Batal", (dialog, which) -> dialog.cancel());

            AlertDialog dialog = builder.create();
            dialog.setOnShowListener(dialogInterface -> {
                input.requestFocus();
                input.postDelayed(() -> {
                    android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(input, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT);
                }, 100);
            });
            dialog.show();
        }

        public void setAirItem(Air airItem) {
            this.airItem = airItem;
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(ctx, airItem.waktu, Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.ctx).inflate(R.layout.modifikasi_air, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Air air = this.daftar.get(position);
        holder.setAirItem(air);
        holder.tvWaktu.setText(air.waktu);
        holder.tvJumlahAir.setText(air.jumlahAir);
        holder.gambar_gelas.setImageResource(R.drawable.gambar_gelas);
        holder.menu.setImageResource(R.drawable.logo_menu);
    }

    @Override
    public int getItemCount() {
        return this.daftar.size();
    }
}
