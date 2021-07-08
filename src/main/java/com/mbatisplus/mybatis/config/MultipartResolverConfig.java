package com.mbatisplus.mybatis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Component
public class MultipartResolverConfig {
    public MultipartResolverConfig() {
    }

    @Bean(
            name = {"multipartResolver"}
    )
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(838860800L);
        return resolver;
    }
}