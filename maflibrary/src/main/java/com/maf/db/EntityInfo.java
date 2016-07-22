package com.maf.db;

import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 对象信息类
 * 
 * @author Administrator
 * 
 */
public class EntityInfo {

	/**
	 * 表名
	 */
	public String table;
	/**
	 * 主键
	 */
	public String pk;

	/**
	 * 是否自增
	 */
	public boolean pkAuto;

	/**
	 * 列
	 */
	public Map<String, String> columns = new HashMap<String, String>();

	/**
	 * 是否存在
	 */
	public boolean checked = false;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * 对面参数Map
	 */
	private static final Map<String, EntityInfo> entitys = new HashMap<String, EntityInfo>();

	/**
	 * 初始化
	 * 
	 * @param entityClazz
	 */
	private EntityInfo(Class<?> entityClazz) {
		Entity entity = entityClazz.getAnnotation(Entity.class);
		if (entity != null) {
			table = TextUtils.isEmpty(entity.table()) ? entityClazz
					.getSimpleName() : entity.table();
		} else {
			table = entityClazz.getSimpleName();
		}
		Field[] fields = entityClazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			Column column = field.getAnnotation(Column.class);
			NoColumn nocolumn = field.getAnnotation(NoColumn.class);
			if (column != null || nocolumn == null) {
				String columname;
				if (column != null) {
					columname = TextUtils.isEmpty(column.name()) ? field
							.getName() : column.name();
					if (column.pk()) {
						this.pk = field.getName();
						Class<?> primaryClazz = field.getType();
						if (primaryClazz == int.class
								|| primaryClazz == Integer.class
								|| primaryClazz == long.class
								|| primaryClazz == Long.class) {
							pkAuto = column.auto();
						} else {
							pkAuto = false;
						}
					}
				} else {
					columname = field.getName();
				}
				columns.put(field.getName(), columname);
			}
		}
	}

	/**
	 * 创建
	 * 
	 * @param entityClazz
	 * @return
	 */
	public static EntityInfo build(Class<?> entityClazz) {
		EntityInfo info = new EntityInfo(entityClazz);
		entitys.put(info.table, info);
		return info;
	}

	public String getTable() {
		return table;
	}

	public String getPk() {
		return pk;
	}

	public boolean isPkAuto() {
		return pkAuto;
	}

	public Map<String, String> getColumns() {
		return columns;
	}
}
