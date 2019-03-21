package com.na.psdk.google;

import com.na.psdk.core.BasePayContext;

public class GooglePayContext extends BasePayContext {
    //商品类型 消费类或订阅类
    private int productType;

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public boolean isSubs() {
        return productType == ProductType.Subs;
    }

}
