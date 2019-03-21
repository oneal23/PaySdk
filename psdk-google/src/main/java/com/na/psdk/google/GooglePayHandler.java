package com.na.psdk.google;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.na.psdk.core.IPayCallbackListener;
import com.na.psdk.core.IPayContext;
import com.na.psdk.core.IPayHandler;
import com.na.psdk.core.PayChannelConfig;
import com.na.psdk.core.PayChannelType;
import com.na.psdk.core.PayErrorCode;
import com.na.psdk.google.util.IabHelper;
import com.na.psdk.google.util.IabResult;
import com.na.psdk.google.util.Inventory;
import com.na.psdk.google.util.Purchase;

import java.util.List;
import java.util.Map;

public class GooglePayHandler implements IPayHandler<GooglePayConfig, GooglePayContext>
        , IabHelper.OnIabSetupFinishedListener
        , IabHelper.QueryInventoryFinishedListener
        , IabHelper.OnConsumeFinishedListener
        , IabHelper.OnIabPurchaseFinishedListener {
    private final static String TAG = "GooglePayHandler";
    private GooglePayContext payContext;
    private IPayCallbackListener payCallbackListener;
    private IabHelper iabHelper;
    private Context context;
    private GooglePayConfig channelConfig;

    @Override
    public void onInit(GooglePayConfig channelConfig) {
        this.channelConfig = channelConfig;
    }

    @Override
    public void pay(Context context, GooglePayContext payContext, final IPayCallbackListener payCallbackListener) {
        try {
            this.payContext = payContext;
            this.payCallbackListener = payCallbackListener;
            this.context = context;
            initIabHelper();
        } catch (Exception e) {
            e.printStackTrace();
            if (payCallbackListener != null) {
                payCallbackListener.onError(PayChannelType.GooglePay, payContext, "pay:" + e.getMessage(), PayErrorCode.Failed);
            }
        }
    }

    private void initIabHelper() {
        String pk = channelConfig.getBase64PublicKey();
        iabHelper = new IabHelper(context.getApplicationContext(), pk);
        iabHelper.enableDebugLogging(BuildConfig.DEBUG);
        iabHelper.startSetup(this);
    }

    @Override
    public void onIabSetupFinished(IabResult result) {
        if (result.isSuccess()) {
            try {
                iabHelper.queryInventoryAsync(this);
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
                startPay();
            }
        } else {
            if (payCallbackListener != null) {
                payCallbackListener.onError(PayChannelType.GooglePay, payContext, "onIabSetupFinished:" + result.getMessage(), PayErrorCode.Failed);
            }
        }
    }

    @Override
    public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
        try {
            if (result.isSuccess()) {
                List<Purchase> list = inventory.getAllPurchases();
                if (list != null) {
                    for (Purchase purchase : list) {
                        if (purchase != null && !isSubs(purchase)) {
                            iabHelper.consumeAsync(purchase, this);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        startPay();
    }

    private boolean isSubs(Purchase gasPurchase) {
        return gasPurchase != null && gasPurchase.getItemType() != null && IabHelper.ITEM_TYPE_SUBS.equals(gasPurchase.getItemType());
    }

    @Override
    public void onConsumeFinished(Purchase purchase, IabResult result) {

    }

    @Override
    public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
        switch (result.getResponse()) {
            case IabHelper.BILLING_RESPONSE_RESULT_OK: {
                if (!isSubs(purchase)) {
                    try {
                        iabHelper.consumeAsync(purchase, this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (payCallbackListener != null) {
                    payCallbackListener.onComplete(PayChannelType.GooglePay, payContext);
                }
                break;
            }
            case IabHelper.IABHELPER_USER_CANCELLED:
            case IabHelper.BILLING_RESPONSE_RESULT_USER_CANCELED: {
                if (payCallbackListener != null) {
                    payCallbackListener.onCancel(PayChannelType.GooglePay, payContext);
                }
                break;
            }
            case IabHelper.BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE: {
                if (payCallbackListener != null) {
                    payCallbackListener.onError(PayChannelType.GooglePay, payContext, result.getMessage(), PayErrorCode.GoogleBillingUnavailable);
                }
                break;
            }
            case IabHelper.BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE:
            case IabHelper.BILLING_RESPONSE_RESULT_DEVELOPER_ERROR:
            case IabHelper.BILLING_RESPONSE_RESULT_ERROR:
            case IabHelper.BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED:
            case IabHelper.BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED:
            default: {
                if (payCallbackListener != null) {
                    payCallbackListener.onError(PayChannelType.GooglePay, payContext, result.getMessage(), PayErrorCode.Failed);
                }
                break;
            }
        }
    }

    private void startPay() {
        try {
            if (payContext.isSubs()) {
                iabHelper.launchSubscriptionPurchaseFlow((Activity) context, payContext.getProductId(), PayRequestCode, this, payContext.getOrderNo());
            } else {
                iabHelper.launchPurchaseFlow((Activity) context, payContext.getProductId(), PayRequestCode, this, payContext.getOrderNo());
            }
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
            if (payCallbackListener != null) {
                payCallbackListener.onError(PayChannelType.GooglePay, payContext, "startPay error=" + e.getMessage(), PayErrorCode.Failed);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (iabHelper != null) {
            iabHelper.handleActivityResult(requestCode, resultCode, data);
        }
    }
}
