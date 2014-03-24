package jerjenkins.model;

import java.util.Date;

public class Foo {
    private String x;
    private Integer y;
    private Date z;

    public Foo(String x, Integer y, Date z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String x() {
        return x;
    }

    public Integer y() {
        return y;
    }

    public Date z() {
        return z;
    }
}
