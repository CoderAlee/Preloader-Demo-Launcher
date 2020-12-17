package org.alee.component.demo.process.a;

import org.alee.component.demo.process.main.MainProcessActivity;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/15
 * @description: xxxx
 *
 *********************************************************/
public class AProcessActivity extends MainProcessActivity {

    @Override
    protected void showDemo() {
        mBuilder.append("调用结果:").append(AProcessTestUtil.getContent());
        mContentView.setText(mBuilder);
    }
}
