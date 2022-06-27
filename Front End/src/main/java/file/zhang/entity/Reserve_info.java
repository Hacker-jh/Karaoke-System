package file.zhang.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Reserve_info implements Serializable {
    private String id;

    private String gId;

    private String rId;

    private String time;

    private BigDecimal reserveFlag;

    private Double totalPrice;

    private Double deposit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public BigDecimal getReserveFlag() {
        return reserveFlag;
    }

    public void setReserveFlag( BigDecimal reserveFlag) {
        this.reserveFlag = reserveFlag;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }
}