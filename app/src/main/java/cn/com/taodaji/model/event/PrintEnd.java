package cn.com.taodaji.model.event;

/**
 * 打印结束
 */
public class PrintEnd {
    private String qrCodeId;

    public PrintEnd(String qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    public String getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(String qrCodeId) {
        this.qrCodeId = qrCodeId;
    }
}
