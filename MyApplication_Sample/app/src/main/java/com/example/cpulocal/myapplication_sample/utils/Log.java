package com.example.cpulocal.myapplication_sample.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.Date;
//
//import org.slf4j.LoggerFactory;
//
//import android.os.Environment;
//import android.text.format.DateFormat;
//import ch.qos.logback.classic.Logger;
//import ch.qos.logback.classic.LoggerContext;
//import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
//import ch.qos.logback.classic.spi.ILoggingEvent;
//import ch.qos.logback.core.FileAppender;

public class Log {
	public static final boolean LOG = true;
    public static final int LOG_TYPE_LOGCAT = 1;
    public static final int LOG_TYPE_BOTH = 2;
    
    private static int logLevel = 1;
    private static int logType = Log.LOG_TYPE_LOGCAT;
    
    
//    private static Logger sLog;
//    private static LoggerContext sLoggerContext;
//    private static FileAppender<ILoggingEvent> sFileAppender;
//    
//    static {
//    	if (LOG) {
//            sLoggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
//
//            sFileAppender = new FileAppender<ILoggingEvent>();
//            sFileAppender.setContext(sLoggerContext);
//            sFileAppender.setName("timestamp");
//            Date d = new Date();
//            String logFileName = Environment.getExternalStorageDirectory().getAbsolutePath()
//                    + "/" + "zalo" + "/logs/zalo_"
//                    + DateFormat.format("yy-MM-dd_kkmmss", d) + ".log";
//
//            // set the file namec
//            sFileAppender.setFile(logFileName);
//
//            PatternLayoutEncoder encoder = new PatternLayoutEncoder();
//            encoder.setContext(sLoggerContext);
//            encoder.setPattern("%r %d %thread %level %logger - %msg%n");
//            encoder.start();
//
//            if (logType == LOG_TYPE_BOTH) {
//                sFileAppender.setEncoder(encoder);
//                sFileAppender.start();
//            }
//    	}
//    }
    
    /**
     * Change current logging level
     * @param level new log level 1 <= level <= 6 
     */
    public static void setLogLevel(int level) {
        logLevel = level;
    }

    /**
     * Get the current log level
     * @return the log level
     */
    public static int getLogLevel() {
        return logLevel;
    }

    public static int i(String tag, String string) {
        if (!LOG) return 0;
        if (logType==LOG_TYPE_LOGCAT) {
        	return android.util.Log.i(tag, string);
        }
        //logFileInfo(tag, string);
        return 0;
    }

	public static int i(String tag, String msg, Throwable tr) {
        if (!LOG) return 0;
        if (logType==LOG_TYPE_LOGCAT) {
        	return android.util.Log.i(tag, msg, tr);
        }
        //logFileInfo(tag, String.format("%s\n%s", msg, tr));
        return 0;
	}
	
    public static int e(String tag, String string) {
    	if (!LOG) return 0;
    	if (logType==LOG_TYPE_LOGCAT) {
    		return android.util.Log.e(tag, string);
    	}    	
    	//logFileError(tag, string);
    	return 0;
    }
    public static int e(String tag, String string, Throwable r) {
    	if (!LOG) return 0;
    	if (logType==LOG_TYPE_LOGCAT) {
    		return android.util.Log.e(tag, string, r);
    	}
    	//logFileError(tag, String.format("%s\n%s", string, getStackTraceString(r)));
    	return 0;
    }
    public static int e(String tag, Throwable r) {
    	if (!LOG) return 0;
    	if (logType==LOG_TYPE_LOGCAT) {
    		return android.util.Log.e(tag, "error", r);
    	}
    	//logFileError(tag, getStackTraceString(r));
    	return 0;
    }    
    public static int d(String tag, String string) {
    	if (!LOG) return 0;
    	if (logType==LOG_TYPE_LOGCAT) {
    		return android.util.Log.d(tag, string);
    	}
    	//logFileDebug(tag, string);
    	return 0;
    }

	public static int d(String tag, String msg, Throwable tr) {
        if (!LOG) return 0;
        if (logType==LOG_TYPE_LOGCAT) {
        	return android.util.Log.d(tag, msg, tr);
        }
        //logFileDebug(tag, String.format("%s\n%s", msg, tr));
        return 0;
	}
	
    public static int v(String tag, String string) {
    	if (!LOG) return 0;
    	if (logType==LOG_TYPE_LOGCAT) {
    		return android.util.Log.v(tag, string);
    	}    	
    	//logFileInfo(tag, string);
    	return 0;
    }
    
	public static int v(String tag, String msg, Throwable tr) {
        if (!LOG) return 0;
        if (logType==LOG_TYPE_LOGCAT) {
        	return android.util.Log.v(tag, msg, tr);
        }
        //logFileInfo(tag, String.format("%s\n%s", msg, tr));
        return 0;
	}	
	
    public static int w(String tag, String string) {
    	if (!LOG) return 0;
    	if (logType==LOG_TYPE_LOGCAT) {
    		return android.util.Log.w(tag, string);
    	}
    	//logFileWarn(tag, string);
    	return 0;
    }
    public static int w(String tag, String string, Throwable r) {
    	if (!LOG) return 0;
    	if (logType==LOG_TYPE_LOGCAT) {
    		return android.util.Log.w(tag, string, r);
    	}
    	//logFileWarn(tag, String.format("%s\n%s", string, getStackTraceString(r)));
    	return 0;
    }
    
    public static int w(String tag, Object[] objs) {
    	if (!LOG) return 0;
    	if (logType==LOG_TYPE_LOGCAT) {
    		for (Object object : objs) {
        		android.util.Log.w(tag, object.toString());
    		}
    	}
//    	for (Object object : objs) {
//    		logFileWarn(tag, object.toString());
//		}
    	return 0;
    }
	public static int w(String tag, Throwable ioe) {
		if (!LOG) return 0;
		if (logType==LOG_TYPE_LOGCAT) {
			return android.util.Log.w(tag, ioe);
		}
		//logFileWarn(tag, getStackTraceString(ioe));
		return 0;
	}	
	
//	private static void logFileDebug(String tag, String msg) {
//		if (!LOG) return;
//		if (sLoggerContext==null) return;
//        if (logType == LOG_TYPE_BOTH) {
//            if (sLog == null || !sLog.getName().equals(tag)) {
//                sLog = sLoggerContext.getLogger(tag);
//                sLog.addAppender(sFileAppender);
//            }
//            sLog.debug(msg);
//        }
//	}
//	
//	private static void logFileInfo(String tag, String msg) {
//		if (!LOG) return;
//		if (sLoggerContext==null) return;
//        if (logType == LOG_TYPE_BOTH) {
//            if (sLog == null || !sLog.getName().equals(tag)) {
//                sLog = sLoggerContext.getLogger(tag);
//                sLog.addAppender(sFileAppender);
//            }
//            sLog.info(msg);
//        }
//	}
//	
//	private static void logFileWarn(String tag, String msg) {
//		if (!LOG) return;
//		if (sLoggerContext==null) return;
//        if (logType == LOG_TYPE_BOTH) {
//            if (sLog == null || !sLog.getName().equals(tag)) {
//                sLog = sLoggerContext.getLogger(tag);
//                sLog.addAppender(sFileAppender);
//            }
//            sLog.warn(msg);
//        }
//	}
//	
//	private static void logFileError(String tag, String msg) {
//		if (!LOG) return;
//		if (sLoggerContext==null) return;
//        if (logType == LOG_TYPE_BOTH) {
//            if (sLog == null || !sLog.getName().equals(tag)) {
//                sLog = sLoggerContext.getLogger(tag);
//                sLog.addAppender(sFileAppender);
//            }
//            sLog.error(msg);
//        }
//	}
//	
//	public static void forceLogFile(String tag, String msg) {
//		if (!LOG) return;
//		if (sLoggerContext==null) return;
//        if (sLog == null || !sLog.getName().equals(tag)) {
//            sLog = sLoggerContext.getLogger(tag);
//            sLog.addAppender(sFileAppender);
//        }
//        sLog.debug(msg);
//	}
	
	public static void forceLogFile(String tag, Object entity) {
		if (entity==null) {
			forceLogFile(tag, "null");
		} else {
			forceLogFile(tag, entity.toString());
		}
	}
	
    /**
     * Handy function to get a loggable stack trace from a Throwable
     * @param tr An exception to log
     */
    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        return sw.toString();
    }

	public static boolean isLoggable(String mTag, int priority) {
		return android.util.Log.isLoggable(mTag, priority);
	}

	public static void println(int priority, String mTag, String string) {
		if (LOG) {
			android.util.Log.println(priority, mTag, string);
		}
	}

}