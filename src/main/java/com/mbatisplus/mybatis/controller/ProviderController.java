package com.mbatisplus.mybatis.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mbatisplus.mybatis.domain.Provider;
import com.mbatisplus.mybatis.service.IProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Echo42
 * @since 2020-12-12
 */
@RestController
@RequestMapping("/sys/provider")
@Api(description ="供应商 操作接口", tags = {"ProviderController"})
public class ProviderController {

    @Autowired
    private IProviderService providerService;

    @ApiOperation(value = "根据条件查询供应商信息")
    @GetMapping("/getProviders")
    public void getProvider(Provider provider){
        EntityWrapper<Provider> wrapper = new EntityWrapper<>();
        wrapper.eq("pid",1);
        List<Provider> providers = providerService.selectList(wrapper);
        System.out.println(providers);

    }
}

