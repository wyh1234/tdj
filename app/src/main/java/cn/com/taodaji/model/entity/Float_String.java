package cn.com.taodaji.model.entity;


public class Float_String {
    private float value;
    private String description;
    private int colorInt;
    private String symbol = "";//正负号

    public Float_String() {

    }

    public Float_String(String symbol, float value, int colorInt, String description) {
        this.symbol = symbol;
        this.value = value;
        this.colorInt = colorInt;
        this.description = description;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getColorInt() {
        return colorInt;
    }

    public void setColorInt(int colorInt) {
        this.colorInt = colorInt;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
