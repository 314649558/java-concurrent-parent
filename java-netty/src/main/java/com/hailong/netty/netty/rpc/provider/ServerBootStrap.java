package com.hailong.netty.netty.rpc.provider;

import com.hailong.netty.ConfigConst;
import com.hailong.netty.netty.rpc.netty.NettyServer;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/18
 * Description:
 *    模拟 Dubbo RPC 的服务端启动程序
 */
public class ServerBootStrap {
    public static void main(String[] args) {
        NettyServer.startServer(ConfigConst.ADDR,ConfigConst.PORT);
    }
}
