package net.zsygfddsd.qujing.bean;

/**
 * Created by mac on 16/3/3.
 */
public class ComRespInfo<T> extends Object{
    private boolean error;
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
