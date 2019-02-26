package io.netty.lingfengdemo;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

/**
 * @author Paul
 * @version 0.1
 * @date 2019/2/26
 */
public class NioServer
{
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    public NioServer()
        throws IOException
    {   //打开一个channel
        serverSocketChannel = ServerSocketChannel.open();
        //配置为非阻塞式
        serverSocketChannel.configureBlocking(false);
        //绑定Server port
    }
    public  void test(ServerSocketChannel channel)
        throws IOException
    {

        Selector selector = Selector.open();
        //注册channel到selector中
        SelectionKey key = channel.register(selector, SelectionKey.OP_CONNECT);
        while (true)
        {
            //通过selector选择channel
            int readyChannels = selector.select();
            if (readyChannels == 0)
            {
                continue;
            }
            Set<SelectionKey> set = selector.selectedKeys();
            for (SelectionKey selectionKey : set)
            {
                if(key.isAcceptable()){

                }else if(key.isValid()){

                }
            }
        }

    }
}
