package cn.com.taodaji.model.event;

/**
 * 打印错误
 */
public class PrintErr {
    private String qrCodeId;

    public PrintErr(String qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    public String getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(String qrCodeId) {
        this.qrCodeId = qrCodeId;
    }
}
