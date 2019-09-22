package {pg}.service;

import java.util.Map;
import {pg}.common.bean.Page;
import {pg}.entity.{entity};
/**
 * Description:{desc}-service接口
 * @version 1.0
 * @author {author}
 * @since {date}
 */
public interface {entity}Service{
    /**
     * Description:分页查询
     * @author:{author}
     * @since {date}
     * @param params  参数键值对象
     * @param page 分页对象
	 * @param orders 排序
     * @return Page
     */
    public Page queryPage(Map<String,Object> params,Page page,String[] orders);
    /**
     * Description:保存记录
     * @author:{author}
     * @since {date}
     * @param {lentity} 
     * @return void
     */
    public void addSave({entity} {lentity});
    /**
     * Description:查询单条记录
     * @author:{author}
     * @since {date}
     * @param params 参数键值对象
     * @return Map
     */
    public {entity} querySingle(Map<String,Object> params);
    /**
     * Description:更新记录
     * @author:{author}
     * @since {date}
     * @param {lentity} 
     * @return void
     */
    public void editSave({entity} {lentity});
    /**
     * Description:删除指定记录
     * @author:{author}
     * @since {date}
     * @param ids ID数组
     * @return void
     */
    public void delete(Long[] ids);
}
