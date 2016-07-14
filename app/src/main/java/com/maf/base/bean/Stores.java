package com.maf.base.bean;

import java.util.List;

/**
 * 项目名称：Ytb_Android
 * 类描述：获取门店信息数据
 * 创建人：mzg
 * 创建时间：2016/7/13 10:19
 * 修改人：mzg
 * 修改时间：2016/7/13 10:19
 * 修改备注：
 */
public class Stores {
    private List<Store> stores;

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }


    public class Store {
        private String id;
        private int storeType;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getStoreType() {
            return storeType;
        }

        public void setStoreType(int storeType) {
            this.storeType = storeType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
