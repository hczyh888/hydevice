package com.hy.common.plugin.shiro.tag;

import com.jfinal.template.Directive;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Created by jie on 2017/4/3.
 * Tag 的公用方法
 */
public abstract class SecureTag extends Directive {
    /**
     * 获取 Subject信息
     *
     * @return Subject
     */
    protected Subject getSubject() {
        return SecurityUtils.getSubject();
    }
}
