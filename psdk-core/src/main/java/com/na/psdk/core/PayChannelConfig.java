package com.na.psdk.core;

public interface PayChannelConfig <T extends IPayHandler>{
    int getChannelType();
    Class<T> getHandlerClass();
}
