package com.na.psdk;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.na.psdk.core.IPayCallbackListener;
import com.na.psdk.core.IPayContext;
import com.na.psdk.core.IPayHandler;

public final class PaymentSdk{

    private static volatile PaymentSdk singleton;
    private IPaymentSdk paymentSdk;

    private PaymentSdk() {
        paymentSdk = new PaymentSdkImpl();
    }

    public static PaymentSdk getInstance() {
        if (singleton == null) {
            synchronized (PaymentSdk.class) {
                if (singleton == null) {
                    singleton = new PaymentSdk();
                }
            }
        }
        return singleton;
    }



    public void pay(Context context, IPayContext payContext, IPayCallbackListener payCallbackListener, int channelType) {
        if (paymentSdk != null) {
            paymentSdk.pay(context, payContext, payCallbackListener, channelType);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (paymentSdk != null) {
            paymentSdk.onActivityResult(requestCode, resultCode, data);
        }
    }
}