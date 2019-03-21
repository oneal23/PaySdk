package com.na.psdk.google;

import com.na.psdk.core.PayChannelConfig;
import com.na.psdk.core.PayChannelType;

public class GooglePayConfig implements PayChannelConfig<GooglePayHandler> {
    private String base64PublicKey;

    public String getBase64PublicKey() {
        return base64PublicKey;
    }

    public void setBase64PublicKey(String base64PublicKey) {
        this.base64PublicKey = base64PublicKey;
    }

    @Override
    public int getChannelType() {
        return PayChannelType.GooglePay;
    }

    @Override
    public Class<GooglePayHandler> getHandlerClass() {
        return GooglePayHandler.class;
    }
}
