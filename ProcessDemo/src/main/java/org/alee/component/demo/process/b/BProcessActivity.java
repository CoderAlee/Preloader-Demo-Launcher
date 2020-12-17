package org.alee.component.demo.process.b;

import org.alee.component.demo.process.main.MainProcessActivity;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/15
 * @description: xxxx
 *
 *********************************************************/
public class BProcessActivity extends MainProcessActivity {

    @Override
    protected void showDemo() {
        mBuilder.append("调用结果:").append(BProcessTestUtil.getInstance().getContent());
        mContentView.setText(mBuilder);
    }
}
