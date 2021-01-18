package com.base.glide.progress;

import android.content.Context;

import com.base.common.Config;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

@GlideModule
public class ProgressAppGlideModule extends AppGlideModule {
//    @Override
//    public boolean isManifestParsingEnabled() {
//        return false;
//    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()));
    }

//    @Override
//    public void applyOptions(Context context, GlideBuilder builder) {
//        super.applyOptions(context, builder);
//        builder.setDiskCache(new DiskLruCacheFactory(Config.glideImageSaveDir, 1024 * 1024 * 300));
//    }
}