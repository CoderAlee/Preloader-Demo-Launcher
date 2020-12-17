package org.alee.component.demo.process.all;

import org.alee.component.demo.process.R;
import org.alee.component.demo.process.a.AProcessTestUtil;
import org.alee.component.demo.process.b.BProcessTestUtil;
import org.alee.component.demo.process.main.MainProcessActivity;
import org.alee.component.demo.process.main.TestUtil;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/15
 * @description: xxxx
 *
 *********************************************************/
public class AllProcessActivity extends MainProcessActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.process_activity_all_process;
    }


    @Override
    protected void bindView() {
        super.bindView();
        findViewById(R.id.btn_process_main).setOnClickListener(v -> {
            mBuilder.append("调用结果:").append(TestUtil.getContent()).append("\r\n");
            mContentView.setText(mBuilder);
        });
        findViewById(R.id.btn_process_a).setOnClickListener(v -> {
            mBuilder.append("调用结果:").append(AProcessTestUtil.getContent()).append("\r\n");
            mContentView.setText(mBuilder);
        });
        findViewById(R.id.btn_process_b).setOnClickListener(v -> {
            mBuilder.append("调用结果:").append(BProcessTestUtil.getInstance().getContent()).append("\r\n");
            mContentView.setText(mBuilder);
        });
    }

    @Override
    protected void showDemo() {
    }
}
