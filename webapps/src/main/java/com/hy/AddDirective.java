package com.hy;

import com.hy.common.plugin.shiro.tag.PrincipalTag;
import com.hy.common.plugin.shiro.tag.ShiroUserTag;
import com.hy.tags.ComboxGroupTag;
import com.hy.tags.ComboxTag;
import com.jfinal.template.Engine;

public class AddDirective {
    public static void AddDirectiveTag(Engine me){
        me.addDirective("shiroUser",ShiroUserTag.class);
        me.addDirective("principal", PrincipalTag.class);
        me.addDirective("combox", ComboxTag.class);
        me.addDirective("comboxgroup", ComboxGroupTag.class);
    }

}
