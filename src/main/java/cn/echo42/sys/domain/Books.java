package cn.echo42.sys.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

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
public class Books implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "bookId", type = IdType.AUTO)
    private Integer bookId;

    @TableField("bookName")
    private String bookName;

    @TableField("bookAuthor")
    private String bookAuthor;

    @TableField("bookPublish")
    private String bookPublish;

    @TableField("bookPage")
    private Integer bookPage;

    @TableField("bookPrice")
    private Float bookPrice;

    @TableField("createDate")
    private LocalDate createDate;


}
