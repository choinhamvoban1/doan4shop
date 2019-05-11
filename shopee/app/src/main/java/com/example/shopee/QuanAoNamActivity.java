package com.example.shopee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopee.Adapter.adapter_sanpham1;
import com.example.shopee.model.products;
import com.example.shopee.retrofit2.APIUtils;
import com.example.shopee.retrofit2.DataClient;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.shopee.Fragment.fragment_dangnhap.sharedPreferences;

public class QuanAoNamActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    NavigationView navigationView;
    View headerview; // khai báo headerview vì nó cx là 1 view

    ArrayList<products> listspnam;
    adapter_sanpham1 adapter_sanphamnam;
    ListView lv;
    SearchView searchView;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ao_nam);
        Toast.makeText(QuanAoNamActivity.this, "quan ao nam hjhjhj", Toast.LENGTH_SHORT).show();
        anhxa();
        navigationviewclick();
        searchviewhihi();
     loadsanpham();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

    }

    private void refresh() {
        listspnam.clear();
        loadsanpham();
        lv.invalidateViews();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void loadsanpham() {
        DataClient dataClient= APIUtils.getData();
        Call<List<products>> callback=dataClient.loadspnam();
        callback.enqueue(new Callback<List<products>>() {
            @Override
            public void onResponse(Call<List<products>> call, Response<List<products>> response) {
                ArrayList<products> listsp = (ArrayList<products>) response.body();
                listspnam.addAll(listsp);
                adapter_sanphamnam.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<products>> call, Throwable t) {

            }
        });
    }




    private void searchviewhihi() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter_sanphamnam.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void navigationviewclick() { // sự kiện click item trong navigation view
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setCheckable(true); // khi chọn sẽ giữ lại trạng thái chọn
                switch (menuItem.getItemId()){
                    case R.id.trangchu:
                        startActivity(new Intent(QuanAoNamActivity.this,TrangChuActivity.class));
                        Toast.makeText(QuanAoNamActivity.this, "trang chủ", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.thucphamnu:
                        startActivity(new Intent(QuanAoNamActivity.this,QuanAoNuActivity.class));

                        break;
                    case R.id.thucphamnam:
                      break;

                    case R.id.giohang:
                        startActivity(new Intent(QuanAoNamActivity.this,GioHangActivity.class));
                        Toast.makeText(QuanAoNamActivity.this, "trang giỏ hàng", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.lichsumuahang:
                        startActivity(new Intent(QuanAoNamActivity.this,LichSuMuaHangActivity.class));
                        Toast.makeText(QuanAoNamActivity.this, "lịch sử mua hàng ", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nhantin:
                        startActivity(new Intent(QuanAoNamActivity.this,NhanTinActivity.class));
                        break;

                }
                return true;
            }
        });
        headerview = navigationView.getHeaderView(0); // bắt sự kiện cho header layout
        final TextView profilename = (TextView) headerview.findViewById(R.id.textView);
        final CircleImageView imghinh=(CircleImageView) headerview.findViewById(R.id.profile_image) ;
        Picasso.with(this).load(APIUtils.Base_Url+"storage/app/public/"+sharedPreferences.getString("hinhanh","")).into(imghinh);
        profilename.setText(sharedPreferences.getString("hoten",""));
        imghinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // chuyển qua màn profile

                startActivity(new Intent(QuanAoNamActivity.this,TrangcanhanActivity.class));
            }
        });

    }


    private void anhxa() {
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swiperefreshlayout);
        lv=findViewById(R.id.lvquanaonu);
        searchView=findViewById(R.id.searchview);
        listspnam=new ArrayList<>();

        adapter_sanphamnam=new adapter_sanpham1(listspnam,this);
        lv.setAdapter(adapter_sanphamnam);

        getSupportActionBar().hide();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.RED));
        navigationView = findViewById(R.id.navigation);
    }
}

