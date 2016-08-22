package com.example.mrh.myqrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_check;
    private TextView tv_result;
    private EditText et_creat;
    private Button btn_creat;
    private ImageView iv_creat;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView () {
        btn_check = (Button) findViewById(R.id.btn_check);
        tv_result = (TextView) findViewById(R.id.tv_result);
        et_creat = (EditText) findViewById(R.id.et_creat);
        btn_creat = (Button) findViewById(R.id.btn_creat);
        iv_creat = (ImageView) findViewById(R.id.iv_creat);

        btn_check.setOnClickListener(this);
        btn_creat.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        switch (v.getId()){
        case R.id.btn_check:
            startActivityForResult(new Intent(this, CaptureActivity.class), 0);
            break;
        case R.id.btn_creat:
            String submit = submit();
            if (submit != null){
//                Log.d(TAG, "onClick: ");
                Bitmap bitmap = EncodingUtils.createQRCode(submit, 600, 600, BitmapFactory
                        .decodeResource
                        (getResources(), R.drawable.haha));
                iv_creat.setImageBitmap(bitmap);
            }
            break;
        }
    }

    private String submit () {
        // validate
        String creat = et_creat.getText().toString().trim();
        if (TextUtils.isEmpty(creat)){
            Toast.makeText(this, "creat不能为空", Toast.LENGTH_SHORT).show();
            return null;
        }
        return creat;
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        String string = null;
        if (resultCode == RESULT_OK && data != null){
            Bundle extras = data.getExtras();
            string = extras.getString("result");
            tv_result.setText(string);
        }
    }
}
