package com.example.shopee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopee.retrofit2.APIUtils;
import com.squareup.picasso.Picasso;

public class ChiTietSanPhamActivity extends AppCompatActivity {
     TextView txttensp,txtgiasp,txtsize,txtsoluong,txtmota;
     ImageView imghinhsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("chi tiết sản phẩm ");
//        intent.putExtra("hinhanh",listsanphamtt.get(position).getImage());
//        intent.putExtra("tensp",listsanphamtt.get(position).getName());
//        intent.putExtra("giasp",listsanphamtt.get(position).getPrice());
//        intent.putExtra("size",listsanphamtt.get(position).getSize());
//        intent.putExtra("soluong",listsanphamtt.get(position).getQuantity());
//        intent.putExtra("mota",listsanphamtt.get(position).getDescription());
        anhxa();
        Intent intent=getIntent();
        String hinhanh=intent.getStringExtra("hinhanh");
        String tensp=intent.getStringExtra("tensp");
        int giasp=intent.getIntExtra("giasp",0);
        String size=intent.getStringExtra("size");
        int soluong=intent.getIntExtra("soluong",0);
        String mota=intent.getStringExtra("mota");
        Picasso.with(this).load(""+ APIUtils.Base_Url+"storage/app/public/"+hinhanh).into(imghinhsp);
        txttensp.setText("tên: "+tensp);
        txtgiasp.setText("giá: "+giasp+" VND");
        txtsoluong.setText("số lượng: "+soluong);
        txtsize.setText("size: "+size);
        txtmota.setText("mô tả: "+mota);
    }

    private void anhxa() {
        txttensp=findViewById(R.id.txttenspCT);
        txtgiasp=findViewById(R.id.giaspCT);
        txtsize=findViewById(R.id.txtsizeCT);
        txtsoluong=findViewById(R.id.txtsoluongCT);
        txtmota=findViewById(R.id.txtmotaCT);
        imghinhsp=findViewById(R.id.imghinhspCT);
    }
}
