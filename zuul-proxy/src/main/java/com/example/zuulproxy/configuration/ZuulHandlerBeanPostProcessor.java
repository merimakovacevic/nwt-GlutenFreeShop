package com.example.zuulproxy.configuration;

import com.example.zuulproxy.interceptor.SystemEventsInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ZuulHandlerBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

    @Override
    public boolean postProcessAfterInstantiation(final Object bean, final String beanName) throws BeansException {

        if (bean instanceof ZuulHandlerMapping) {

            ZuulHandlerMapping zuulHandlerMapping = (ZuulHandlerMapping) bean;
            zuulHandlerMapping.setInterceptors(new SystemEventsInterceptor());
        }

        return super.postProcessAfterInstantiation(bean, beanName);
    }

}