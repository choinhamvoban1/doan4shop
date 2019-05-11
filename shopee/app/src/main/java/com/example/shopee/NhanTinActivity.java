package com.example.shopee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NhanTinActivity extends AppCompatActivity {
String phone="+84985078116";
String mess="";
    int MY_PERMISSIONS_REQUEST_READ_CONTACTS=123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_tin);
        getSupportActionBar().setTitle("nhắn tin tới shop");
        final EditText edtmess=findViewById(R.id.tinnhan);


        Button btn=findViewById(R.id.btnsend);

        if (ContextCompat.checkSelfPermission(NhanTinActivity.this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(NhanTinActivity.this,
                    Manifest.permission.SEND_SMS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(NhanTinActivity.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mess=edtmess.getText().toString();
                send_sms(phone,mess);
                Toast.makeText(NhanTinActivity.this, "tin nhắn văn bản đã được gửi tới chủ shop", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void send_sms(String numberphone,String messagecontent) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(numberphone,null,messagecontent,null,null);
        Toast.makeText(this, "đã send message :v ", Toast.LENGTH_SHORT).show();
    }

}
