package com.maf.scanlib.activity;

/**
 * 类描述：扫描二维码常量
 * 创建人：mzg
 */
public class SysCodeZxing {
    public static final int auto_focus = 0x0001;
    public static final int decode = 0x0002;
    public static final int decode_failed = 0x0003;
    public static final int decode_succeeded = 0x0004;
    public static final int encode_failed = 0x0005;
    public static final int encode_succeeded = 0x0006;
    public static final int launch_product_query = 0x0007;
    public static final int quit = 0x0008;
    public static final int restart_preview = 0x0009;
    public static final int return_scan_result = 0x000a;
    public static final int search_book_contents_failed = 0x000b;
    public static final int search_book_contents_succeeded = 0x000c;

    /**
     * 获取二维码扫描结果的key
     */
    public static final String CODE_RESULT_KEY = "code_result_key";
}
