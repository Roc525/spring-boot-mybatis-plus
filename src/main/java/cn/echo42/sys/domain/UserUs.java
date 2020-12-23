package cn.echo42.sys.domain;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class UserUs implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private String name;

    private Integer age;


}
