package io.netty.example.dubbo.loadBanlance;

import java.util.Collections;
import java.util.List;

/**
 * todo 描述作用
 *
 * @author 逼哥
 * @date 2019/12/3
 */
public class RandomLoadBalance implements LoadBalance {

    @Override
    public String select(List<String> addressList) {
        if (addressList.size() == 0) {
            return null;
        }
        Collections.shuffle(addressList);
        return addressList.get(0);
    }
}
