package br.com.liveo.parsepush.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SendCallback;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.liveo.parsepush.R;
import br.com.liveo.parsepush.ui.base.BaseActivity;
import br.com.liveo.parsepush.util.Constants;

/**
 * Created by Rudsonlive on 13/01/16.
 */
public class NewUserActivity extends BaseActivity {

    private EditText mEdtName;
    private EditText mEdtMessage;
    private SwitchCompat swShowNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_push);

        mEdtName = (EditText) findViewById(R.id.edtName);
        mEdtMessage = (EditText) findViewById(R.id.edtMessage);
        swShowNotification = (SwitchCompat) findViewById(R.id.swShowNotification);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);

        toolbar.setTitle(getString(R.string.send));
        toolbar.setNavigationIcon(R.drawable.ic_done_white_24dp);
        this.setUpAppToolbar(toolbar, appBarLayout);
        this.showDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            if (validation()){
                sendPush(mEdtMessage.getText().toString(), swShowNotification.isChecked());
            }
        }
        return true;
    }

    private boolean validation(){
        if (mEdtName.getText().toString().isEmpty()) {
            mEdtName.requestFocus();
            Toast.makeText(getApplicationContext(), R.string.enter_a_message, Toast.LENGTH_SHORT).show();
            return false;
        }else if (mEdtMessage.getText().toString().isEmpty()) {
            mEdtMessage.requestFocus();
            Toast.makeText(getApplicationContext(), R.string.enter_a_message, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void sendPush(String message, boolean isShow){

        try {
            JSONObject jsonPush = new JSONObject();
            jsonPush.put(Constants.ALERT, message);
            jsonPush.put(Constants.SHOW_NOTIFICATION, isShow);
            jsonPush.put(Constants.NAME, mEdtName.getText().toString());
            jsonPush.put(Constants.OBJECT_ID, ParseUser.getCurrentUser().getObjectId());

            ParsePush parsePush = new ParsePush();
            parsePush.setChannel(Constants.CHANNEL);
            parsePush.setData(jsonPush);
            parsePush.sendInBackground(new SendCallback() {
                @Override
                public void done(ParseException e) {
                    finish();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
