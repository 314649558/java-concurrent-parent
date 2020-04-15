package com.hailong.netty.netty.protocol;

import lombok.Data;

/**
 * Created By:袁海龙[314649558@qq.com]
 * Created Date: 2020/4/15
 * Description:
 *     自定义协议
 */
@Data
public class MessageProtocol {
    private int len;
    private byte[] content;
}
