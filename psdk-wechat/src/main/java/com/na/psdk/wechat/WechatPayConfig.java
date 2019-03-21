package com.na.psdk.wechat;

import com.na.psdk.core.PayChannelConfig;
import com.na.psdk.core.PayChannelType;

public class WechatPayConfig implements PayChannelConfig<WechatPayHandler> {
    private String appId;
    @Override
    public int getChannelType() {
        return PayChannelType.WechatPay;
    }

    @Override
    public Class<WechatPayHandler> getHandlerClass() {
        return WechatPayHandler.class;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
