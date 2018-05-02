package com.sukidesu.common.common.enums;

/**
 * @author weixian.yan
 * @created on 18:36 2018/4/29
 * @description:
 */
public enum OrderStateEnum {
    SUCCESS(1,"成功"),
    FAILED(0,"失败"),
    SENT(2,"已发货"),
    ;
    private int state;

    private String info;

    OrderStateEnum(int state, String info) {
        this.state = state;
        this.info = info;
    }

    public static OrderStateEnum stateOf(int index){
        for(OrderStateEnum state : values()){
            if(state.getState() == index ){
                return state;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
