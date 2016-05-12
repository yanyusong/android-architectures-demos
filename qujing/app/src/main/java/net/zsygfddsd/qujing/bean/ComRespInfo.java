package net.zsygfddsd.qujing.bean;

/**
 * Created by mac on 16/3/3.
 */
public class ComRespInfo {
    private boolean error;
    private String results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }
}
