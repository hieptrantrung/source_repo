@Override
  public IBinder onBind(Intent intent) {
		return binder;
	}
	
	private final IMultiplier.Stub binder = new IMultiplier.Stub() {
		
		@Override
		public void multiply(final int a, final int b) throws RemoteException {
			final int result = a*b;
			Handler handler = new Handler(Looper.getMainLooper());
			handler.post(new Runnable(){

				@Override
				public void run() {
					Toast.makeText(getApplicationContext(), a+"*"+b+"= "+result, Toast.LENGTH_SHORT).show();
					
				}
				
			});
			
		}
	};