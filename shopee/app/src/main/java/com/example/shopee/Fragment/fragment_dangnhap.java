package com.example.shopee.Fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopee.R;
import com.example.shopee.TrangChuActivity;
import com.example.shopee.dbsqlite.Database;
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

import static android.content.Context.MODE_PRIVATE;

public class fragment_dangnhap extends Fragment {
    EditText edttkdn,edtmkdn;
    SQLiteDatabase database;
    CheckBox cb;
    public static SharedPreferences sharedPreferences;


    public fragment_dangnhap() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_dangnhap,container,false);

        Button btndangnhap=view.findViewById(R.id.btndangnhap);
        edttkdn=view.findViewById(R.id.edttk);
        edtmkdn=view.findViewById(R.id.edtmk);
        cb=view.findViewById(R.id.checkBox);
        database= Database.initDatabase(getActivity(),"shopeeanthi.sqlite");
         sharedPreferences=getContext().getSharedPreferences("luutk",MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        if(!cb.isChecked()){
            edttkdn.setText(sharedPreferences.getString("taikhoan",""));
            edtmkdn.setText(sharedPreferences.getString("matkhau",""));
            cb.setChecked(sharedPreferences.getBoolean("cbluumk",false));

            Toast.makeText(getContext(), "ok ok ", Toast.LENGTH_SHORT).show();
        }else{
            edttkdn.setText("");
            edtmkdn.setText("");
        }



        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk=edttkdn.getText().toString();
                 String mk=edtmkdn.getText().toString();
                DataClient dataClient= APIUtils.getData();
               Call<List<accounts>> callback=dataClient.login(tk,mk);
               callback.enqueue(new Callback<List<accounts>>() {
                   @Override
                   public void onResponse(Call<List<accounts>> call, Response<List<accounts>> response) {
                       ArrayList<accounts> listtk= (ArrayList<accounts>) response.body();

                       if(listtk.size()<=0){
                           Toast.makeText(getContext(), "mk ko đúng   ", Toast.LENGTH_SHORT).show();
                           editor.remove("taikhoan");
                           editor.remove("matkhau");
                           editor.remove("cbluumk");
                       }else{

//                           ContentValues contentValues=new ContentValues();
//                           contentValues.put("id",listtk.get(0).getId());
//                           contentValues.put("taikhoan", listtk.get(0).getUsername());
//                           contentValues.put("matkhau", listtk.get(0).getPassword());
//                           contentValues.put("sdt", listtk.get(0).getPhone());
//                           contentValues.put("diachi", listtk.get(0).getAddress());
//                           contentValues.put("hoten", listtk.get(0).getName());
//                           contentValues.put("hinhanh", listtk.get(0).getImage());
//
//                           database.insert("tk",null,contentValues);
                             if(cb.isChecked()){
                                 editor.putInt("id",listtk.get(0).getId());
                                 editor.putString("taikhoan",edttkdn.getText().toString());
                                 editor.putString("matkhau",edtmkdn.getText().toString());
                                 editor.putString("hoten",listtk.get(0).getName());
                                 editor.putString("sdt",listtk.get(0).getPhone());
                                 editor.putString("diachi",listtk.get(0).getAddress());
                                 editor.putString("hinhanh",listtk.get(0).getImage());
                                 editor.putBoolean("cbluumk",true);

                                 editor.commit(); // phải có xác nhận thì nó ms áp dụng được
                                 Intent intent=new Intent(getContext(),TrangChuActivity.class);
                                 startActivity(intent);

                             }else{
                                 editor.putString("taikhoan",edttkdn.getText().toString());
                                 editor.putString("matkhau",edtmkdn.getText().toString());
                                 editor.putBoolean("cbluumk",false);
                                 editor.apply();
                                 Intent intent=new Intent(getContext(),TrangChuActivity.class);
                                 startActivity(intent);

                             }



                       }


                   }

                   @Override
                   public void onFailure(Call<List<accounts>> call, Throwable t) {

                   }
               });
            }
        });

       return view;
    }
}
