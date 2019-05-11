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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopee.Adapter.adapter_sanpham1;
import com.example.shopee.dbsqlite.Database;
import com.example.shopee.model.accounts;
import com.example.shopee.model.products;
import com.example.shopee.retrofit2.APIUtils;
import com.example.shopee.retrofit2.DataClient;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.shopee.Fragment.fragment_dangnhap.sharedPreferences;

public class TrangChuActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    NavigationView navigationView;
    View headerview; // khai báo headerview vì nó cx là 1 view

    ArrayList<products> listsanphamtt;
    ArrayList<accounts> listacc;
    adapter_sanpham1 adapter_sanphamtt;
    ListView lv;
    SearchView searchView;
    SQLiteDatabase database;
    SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        database= Database.initDatabase(this,"shopeeanthi.sqlite");


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
        loadsanpham();
        lv.invalidateViews();
        swipeRefreshLayout.setRefreshing(false);
    }


    private void loadsanpham() {
        listsanphamtt.clear();
        DataClient dataClient= APIUtils.getData();
        Call<List<products>> callback=dataClient.loadsptt1();
        callback.enqueue(new Callback<List<products>>() {
            @Override
            public void onResponse(Call<List<products>> call, Response<List<products>> response) {
                ArrayList<products> listsp = (ArrayList<products>) response.body();
                listsanphamtt.addAll(listsp);
                adapter_sanphamtt.notifyDataSetChanged();
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
                adapter_sanphamtt.getFilter().filter(newText);
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
                        Toast.makeText(TrangChuActivity.this, "trang chủ", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.thucphamnam:
                        Intent intent=new Intent(TrangChuActivity.this,QuanAoNamActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.thucphamnu:
                        startActivity(new Intent(TrangChuActivity.this,QuanAoNuActivity.class));
                        break;

                    case R.id.giohang:
                        startActivity(new Intent(TrangChuActivity.this,GioHangActivity.class));
                        break;

                    case R.id.lichsumuahang:
                        startActivity(new Intent(TrangChuActivity.this,LichSuMuaHangActivity.class));
                        Toast.makeText(TrangChuActivity.this, "lịch sử mua hàng ", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nhantin:
                        startActivity(new Intent(TrangChuActivity.this,NhanTinActivity.class));

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

                startActivity(new Intent(TrangChuActivity.this,TrangcanhanActivity.class));
            }
        });

    }


    private void anhxa() {
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swiperefreshlayout);
        lv=findViewById(R.id.lvtrangchu);
        searchView=findViewById(R.id.searchview);
        listsanphamtt=new ArrayList<>();
        listacc=new ArrayList<>();

      adapter_sanphamtt=new adapter_sanpham1(listsanphamtt,this);
      lv.setAdapter(adapter_sanphamtt);
        getSupportActionBar().hide();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.navigation);
          loadtaikhoan();

    }

    private void loadtaikhoan() {
        Cursor cursor=database.rawQuery("select * from tk",null);
        while (cursor.moveToNext()){
            int id=cursor.getInt(0);
            String taikhoan=cursor.getString(1);
            String matkhau=cursor.getString(2);
            String sdt=cursor.getString(3);
            String diachi=cursor.getString(4);
            String hoten=cursor.getString(5);
            String hinhanh=cursor.getString(6);

            listacc.add(new accounts(id,taikhoan,matkhau,sdt,diachi,hoten,hinhanh));


        }
    }

}
