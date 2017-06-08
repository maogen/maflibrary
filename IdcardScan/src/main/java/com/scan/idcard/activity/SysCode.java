package com.scan.idcard.activity;

/**
 * 项目名称：RentalRoom
 * 类描述：
 * 创建人：zgmao
 * 创建时间：2017/6/7
 * 修改人：zgmao
 * 修改时间：2017/6/7
 * 修改备注：
 * Created by zgmao on 2017/6/7.
 */

public class SysCode {
    public static final int auto_focus = 0x2017001;
    public static final int restart_preview = 0x2017002;
    public static final int decode_succeeded = 0x2017003;
    public static final int decode_failed = 0x2017004;
    public static final int return_scan_result = 0x2017005;
    public static final int launch_product_query = 0x2017006;
    public static final int quit = 0x2017007;
    public static final int decode = 0x2017008;

    /**
     * 获取身份证扫描结果的key
     */
    public static final String SCAN_RESULT_ID_CARD = "scan_result_id_card";

}
