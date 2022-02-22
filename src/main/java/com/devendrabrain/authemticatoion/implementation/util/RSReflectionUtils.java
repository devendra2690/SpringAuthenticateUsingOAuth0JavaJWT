/*******************************************************************************
 * Copyright (c) 2018, RevSpring, Inc.
 * All Rights Reserved.
 * This software is proprietary to RevSpring, Inc.
 * Your access to this software is governed by the terms of your license agreement with RevSpring, Inc.
 * Any other use of the software is strictly prohibited.
 * @author Trilok Rajan 
 *******************************************************************************/
package com.devendrabrain.authemticatoion.implementation.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSReflectionUtils {
	
	protected static final Logger log = LoggerFactory.getLogger(RSReflectionUtils.class);


	public static Map<String, String> getFieldNamesAndValuesAsString(final Object valueObj)
			throws IllegalArgumentException, IllegalAccessException {
		Class<? extends Object> valueClass = valueObj.getClass();
		Map<String, String> fieldMap = new HashMap<>();
		Field[] valueObjFields = valueClass.getDeclaredFields();
		for (int i = 0; i < valueObjFields.length; i++) {
			String fieldName = valueObjFields[i].getName();
			valueObjFields[i].setAccessible(true);
			if (valueObjFields[i].getType().toString().equals("class java.lang.String")) {
				String newObj = (String) valueObjFields[i].get(valueObj);
				if (StringUtils.isNotBlank(newObj)) {
					fieldMap.put(fieldName, newObj);
				}
			}
		}
		return fieldMap;
	}

	public static Map<String, String[]> getFieldNamesAndValuesAsArray(final Object valueObj)
			throws IllegalArgumentException, IllegalAccessException {
		Class<? extends Object> valueClass = valueObj.getClass();
		Map<String, String[]> fieldMap = new HashMap<>();
		Field[] valueObjFields = valueClass.getDeclaredFields();
		for (int i = 0; i < valueObjFields.length; i++) {
			String fieldName = valueObjFields[i].getName();
			valueObjFields[i].setAccessible(true);
			if (valueObjFields[i].getType().isArray()) {
				String[] newObj = (String[]) valueObjFields[i].get(valueObj);
				if (ArrayUtils.isNotEmpty(newObj)) {
					fieldMap.put(fieldName, newObj);
				}
			}
		}
		return fieldMap;
	}

}
