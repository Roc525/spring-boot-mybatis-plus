package cn.echo42.sys.service.impl;

import cn.echo42.sys.domain.User;
import cn.echo42.sys.mapper.UserMapper;
import cn.echo42.sys.service.IUserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
