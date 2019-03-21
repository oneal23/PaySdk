package com.na.psdk.core;

import java.util.HashMap;

public abstract class BasePayContext implements IPayContext {
    //商品订单号
    private String orderNo;
    //商品id
    private String productId;
    //支付渠道
    private String channel;
    //订单金额 单位分
    private int amount;
    //3 位 ISO 货币代码
    private String currency;
    //发起支付请求客户端的IPv4地址
    private String clientIp;
    //商品标题
    private String subject;
    //商品描述信息
    private String body;
    //订单附加说明
    private String description;
    //app信息
    private HashMap<String, String> appInfo;
    //特定渠道发起交易时需要的额外参数
    private HashMap<String, String> extra;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<String, String> getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(HashMap<String, String> appInfo) {
        this.appInfo = appInfo;
    }

    public HashMap<String, String> getExtra() {
        return extra;
    }

    public void setExtra(HashMap<String, String> extra) {
        this.extra = extra;
    }
}
