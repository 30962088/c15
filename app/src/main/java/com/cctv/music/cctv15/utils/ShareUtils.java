package com.cctv.music.cctv15.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.cctv.music.cctv15.R;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class ShareUtils {

    public static void shareWebsite(final Context context, SHARE_MEDIA media,
                                    final String title, final String url,File bitmapFile) {
        Bitmap bitmap = null;
        if(bitmapFile != null && bitmapFile.exists()){
            bitmap = BitmapFactory.decodeFile(bitmapFile.toString());
        }
        if(bitmap == null){
            bitmap = drawableToBitmap(context
                    .getResources().getDrawable(R.mipmap.ic_launcher));
        }

        if (media == SHARE_MEDIA.WEIXIN || media == SHARE_MEDIA.WEIXIN_CIRCLE) {
            shareWeixinWeb(context, title, url, media,bitmap);
        } else {

            final UMSocialService mController = UMServiceFactory.getUMSocialService(title);
            mController.getConfig().closeToast();
            switch (media) {
                case QZONE: {
                    QZoneShareContent content = new QZoneShareContent();
                    content.setTitle(title);
                    content.setAppWebSite(url);
                    content.setShareContent(url);
                    content.setShareImage(new UMImage(context,bitmap));
                    content.setTargetUrl(url);
                    mController.setShareMedia(content);
                }
                break;
                case QQ: {
                    QQShareContent content = new QQShareContent();
                    content.setTitle(title);
                    content.setAppWebSite(url);
                    content.setShareContent(title + " " + url);
                    content.setTargetUrl(url);
                    content.setShareImage(new UMImage(context,bitmap));
                    mController.setShareMedia(content);
                }
                break;
                default:
                    mController.setAppWebSite(url);
                    mController.setShareContent(title + " " + url);
                    break;
            }
            mController.directShare(context, media, new SocializeListeners.SnsPostListener() {

                @Override
                public void onStart() {

                }

                @Override
                public void onComplete(SHARE_MEDIA media, int arg1,
                                       SocializeEntity arg2) {
                    if (media == SHARE_MEDIA.SINA) {
                        Utils.tip(context, "分享成功");
                    }
                }
            });
        }
    }

    public static void shareWeixinWeb(Context context, String title,
                                      String url, SHARE_MEDIA media,Bitmap bitmap) {
        int scene = SendMessageToWX.Req.WXSceneSession;
        if (media == SHARE_MEDIA.WEIXIN_CIRCLE) {
            scene = SendMessageToWX.Req.WXSceneTimeline;
        }

        WXWebpageObject localWXWebpageObject = new WXWebpageObject();
        localWXWebpageObject.webpageUrl = url;
        WXMediaMessage localWXMediaMessage = new WXMediaMessage(
                localWXWebpageObject);
        localWXMediaMessage.title = title;
        localWXMediaMessage.description = title;
        localWXMediaMessage.thumbData = getBitmapBytes(bitmap, false);
        SendMessageToWX.Req localReq = new SendMessageToWX.Req();
        localReq.scene = scene;
        localReq.transaction = System.currentTimeMillis() + "";
        localReq.message = localWXMediaMessage;
        IWXAPI api = WXAPIFactory.createWXAPI(context, AppConfig.getInstance().getWX_APPID(), true);
        api.sendReq(localReq);
    }

    private static byte[] getBitmapBytes(Bitmap bitmap, boolean paramBoolean) {
        Bitmap localBitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);
        int i;
        int j;
        if (bitmap.getHeight() > bitmap.getWidth()) {
            i = bitmap.getWidth();
            j = bitmap.getWidth();
        } else {
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
        while (true) {
            localCanvas.drawBitmap(bitmap, new Rect(0, 0, i, j), new Rect(0, 0,
                    80, 80), null);
            if (paramBoolean)
                bitmap.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {

            }
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                android.graphics.Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

}
