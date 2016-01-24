package br.com.liveo.parsepush.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.List;

import br.com.liveo.parsepush.R;
import br.com.liveo.parsepush.adapter.MainAdapter;
import br.com.liveo.parsepush.model.Push;
import br.com.liveo.parsepush.ui.base.BaseActivity;
import br.com.liveo.parsepush.util.Constants;
import br.com.liveo.parsepush.util.Utils;

public class MainActivity extends BaseActivity {

    private MainAdapter mMainAdapter;
    private ArrayList<Push> mPushList;
    private RecyclerView mRecyclerView;

    private static final String EMAIL = "naty@gmail.com";
    private static final String USER_NAME = "natylive";
    private static final String PASSWORD = "a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpFindViewById();
        setUpPushShowActivity(getIntent().getExtras());
    }

    private void setUpFindViewById(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        this.setUpAppToolbar(toolbar, appBarLayout);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fabAdd);
        floatingActionButton.setOnClickListener(onCliclNewPush);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpAdapter(String jsonPush){
        if (jsonPush != null) {
            if (mPushList == null) {
                mPushList = new ArrayList<>();

                mMainAdapter = new MainAdapter(this, mPushList);
                mRecyclerView.setAdapter(mMainAdapter);
            }

            Push push = new Gson().fromJson(jsonPush, Push.class);

            mPushList.add(push);
            mMainAdapter.notifyDataSetChanged();
        }
    }

    private View.OnClickListener onCliclNewPush = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //signUp();
            startActivity(new Intent(getApplicationContext(), NewUserActivity.class));
        }
    };

    private void signUp(){
        ParseUser user = new ParseUser();
        user.setUsername(USER_NAME);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    login();
                    Toast.makeText(getApplicationContext(), "Login cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Erro ao cadastrar login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void login(){
        ParseUser.logInInBackground(USER_NAME, PASSWORD, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Utils.subscribeWithUser(user.getObjectId());
                    Toast.makeText(getApplicationContext(), "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao fazer login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setUpPushShowActivity(intent.getExtras());
    }

    private void setUpPushShowActivity(Bundle extra){
        try{
            if (extra != null) {
                String jsonPush = extra.getString(Constants.PUSH_JSON);
                setUpAdapter(jsonPush);
            }
        }catch (Exception e){
            e.getMessage();
        }
    }
}
