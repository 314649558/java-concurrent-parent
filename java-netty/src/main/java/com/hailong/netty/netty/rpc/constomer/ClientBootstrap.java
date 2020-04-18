package com.hailong.netty.netty.rpc.constomer;

import com.hailong.netty.netty.rpc.netty.NettyClient;
import com.hailong.netty.netty.rpc.publicinterface.HelloServices;
import lombok.extern.slf4j.Slf4j;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/18
 * Description:
 */
@Slf4j(topic = "c.ClientBootstrap")
public class ClientBootstrap {
    public static void main(String[] args) throws InterruptedException {
        NettyClient client=new NettyClient();
        HelloServices services = (HelloServices) client.getBean(HelloServices.class);
        for(;;){
            Thread.sleep(2000);
            String s = services.hello("Hello dubbo RPC");
            log.info("调用返回结果 : {}",s);
        }

    }
}
