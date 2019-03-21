package com.na.psdk.core;

public interface IPayCallbackListener {
    void onComplete(int channelType, IPayContext payContext);

    void onError(int channelType, IPayContext payContext, String errMsg, int errcode);

    void onCancel(int channelType, IPayContext payContext);
}
