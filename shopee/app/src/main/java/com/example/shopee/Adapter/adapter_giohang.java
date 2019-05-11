package com.example.shopee.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.shopee.ChiTietSanPhamActivity;
import com.example.shopee.GioHangActivity;
import com.example.shopee.R;
import com.example.shopee.model.products;
import com.example.shopee.retrofit2.APIUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter_giohang extends BaseAdapter implements Filterable {
    ArrayList<products> listsanpham;
    ArrayList<products> listsanphamorigin;
    Context context;

    products sp;

    public adapter_giohang(ArrayList<products> listsanpham, Context context) {
        this.listsanpham = listsanpham;
        this.context = context;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<products> results = new ArrayList<products>();
                if (listsanphamorigin == null)
                    listsanphamorigin = listsanpham;
                if (constraint != null) {
                    if (listsanphamorigin != null && listsanphamorigin.size() > 0) {
                        for (final products g : listsanphamorigin) {
                            if (g.getName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listsanpham = (ArrayList<products>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getCount() {
        return listsanpham.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    private class viewholder{  // tạo 1 class viewholder đầy đủ các thuộc tính của file dongkhach.xml
        TextView txttensp,txtgiasp,txtsize;
        ImageView imghinhsanpham;
        ImageView btncong,btntru;
        TextView txtsl;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final viewholder holder;
        if(convertView == null){ // kiểm tra nếu convertView == null
            holder=new viewholder(); // tạo 1 biến holder mới
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // cái này ko biết bắt buộc phải có
            convertView=inflater.inflate(R.layout.donggiohang,null); // nạp tài nguyên xml
            // ánh xạ view với cái tài nguyên xml
            holder.txttensp= convertView.findViewById(R.id.txttensp);
            holder.txtgiasp= convertView.findViewById(R.id.txtgiasp);
            holder.txtsize=convertView.findViewById(R.id.txtsize);
            holder.imghinhsanpham= convertView.findViewById(R.id.imageViewsanpham);
            holder.btncong=convertView.findViewById(R.id.btncong);
            holder.btntru=convertView.findViewById(R.id.btntru);
            holder.txtsl=convertView.findViewById(R.id.edtsoluong);

            convertView.setTag(holder);
        }else{
            holder = (viewholder) convertView.getTag();
        }

         sp=listsanpham.get(position);

        holder.txttensp.setText(""+sp.getName());
        holder.txtgiasp.setText("giá: "+sp.getPrice());
        holder.txtsize.setText("size: "+sp.getSize());
        Picasso.with(context).load(""+  APIUtils.Base_Url+"storage/app/public/"+sp.getImage()).into(holder.imghinhsanpham);

        holder.btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               int sl= Integer.parseInt(holder.txtsl.getText().toString());
               sl=sl+1;
               holder.txtsl.setText(""+sl);
                GioHangActivity.tongtien=0;
                listsanpham.get(position).setQuantity(sl);

                GioHangActivity.tinhtong();
            }
        });

        holder.btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int sl= Integer.parseInt(holder.txtsl.getText().toString());
                sl=sl-1;
                holder.txtsl.setText(""+sl);
                GioHangActivity.tongtien=0;
                listsanpham.get(position).setQuantity(sl);

                GioHangActivity.tinhtong();

            }
        });
        holder.imghinhsanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ChiTietSanPhamActivity.class);
                intent.putExtra("hinhanh",listsanpham.get(position).getImage());
                intent.putExtra("tensp",listsanpham.get(position).getName());
                intent.putExtra("giasp",listsanpham.get(position).getPrice());
                intent.putExtra("size",listsanpham.get(position).getSize());
                intent.putExtra("soluong",listsanpham.get(position).getQuantity());
                intent.putExtra("mota",listsanpham.get(position).getDescription());
                context.startActivity(new Intent(intent));
            }
        });

        return convertView;
    }
}
