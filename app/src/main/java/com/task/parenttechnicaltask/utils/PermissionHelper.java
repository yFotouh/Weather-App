package com.task.parenttechnicaltask.utils;

import android.content.Context;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterBuilder;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

public class PermissionHelper {
    public static void getPermission(Context context, MultiplePermissionsListener listener, String... permission) {
        Dexter.withContext(context)
                .withPermissions(permission)
                .withListener(listener)
                .check();
    }
}
