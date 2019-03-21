package com.na.psdk.wechat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.na.psdk.core.IPayCallbackListener;
import com.na.psdk.core.IPayHandler;
import com.na.psdk.core.PayChannelType;
import com.na.psdk.core.PayErrorCode;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WechatPayHandler implements IPayHandler<WechatPayConfig, WechatPayContext> {
    private static WechatPayHandler handler;
    private WechatPayConfig wechatPayConfig;
    private IWXAPI iwxapi;
    private IPayCallbackListener payCallbackListener;
    private WechatPayContext payContext;

    public static WechatPayHandler getDefault() {
        return handler;
    }

    public IWXAPI getApi() {
        return iwxapi;
    }

    @Override
    public void onInit(WechatPayConfig channelConfig) {
        wechatPayConfig = channelConfig;
        handler = this;
    }

    @Override
    public void pay(Context context, WechatPayContext payContext, IPayCallbackListener payCallbackListener) {

        try {
            this.payCallbackListener = payCallbackListener;
            this.payContext = payContext;
            String appId = wechatPayConfig.getAppId();
            if (iwxapi == null) {
                iwxapi = WXAPIFactory.createWXAPI(context.getApplicationContext(), appId, false);
                iwxapi.registerApp(appId);
            }

            PayReq req = new PayReq();
            req.appId = appId;
            req.partnerId = payContext.getPartnerId();
            req.prepayId =  payContext.getPrepayId();
            req.nonceStr =  payContext.getNonceStr();
            req.timeStamp = payContext.getTimeStamp();
            req.packageValue = payContext.getPackageValue();
            req.sign = payContext.getSign();
            req.extData = payContext.getOrderNo(); // optional
            iwxapi.sendReq(req);
        } catch (Exception e) {
            e.printStackTrace();
            if (payCallbackListener != null) {
                payCallbackListener.onError(PayChannelType.WechatPay, payContext, "pay:" + e.getMessage(), PayErrorCode.Failed);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

    }

    public void onReq(BaseReq baseReq) {

    }

    public void onResp(BaseResp resp) {

        switch (resp.getType()) {
            case ConstantsAPI.COMMAND_PAY_BY_WX: {
                payCallBack(resp);
                break;
            }
            default: {
                break;
            }
        }
    }

    private void payCallBack(BaseResp resp) {
        if (payCallbackListener != null && resp != null) {
            try {
                switch (resp.errCode) {
                    case BaseResp.ErrCode.ERR_OK: {
                        payCallbackListener.onComplete(PayChannelType.WechatPay, payContext);
                        break;
                    }
                    case BaseResp.ErrCode.ERR_USER_CANCEL: {
                        payCallbackListener.onCancel(PayChannelType.WechatPay, payContext);
                        break;
                    }
                    default: {
                        payCallbackListener.onError(PayChannelType.WechatPay, payContext, resp.errStr, PayErrorCode.Failed);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                payCallbackListener.onError(PayChannelType.WechatPay, payContext, "payCallBack:"+ e.getMessage(), PayErrorCode.Failed);
            }
        }
    }

}
