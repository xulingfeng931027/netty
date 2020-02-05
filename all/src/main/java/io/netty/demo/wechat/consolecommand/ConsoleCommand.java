package io.netty.demo.wechat.consolecommand;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 控制台命令接口
 */
public interface ConsoleCommand {
    void exec(Scanner scanner, Channel channel);
}
