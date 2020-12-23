package com.mbatisplus.mybatis.service.Impl;

import com.mbatisplus.mybatis.base.service.impl.BaseServiceImpl;
import com.mbatisplus.mybatis.domain.Provider;
import com.mbatisplus.mybatis.mapper.ProviderMapper;
import com.mbatisplus.mybatis.service.IProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Echo42
 * @since 2020-12-12
 */
@Service("providerService")
public class ProviderServiceImpl extends BaseServiceImpl<ProviderMapper, Provider> implements IProviderService {

    @Autowired
    private ProviderMapper providerMapper;

    @Override
    public List<Provider> getProvider(Provider provider) {
        return providerMapper.getProvider(provider);
    }

    @Override
    public Integer remove(Integer id) {
        return providerMapper.deleteById(id);
    }
}
