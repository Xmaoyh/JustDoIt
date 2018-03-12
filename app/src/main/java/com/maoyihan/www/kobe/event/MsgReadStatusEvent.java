package com.maoyihan.www.kobe.event;

/**
 * 全部、消息、公告红点显示情况
 * Created by MaoYiHan on 2017/9/2 0002
 */

public class MsgReadStatusEvent {
    public int isAllRead, isMessageRead, isNoticeRead;//0:不显示红点；1:显示红点；3:未返回

    public MsgReadStatusEvent(int allRead, int messageRead, int isNoticeRead) {
        this.isAllRead = allRead;
        this.isMessageRead = messageRead;
        this.isNoticeRead = isNoticeRead;
    }
}
