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

public static void printTelephonyManagerMethodNamesForThisDevice(Context context) {

    TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    Class<?> telephonyClass;
    try {
        telephonyClass = Class.forName(telephony.getClass().getName());
        Method[] methods = telephonyClass.getMethods();
        for (int idx = 0; idx < methods.length; idx++) {

            System.out.println("\n" + methods[idx] + " declared by " + methods[idx].getDeclaringClass());
        }
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
} 




// .

http://acra3.zapps.vn/acralyzer/_design/acralyzer/index.html#/reports-browser/zalo-android-12100203/user/00000000-0000-0000-0000-00000   c2847b5






//chat.undo  //MSG_TYPE_UNDO


cmd 600

[{"id":153452208669,"item":{"uid":109892775,"ts":1499242201309},"type":14}]




cmd 600	- type 84

Sender Id 
73816860
User List 
74305851
Command 
600
Sub Command 
84
Format
▼
Return Package
Data 
[
{
"item":{
"uid":"213165605",
"avt":"http://s120.avatar.talk.zdn.vn/default",
"cover":"http://cover.talk.zdn.vn/b/0/4/9/49/b63ee8b36353432ca4dbbcb377c641d8.jpg",
"dpn":"Giang Quốc Trung",
"uname":"",
"phone":"+84984083098",
"desc":"Ruby Test vừa được thêm vào danh bạ. Hãy kết bạn để nhắn tin và trò chuyện miễn phí!",
"notify":1,
"showQuickReply":1,
"showMessageEmpty":1,
"topOutStranger":1,
"showPhotos":1,
"ttl":259200000
},
"id":153564611640,
"type":84
}
]







#################################

[{"id":1,"item":{"avt":"http://s120.avatar.talk.zdn.vn/6/d/2/0/2/120/91d30ded20ee4011167972fb5392fdcd.jpg","count":1,"dpn":"G\u00F3c Th\u01B0 Gi\u00E3n","userId":208680108},"type":68}]

[{"item":{"srcReq":0,"action":0,"actionType":null,"title":null,"desc":null,"listAction":[{"btnLabel":"\u0110\u1ED3ng \u00FD k\u1EBFt b\u1EA1n","reqSrc":90,"actionId":1},{"btnLabel":"Th\u00EAm b\u1EA1n","reqSrc":90,"actionId":2},{"btnLabel":"Xem trang c\u00E1 nh\u00E2n","actionId":4}],"phone":"+84969201192","visibleActionMain":1,"visibleActionRemove":1,"customText":"","msg":"ABC","frAppId":0,"ref":null,"uid":172717391,"dpn":"News","avt":"http://s120.avatar.talk.zdn.vn/5/6/9/e/4/120/865ddf69bc167ca2aa85e3eaee67089d.jpg","ged":0,"time":1512717500336},"id":181890664607,"type":13}]

[{"item":{"srcReq":0,"action":0,"actionType":null,"title":null,"desc":null,"listAction":[{"btnLabel":"\u0110\u1ED3ng \u00FD k\u1EBFt b\u1EA1n","reqSrc":90,"actionId":1},{"btnLabel":"Th\u00EAm b\u1EA1n","reqSrc":90,"actionId":2},{"btnLabel":"Xem trang c\u00E1 nh\u00E2n","actionId":4}],"phone":"+84969201192","visibleActionMain":1,"visibleActionRemove":1,"customText":"","msg":"ABC","frAppId":0,"ref":null,"uid":102262314,"dpn":"Long B\u00E9o","avt":"http://s120.avatar.talk.zdn.vn/1/8/7/6/52/120/aac3368b3e673d2773d1a99d7b72de3e.jpg","ged":0,"time":1512717577600},"id":181890963384,"type":13}]

[{"id":1,"item":{"avt":"http://s120.avatar.talk.zdn.vn/0/c/4/4/116/120/bf4b9ebcd353bced793a36ffc4edfd8a.jpg","count":1,"dpn":"Alo\uD83E\uDD2A Vubl","userId":100051415},"type":68}]

[{"item":{"srcReq":0,"action":0,"actionType":null,"title":null,"desc":null,"listAction":[{"btnLabel":"\u0110\u1ED3ng \u00FD k\u1EBFt b\u1EA1n","reqSrc":90,"actionId":1},{"btnLabel":"Th\u00EAm b\u1EA1n","reqSrc":90,"actionId":2},{"btnLabel":"Xem trang c\u00E1 nh\u00E2n","actionId":4}],"phone":"+84969201192","visibleActionMain":1,"visibleActionRemove":1,"customText":"","msg":"ABC","frAppId":0,"ref":null,"uid":102262314,"dpn":"Long B\u00E9o","avt":"http://s120.avatar.talk.zdn.vn/1/8/7/6/52/120/aac3368b3e673d2773d1a99d7b72de3e.jpg","ged":0,"time":1512717577600},"id":181890963384,"type":13}]
####################################



roi a Tính
123.30.135.75 : 53
thanhpv / 123456



parseMsgNotify










##########################


{"data":[{"type":10,"id":105824,"response":0,"pre":[{"code":1,"has":0,"res":"{\"pkgname\":\"com.epi\"}"}],"body":{"type":2,"duration":30,"del":1,"clickclose":1,"title":"Tin HOT mỗi ngày","href":"http://jp.zaloapp.com/track?ot=sticky_message&oId=105824&url=http%3A%2F%2Fl%2E123c%2Evn%2Fd%2Fbaomoiapps%5Fon%5Fzalotabmore%3F%5Fzsrc%3Dzads&appId=0&uId=863382565400506248&sig=a86398f106ab0d7e212b8ec14c50b0d6&t=1513842080607&cid=1278&aiid=896&type=1&srcIdx=0&_zoa=SsKU25pt65Tk1bDY8VzFV7HbQMPq_bCqRtHsZpFP5xKRKY2Lwz0qr8nQ5gdaY6xWbZ0PW_dmSxQPQWYMlOexofKGQC-ProR6NbacZsuqFrhu25S","thumb":"https://res-zalo.zadn.vn/upload/media/2017/12/19/BM_app_icon_200x200_1513671196112.jpg","desc":"Tải Báo Mới, không lỡ tin hot nào!","params":"","clickcloseDuration":0,"owner":"ZaloBaoMoi","showNotify":0,"startIndex":0,"minItemCount":0,"btnCap":"","banner":null,"distributeId":"stk_1278_896_1513842080579","ClearType":0,"app_referrer":"src=msg-ads&subdata="}}]}


################
sercurity question

{"error_code":2036,"error_message":"Require answer","data":{"question":{"content":"Chọn ra những người bạn trên Zalo?","note":"Đây là lần đầu bạn đăng nhập trên thiết bị này. Vui lòng trả lời câu hỏi sau để xác minh tài khoản của bạn.","questions":[{"title":"Minhlb","thumb":"http:\/\/avatar.talk.zdn.vn\/7\/6\/a\/c\/20\/75\/b57104a9ac8511cd0c11a8a043ed0cf5.jpg","value":"73185613","phone_number":""},{"title":"Đời Không Như Mơ","thumb":"http:\/\/avatar.talk.zdn.vn\/8\/1\/2\/8\/4\/75\/b779822b58e43d4d3b15ce3201baf87a.jpg","value":"119821365","phone_number":""},{"title":"Nhân Mã","thumb":"http:\/\/avatar.talk.zdn.vn\/7\/9\/7\/a\/40\/75\/e1fdfa01029c3ffae7e1b5cafa08cd67.jpg","value":"119815904","phone_number":""},{"title":"Kim Vang Giot Le","thumb":"http:\/\/avatar.talk.zdn.vn\/f\/9\/a\/3\/6\/75\/d3ed936714ef37cfac304452ff19ef99.jpg","value":"120044258","phone_number":""},{"title":"The Thanh","thumb":"http:\/\/avatar.talk.zdn.vn\/a\/9\/1\/2\/1\/75\/2b17315f48345d96c96d206e7fb6583c.jpg","value":"119804082","phone_number":""},{"title":"Nha Nguyen","thumb":"http:\/\/avatar.talk.zdn.vn\/d\/1\/5\/0\/28\/75\/3709f9140b6ab7c24a0f9eb0a887476d.jpg","value":"139022094","phone_number":""},{"title":"Tài khoản bị khóa","thumb":"http:\/\/avatar.talk.zdn.vn\/2\/1\/d\/e\/13\/75\/31213f9493a50acab734a63998e3dff1.jpg","value":"120239554","phone_number":""},{"title":"Phuong Tam","thumb":"http:\/\/avatar.talk.zdn.vn\/1\/2\/e\/e\/6\/75\/02a87b1ea8d3d8ec42f2e840c3ce8b86.jpg","value":"120290803","phone_number":""},{"title":"Cẩm Tiên","thumb":"http:\/\/avatar.talk.zdn.vn\/1\/b\/0\/3\/17\/75\/45b197d0dc1d1c42352611d66e886eb6.jpg","value":"119835385","phone_number":""},{"title":"Quốc Bảo","thumb":"http:\/\/avatar.talk.zdn.vn\/9\/4\/8\/9\/60\/75\/0afeeaea668904d387610cc6cd4fc142.jpg","value":"120234967","phone_number":""},{"title":"Vĩnh Thành Châu","thumb":"http:\/\/avatar.talk.zdn.vn\/c\/5\/e\/c\/51\/75\/74ff14df57f025085f843455c0dfcc9c.jpg","value":"119951960","phone_number":""},{"title":"Tín Trần","thumb":"http:\/\/avatar.talk.zdn.vn\/5\/b\/c\/e\/20\/75\/cf665f59e9cadfea0c9d0c7f30376f58.jpg","value":"73264082","phone_number":""}],"question_type":2,"layout_type":2,"answer_type":2}}}



#######################

Suggest chat free action

{"error_code":0,"error_message":"Successful.","data":{"enable":1,"dataType":2,"index":4,"expiredDuration":32400,"suggestListId":2,"suggestListTitle":"Hẹn hò tụ tập tổng kết năm 2017!🎉🍾🥂","hasMenu":1,"actionTypeMenu":1,"loadMore":0,"suggestItems":[{"typeSuggestChat":4,"typeSuggestAction":1,"dataInfo":{"actionType":"action.open.creategroup","title":"🎅🎄Tạo nhóm chia sẻ những điều thú vị hôm nay!","imgType":1,"gifUrl":"http:\/\/res.zalo.zdn.vn\/upload\/media\/2017\/3\/8\/grouphelp2_1488958379526.gif","imgUrl":"","width":100,"height":100,"data":null}},{"typeSuggestChat":4,"typeSuggestAction":1,"dataInfo":{"actionType":"action.open.poststory","title":"Chia sẻ những điều thú vị hôm nay!","imgType":0,"gifUrl":"","imgUrl":"https:\/\/res-zalo.zadn.vn\/upload\/media\/2017\/12\/19\/icn_suggest_camera_1513671102023.png","width":51,"height":51,"data":null}},{"typeSuggestChat":3,"typeSuggestAction":1,"dataInfo":{"id":0,"groupId":184838,"name":"Nhóm rảnh hàng","desc":"","currentMems":[],"updateMems":[],"type":2,"creatorId":70015339,"ts":0,"senderId":0,"avt":"http:\/\/s120.ava.grp.talk.zdn.vn\/1\/b\/d\/c\/1\/120\/e78647a2a3e36723c5613c789a6f4968.jpg","fullAvt":"http:\/\/ava.grp.talk.zdn.vn\/1\/b\/d\/c\/1\/360\/e78647a2a3e36723c5613c789a6f4968.jpg","subType":1,"maxUsers":500,"adminIds":[],"admins":[],"setting":{"blockName":0,"signAdminMsg":0,"addMemberOnly":0,"setTopicOnly":0},"totalMembers":5,"hasMore":0,"memberIds":[102150950,122411600,73816860,72248198,70015339],"extraInfo":{"media":1},"discoverItemType":2,"isOnline":1}},{"typeSuggestChat":3,"typeSuggestAction":1,"dataInfo":{"id":0,"groupId":21028942,"name":"Social Feature aaa jdjdj jddjj","desc":"","currentMems":[{"id":73816860,"dName":"Hieptyty","avatar":"http:\/\/s120.avatar.talk.zdn.vn\/e\/3\/3\/2\/36\/120\/584bd805c92d899c38a92a009192ade9.jpg","status":null},{"id":70015339,"dName":"Hieptt","avatar":"http:\/\/s120.avatar.talk.zdn.vn\/3\/0\/c\/d\/46\/120\/0e61040745f69fbdf967d67b71265b8a.jpg","status":null},{"id":109892775,"dName":"Hiepazb","avatar":"http:\/\/s120.avatar.talk.zdn.vn\/b\/5\/5\/5\/6\/120\/9265593101fb0a35034d8862ee233958.jpg","status":null}],"updateMems":[],"type":1,"creatorId":73816860,"ts":0,"senderId":0,"avt":"http:\/\/s120.ava.grp.talk.zdn.vn\/default","fullAvt":"http:\/\/s120.ava.grp.talk.zdn.vn\/default","subType":1,"maxUsers":500,"adminIds":[],"admins":[],"setting":{"blockName":0,"signAdminMsg":0,"addMemberOnly":0,"setTopicOnly":0},"totalMembers":3,"hasMore":0,"memberIds":[70015339,109892775,73816860],"extraInfo":{"media":1},"discoverItemType":2,"isOnline":1}},{"typeSuggestChat":3,"typeSuggestAction":1,"dataInfo":{"id":0,"groupId":230612,"name":"Nhóm 7 - Outing G2","desc":"","currentMems":[{"id":101168463,"dName":"Tran Cong Nghia","avatar":"http:\/\/s120.avatar.talk.zdn.vn\/0\/f\/8\/3\/2\/120\/413a32b4b959e8a91476e09ceda810f0.jpg","status":null},{"id":2173830,"dName":"Thanh Thương","avatar":"http:\/\/s120.avatar.talk.zdn.vn\/b\/8\/c\/0\/96\/120\/81aa35ec2879278eb46a783621554aea.jpg","status":null},{"id":70015339,"dName":"Hieptt","avatar":"http:\/\/s120.avatar.talk.zdn.vn\/3\/0\/c\/d\/46\/120\/0e61040745f69fbdf967d67b71265b8a.jpg","status":null}],"updateMems":[],"type":1,"creatorId":100319729,"ts":0,"senderId":0,"avt":"http:\/\/s120.ava.grp.talk.zdn.vn\/default","fullAvt":"http:\/\/s120.ava.grp.talk.zdn.vn\/default","subType":1,"maxUsers":100,"adminIds":[],"admins":[],"setting":{"blockName":0,"signAdminMsg":0,"addMemberOnly":0,"setTopicOnly":0},"totalMembers":3,"hasMore":0,"memberIds":[2173830,70015339,101168463],"extraInfo":{"media":1},"discoverItemType":2,"isOnline":1}},{"typeSuggestChat":3,"typeSuggestAction":1,"dataInfo":{"id":0,"groupId":20027578,"name":"Feature Group","desc":"","currentMems":[],"updateMems":[],"type":1,"creatorId":176994200,"ts":0,"senderId":0,"avt":"http:\/\/s120.ava.grp.talk.zdn.vn\/c\/f\/5\/5\/1\/120\/7bc852295cf0141dee63f3543e58c21d.jpg","fullAvt":"http:\/\/ava.grp.talk.zdn.vn\/c\/f\/5\/5\/1\/360\/7bc852295cf0141dee63f3543e58c21d.jpg","subType":1,"maxUsers":500,"adminIds":[],"admins":[],"setting":{"blockName":0,"signAdminMsg":0,"addMemberOnly":0,"setTopicOnly":0},"totalMembers":6,"hasMore":0,"memberIds":[176994200,70200360,100872758,70015339,108157824,70582909],"extraInfo":{"media":1},"discoverItemType":2,"isOnline":1}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":22,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":100257578,"usr":"t_z08zze9cme9","dpn":"Mỹ Ngọc","avt":"http:\/\/s120.avatar.talk.zdn.vn\/6\/c\/7\/d\/8\/120\/07674fae847cbfd210970c673479a2e8.jpg","phone":"+841646699369","src":1,"ged":1,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":27,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":100621130,"usr":"t_z08zg7096dd","dpn":"Huy Dương Tử","avt":"http:\/\/s120.avatar.talk.zdn.vn\/7\/a\/8\/0\/9\/120\/a162b6530d157ffdaf56f19843717d81.jpg","phone":"+841689089117","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":37,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":186381661,"usr":"t_z08zgnmdn7z","dpn":"Kevin","avt":"http:\/\/s120.avatar.talk.zdn.vn\/2\/4\/1\/7\/8\/120\/4ed6b8c65779b522f882718e32797cbe.jpg","phone":"+841685238001","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":27,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":138033586,"usr":"t_m7e6zncd9n","dpn":"Spinning Ten Rat Dai Va Rat Tay","avt":"http:\/\/s120.avatar.talk.zdn.vn\/b\/2\/8\/1\/3\/120\/b35fdcc5bf4803454e6eeb7b5c377ad1.jpg","phone":"+84997012243","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":51,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":153790483,"usr":"t_m7e08g80d9","dpn":"Minh Nhat","avt":"http:\/\/s120.avatar.talk.zdn.vn\/default","phone":"+84903734873","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":32,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":109611698,"usr":"t_m7e0aci6g7","dpn":"Kentee Truong","avt":"http:\/\/s120.avatar.talk.zdn.vn\/e\/0\/d\/b\/17\/120\/825d2ad23783baf7ab4a3e806fd10f71.jpg","phone":"+84906333127","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":30,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":177499092,"usr":"t_m7ei9bmm80","dpn":"Truong Anh Duy","avt":"http:\/\/s120.avatar.talk.zdn.vn\/default","phone":"+84938785536","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":33,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":114929259,"usr":"t_m7eid897f6","dpn":"Khuê Nguyễn","avt":"http:\/\/s120.avatar.talk.zdn.vn\/f\/e\/e\/a\/24\/120\/e83a00e1e8229d8a39ea0e1238326310.jpg","phone":"+84942800246","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":47,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":126854201,"usr":"t_z07fbmie07i","dpn":"Phạm Đức","avt":"http:\/\/s120.avatar.talk.zdn.vn\/0\/4\/4\/2\/8\/120\/51603f7a0b521f072622e2331ad3d63d.jpg","phone":"+841267711474","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":30,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":69323750,"usr":"t_m7ez78fd9n","dpn":"Tu Anh","avt":"http:\/\/s120.avatar.talk.zdn.vn\/9\/9\/5\/2\/68\/120\/c69337bd8c47ee6e225d4b9e8588103f.jpg","phone":"+84919757587","src":1,"ged":1,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":33,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":3492638,"usr":"t_m7ei9zcmmc","dpn":"Nguyễn Như Hoàn","avt":"http:\/\/s120.avatar.talk.zdn.vn\/a\/8\/f\/f\/191\/120\/7ba2399e3d3bccbddb27e4167c267eeb.jpg","phone":"+84938158812","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":29,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":109854222,"usr":"t_z07fbgdc6en","dpn":"Nguyen Dinh An","avt":"http:\/\/s120.avatar.talk.zdn.vn\/e\/3\/4\/0\/10\/120\/b6f60a29a7ea285110a419df48353f8f.jpg","phone":"+841267377251","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":34,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":104079586,"usr":"t_m7e0d79ci9","dpn":"Kiên Trần","avt":"http:\/\/s120.avatar.talk.zdn.vn\/7\/5\/9\/9\/1\/120\/c5a8cc7731cc5f48eb2aa5e743e01905.jpg","phone":"+84909181353","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":29,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":167085404,"usr":"t_m7e0dzdzmn","dpn":"Cộng Đồng Massage","avt":"http:\/\/s120.avatar.talk.zdn.vn\/b\/3\/1\/0\/2\/120\/8879011d0b9911aef652e48852a006a9.jpg","phone":"+84908801747","src":1,"ged":1,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":18,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":175319380,"usr":"t_m7e0dfz0be","dpn":"Hai","avt":"http:\/\/s120.avatar.talk.zdn.vn\/default","phone":"+84909669950","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":26,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":151325393,"usr":"t_m7e0dm08ge","dpn":"Vũ Ngọc Chiến","avt":"http:\/\/s120.avatar.talk.zdn.vn\/b\/4\/c\/9\/11\/120\/0bb66868e882709852ba0c10d0996de2.jpg","phone":"+84909012430","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":30,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":119670995,"usr":"t_m7e0dm68b9","dpn":"Nguyen Ngoc Duy","avt":"http:\/\/s120.avatar.talk.zdn.vn\/5\/e\/0\/f\/2\/120\/b8484f51ba295ca0e1e35a72aea12284.jpg","phone":"+84909037113","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":30,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":162266113,"usr":"t_m7e099g97f","dpn":"Shineshiao","avt":"http:\/\/s120.avatar.talk.zdn.vn\/6\/a\/e\/a\/4\/120\/d2645c41079a24f0796208a758ec9905.jpg","phone":"+84905096959","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":32,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":159938413,"usr":"t_m7ezm9if6i","dpn":"Taiphan","avt":"http:\/\/s120.avatar.talk.zdn.vn\/8\/c\/a\/3\/10\/120\/7fc763947045d92227bec4c208df23c3.jpg","phone":"+84917673186","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":26,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":139022094,"usr":"t_m7enin8b6e","dpn":"Nha Nguyen","avt":"http:\/\/s120.avatar.talk.zdn.vn\/d\/1\/5\/0\/28\/120\/3709f9140b6ab7c24a0f9eb0a887476d.jpg","phone":"+84947712238","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":22,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":117605293,"usr":"t_m7e0enmizn","dpn":"Mun","avt":"http:\/\/s120.avatar.talk.zdn.vn\/f\/c\/3\/e\/58\/120\/a4f00ebf67c199c19cf3fc4b7947c528.jpg","phone":"+84909948819","src":1,"ged":1,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":24,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":74305851,"usr":"t_z08zi7a7f8e","dpn":"Bùi Xuân Quang","avt":"http:\/\/s120.avatar.talk.zdn.vn\/9\/4\/c\/2\/59\/120\/61138c76ad665ffec9fe7bb4c551ff50.jpg","phone":"+841656184078","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":53,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":135489999,"usr":"t_m7eimic6n8","dpn":"Nguyễn Thị Thùy Trang","avt":"http:\/\/s120.avatar.talk.zdn.vn\/7\/6\/a\/6\/6\/120\/fe478e7c78cb8dbbf9eb520664dc2545.jpg","phone":"+84934030264","src":1,"ged":1,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":29,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":111575243,"usr":"t_m7e0ddg0a6","dpn":"Pham Thi Ngoc Chi","avt":"http:\/\/s120.avatar.talk.zdn.vn\/0\/f\/4\/2\/20\/120\/ddfbb2c3a2b8ab93d7ccd6174beb0b3d.jpg","phone":"+84909551142","src":1,"ged":1,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":27,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":101629599,"usr":"t_m7egc6gnz8","dpn":"Ⓚⓗⓐⓝⓗ","avt":"http:\/\/s120.avatar.talk.zdn.vn\/4\/c\/4\/e\/34\/120\/165814196ee582b8ee8925a1194c82b3.jpg","phone":"+84975153304","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":47,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":188313857,"usr":"t_m7e0d7cbic","dpn":"Huynhdung","avt":"http:\/\/s120.avatar.talk.zdn.vn\/default","phone":"+84909193388","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":26,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":110008960,"usr":"t_m7e0cmenc0","dpn":"Đoàn Thái","avt":"http:\/\/s120.avatar.talk.zdn.vn\/c\/4\/7\/3\/79\/120\/ece672f46ddaa8b5a1c2f6bac9db8d5e.jpg","phone":"+84908020032","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":34,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":102514125,"usr":"t_m7e0db8z68","dpn":"Ân Lê","avt":"http:\/\/s120.avatar.talk.zdn.vn\/9\/8\/9\/6\/20\/120\/8db6582dd37cd5ff7b7fc9c5398f559f.jpg","phone":"+84909436648","src":1,"ged":0,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":28,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":74009988,"usr":"t_m7e0786f60","dpn":"Baby Aries","avt":"http:\/\/s120.avatar.talk.zdn.vn\/6\/9\/3\/6\/209\/120\/cea1abfab42ea4494b090ef1fca6648f.jpg","phone":"+84902943968","src":1,"ged":1,"msg":"","time":0}},{"typeSuggestChat":1,"typeSuggestAction":1,"dataInfo":{"status":"","age":25,"reqSrc":0,"type":0,"group_msg":0,"sgway":0,"srcType":1,"isOnline":1,"uid":201754362,"usr":"t_m7emiceace","dpn":"Codeforfood","avt":"http:\/\/s120.avatar.talk.zdn.vn\/4\/4\/8\/5\/7\/120\/c7c976aec3c1d5c527a63d4d776ee9f7.jpg","phone":"+84981880910","src":1,"ged":0,"msg":"","time":0}}]}}








##############


{"e":0,"last":"1517762382010","msg":[{"text":{"type":"chat.recommended","data":{"to":73816860,"id":897536563331,"cliMsgId":1517762382008,"time":1517762382,"fromU":105080797,"ts":1517762382008,"fromD":"Hồ Quang Hiếu","mcrypt":0,"iv":null,"msg":"","notify":0,"attach":{"title":"Click vào đây để cập nhật những thông tin mới nhất của Hiếu nhé","description":"Click vào đây để cập nhật những thông tin mới nhất của Hiếu nhé","href":"http:\/\/media.zalo.me\/articles?pageId=2033362637102127275","thumb":"http:\/\/w160.link.talk.zdn.vn\/84e08cede3ac0af253bd.jpg","childnumber":0,"action":"recommened.link","params":"{\"mediaTitle\":\"Chào mừng bạn đến với Official Account Hồ Quang Hiếu trên Zalo\",\"count\":\"\",\"stream_icon\":\"\",\"src\":\"media.zalo.me\",\"artist\":\"\",\"type\":0,\"streamUrl\":\"\"}","type":""},"ttl":0,"srcType":5,"paramsExt":{"notifyActionCate":0,
"notifyTxt":"",
"previewTxt":"",
"richContentNotif":{
"notifType":0,
"celsius":"11℃",
"title":"Trời lạnh",
"dateTime":"4/02 Mậu Tuất - Tết Trung Thu",
"description":"Trời lạnh lắm đấy hãy nhớ mặc áo ấm",
"icon":"http://zalo.me/nang.jpg"
}},"topOut":0,"topOutTimeOut":31536000,"topOutImprTimeOut":31536000}},"ts":1517762382008}]}



###########

{"e":0,"last":"1517789898127","msg":[{"text":{"type":"chat.list.action","data":{"to":116505517,"id":897592115476,"cliMsgId":1517788872989,"time":1517788872,"fromU":116505517,"ts":1517788872989,"fromD":"Thời Tiết","mcrypt":0,"iv":null,"msg":"","notify":1,"attach":[{"title":"Chúc ngày mới tốt lành, thời tiết Vĩnh Long hôm nay (05\/02): Trời đẹp, nhiệt độ 21-30℃.","description":"Nhấn vào để xem thời tiết cả nước","href":"https:\/\/news.zing.vn\/thoi-tiet-ngay-52-sai-gon-nang-rao-ha-noi-hung-nang-giua-ngay-ret-post817343.html?utm_source=zalo&utm_medium=broadcast&utm_campaign=weather_vinhlong","thumb":"https:\/\/znews-stc.zdn.vn\/static\/weather\/sunny\/10\/vinhlong.jpg","childnumber":0,"action":"","params":"","type":"l.a.header.only"},{"title":"Hãy để âm nhạc giúp mỗi ngày của bạn đều là những ngày vui","description":"","href":"https:\/\/mp3.zing.vn\/album\/Nhung-Ngay-Vui-Various-Artists\/ZWZCO9CB.html?utm_source=zalo&utm_medium=oa&utm_campaign=ThoiTiet&utm_source=zalo&utm_medium=broadcast&utm_campaign=weather_vinhlong","thumb":"https:\/\/zmp3-photo.zadn.vn\/thumb\/240_240\/covers\/1\/7\/17921379573afbc62033e31caad77840_1504474431.jpg","childnumber":0,"action":"","params":"","type":"l.a.child.full"},{"title":"Thứ 2 cuối cùng trước khi nghỉ Tết, hãy nhớ hoàn thành những việc này","description":"","href":"https:\/\/news.zing.vn\/thu-2-cuoi-cung-truoc-khi-nghi-tet-hay-nho-hoan-thanh-nhung-viec-nay-post817354.html?utm_source=zalo&utm_medium=broadcast&utm_campaign=weather_vinhlong#slideshow","thumb":"https:\/\/znews-photo-td.zadn.vn\/w120\/Uploaded\/oqivovbv\/2018_02_04\/Thumb.jpg","childnumber":0,"action":"","params":"","type":"l.a.child.full"},{"title":"Cung hoàng đạo nào đầu tuần gặp rắc rối tình cảm?","description":"","href":"https:\/\/news.zing.vn\/chuyen-tinh-cam-bien-dong-cua-12-cung-hoang-dao-ngay-dau-tuan-post817357.html?utm_source=zalo&utm_medium=broadcast&utm_campaign=weather_vinhlong#slideshow","thumb":"https:\/\/znews-photo-td.zadn.vn\/w120\/Uploaded\/neg_rtlzofn\/2018_02_04\/Thumb.jpg","childnumber":0,"action":"","params":"","type":"l.a.child.full"}],"ttl":0,"srcType":5,"paramsExt":{"notifyTxt":"Chúc ngày mới tốt lành, thời tiết Vĩnh Long hôm nay (05\/02): Trời đẹp, nhiệt độ 21-30℃.","previewTxt":"Chúc ngày mới tốt lành, thời tiết Vĩnh Long hôm nay (05\/02): Trời đẹp, nhiệt độ 21-30℃.","notifyActionCate":1},"topOut":1,"topOutTimeOut":31536000,"topOutImprTimeOut":31536000}},"ts":1517788872989},{"text":{"type":"chat.list.action","data":{"to":116505517,"id":897592115476,"cliMsgId":1517788872989,"time":1517788872,"fromU":116505517,"ts":1517788872989,"fromD":"Thời Tiết","mcrypt":0,"iv":null,"msg":"","notify":1,"attach":[{"title":"Chúc ngày mới tốt lành, thời tiết Vĩnh Long hôm nay (05\/02): Trời đẹp, nhiệt độ 21-30℃.","description":"Nhấn vào để xem thời tiết cả nước","href":"https:\/\/news.zing.vn\/thoi-tiet-ngay-52-sai-gon-nang-rao-ha-noi-hung-nang-giua-ngay-ret-post817343.html?utm_source=zalo&utm_medium=broadcast&utm_campaign=weather_vinhlong","thumb":"https:\/\/znews-stc.zdn.vn\/static\/weather\/sunny\/10\/vinhlong.jpg","childnumber":0,"action":"","params":"","type":"l.a.header.only"},{"title":"Hãy để âm nhạc giúp mỗi ngày của bạn đều là những ngày vui","description":"","href":"https:\/\/mp3.zing.vn\/album\/Nhung-Ngay-Vui-Various-Artists\/ZWZCO9CB.html?utm_source=zalo&utm_medium=oa&utm_campaign=ThoiTiet&utm_source=zalo&utm_medium=broadcast&utm_campaign=weather_vinhlong","thumb":"https:\/\/zmp3-photo.zadn.vn\/thumb\/240_240\/covers\/1\/7\/17921379573afbc62033e31caad77840_1504474431.jpg","childnumber":0,"action":"","params":"","type":"l.a.child.full"},{"title":"Thứ 2 cuối cùng trước khi nghỉ Tết, hãy nhớ hoàn thành những việc này","description":"","href":"https:\/\/news.zing.vn\/thu-2-cuoi-cung-truoc-khi-nghi-tet-hay-nho-hoan-thanh-nhung-viec-nay-post817354.html?utm_source=zalo&utm_medium=broadcast&utm_campaign=weather_vinhlong#slideshow","thumb":"https:\/\/znews-photo-td.zadn.vn\/w120\/Uploaded\/oqivovbv\/2018_02_04\/Thumb.jpg","childnumber":0,"action":"","params":"","type":"l.a.child.full"},{"title":"Cung hoàng đạo nào đầu tuần gặp rắc rối tình cảm?","description":"","href":"https:\/\/news.zing.vn\/chuyen-tinh-cam-bien-dong-cua-12-cung-hoang-dao-ngay-dau-tuan-post817357.html?utm_source=zalo&utm_medium=broadcast&utm_campaign=weather_vinhlong#slideshow","thumb":"https:\/\/znews-photo-td.zadn.vn\/w120\/Uploaded\/neg_rtlzofn\/2018_02_04\/Thumb.jpg","childnumber":0,"action":"","params":"","type":"l.a.child.full"}],"ttl":0,"srcType":5,"paramsExt":{"notifyTxt":"Chúc ngày mới tốt lành, thời tiết Vĩnh Long hôm nay (05\/02): Trời đẹp, nhiệt độ 21-30℃.","previewTxt":"Chúc ngày mới tốt lành, thời tiết Vĩnh Long hôm nay (05\/02): Trời đẹp, nhiệt độ 21-30℃.","notifyActionCate":1},"topOut":1,"topOutTimeOut":31536000,"topOutImprTimeOut":31536000}},"ts":1517788872989}]}


{"e":0,"last":"1517789898127","msg":[{"text":{"type":"chat.list.action","data":{"to":116505517,"id":897592115476,"cliMsgId":1517788872989,"time":1517788872,"fromU":116505517,"ts":1517788872989,"fromD":"Thời Tiết","mcrypt":0,"iv":null,"msg":"","notify":1,"attach":[{"title":"Chúc ngày mới tốt lành, thời tiết Vĩnh Long hôm nay (05\/02): Trời đẹp, nhiệt độ 21-30℃.","description":"Nhấn vào để xem thời tiết cả nước","href":"https:\/\/news.zing.vn\/thoi-tiet-ngay-52-sai-gon-nang-rao-ha-noi-hung-nang-giua-ngay-ret-post817343.html?utm_source=zalo&utm_medium=broadcast&utm_campaign=weather_vinhlong","thumb":"https:\/\/znews-stc.zdn.vn\/static\/weather\/sunny\/10\/vinhlong.jpg","childnumber":0,"action":"","params":"","type":"l.a.header.only"},{"title":"Hãy để âm nhạc giúp mỗi ngày của bạn đều là những ngày vui","description":"","href":"https:\/\/mp3.zing.vn\/album\/Nhung-Ngay-Vui-Various-Artists\/ZWZCO9CB.html?utm_source=zalo&utm_medium=oa&utm_campaign=ThoiTiet&utm_source=zalo&utm_medium=broadcast&utm_campaign=weather_vinhlong","thumb":"https:\/\/zmp3-photo.zadn.vn\/thumb\/240_240\/covers\/1\/7\/17921379573afbc62033e31caad77840_1504474431.jpg","childnumber":0,"action":"","params":"","type":"l.a.child.full"},{"title":"Thứ 2 cuối cùng trước khi nghỉ Tết, hãy nhớ hoàn thành những việc này","description":"","href":"https:\/\/news.zing.vn\/thu-2-cuoi-cung-truoc-khi-nghi-tet-hay-nho-hoan-thanh-nhung-viec-nay-post817354.html?utm_source=zalo&utm_medium=broadcast&utm_campaign=weather_vinhlong#slideshow","thumb":"https:\/\/znews-photo-td.zadn.vn\/w120\/Uploaded\/oqivovbv\/2018_02_04\/Thumb.jpg","childnumber":0,"action":"","params":"","type":"l.a.child.full"},{"title":"Cung hoàng đạo nào đầu tuần gặp rắc rối tình cảm?","description":"","href":"https:\/\/news.zing.vn\/chuyen-tinh-cam-bien-dong-cua-12-cung-hoang-dao-ngay-dau-tuan-post817357.html?utm_source=zalo&utm_medium=broadcast&utm_campaign=weather_vinhlong#slideshow","thumb":"https:\/\/znews-photo-td.zadn.vn\/w120\/Uploaded\/neg_rtlzofn\/2018_02_04\/Thumb.jpg","childnumber":0,"action":"","params":"","type":"l.a.child.full"}],"ttl":0,"srcType":5,"paramsExt":{"notifyTxt":"Chúc ngày mới tốt lành, thời tiết Vĩnh Long hôm nay (05\/02): Trời đẹp, nhiệt độ 21-30℃.","previewTxt":"Chúc ngày mới tốt lành, thời tiết Vĩnh Long hôm nay (05\/02): Trời đẹp, nhiệt độ 21-30℃.","notifyActionCate":1},"topOut":1,"topOutTimeOut":31536000,"topOutImprTimeOut":31536000}},"ts":1517788872989}]}


"richContentNotif":{
"notifType":0,
"celsius":"11℃",
"title":"Trời lạnh",
"dateTime":"4/02 Mậu Tuất - Tết Trung Thu",
"description":"Trời lạnh lắm đấy hãy nhớ mặc áo ấm",
"icon":"https://ssl.gstatic.com/onebox/weather/64/partly_cloudy.png"
}


https://ssl.gstatic.com/onebox/weather/64/partly_cloudy.png



{"e":0,"last":"1517789898127","msg":[{"text":{"type":"chat.list.action","data":{"to":116505517,"id":897592115476,"cliMsgId":1517788872989,"time":1517788872,"fromU":116505517,"ts":1517788872989,"fromD":"Thời Tiết","mcrypt":0,"iv":null,"msg":"","notify":1,"attach":[{"title":"Chúc ngày mới tốt lành, thời tiết Vĩnh Long hôm nay (05\/02): Trời đẹp, nhiệt độ 21-30℃.","description":"Nhấn vào để xem thời tiết cả nước","href":"https:\/\/news.zing.vn\/thoi-tiet-ngay-52-sai-gon-nang-rao-ha-noi-hung-nang-giua-ngay-ret-post817343.html?utm_source=zalo&utm_medium=broadcast&utm_campaign=weather_vinhlong","thumb":"https:\/\/znews-stc.zdn.vn\/static\/weather\/sunny\/10\/vinhlong.jpg","childnumber":0,"action":"","params":"","type":"l.a.header.only"},{"title":"Hãy để âm nhạc giúp mỗi ngày của bạn đều là những ngày vui","description":"","href":"https:\/\/mp3.zing.vn\/album\/Nhung-Ngay-Vui-Various-Artists\/ZWZCO9CB.html?utm_source=zalo&utm_medium=oa&utm_campaign=ThoiTiet&utm_source=zalo&utm_medium=broadcast&utm_campaign=weather_vinhlong","thumb":"https:\/\/zmp3-photo.zadn.vn\/thumb\/240_240\/covers\/1\/7\/17921379573afbc62033e31caad77840_1504474431.jpg","childnumber":0,"action":"","params":"","type":"l.a.child.full"},{"title":"Thứ 2 cuối cùng trước khi nghỉ Tết, hãy nhớ hoàn thành những việc này","description":"","href":"https:\/\/news.zing.vn\/thu-2-cuoi-cung-truoc-khi-nghi-tet-hay-nho-hoan-thanh-nhung-viec-nay-post817354.html?utm_source=zalo&utm_medium=broadcast&utm_campaign=weather_vinhlong#slideshow","thumb":"https:\/\/znews-photo-td.zadn.vn\/w120\/Uploaded\/oqivovbv\/2018_02_04\/Thumb.jpg","childnumber":0,"action":"","params":"","type":"l.a.child.full"},{"title":"Cung hoàng đạo nào đầu tuần gặp rắc rối tình cảm?","description":"","href":"https:\/\/news.zing.vn\/chuyen-tinh-cam-bien-dong-cua-12-cung-hoang-dao-ngay-dau-tuan-post817357.html?utm_source=zalo&utm_medium=broadcast&utm_campaign=weather_vinhlong#slideshow","thumb":"https:\/\/znews-photo-td.zadn.vn\/w120\/Uploaded\/neg_rtlzofn\/2018_02_04\/Thumb.jpg","childnumber":0,"action":"","params":"","type":"l.a.child.full"}],"ttl":0,"srcType":5,"paramsExt":{"notifyTxt":"Chúc ngày mới tốt lành, thời tiết Vĩnh Long hôm nay (05\/02): Trời đẹp, nhiệt độ 21-30℃.","previewTxt":"Chúc ngày mới tốt lành, thời tiết Vĩnh Long hôm nay (05\/02): Trời đẹp, nhiệt độ 21-30℃.","notifyActionCate":1, "richContentNotif":{
"notifType":0,
"celsius":"11",
"title":"Trời lạnh",
"dateTime":"4/02 Mậu Tuất - Tết Trung Thu",
"description":"Trời lạnh lắm đấy hãy nhớ mặc áo ấm",
"icon":"https://ssl.gstatic.com/onebox/weather/64/partly_cloudy.png"
}},"topOut":1,"topOutTimeOut":31536000,"topOutImprTimeOut":31536000}},"ts":1517788872989}]}




Bắn noti thời tiết


{"e":0,"last":"1517789898127","msg":[{"text":{"type":"chat.list.action","data":{"to":116505517,"id":897592115476,"cliMsgId":1520911830001,"time":1517788872,"fromU":116505517,"ts":1520911830001,"fromD":"Thời Tiết","mcrypt":0,"iv":null,"msg":"","notify":1,"attach":[{"title":"Chúc ngày mới tốt lành, thời tiết Vĩnh Long hôm nay (05\/02): Trời đẹp, nhiệt độ 21-30℃.","description":"Nhấn vào để xem thời tiết cả nước","href":"https:\/\/news.zing.vn\/thoi-tiet-ngay-52-sai-gon-nang-rao-ha-noi-hung-nang-giua-ngay-ret-post817343.html?utm_source=zalo&utm_medium=broadcast&utm_campaign=weather_vinhlong","thumb":"https:\/\/znews-stc.zdn.vn\/static\/weather\/sunny\/10\/vinhlong.jpg","childnumber":0,"action":"","params":"","type":"l.a.header.only"},{"title":"Hãy để âm nhạc giúp mỗi ngày của bạn đều là những ngày vui","description":"","href":"https:\/\/mp3.zing.vn\/album\/Nhung-Ngay-Vui-Various-Artists\/ZWZCO9CB.html?utm_source=zalo&utm_medium=oa&utm_campaign=ThoiTiet&utm_source=zalo&utm_medium=broadcast&utm_campaign=weather_vinhlong","thumb":"https:\/\/zmp3-photo.zadn.vn\/thumb\/240_240\/covers\/1\/7\/17921379573afbc62033e31caad77840_1504474431.jpg","childnumber":0,"action":"","params":"","type":"l.a.child.full"},{"title":"Thứ 2 cuối cùng trước khi nghỉ Tết, hãy nhớ hoàn thành những việc này","description":"","href":"https:\/\/news.zing.vn\/thu-2-cuoi-cung-truoc-khi-nghi-tet-hay-nho-hoan-thanh-nhung-viec-nay-post817354.html?utm_source=zalo&utm_medium=broadcast&utm_campaign=weather_vinhlong#slideshow","thumb":"https:\/\/znews-photo-td.zadn.vn\/w120\/Uploaded\/oqivovbv\/2018_02_04\/Thumb.jpg","childnumber":0,"action":"","params":"","type":"l.a.child.full"},{"title":"Cung hoàng đạo nào đầu tuần gặp rắc rối tình cảm?","description":"","href":"https:\/\/news.zing.vn\/chuyen-tinh-cam-bien-dong-cua-12-cung-hoang-dao-ngay-dau-tuan-post817357.html?utm_source=zalo&utm_medium=broadcast&utm_campaign=weather_vinhlong#slideshow","thumb":"https:\/\/znews-photo-td.zadn.vn\/w120\/Uploaded\/neg_rtlzofn\/2018_02_04\/Thumb.jpg","childnumber":0,"action":"","params":"","type":"l.a.child.full"}],"ttl":0,"srcType":5,"paramsExt":{"notifyTxt":"Chúc ngày mới tốt lành, thời tiết Vĩnh Long hôm nay (05\/02): Trời đẹp, nhiệt độ 21-30℃.","previewTxt":"Chúc ngày mới tốt lành, thời tiết Vĩnh Long hôm nay (05\/02): Trời đẹp, nhiệt độ 21-30℃.","notifyActionCate":1, "richContentNotif":{
"notifType":0,
"celsius":"11",
"title":"Trời lạnh",
"dateTime":"4/02 Mậu Tuất - Tết Trung Thu",
"description":"Trời lạnh lắm đấy hãy nhớ mặc áo ấm",
"icon":"https://ssl.gstatic.com/onebox/weather/64/partly_cloudy.png"
}},"topOut":1,"topOutTimeOut":31536000,"topOutImprTimeOut":31536000}},"ts":1520911830001}]}






###########


installIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON,
                    Intent.ShortcutIconResource.fromContext(getActivity(), R.drawable.icon));




//                    getZaloViewManager().showZaloView(PolicyZView.class, null, TRANSLATION_HORIZONTAL_WITH_FADE_IN, true);
                    Utils.openProfile("177248527", getZaloViewManager());







############


applicationVariants.all { variant ->
        println "variant:" + variant.getBuildType().getName() // this is the value!

        //Append BUILD_ID to mapping file's name to distinguish amongst different builds
        if (variant.getBuildType().isMinifyEnabled()) {
            variant.assemble.doLast {
                def buildIdName = variant.getBuildType().getBuildConfigFields().get("BUILD_ID").value.replace("\"", "")

                copy {
                    from variant.mappingFile
                    into "${rootDir}/app/build/outputs/mapping/full/release"
                    rename { String fileName ->
                        "mapping-${buildIdName}.txt"
                    }
                }
            }
        }

    }




	
	##################
	
	
	
{"e":0,
"last":"1532516661933",
"msg":[
		{"text":
			{"type":"webchat","data":{"to":70015339,"id":1061768992572,"cliMsgId":1532516661192,"time":1532516664,"fromU":73816860,"ts":1532516661935,"fromD":"Hiep Tt","mcrypt":1,"iv":"CHmoBg6Gsf\/97MQzqOexSA==","msg":"r7BHHbZCYkfR8\/Of5poKIQ==","notify":1,
				"attach":{
							"title":"Đoàn Thái tạo ghi chú mới Hello aaaaa",
							"description":"",
							"href":"",
							"thumb":"",
							"childnumber":0,
							"action":"msginfo.actionlist",
							"params":"{\"actions\":[{\"actionType\":\"action.open.grouptopic.detail\",\"actionData\":\"{\\\"topicId\\\":3942384}\",\"actionColor\":-16538118,\"actionLabel\":\"Xem\",\"actionLabelv2\":{\"en\":\"View\",\"vi\":\"Xem\",\"my\":\"View\"}}],\"highLights\":[{\"uid\":110008960,\"pos\":0,\"len\":9}],\"totalUpdateMem\":1,\"iconUrl\":\"https:\/\/res-zalo.zadn.vn\/upload\/media\/2018\/4\/20\/chat_tip_icon_note_1524212909230.png\",\"avatarUrl\":[],\"msg\":{\"en\":\"%1$s created a new note %2$s\",\"vi\":\"%1$s t\\u1EA1o ghi ch\\u00FA m\\u1EDBi %2$s\",\"my\":\"%1$s created a new note %2$s\"},\"highLightsV2\":[{\"uid\":110008960,\"dpn\":\"\\u0110o\\u00E0n Th\\u00E1i\",\"type\":0,\"ts\":0},{\"uid\":0,\"dpn\":\"Hello aaaaa\",\"type\":0,\"ts\":0}]}",
							"type":""
						},
			"ttl":0,
			"srcType":3,
			"layoutType":1}},
		
"ts":1532516661929}]}




	