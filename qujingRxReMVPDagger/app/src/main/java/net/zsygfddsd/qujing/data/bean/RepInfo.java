package net.zsygfddsd.qujing.data.bean;

/**
 * Created by mac on 16/3/3.
 */
public class RepInfo<T> {
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
