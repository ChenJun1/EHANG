package com.cvnavi.logistics.i51ehang.app.bean.eventbus;

/**
 * Created by george on 2016/11/4.
 */

public class EventSelect {
    private String signState;
    private String useState;
    private int signId;//签收buttonid
    private int useId;

    public int getSignId() {
        return signId;
    }

    public void setSignId(int signId) {
        this.signId = signId;
    }

    public int getUseId() {
        return useId;
    }

    public void setUseId(int useId) {
        this.useId = useId;
    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        this.signState = signState;
    }

    public String getUseState() {
        return useState;
    }

    public void setUseState(String useState) {
        this.useState = useState;
    }
}
