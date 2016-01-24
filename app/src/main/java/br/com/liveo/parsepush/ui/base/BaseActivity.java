package br.com.liveo.parsepush.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

/**
 * Created by Rudsonlive on 13/01/16.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void setUpAppToolbar(Toolbar toolbar, AppBarLayout appBarLayout){
        this.setSupportActionBar(toolbar);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBarLayout.setElevation(15);
        }
    }

    public void showDisplayHomeAsUpEnabled(boolean showHomeAsUp){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
        }
    }
}
