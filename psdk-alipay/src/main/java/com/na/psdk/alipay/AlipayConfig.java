package com.na.psdk.alipay;

import com.na.psdk.core.PayChannelConfig;
import com.na.psdk.core.PayChannelType;

public class AlipayConfig implements PayChannelConfig<AlipayHandler> {
    @Override
    public int getChannelType() {
        return PayChannelType.Alipay;
    }

    @Override
    public Class<AlipayHandler> getHandlerClass() {
        return AlipayHandler.class;
    }
}
