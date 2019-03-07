/**
 * DateConverter.java
 * Created at 2017-10-25
 * Created by pc
 * Copyright (C) 2017 BROADTEXT SOFTWARE, All rights reserved.
 */
package com.swsm.platform.util.converter.date;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>ClassName: DateConverter</p>
 * <p>Description: TODO</p>
 * <p>Author: pc</p>
 * <p>Date: 2017-10-25</p>
 */
public class DateConverter implements Converter<String, Date> {

    private Logger logger = LoggerFactory.getLogger(DateConverter.class);


    public Date convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(source);
        } catch (ParseException e) {
            logger.error("日期参数转换错误", e);
        }
        return null;
    }

}
