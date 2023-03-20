package com.lijt.chat.common.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>类  名：com.lijt.chat.common.base.BaseEntify</p>
 * <p>类描述：todo</p>
 * <p>创建时间：2023/3/20 9:59</p>
 *
 * @author junting.li
 * @version 1.0
 */
@Data
public class BaseEntify extends Model<BaseEntify> implements Serializable {
    private static final long serialVersionUID = -3200699394323054885L;
    /**
     * 创建人
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private Long createdBy;
    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private Date createdTime;
    /**
     * 更新人
     */
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;
    /**
     * 更新时间
     */
    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;

}
