package cn.com.taodaji.model.event;

/**
 * 开始打印
 */
public class PrintStart {
    private String qrCodeId;

    public PrintStart(String qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    public String getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(String qrCodeId) {
        this.qrCodeId = qrCodeId;
    }
}
