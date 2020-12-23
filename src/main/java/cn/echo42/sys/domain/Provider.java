package cn.echo42.sys.domain;

import com.baomidou.mybatisplus.annotations.TableField;
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
public class Provider implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "pid", type = IdType.AUTO)
    private Integer pid;

    private String providerCode;

    @TableField("providerName")
    private String providerName;

    private String people;

    private String phone;

    private String address;

    private String fax;

    private String describe;

    private LocalDateTime createDate;


}
