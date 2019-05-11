package com.example.shopee;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopee.retrofit2.APIUtils;
import com.squareup.picasso.Picasso;

import androidx.appcompat.widget.Toolbar;

import static com.example.shopee.Fragment.fragment_dangnhap.sharedPreferences;


public class TrangcanhanActivity extends Activity {
    ImageView imghinh;
    TextView txtsdt,txtdiachi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangcanhan);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
       toolbar.setTitle(""+sharedPreferences.getString("hoten",""));

       toolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),TrangChuActivity.class));
           }
       });
        imghinh=findViewById(R.id.imghinh);
        txtdiachi=findViewById(R.id.txtdiachi);
        txtsdt=findViewById(R.id.txtsdt);
        txtsdt.setText(sharedPreferences.getString("sdt",""));
        txtdiachi.setText(sharedPreferences.getString("diachi",""));
        Picasso.with(this).load(""+ APIUtils.Base_Url+"storage/app/public/"+sharedPreferences.getString("hinhanh","")).into(imghinh);



    }
}
