package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.zhenai.business.account.provider.AccountProvider;
import com.zhenai.business.im.provider.IMProvider;
import com.zhenai.business.media.avatar.UploadAvatarDialogActivity;
import com.zhenai.business.media.provider.MediaProvider;
import com.zhenai.business.media.take_photo.TakePhotoActivity;
import com.zhenai.business.media.view.activity.MediaAlbumActivity;
import com.zhenai.business.media.view.activity.MediaPreviewActivity;
import com.zhenai.business.profile.cache.MyBasicProfileCacheProvider;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Group$$business implements IRouteGroup {
    @Override // com.alibaba.android.arouter.facade.template.IRouteGroup
    public void loadInto(Map<String, RouteMeta> map) {
        RouteType routeType = RouteType.ACTIVITY;
        map.put("/business/avatar/TakePhotoActiviy", RouteMeta.a(routeType, TakePhotoActivity.class, "/business/avatar/takephotoactiviy", "business", null, -1, Integer.MIN_VALUE));
        map.put("/business/avatar/UploadAvatarDialogActivity", RouteMeta.a(routeType, UploadAvatarDialogActivity.class, "/business/avatar/uploadavatardialogactivity", "business", null, -1, Integer.MIN_VALUE));
        map.put("/business/media/MediaAlbumActivity", RouteMeta.a(routeType, MediaAlbumActivity.class, "/business/media/mediaalbumactivity", "business", null, -1, Integer.MIN_VALUE));
        map.put("/business/photo/MediaPreviewActivity", RouteMeta.a(routeType, MediaPreviewActivity.class, "/business/photo/mediapreviewactivity", "business", null, -1, Integer.MIN_VALUE));
        RouteType routeType2 = RouteType.PROVIDER;
        map.put("/business/provider/AccountProvider", RouteMeta.a(routeType2, AccountProvider.class, "/business/provider/accountprovider", "business", null, -1, Integer.MIN_VALUE));
        map.put("/business/provider/IMProvider", RouteMeta.a(routeType2, IMProvider.class, "/business/provider/improvider", "business", null, -1, Integer.MIN_VALUE));
        map.put("/business/provider/MediaProvider", RouteMeta.a(routeType2, MediaProvider.class, "/business/provider/mediaprovider", "business", null, -1, Integer.MIN_VALUE));
        map.put("/business/provider/MyBasicProfileCacheProvider", RouteMeta.a(routeType2, MyBasicProfileCacheProvider.class, "/business/provider/mybasicprofilecacheprovider", "business", null, -1, Integer.MIN_VALUE));
    }
}
