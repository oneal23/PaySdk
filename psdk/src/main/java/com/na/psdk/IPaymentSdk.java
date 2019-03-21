package com.na.psdk;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.na.psdk.core.IPayCallbackListener;
import com.na.psdk.core.IPayContext;

public interface IPaymentSdk {
    void pay(Context context, IPayContext payContext, IPayCallbackListener payCallbackListener, int channelType);

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
}
