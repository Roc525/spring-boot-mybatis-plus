package cn.echo42.sys.service.impl;

import cn.echo42.sys.domain.Books;
import cn.echo42.sys.mapper.BooksMapper;
import cn.echo42.sys.service.IBooksService;
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
public class BooksServiceImpl extends ServiceImpl<BooksMapper, Books> implements IBooksService {

}
