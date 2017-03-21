// Send msg test - AboutView.java

boolean testSendMsg = false;
String uidSend = "70582909";
int fromValue = 1;
int toValue = 3000;
if (testSendMsg)
	sendTest(uidSend, fromValue, toValue);


private void sendTest(final String uidTo, final int fromValue, final int toValue)
{
	new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				/*for(int i = fromValue; i <= toValue; i++)
				{
					ChatContent cc = new ChatContent();
					cc.cliMsgId = ""+System.currentTimeMillis();
					cc.message = String.valueOf(i);
					cc.ownerId = uidTo;
					cc.receiverUid = uidTo;
					cc.senderUid = CoreUtility.currentUserUid;
					cc.state = 6;
					cc.timestamp = System.currentTimeMillis();
					cc.type = ChatContent.TYPE_TEXT;

					ZaloBusiness businessSendMessageToFriend = new ZaloBusinessImpl();
					businessSendMessageToFriend.setBusinessListener(new BusinessListener() {

						public void onErrorData(ErrorMessage error_message) {

						}
						public void onDataProcessed(Object entity) {

						}
					});

					businessSendMessageToFriend.sendMessageToFriend(uidTo, cc, false);
					android.util.Log.i(TAG, "Send Message: "+cc.message);
					Thread.sleep(10);
				}*/

				Field[] fields = R.string.class.getFields();

				for(int i = fromValue; i <= toValue; i++)
				{
//                      int ranStringID = new Random().nextInt(fields.length);
					Random r = new Random();
					int ranFieldID = r.nextInt(fields.length - 1);

					if(ranFieldID > 0)
					{
						int resId = getResId(fields[ranFieldID].getName(), com.zing.zalo.R.string.class);
						if(resId > 0)
						{
							String msgToSend = getActivity().getResources().getString(resId);
							if(!TextUtils.isEmpty(msgToSend))
							{
								ChatContent cc = new ChatContent();
								cc.cliMsgId = ""+System.currentTimeMillis();
								cc.message = msgToSend;
								cc.ownerId = uidTo;
								cc.receiverUid = uidTo;
								cc.senderUid = CoreUtility.currentUserUid;
								cc.state = 6;
								cc.timestamp = System.currentTimeMillis();
								cc.type = ChatContent.TYPE_TEXT;

								ZaloBusiness businessSendMessageToFriend = new ZaloBusinessImpl();
								businessSendMessageToFriend.setBusinessListener(new BusinessListener() {

									public void onErrorData(ErrorMessage error_message) {

									}
									public void onDataProcessed(Object entity) {

									}
								});

								businessSendMessageToFriend.sendMessageToFriend(uidTo, cc, false);
								android.util.Log.i(TAG, "Send Message: "+cc.message);
								Thread.sleep(10);
							}
						}

					}
				}


//                    for (int  i = 0; i < fields.length; i++) {
//                        stringNames[i] = fields[i].getName();
//                        if(!TextUtils.isEmpty(stringNames[i]))
//                        {
//                            ChatContent cc = new ChatContent();
//                            cc.cliMsgId = ""+System.currentTimeMillis();
//                            cc.message = stringNames[i];//String.valueOf(i);
//                            cc.ownerId = uidTo;
//                            cc.receiverUid = uidTo;
//                            cc.senderUid = CoreUtility.currentUserUid;
//                            cc.state = 6;
//                            cc.timestamp = System.currentTimeMillis();
//                            cc.type = ChatContent.TYPE_TEXT;
//
//                            ZaloBusiness businessSendMessageToFriend = new ZaloBusinessImpl();
//                            businessSendMessageToFriend.setBusinessListener(new BusinessListener() {
//
//                                public void onErrorData(ErrorMessage error_message) {
//
//                                }
//                                public void onDataProcessed(Object entity) {
//
//                                }
//                            });
//
//                            businessSendMessageToFriend.sendMessageToFriend(uidTo, cc, false);
//                            android.util.Log.i(TAG, "Send Message: "+cc.message);
//                            Thread.sleep(10);
//                        }
//                    }

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}).start();
}


public static int getResId(String resName, Class<?> c) {

	try {
		Field idField = c.getDeclaredField(resName);
		return idField.getInt(idField);
	} catch (Exception e) {
		e.printStackTrace();
		return -1;
	}
}


// .





//chat.undo






