package com.mbatisplus.mybatis.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mbatisplus.mybatis.domain.IgrDev;
import com.mbatisplus.mybatis.domain.Provider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Echo42
 * @since 2020-12-12
 */
@Repository
public interface IgrDevMapper extends BaseMapper<IgrDev> {

    List<IgrDev> getIgrDev(IgrDev igrDev);
}
