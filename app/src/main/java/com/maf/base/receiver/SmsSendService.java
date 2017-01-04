package com.maf.base.receiver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 项目名称：maflibrary
 * 类描述：
 * 创建人：mzg
 * 创建时间：2017/1/4 16:23
 * 修改人：mzg
 * 修改时间：2017/1/4 16:23
 * 修改备注：
 */

public class SmsSendService extends Service {
    public SmsSendService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
