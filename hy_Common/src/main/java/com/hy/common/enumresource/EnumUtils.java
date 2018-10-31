package com.hy.common.enumresource;

import com.hy.common.constant.Const;
import com.hy.common.utils.EnumMessage;
import com.jfinal.kit.Ret;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EnumUtils {
    public static List<EnumBean> EnumToList(String enumName){
        List<EnumBean> values = new ArrayList<>();
        if (enumName != null && !"".equals(enumName)) {
            Class clzz = null;
            try {
                clzz = Class.forName(Const.PACKAGE_NAME + "." + enumName);
                Method method = clzz.getMethod("values");
                EnumMessage inter[] = (EnumMessage[]) method.invoke(new Object[]{}, new Object[]{});
                for (EnumMessage enumMessage : inter) {
                    EnumBean e = new EnumBean();
                    e.setCode(enumMessage.getCode());
                    e.setValue(enumMessage.getValue());
                    values.add(e);
                }

            } catch (Exception e) {

            }
        }
        return values;
    }
}
