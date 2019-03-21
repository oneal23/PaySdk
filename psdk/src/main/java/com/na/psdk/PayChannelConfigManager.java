package com.na.psdk;

import android.content.Context;

import com.na.psdk.core.PayChannelConfig;

import java.util.LinkedHashMap;
import java.util.Map;

public class PayChannelConfigManager {
    private static Map<Integer, PayChannelConfig> configs = new LinkedHashMap<>();

    public static PayChannelConfig getPayChannelConfig(int type) {
        return configs.get(type);
    }

    public static void addPlatformConfig(PayChannelConfig config) {
        if (config != null) {
            int type = config.getChannelType();
            PayChannelConfig platformConfig = getPayChannelConfig(type);
            if (platformConfig != null) {
                configs.remove(type);
            }
            configs.put(type, config);
        }
    }

}
