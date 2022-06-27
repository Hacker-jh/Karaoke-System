package file.zhang.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Room_info implements Serializable {
    private String id;

    private BigDecimal rsize;

    private BigDecimal price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getRsize() {
        return rsize;
    }

    public void setRsize(BigDecimal rsize) {
        this.rsize = rsize;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}