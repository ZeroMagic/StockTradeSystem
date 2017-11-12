package cn.edu.zju.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Anthony on 2017/10/20.
 * 持仓类
 */
@Entity
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "stockname", nullable = false)
    private String stockname;
    @Column(name = "stockcode", nullable = false)
    private String stockcode;
    @Column(name = "total", nullable = false)
    private String total;
    @Column(name = "available", nullable = false)
    private String available;
    @Column(name = "belong", nullable = false)
    private String belong;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStockname() {
        return stockname;
    }

    public void setStockname(String stockname) {
        this.stockname = stockname;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }
}
