package com.maf.base.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.maf.activity.BaseTitleActivity;
import com.maf.dialog.ShowQRCodeDialogUtils;
import com.maf.git.GsonUtils;
import com.maf.scanlib.activity.ActivityScanQRCode;
import com.maf.scanlib.activity.SysCodeZxing;
import com.maf.utils.BaseToast;
import com.maf.utils.Lg;
import com.scan.idcard.activity.ActivityScanIdCard;
import com.scan.idcard.activity.IDCardResult;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：二维码测试界面
 * 创建人：mzg
 * 创建时间：2016/8/31 9:31
 * 修改人：mzg
 * 修改时间：2016/8/31 9:31
 * 修改备注：
 */
public class CodeActivity extends BaseTitleActivity {
    private Button btnCreateCode;// 生成二维码
    private Button btnScanCode;// 扫描二维码
    private Button btn_scan_id_card;// 扫描身份证

    @Override
    protected void initTitleView() {
        titleBarView.setTitle("扫描二维码");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_code;
    }

    @Override
    protected void initView() {
        btnCreateCode = (Button) findViewById(R.id.btn_create_code);
        btnScanCode = (Button) findViewById(R.id.btn_scan_code);
        btn_scan_id_card = (Button) findViewById(R.id.btn_scan_id_card);

    }

    @Override
    protected void initEvent() {
        btnCreateCode.setOnClickListener(this);
        btnScanCode.setOnClickListener(this);
        btn_scan_id_card.setOnClickListener(this);
    }

    @Override
    protected void initValue() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create_code:
                // 生成二维码
                ShowQRCodeDialogUtils.showQRCodeDialog(this, "一个测试的二维码",
                        true, R.drawable.maf_icon);
                break;
            case R.id.btn_scan_code:
                // 扫描二维码
                ActivityScanQRCode.actionStartForResult(this, 1001);
                break;
            case R.id.btn_scan_id_card:
                // 扫描身份证
                ActivityScanIdCard.actionStart(this, 1002);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // 返回内容成功
            switch (requestCode) {
                case 1001:
                    // 从扫描二维码界面返回内容
                    String code = data.getStringExtra(SysCodeZxing.CODE_RESULT_KEY);
                    BaseToast.makeTextShort("扫描结果：" + code);
                    break;
                case 1002:
                    // 从扫描身份证界面返回
                    IDCardResult result = (IDCardResult) data.getSerializableExtra(com.scan.idcard.activity.SysCode.SCAN_RESULT_ID_CARD);
                    BaseToast.makeTextShort("扫描结果：" + GsonUtils.gsonToString(result));
                    Lg.d("扫描结果：" + GsonUtils.gsonToString(result));
                    break;
                default:
                    break;
            }
        }
    }
}
