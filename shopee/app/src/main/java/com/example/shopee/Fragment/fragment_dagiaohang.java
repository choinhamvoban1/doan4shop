package com.example.shopee.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.shopee.Adapter.adapter_dagiaohang;
import com.example.shopee.ChiTietHoaDonActivity;
import com.example.shopee.R;
import com.example.shopee.dbsqlite.Database;
import com.example.shopee.model.HoaDonBan;
import com.example.shopee.model.accounts;
import com.example.shopee.retrofit2.APIUtils;
import com.example.shopee.retrofit2.DataClient;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shopee.Fragment.fragment_dangnhap.sharedPreferences;

public class fragment_dagiaohang extends Fragment {
    ListView lv;
    ArrayList<accounts> listacc;
    ArrayList<HoaDonBan> listdagiaohang;
    adapter_dagiaohang adapterdagiaohang;
    SQLiteDatabase database;
    int idtk;

    public fragment_dagiaohang() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dagiaohang,container,false);
        database= Database.initDatabase((Activity) getContext(),"shopeeanthi.sqlite");
      lv=view.findViewById(R.id.lvdagiaohang);
       listdagiaohang=new ArrayList<>();
        listacc=new ArrayList<>();
       adapterdagiaohang=new adapter_dagiaohang(listdagiaohang,getContext());
       lv.setAdapter(adapterdagiaohang);
        loadsanphamdagiao();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ChiTietHoaDonActivity.class);
                intent.putExtra("id", listdagiaohang.get(position).getId());
                startActivity(intent);
            }
        });
        return view;
    }

    private void loadsanphamdagiao(){
        loadtaikhoan();
        DataClient dataClient= APIUtils.getData();
        Call<List<HoaDonBan>> callback=dataClient.loadhoadondagiaohang(idtk);
        callback.enqueue(new Callback<List<HoaDonBan>>() {
            @Override
            public void onResponse(Call<List<HoaDonBan>> call, Response<List<HoaDonBan>> response) {
                ArrayList<HoaDonBan>  list= (ArrayList<HoaDonBan>) response.body();
                listdagiaohang.addAll(list);
                adapterdagiaohang.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<HoaDonBan>> call, Throwable t) {

            }
        });

    }

    private void loadtaikhoan() {
//        Cursor cursor=database.rawQuery("select * from tk",null);
//        while (cursor.moveToNext()){
//            int id=cursor.getInt(0);
//            String taikhoan=cursor.getString(1);
//            String matkhau=cursor.getString(2);
//            String sdt=cursor.getString(3);
//            String diachi=cursor.getString(4);
//            String hoten=cursor.getString(5);
//            String hinhanh=cursor.getString(6);
//
//            listacc.add(new accounts(id,taikhoan,matkhau,sdt,diachi,hoten,hinhanh));
            idtk = sharedPreferences.getInt("id", 0);

        }
    }

