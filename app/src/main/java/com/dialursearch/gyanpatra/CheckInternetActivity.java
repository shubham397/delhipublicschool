package com.dialursearch.gyanpatra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CheckInternetActivity extends Activity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_internet);

        btn=(Button)findViewById(R.id.btn_check_internet);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CheckInternetActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
