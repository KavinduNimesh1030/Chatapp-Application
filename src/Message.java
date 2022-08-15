import java.io.Serializable;

public class Message<T> {

    private T massage;

    public T getPayload() {
        return massage;
    }

    public void setPayload(T massage) {
       this.massage = massage;
    }
}


