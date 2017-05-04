package com.maf.base.logic;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.maf.base.bean.Trailer;
import com.maf.base.bean.Trailers;
import com.maf.git.GsonUtils;
import com.maf.interfaces.IViewListener;
import com.maf.net.XAPIServiceListener;
import com.maf.net.XBaseAPIUtils;
import com.maf.utils.Lg;

import java.util.List;

/**
 * 项目名称：maflibrary
 * 类描述：请求电影列表，刷新的逻辑
 * 创建人：zgmao
 * 创建时间：2017/5/4
 * 修改人：Administrator
 * 修改时间：2017/5/4
 * 修改备注：
 * Created by Administrator on 2017/5/4.
 */

public class MovieLogic {
    private Context context;
    private IViewListener<Trailers> iViewListener;

    // 基础地址
    private String baseUrl = "http://api.m.mtime.cn/PageSubArea/";
    // 接口名
    private String movieAction = "/TrailerList.api";

    public MovieLogic(Context _context, IViewListener<Trailers> _iViewListener) {
        this.context = _context;
        this.iViewListener = _iViewListener;
    }

    /**
     * 请求电影数据
     */
    public void requestMovieList() {
        XBaseAPIUtils.get(baseUrl, movieAction, null,
                null, new XAPIServiceListener() {
                    @Override
                    public void onSuccess(String result) {
                        Lg.d("获取成功：" + result);
                        Trailers trailers = GsonUtils.stringToGson(result, new
                                TypeToken<Trailers>() {
                                });
                        iViewListener.updateView(trailers);
                        iViewListener.onSuccess(result);
                    }

                    @Override
                    public void onError(String result) {
                        // 请求错误
                        Lg.d("获取失败：" + result);
//                        BaseToast.makeTextShort("获取热修复信息失败");
                        iViewListener.onError(result);
                    }

                    @Override
                    public void onFinished() {
                        iViewListener.onFinished();
                    }
                });
    }
}
