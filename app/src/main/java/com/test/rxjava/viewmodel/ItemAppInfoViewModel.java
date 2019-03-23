package com.test.rxjava.viewmodel;

import android.content.Context;
import androidx.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.test.rxjava.model.AppInfo;

public class ItemAppInfoViewModel extends MyObservable {

    private AppInfo appInfo;

    public ItemAppInfoViewModel(Context context, AppInfo appInfo) {
        super(context);
        this.appInfo = appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
        notifyChange();
    }

    public String getName() {
        return appInfo.getName();
    }

    public String getIconPath() {
        return appInfo.getIcon();
    }

    @BindingAdapter("bind:imagePath")
    public static void setImage(ImageView image, String path) {

        if (path == null || path.isEmpty()) {
            return;
        }

        Bitmap bitmap = BitmapFactory.decodeFile(path);
        image.setImageBitmap(bitmap);
    }
}
