package cn.com.taodaji.model.entity;

/**
 * Created by zhaowenlong on 2019/3/22.
 */
public class AddSubuserBean {

    /**
     * entityId : 1350
     * employeesEntityId : 24
     * err : 0
     * msg : 操作成功!
     */

    private int entityId;
    private int employeesEntityId;
    private int err;
    private String msg;

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getEmployeesEntityId() {
        return employeesEntityId;
    }

    public void setEmployeesEntityId(int employeesEntityId) {
        this.employeesEntityId = employeesEntityId;
    }

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
