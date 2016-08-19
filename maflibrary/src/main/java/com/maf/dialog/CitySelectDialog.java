package com.maf.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.maf.R;
import com.maf.city_wheel.ArrayWheelAdapter;
import com.maf.city_wheel.CityModel;
import com.maf.city_wheel.DistrictModel;
import com.maf.city_wheel.OnWheelChangedListener;
import com.maf.city_wheel.ProvinceModel;
import com.maf.city_wheel.WheelView;
import com.maf.city_wheel.XmlParserHandler;
import com.maf.utils.ScreenUtils;
import com.maf.utils.StringUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 项目名称：Ytb_Android
 * 类描述：选择省市对话框
 * 创建人：mzg
 * 创建时间：2016/8/19 9:45
 * 修改人：mzg
 * 修改时间：2016/8/19 9:45
 * 修改备注：
 */
public class CitySelectDialog extends Dialog implements OnWheelChangedListener {
    private WheelView mViewProvince;// 显示省
    private WheelView mViewCity;// 显示市
    private WheelView mViewDistrict;// 显示县
    private Button mBtnConfirm;// 确定
    private Button mBtnCancel;// 取消
    protected String[] mProvinceDatas;
    protected Map<String, String[]> mCitisDatasMap = new HashMap<>();
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<>();
    protected Map<String, String> mZipcodeDatasMap = new HashMap<>();
    protected String mCurrentProviceName;
    protected String mCurrentCityName;
    protected String mCurrentDistrictName = "";
    protected String mCurrentZipCode = "";

    public CitySelectDialog(Context context) {
        this(context, R.style.customDialog);
    }

    public CitySelectDialog(Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.layout_dialog_select_city);
        initView();
    }

    /**
     * 初始化城市数据
     */
    private void initProvinceDatas() {
        List<ProvinceModel> provinceList;
        // 读取省市数据
        AssetManager asset = getContext().getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            provinceList = handler.getDataList();
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 初始化布局
     */
    private void initView() {
        // 设置大小
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        int width = ScreenUtils.getScreenWidth(getContext());
        layoutParams.width = width - 20;
        setUpViews();
        setUpListener();
        setUpData();
    }

    /**
     * 设置布局控件
     */
    private void setUpViews() {
        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
        mBtnCancel = (Button) findViewById(R.id.btn_cancel);
    }

    /**
     * 设置选择监听器
     */
    private void setUpListener() {
        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);
        mViewDistrict.addChangingListener(this);
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSelectListener != null) {
                    onSelectListener.onCitySelect(mCurrentProviceName, mCurrentCityName, mCurrentDistrictName);
                }
                dismiss();
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 设置控件显示相关属性
     */
    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<>(getContext(), mProvinceDatas));
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities("", "");
    }

    /**
     * 刷新县
     *
     * @param areaName 县名
     */
    private void updateAreas(String areaName) {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<>(getContext(), areas));
        int areaIndex = 0;
        if (areas == null || areas.length <= 0) {
            areaIndex = 0;
        } else if (StringUtils.isEmpty(areaName)) {
            areaIndex = 0;
        } else {
            for (int i = 0; i < areas.length; i++) {
                if (areas[i].equals(areaName)) {
                    areaIndex = i;
                    break;
                }
            }
        }
        mViewDistrict.setCurrentItem(areaIndex);
        mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[areaIndex];
        mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
    }

    /**
     *
     */
    /**
     * 刷新市
     *
     * @param cityName 如果市名不为空，默认选中第一行
     */
    private void updateCities(String cityName, String districtName) {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<>(getContext(), cities));
        int cityIndex = 0;
        if (cities == null || cities.length <= 0) {
            cityIndex = 0;
        } else if (StringUtils.isEmpty(cityName)) {
            cityIndex = 0;
        } else {
            for (int i = 0; i < cities.length; i++) {
                if (cities[i].equals(cityName)) {
                    cityIndex = i;
                    break;
                }
            }
        }
        mViewCity.setCurrentItem(cityIndex);
        updateAreas(districtName);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities("", "");
        } else if (wheel == mViewCity) {
            updateAreas("");
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    /**
     * 设置已经选中的城市信息
     *
     * @param provinceName 省
     * @param cityName     市
     * @param districtName 区
     */
    public void setSelectCity(String provinceName, String cityName, String districtName) {
        if (mProvinceDatas == null || mProvinceDatas.length <= 0) {
            return;
        }
        if (StringUtils.isEmpty(provinceName)) {
            return;
        }
        // 查找省
        for (int i = 0; i < mProvinceDatas.length; i++) {
            if (mProvinceDatas[i].equals(provinceName)) {
                mViewProvince.setCurrentItem(i);
                updateCities(cityName, districtName);
                break;
            }
        }
    }

    /**
     * 选择监听器，省市县
     */
    public interface OnSelectListener {
        void onCitySelect(String provinceName, String cityName, String districtName);
    }

    private OnSelectListener onSelectListener;

    /**
     * 设置选择监听器
     *
     * @param onSelectListener 监听器
     */
    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }
}
