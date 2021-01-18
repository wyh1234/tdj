package cn.com.taodaji.model.entity;

public class IntegralAliPay {


    /**
     * status : 200
     * data : alipay_sdk=alipay-sdk-java-3.7.110.ALL&app_id=2019042464254677&biz_content=%7B%22out_trade_no%22%3A%22123456788%22%2C%22subject%22%3A%22%E6%B7%98%E5%A4%A7%E9%9B%86%E7%A7%AF%E5%88%86%E5%95%86%E5%9F%8E%22%2C%22total_amount%22%3A%22200.5900%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F192.168.10.108%3A8898%2Fstore%2Fali%2Fnotify&return_url=http%3A%2F%2F192.168.10.108%3A8898%2Fstore%2Fali%2Freturn&sign=ZfMH19nEy74CcDS3LTLRGdtzLLqB%2BRSq06qYggW2W975fn5ador3Y2zBbGm%2FDHESLjDU4LaeUJTnTu9TsWbWyQiRSTMFqyqgrLMbe4iFduC%2FrJiqz9BoSIT4sScde6vbLQ2X9Pqp3BYLj7woL%2FHQNVlL6mG3DhDPZZwU882MpzyzT6VA0lH77nSgrtpyEnNBCR8Rl51pELMCT%2B5TvQAuNnD8jTDkSk0sbsCOekEa2DfyOu8nFBlFPTFdU9mg%2BOV6oOyyjmwMHbPr0T7QJhO2upkrkrOEoUcnUWrV6U2XuvFwpNvKVp4GFkjOHCtPulkjZyDuIf5pE0cv10ZHNNjM4w%3D%3D&sign_type=RSA2&timestamp=2019-08-08+17%3A04%3A11&version=1.0
     * totalCount : null
     * error : null
     * message : null
     * errorCode : null
     */

    private int err;
    private String data;
    private String msg;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
