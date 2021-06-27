package com.shenkong.bzzmaster.ui.fragment.product;

import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.ui.base.BaseViewMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ProductViewModel extends BaseViewMode<ProductEvent> {
    private final HashMap<String, List<ProductBean>> productMap = new HashMap<>();
    private Timer timer;
    private TimerTask timerTask;

    public void initProductData(String productName) {
        startSingleTimerTask(new TimerTask() {
            @Override
            public void run() {
                ArrayList<ProductBean> productBeanList = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    productBeanList.add(new ProductBean(productName + "早期矿工满存挖矿计划" + i));
                }
                if (!productMap.containsKey(productName)) {
                    productMap.put(productName, productBeanList);
                } else {
                    if (!productMap.containsValue(productBeanList)) {
                        productMap.put(productName, productBeanList);
                    }
                }
                uiRefreshCallBack.updateProductAdapter(productMap.get(productName));
            }
        });

        /*Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();*/
    }

    private void startSingleTimerTask(TimerTask task) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

        timer = new Timer();
        this.timerTask = task;
        timer.schedule(timerTask, 1000);
    }
}
