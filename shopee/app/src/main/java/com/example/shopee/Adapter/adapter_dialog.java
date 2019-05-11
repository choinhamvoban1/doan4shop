package com.example.shopee.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopee.R;
import com.example.shopee.model.ChiTietHoaDonBan;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter_dialog extends BaseAdapter {
    ArrayList<ChiTietHoaDonBan> listchitiethoadonban;
    Context context;

    public adapter_dialog(ArrayList<ChiTietHoaDonBan> listchitiethoadonban, Context context) {
        this.listchitiethoadonban = listchitiethoadonban;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listchitiethoadonban.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // cái này ko biết bắt buộc phải có
        convertView=inflater.inflate(R.layout.dialog_item,null);
        ImageView imghinhsp=convertView.findViewById(R.id.imageView);
        TextView txttensp=convertView.findViewById(R.id.txttensanpham);
        TextView txtdongia=convertView.findViewById(R.id.txtdongia);
        TextView soluong=convertView.findViewById(R.id.soluong);
        TextView txttongtien=convertView.findViewById(R.id.txttongtien);
        ChiTietHoaDonBan cthdb=listchitiethoadonban.get(position);
        Picasso.with(context).load(cthdb.getHinhAnh()).into(imghinhsp);
        txttensp.setText("tên sp: "+cthdb.getTenSp());
        txtdongia.setText("đơn giá:"+cthdb.getDonGia());
        soluong.setText("số lượng "+cthdb.getSoLuong());
        txttongtien.setText("thành tiền "+cthdb.getThanhTien());

        return convertView;

    }
}
