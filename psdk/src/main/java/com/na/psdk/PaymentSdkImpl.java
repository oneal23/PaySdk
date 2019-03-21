package com.na.psdk;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.na.psdk.core.IPayCallbackListener;
import com.na.psdk.core.IPayContext;
import com.na.psdk.core.IPayHandler;
import com.na.psdk.core.PayChannelConfig;

import java.util.LinkedHashMap;
import java.util.Map;

public class PaymentSdkImpl implements IPaymentSdk {
    private final Map<Integer, IPayHandler> handlers = new LinkedHashMap<>();

    private PayChannelConfig getPlatformConfig(int type) {
        return PayChannelConfigManager.getPayChannelConfig(type);
    }

    private IPayHandler getHandler(int type) {
        IPayHandler handler = handlers.get(type);
        if (handler == null) {
            handler = creatHandler(getPlatformConfig(type));
            if (handler != null) {
                handlers.put(type, handler);
            }
        }
        return handler;
    }

    private IPayHandler creatHandler(PayChannelConfig payChannelConfig) {
        if (payChannelConfig != null) {
            try {
                return (IPayHandler) payChannelConfig.getHandlerClass().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void pay(Context context, IPayContext payContext, IPayCallbackListener payCallbackListener, int channelType) {
        IPayHandler handler = getHandler(channelType);
        if (handler != null) {
            handler.onInit(getPlatformConfig(channelType));
            handler.pay(context, payContext, payCallbackListener);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        for (Map.Entry<Integer, IPayHandler> entry : handlers.entrySet()) {
            entry.getValue().onActivityResult(requestCode, resultCode, data);
        }
    }
}
