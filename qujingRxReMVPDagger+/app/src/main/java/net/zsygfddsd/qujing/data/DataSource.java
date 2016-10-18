package net.zsygfddsd.qujing.data;

import com.zsygfddsd.spacestation.data.bean.ComRespInfo;

import net.zsygfddsd.qujing.data.bean.Welfare;

import java.util.List;

import rx.Observable;

/**
 * Created by mac on 2016/10/11.
 */

public class DataSource {

    public interface WelfareDataSource {

        Observable<ComRespInfo<List<Welfare>>> getWelfareList(String type, String pageSize, String page);
    }

}
