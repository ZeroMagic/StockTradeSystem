package cn.edu.zju.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Anthony on 2017/10/20.
 * 订单类
 */
@Entity
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "time", nullable = false)
    private String time;
    @Column(name = "price", nullable = false)
    private String price;
    @Column(name = "quantity", nullable = false)
    private String quantity;
    @Column(name = "state", nullable = false)
    private String state;
    @Column(name = "belong", nullable = false)
    private String belong;
    @Column(name = "stockcode", nullable = false)
    private String stockcode;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "total", nullable = false)
    private String total;
    @Column(name = "finish", nullable = false)
    private String finish;
    @Column(name = "averageprice", nullable = false)
    private String averageprice;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getAverageprice() {
        return averageprice;
    }

    public void setAverageprice(String averageprice) {
        this.averageprice = averageprice;
    }
}
