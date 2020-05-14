package com.example.raycastcanvastry;

public class TreadTry {
    Runnable runnable = new Runnable() {
        public void run() {
            // Переносим сюда старый код
            long endTime = System.currentTimeMillis()
                    + 20 * 1000;

            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime -
                                System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            }
//            Log.i("Thread", "Сегодня коты перебегали дорогу: " + mCounter++ + " раз");
            // Нельзя!
            // TextView infoTextView =
            //         (TextView) findViewById(R.id.textViewInfo);
            // infoTextView.setText("Сегодня коты перебегали дорогу: " + mCounter++ + " раз");
        }
    };
    Thread thread = new Thread(runnable);
//    thread.start();
}
