package io.netty.example.dubbo.loadBanlance;

import java.util.List;

/**
 * todo 描述作用
 *
 * @author 逼哥
 * @date 2019/12/3
 */
public interface LoadBalance {    String select(List<String> addressList);

}
