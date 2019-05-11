package com.example.shopee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopee.Adapter.adapter_giohang;
import com.example.shopee.dbsqlite.Database;
import com.example.shopee.model.ChiTietHoaDonBan;
import com.example.shopee.model.HoaDonBan;
import com.example.shopee.model.products;
import com.example.shopee.model.accounts;
import com.example.shopee.retrofit2.APIUtils;
import com.example.shopee.retrofit2.DataClient;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.shopee.Fragment.fragment_dangnhap.sharedPreferences;

public class GioHangActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    NavigationView navigationView;
    View headerview; // khai báo headerview vì nó cx là 1 view

    static ArrayList<products> listsanpham;
    ArrayList<accounts> listacc;
    adapter_giohang adapterGiohang;
    ListView lvgiohang;
    SearchView searchView;
    static TextView txtthanhtien;
    public static int tongtien=0;

    Button btnthanhtoan;
    SQLiteDatabase database;

    int idln=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        database= Database.initDatabase(this,"shopeeanthi.sqlite");
        getSupportActionBar().setTitle("giỏ hàng ");
        anhxa();
        navigationviewclick();
        loadsanpham();
        loadtaikhoan();
        tinhtong();
        searchviewsp();
        thanhtoan();
        xoamathang();


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
//            listacc.add(new accounts(id,taikhoan,matkhau,sdt,diachi,hoten,hinhanh));
//        }
        listacc.add(new accounts(sharedPreferences.getInt("id",0),
                sharedPreferences.getString("taikhoan",""),
                sharedPreferences.getString("matkhau",""),
                sharedPreferences.getString("sdt",""),
                sharedPreferences.getString("diachi",""),
                sharedPreferences.getString("hoten",""),
                sharedPreferences.getString("hinhanh","")));
    }

    private void loadsanpham() {
        listsanpham.clear();
        Cursor cursor=database.rawQuery("select * from sanpham",null);
        while (cursor.moveToNext()){
            int id=cursor.getInt(0);
            String tensp=cursor.getString(1);
            int giasp=cursor.getInt(2);
            int soluong=cursor.getInt(3);
            String size=cursor.getString(4);
            String mota=cursor.getString(5);
            String hinhanh=cursor.getString(6);
            String loai=cursor.getString(7);
            listsanpham.add(new products(id,tensp,giasp,soluong,size,mota,hinhanh,loai));
            adapterGiohang.notifyDataSetChanged();

        }

    }

    private void xoamathang() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            database.delete("sanpham","id=?",new String[]{""+listsanpham.get(position).getId()});
                listsanpham.remove(position);
                adapterGiohang.notifyDataSetChanged();
                tinhtong();
                return false;
            }
        });
    }

    private void thanhtoan() {
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //HoaDonBan(Integer id, Integer idTaiKhoan, Integer tongTien, String hoTen, String ngayLapHd, Integer daGiaoHang)
                // kiểm tra xem có mặt hàng nào ko
                // lấy dữ liệu từ list account + thiết lập 1 hóa đơn bán ghi lại vào csdl --> ghi các mătj hàng vào chi tiết hóa đơn bán
                if(listsanpham.size()>0){
                  // b1 gửi thông tin để lập hóa đơn bán hàng
                    int tongtien=Integer.parseInt(txtthanhtien.getText().toString());
                    final int idtk=listacc.get(0).getId();
                    String hoten=listacc.get(0).getName();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    Date date = new Date();
                    String ngaylaphd=dateFormat.format(date);
                    final int dagiaohang=0;

                    DataClient dataClient= APIUtils.getData();

                    Call<List<HoaDonBan>> callbaclhdb=dataClient.laphoadon(idtk,tongtien,hoten,ngaylaphd,dagiaohang);

                    callbaclhdb.enqueue(new Callback<List<HoaDonBan>>() {
                        @Override
                        public void onResponse(Call<List<HoaDonBan>> call, Response<List<HoaDonBan>> response) {
                           ArrayList<HoaDonBan> list= (ArrayList<HoaDonBan>) response.body();
                           for(int i=0;i<list.size();i++){
                               // tìm ra id lớn nhất (bản ghi mới nhất )
                             if(idln<=list.get(i).getId()){
                                 idln=list.get(i).getId();
                             }
                           }
                           // ghi vào bảng chi tiết hóa đơn bán hàng từng sản phẩm

                            for(int i=0;i<listsanpham.size();i++){
                                String tensp=listsanpham.get(i).getName();
                                String hinhsp=listsanpham.get(i).getImage();
                                int idsp=listsanpham.get(i).getId();
                                 int soluonng=listsanpham.get(i).getQuantity();
                                 int dongia=listsanpham.get(i).getPrice();
                                 int thanhtien=soluonng*dongia;
                             DataClient dataClient1=APIUtils.getData();
                             Call<List<ChiTietHoaDonBan>> call1=dataClient1.ghichitiethoadon(idsp,idln,soluonng,dongia,thanhtien,tensp,hinhsp);
                             call1.enqueue(new Callback<List<ChiTietHoaDonBan>>() {
                                 @Override
                                 public void onResponse(Call<List<ChiTietHoaDonBan>> call, Response<List<ChiTietHoaDonBan>> response) {
                                     Toast.makeText(GioHangActivity.this, "đã ghi vào chi tiết hóa đơn ", Toast.LENGTH_SHORT).show();

                                 }

                                 @Override
                                 public void onFailure(Call<List<ChiTietHoaDonBan>> call, Throwable t) {

                                 }
                             });
                            }
                            for(int i=0;i<listsanpham.size();i++){
                                database.delete("sanpham","id=?",new String[]{""+listsanpham.get(i).getId()});

                            }
                            // update lại số lượng trong bảng sản phẩm
                            for(int i=0;i<listsanpham.size();i++){
                                Log.d("aaa", "idsp "+listsanpham.get(i).getId());
                                Log.d("aaa", "só lượng sp "+listsanpham.get(i).getQuantity());
                                DataClient dataClient1=APIUtils.getData();
                                Call<List<products>> callbacksanpham=dataClient1.updatesoluongsanpham(listsanpham.get(i).getId(),listsanpham.get(i).getQuantity());
                                callbacksanpham.enqueue(new Callback<List<products>>() {
                                    @Override
                                    public void onResponse(Call<List<products>> call, Response<List<products>> response) {
                                        Toast.makeText(GioHangActivity.this, "đã đặt hàng thành công ", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<List<products>> call, Throwable t) {

                                    }
                                });
                            }


                            listsanpham.clear();
                            tinhtong();
                            adapterGiohang.notifyDataSetChanged();


                        }

                        @Override
                        public void onFailure(Call<List<HoaDonBan>> call, Throwable t) {

                        }
                    });

                }else{
                    Toast.makeText(GioHangActivity.this, "giỏ hàng trống không thể thanh toán", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void searchviewsp() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                if (TextUtils.isEmpty(newText)) {
//                    lvgiohang.clearTextFilter();
//                } else {
//                    lvgiohang.setFilterText(newText);
//                }
                adapterGiohang.getFilter().filter(newText);
                return false;

                //return true;

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
                        startActivity(new Intent(GioHangActivity.this,TrangChuActivity.class));
                        Toast.makeText(GioHangActivity.this, "trang chủ", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.thucphamnu:
                        startActivity(new Intent(GioHangActivity.this,QuanAoNuActivity.class));
                        Toast.makeText(GioHangActivity.this, "thực phẩm  nữ", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.thucphamnam:
                        startActivity(new Intent(GioHangActivity.this,QuanAoNamActivity.class));
                        Toast.makeText(GioHangActivity.this, "thực phẩm  nam", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.giohang:
                        Toast.makeText(GioHangActivity.this, "trang giỏ hàng", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.lichsumuahang:
                        startActivity(new Intent(GioHangActivity.this,LichSuMuaHangActivity.class));
                        Toast.makeText(GioHangActivity.this, "lịch sử mua hàng ", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nhantin:
                        startActivity(new Intent(GioHangActivity.this,NhanTinActivity.class));

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

                startActivity(new Intent(GioHangActivity.this,TrangcanhanActivity.class));
            }
        });

    }


    private void anhxa() {

        getSupportActionBar().hide();
        btnthanhtoan=findViewById(R.id.btnthanhtoan);

        navigationView = findViewById(R.id.navigation);
        lvgiohang=findViewById(R.id.lvgiohang);
        searchView=findViewById(R.id.searchview);
        txtthanhtien=findViewById(R.id.txtthanhtien);

        listsanpham=new ArrayList<>();
        listacc=new ArrayList<>();


        adapterGiohang=new adapter_giohang(listsanpham,this);
        lvgiohang.setAdapter(adapterGiohang);


    }

    public static void tinhtong(){
        txtthanhtien.setText("");
        tongtien=0;

        for (int i=0;i<listsanpham.size();i++){
            tongtien=tongtien+listsanpham.get(i).getQuantity()*listsanpham.get(i).getPrice();
        }
        txtthanhtien.setText(""+tongtien);

    }

    private class ghichitiethoadon extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            for(int i=0;i<listsanpham.size();i++){
                try {
                    Thread.sleep(500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "ok";
        }
    }
}
