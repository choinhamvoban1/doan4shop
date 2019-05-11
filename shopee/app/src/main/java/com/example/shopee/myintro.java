package com.example.shopee;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class myintro extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntroFragment.newInstance("một thế giới mua sắm rộng lớn ", // dòng trên
                "đang ở trước mắt và trong tầm tay của bạn", // dòng dưới
                R.mipmap.thegioi, // hình ảnh ở giữa
                Color.parseColor("#51e2b7"))); // màu nền

        addSlide(AppIntroFragment.newInstance("chúng tôi luôn bảo vệ người tiêu dùng",
                "một cách tuyệt đối%",
                R.mipmap.baove,
                Color.parseColor("#51e2b7")));

        addSlide(AppIntroFragment.newInstance("giao hàng tận nơi tận chỗ ban đứng",
                "vậy nên hãy yên tâm",
                R.mipmap.giaohang,
                Color.parseColor("#51e2b7")));


        addSlide(AppIntroFragment.newInstance("cuối cùng hãy đến trang hoàng lại bản thân đê thoát kiếp F.A",
                "nhân tiện đây cũng là người code app hi :v",
                R.mipmap.dev,
                Color.parseColor("#51e2b7")));

        showStatusBar(false);
        setBarColor(Color.parseColor("#333639"));
        setSeparatorColor(Color.parseColor("#2196F3"));
    }

    @Override
    public void onDonePressed() { // nhấn nút done
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) { // nhấn nút skip
        startActivity(new Intent(myintro.this,MainActivity.class));
        finish();
    }

    @Override
    public void onSlideChanged() { // khi vuốt slide

    }
}
