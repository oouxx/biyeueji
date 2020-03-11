package org.linlinjava.litemall.wx.annotation.support;

import org.linlinjava.litemall.wx.annotation.HotGoodsAnnotation;
import org.linlinjava.litemall.db.service.MarkHotGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

public class HotGoodsFilter implements HandlerMethodArgumentResolver {
    @Autowired
    private MarkHotGoodsService markHotGoodsService;
    public static final String param = "isHot";
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(Boolean.class) && methodParameter.hasParameterAnnotation(HotGoodsAnnotation.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        String params = request.getParameter(param);
        return null;
    }
}
