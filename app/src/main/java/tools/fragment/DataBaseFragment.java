package tools.fragment;


import com.base.activity.CustomerFragment;
import com.base.entity.ResultInfo;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;

import cn.com.taodaji.common.Constants;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.ui.activity.integral.requsest.IntegralShopRequestPresenter;
import retrofit2.Call;

/**
 * Created by yangkuo on 2018-08-29.
 */
public abstract class DataBaseFragment extends CustomerFragment implements Constants {

    @Deprecated
    public RequestPresenter getRequestPresenter() {
        return RequestPresenter.getInstance();
    }
    @Deprecated
    public IntegralShopRequestPresenter getIntegralRequestPresenter() {
        return IntegralShopRequestPresenter.getInstance();
    }
    public <T> void addRequest(Call<T> call, RequestCallback<T> callback) {
        callback.setCall(call).enqueue(callback);
    }

    public <T> void addRequest(Call<ResultInfo<T>> call, ResultInfoCallback<T> callback) {
        callback.setCall(call).enqueue(callback);
    }
}
