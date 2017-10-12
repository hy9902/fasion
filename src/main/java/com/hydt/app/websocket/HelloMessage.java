package com.hydt.app.websocket;

/**
 * Created by bean_huang on 2017/10/11.
 */
public class HelloMessage {
    private String name;

    @java.beans.ConstructorProperties({"name"})
    public HelloMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof HelloMessage)) return false;
        final HelloMessage other = (HelloMessage) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof HelloMessage;
    }

    public String toString() {
        return "HelloMessage(name=" + this.getName() + ")";
    }
}
