package com.na.psdk.alipay;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.na.psdk.core.IPayCallbackListener;
import com.na.psdk.core.IPayContext;
import com.na.psdk.core.IPayHandler;

public class AlipayHandler implements IPayHandler<AlipayConfig, AlipayContext> {
    @Override
    public void onInit(AlipayConfig channelConfig) {

    }

    @Override
    public void pay(Context context, AlipayContext payContext, IPayCallbackListener payCallbackListener) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

    }
}
