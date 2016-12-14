package com.maf.base.activity;

import android.view.View;
import android.widget.Button;

import com.alipay.euler.andfix.patch.PatchManager;
import com.google.gson.reflect.TypeToken;
import com.maf.activity.BaseBackActivity;
import com.maf.base.bean.PatchBean;
import com.maf.git.GsonUtils;
import com.maf.net.XAPIServiceListener;
import com.maf.net.XBaseAPIUtils;
import com.maf.utils.BaseToast;
import com.maf.utils.DialogUtil;
import com.maf.utils.FileUtils;
import com.maf.utils.Lg;
import com.maf.utils.StringUtils;

import org.xutils.common.Callback;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import maf.com.mafproject.BuildConfig;
import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：热修复测试activity
 * 创建人：mzg
 * 创建时间：2016/10/9 17:32
 * 修改人：mzg
 * 修改时间：2016/10/9 17:32
 * 修改备注：
 */
public class HotFixActivity extends BaseBackActivity {
    private Button btnGetPath;// 获取修复包路劲
    private Button btnFix;// 修复
    private Button btnPrint;// 打印消息
    //热修复工具
    private PatchManager patchManager;
    // 热修复插件地址
    private String patchPath;
    // 热修复后台地址
    private String patchBaseUrl = "http://192.168.1.174:8089/HotFixWebservice/";
    // 热修复后台地址
    private String patchAction = "getPatchPath.do";
    // 插件下载到本地的地址
    private String filePath;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_hotfix;
    }

    @Override
    protected void initView() {
        btnGetPath = (Button) findViewById(R.id.btn_get_patch_path);
        btnFix = (Button) findViewById(R.id.btn_start_fix);
        btnPrint = (Button) findViewById(R.id.btn_print);
    }

    @Override
    protected void initEvent() {
        btnGetPath.setOnClickListener(this);
        btnFix.setOnClickListener(this);
        btnPrint.setOnClickListener(this);
    }

    @Override
    protected void initValue() {
        // 获取修复包地址
        // 一般在Application中初始化
        patchManager = new PatchManager(this);
        patchManager.init(BuildConfig.VERSION_NAME);
        patchManager.loadPatch();
        // 建议使用是，将之前下载的插件包删除
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_patch_path:
                requestPatch();
                break;
            case R.id.btn_start_fix:
                // 开始修复
                if (StringUtils.isEmpty(filePath)) {
                    BaseToast.makeTextShort("插件地址为空，插件未下载");
                } else if (!new File(filePath).exists()) {
                    BaseToast.makeTextShort("插件未找到：" + filePath);
                } else {
                    BaseToast.makeTextShort("开始修复");
                    try {
                        patchManager.addPatch(filePath);
                        BaseToast.makeTextShort("修复成功");
                    } catch (IOException e) {
                        e.printStackTrace();
                        BaseToast.makeTextShort("修复出现异常");
                    }
                }
                break;
            case R.id.btn_print:
                // 打印
                BaseToast.makeTextShort("bug修复前的版本");
//                BaseToast.makeTextShort("bug已经被修复");
                break;
            default:
                break;
        }
    }

    /**
     * 请求插件路径
     */
    private void requestPatch() {
        DialogUtil.showProgressDialog(this);
        Map<String, Object> map = new HashMap<>();
        map.put("appversion", BuildConfig.VERSION_NAME);
        XBaseAPIUtils.post(patchBaseUrl, patchAction, null,
                map, new XAPIServiceListener() {
                    @Override
                    public void onSuccess(String result) {
                        Lg.d("获取成功：" + result);
                        PatchBean patchBean = GsonUtils.stringToGson(result, new TypeToken<PatchBean>() {
                        });
                        if (patchBean != null && patchBean.isHasNewPatch()) {
                            patchPath = patchBean.getPatchPath();
                            // 有新插件，下载插件地址
//                            DialogUtil.dismissDialog();
                            BaseToast.makeTextShort("有新插件：" + patchPath);
                            loadPatch();

                        } else {
                            patchPath = "";
                            DialogUtil.dismissDialog();
                            BaseToast.makeTextShort("无需修复");
                        }
                    }

                    @Override
                    public void onError(String result) {
                        // 请求错误
                        Lg.d("获取失败：" + result);
                        patchPath = "";
                        BaseToast.makeTextShort("获取热修复信息失败");
                        DialogUtil.dismissDialog();
                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

    /**
     * 下载插件
     */
    private void loadPatch() {
        if (StringUtils.isEmpty(patchPath)) {
            Lg.d("插件地址为空");
            return;
        }
        if (!patchPath.startsWith("http")) {
            // 插件地址不是http开头的，凭借
            patchPath = patchBaseUrl + patchPath;
        }
        File file = new File(patchPath);
        String fileName = file.getName();
        filePath = FileUtils.getFilePath(fileName);
        XBaseAPIUtils.loadFile(patchPath, filePath, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                Lg.d("开始下载文件");
                BaseToast.makeTextShort("开始下载热修复插件");
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                float p = current / (float) total * 100;
                DecimalFormat format = new DecimalFormat("#.00");
                Lg.d("文件下载进度:" + format.format(p) + "%");
            }

            @Override
            public void onSuccess(File result) {
                filePath = result.getAbsolutePath();
                Lg.d("文件下载成功：" + filePath);
                BaseToast.makeTextShort("插件下载成功");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Lg.d("文件下载失败");
                ex.printStackTrace();
                BaseToast.makeTextShort("插件下载失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                DialogUtil.dismissDialog();
            }
        });
    }
}
