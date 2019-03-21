package com.na.psdk.core;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

public interface IPayHandler<T extends PayChannelConfig, P extends IPayContext> {
    int PayRequestCode = 10001;

    void onInit(T channelConfig);

    void pay(Context context, P payContext, IPayCallbackListener payCallbackListener);

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
}
