package net.zsygfddsd.qujing.modules.WelfareDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import net.zsygfddsd.qujing.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mac on 16/7/20.
 */
public class WelfareDetailActivity extends AppCompatActivity {

    @BindView(R.id.textview)
    TextView textview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.textview)
    public void onClick() {
    }
}
