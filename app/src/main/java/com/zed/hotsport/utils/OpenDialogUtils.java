package com.zed.hotsport.utils;

import android.content.DialogInterface;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/11/10.
 */

public class OpenDialogUtils {

    /**
     * 阻止对话框关闭
     * @param dialog
     */
    public static void stopCloseDialog(DialogInterface dialog){
        try {
            Field field = dialog.getClass().getSuperclass()
                    .getDeclaredField("mShowing");
            field.setAccessible(true);
            // 将mShowing变量设为false，表示对话框已关闭,系统就不再处理关对话框的逻辑了
            field.set(dialog, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 让系统自动处理对话框关闭
     * @param dialog
     */
    public static void closeDialog(DialogInterface dialog){
        try {
            Field field = dialog.getClass().getSuperclass()
                    .getDeclaredField("mShowing");
            field.setAccessible(true);
            // 将mShowing变量设为true，表示对话框没有关闭，系统会自动处理关闭
            field.set(dialog, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
