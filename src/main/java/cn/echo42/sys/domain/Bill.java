package cn.echo42.sys.domain;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Echo42
 * @since 2020-12-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Bill implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "bid", type = IdType.AUTO)
    private Integer bid;

    private String billCode;

    private String billName;

    private String billCom;

    private Integer billNum;

    private Double money;

    /**
     * 是否付款 0 未付款， 1已付款
     */
    private Integer pay;

    /**
     * 供应商id
     */
    private Integer pid;

    private LocalDateTime createDate;


}
