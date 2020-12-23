package cn.echo42.sys.service.impl;

import cn.echo42.sys.domain.TblUser;
import cn.echo42.sys.mapper.TblUserMapper;
import cn.echo42.sys.service.ITblUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Echo42
 * @since 2020-12-12
 */
@Service
public class TblUserServiceImpl extends ServiceImpl<TblUserMapper, TblUser> implements ITblUserService {

}
