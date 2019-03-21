package com.na.psdk.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.na.psdk.PayChannelConfigManager;
import com.na.psdk.PaymentSdk;
import com.na.psdk.core.IPayCallbackListener;
import com.na.psdk.core.IPayContext;
import com.na.psdk.core.PayChannelType;
import com.na.psdk.google.GooglePayConfig;
import com.na.psdk.google.GooglePayContext;
import com.na.psdk.google.ProductType;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IPayCallbackListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tvStartPay).setOnClickListener(this);
        GooglePayConfig googlePayConfig = new GooglePayConfig();
        googlePayConfig.setBase64PublicKey("YOUR_Base64PublicKey");
        PayChannelConfigManager.addPlatformConfig(googlePayConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PaymentSdk.getInstance().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvStartPay: {
                GooglePayContext payContext = new GooglePayContext();
                payContext.setProductType(ProductType.Subs);
                payContext.setProductId("YOUR_PRODUCTID");
                payContext.setOrderNo("201903181634");
                PaymentSdk.getInstance().pay(this, payContext, this, PayChannelType.GooglePay);
                break;
            }
            default: {
                break;
            }
        }
    }

    @Override
    public void onComplete(int channelType, IPayContext payContext) {
        Log.e(TAG, "onComplete ");
    }

    @Override
    public void onError(int channelType, IPayContext payContext, String errMsg, int errcode) {
        Log.e(TAG, "onError errmsg=" + errMsg + ",errcode=" + errcode);
    }

    @Override
    public void onCancel(int channelType, IPayContext payContext) {
        Log.e(TAG, "onCancel ");
    }
}
