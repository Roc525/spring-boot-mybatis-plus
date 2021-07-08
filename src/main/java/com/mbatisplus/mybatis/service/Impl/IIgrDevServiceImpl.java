package com.mbatisplus.mybatis.service.Impl;

import com.mbatisplus.mybatis.base.service.impl.BaseServiceImpl;
import com.mbatisplus.mybatis.domain.IgrDev;
import com.mbatisplus.mybatis.mapper.IgrDevMapper;
import com.mbatisplus.mybatis.service.IIgrDevService;
import org.springframework.stereotype.Service;

@Service("iIgrDevService")
public class IIgrDevServiceImpl extends BaseServiceImpl<IgrDevMapper, IgrDev> implements IIgrDevService {
}
