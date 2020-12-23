package com.mbatisplus.mybatis.service;

import com.baomidou.mybatisplus.service.IService;
import com.mbatisplus.mybatis.domain.Provider;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Echo42
 * @since 2020-12-12
 */
public interface IProviderService extends IService<Provider> {

    List<Provider> getProvider(Provider provider);

    Integer remove(Integer id);
}
