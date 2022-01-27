package com.zxj.appstartup.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zxj.appstartup.manage.StartupManager;
import com.zxj.appstartup.startup.Startup;

import java.util.List;

public class StartupProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        try {
            List<Startup<?>> startups = StartupInitializer.discoverAndInitializ(getContext(), getClass().getName());
            new StartupManager.Builder()
                    .addAllStartup(startups)
                    .build(getContext())
                    .start()
                    .await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}