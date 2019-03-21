
package com.na.psdk.wechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WechatPayActivity extends Activity implements IWXAPIEventHandler {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleApi(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleApi(intent);
    }

    private void handleApi(Intent intent) {
        try {
            WechatPayHandler.getDefault().getApi().handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
        try {
            WechatPayHandler.getDefault().onReq(baseReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

    @Override
    public void onResp(BaseResp baseResp) {
        try {
            WechatPayHandler.getDefault().onResp(baseResp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }
}
