package com.grability.cloudstore.ui.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.grability.cloudstore.R;
import com.grability.cloudstore.utils.AnimateUtil;
import com.grability.cloudstore.utils.OrientationUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.ivCloud) ImageView ivCloud;
    @Bind(R.id.ivLetter) ImageView ivLetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        OrientationUtil.adjustScreenOrientation(this);
        loadAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 3000);
    }

    private void loadAnimation() {
        new AnimateUtil().setAnimation(ivCloud, 1, android.R.anim.slide_out_right, 4000);
        new AnimateUtil().setAnimation(ivLetter, 1, android.R.anim.fade_in, 4000);
    }

}
