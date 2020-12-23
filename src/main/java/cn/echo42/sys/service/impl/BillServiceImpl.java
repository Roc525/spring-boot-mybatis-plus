package cn.echo42.sys.service.impl;

import cn.echo42.sys.domain.Bill;
import cn.echo42.sys.mapper.BillMapper;
import cn.echo42.sys.service.IBillService;
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
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements IBillService {

}
