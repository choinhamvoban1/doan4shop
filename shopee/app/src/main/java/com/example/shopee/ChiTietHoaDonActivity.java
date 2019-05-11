package com.example.shopee;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shopee.Adapter.adapter_chitiethoadon;
import com.example.shopee.model.ChiTietHoaDonBan;
import com.example.shopee.retrofit2.APIUtils;
import com.example.shopee.retrofit2.DataClient;

import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonActivity extends AppCompatActivity {
ListView lv;
ArrayList<ChiTietHoaDonBan> listchitiet;
adapter_chitiethoadon adapterChitiethoadon;
int idhoadon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitychitiethoadon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("chi tiết hóa đơn bán hàng");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#26CE26")));
        lv=findViewById(R.id.lvchitiet);
        listchitiet=new ArrayList<>();
        adapterChitiethoadon=new adapter_chitiethoadon(listchitiet,this);
        lv.setAdapter(adapterChitiethoadon);
        Intent in=getIntent();
        idhoadon=in.getIntExtra("id",0);
           loadchitiet();

    }

    private void loadchitiet() {
        DataClient dataClient= APIUtils.getData();
        Call<List<ChiTietHoaDonBan>> callback=dataClient.loadchitiethoadon(idhoadon);
        callback.enqueue(new Callback<List<ChiTietHoaDonBan>>() {
            @Override
            public void onResponse(Call<List<ChiTietHoaDonBan>> call, Response<List<ChiTietHoaDonBan>> response) {
                ArrayList<ChiTietHoaDonBan>listchitiet1= (ArrayList<ChiTietHoaDonBan>) response.body();
                listchitiet.addAll(listchitiet1);
                adapterChitiethoadon.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<ChiTietHoaDonBan>> call, Throwable t) {
                Toast.makeText(ChiTietHoaDonActivity.this, "lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
