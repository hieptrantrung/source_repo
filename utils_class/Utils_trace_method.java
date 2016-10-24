private void startTrace() {
        ActivityManager activityManager = (ActivityManager) MainApplication.getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
        int memoryClass = activityManager.getMemoryClass();
        android.util.Log.i("ThreadDump", "memoryClass=" + memoryClass);


        Utils.showMess("start tracing. Please wait !");
        StringBuilder sb = new StringBuilder();
        sb.append("----------------- Start tracing...-------------------\n");

        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
        for (int i = 0; i <  threadArray.length; i++) {
            Thread t = threadArray[i];
            sb.append("Thread :"+t+":"+"state:"+t.getState());
            StackTraceElement[] traces = t.getStackTrace();
            for (int j = 0; j < traces.length; j++) {
                sb.append("\n");
                sb.append(traces[j].toString());
            }
            sb.append("\n\n");
        }

        android.util.Log.i("ThreadDump", sb.toString());
        writeTimelineLogs(sb.toString());

        // Dump by trace view
        Debug.startMethodTracing("trace_312");

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Debug.stopMethodTracing();

                File pathTrace = new File(MainApplication.getAppContext().getExternalFilesDir(null), "trace_312.trace");
                if (pathTrace.exists()) {
                    File destTrace = new File(tracePath);
                    try {
                        com.zing.zalo.media.util.FileUtils.copy(pathTrace, destTrace);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                Utils.showMess("Stop tracing");
                sendEmail();
            }
        }, 8000);

    }

    private android.os.Handler mHandler = new android.os.Handler(Looper.getMainLooper());

    public static final String tracePath = GlobalData.sdcardPath + "/312_trace.trace";
    public static final String logPath = GlobalData.sdcardPath + "/312_threads.txt";
    public static final String logStickerPath = GlobalData.sdcardPath + "/312_sticker.txt";

    public static boolean writeTimelineLogs(String logMessage) {
        try {
            String tmp = "-------------------------------- \n";

				/*logMessage = GlobalData.versionName + "\t" +System.currentTimeMillis() + "\t"
                + logMessage + "\n";*/
            long times = System.currentTimeMillis() - 0;
            logMessage = "Time: " + times + " Millis - " + logMessage + "\n";

            if (logMessage.indexOf("API END") > 0)
                logMessage = logMessage + tmp;
				/*else if(logMessage.indexOf("API START") > 0)
					logMessage = tmp + logMessage;*/

            File file = new File(logPath);

            //if(file != null)// && file.length()> (2*1024*1024))//Delete if over 2MB
            if (file.length() > (2 * 1024 * 1024)) {
                file.delete();
                file.createNewFile();
            }

            if (!file.exists())
                file.createNewFile();

            BufferedWriter bf = new BufferedWriter(new FileWriter(file, true));
            bf.write(logMessage);
            bf.flush();
            bf.close();
            return true;
        } catch (Exception ex) {
            Log.d("FileUtil - AppendFile", ex.getMessage());
            return false;
        }
    }

    public static boolean writeLogSticker(String logMessage) {
        try {
            android.util.Log.i("ThreadDump","Sticker: " + logMessage);
            String tmp = "-------------------------------- \n";

				/*logMessage = GlobalData.versionName + "\t" +System.currentTimeMillis() + "\t"
                + logMessage + "\n";*/
            long times = System.currentTimeMillis() - 0;
            logMessage = "Time: " + times + " Millis - " + logMessage + "\n";

            if (logMessage.indexOf("API END") > 0)
                logMessage = logMessage + tmp;
				/*else if(logMessage.indexOf("API START") > 0)
					logMessage = tmp + logMessage;*/

            File file = new File(logStickerPath);

            //if(file != null)// && file.length()> (2*1024*1024))//Delete if over 2MB
            if (file.length() > (2 * 1024 * 1024)) {
                file.delete();
                file.createNewFile();
            }

            if (!file.exists())
                file.createNewFile();

            BufferedWriter bf = new BufferedWriter(new FileWriter(file, true));
            bf.write(logMessage);
            bf.flush();
            bf.close();
            return true;
        } catch (Exception ex) {
            Log.d("FileUtil - AppendFile", ex.getMessage());
            return false;
        }
    }

    private void sendEmail() {
        try {
            synchronized (isFeedbackProgressing) {
                if (isFeedbackProgressing.get()) {
                    Utils.showMess(R.string.waiting);
                } else {
                    isFeedbackProgressing.set(true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                QOSBackgroundWorker.forceSaveAllQOS();
                                sendEmailFeedBack(getActivity());
                                SharedPreferencesHelper.setLastTimestampLogQOS(MainApplication.getAppContext(), 0);
                                QOSBackgroundWorker.resumeThread();
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                synchronized (isFeedbackProgressing) {
                                    isFeedbackProgressing.set(false);
                                }
                            }
                        }
                    }).start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }