package com.wy.store.app;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class TestFxJava  {
	
	
	public static void main(String[] args) {
//		
//		 ApplicationContext context = new ClassPathXmlApplicationContext("/springconfig.xml");
////		
//		 UserService userService = (UserService)context.getBean("userService");
////		 
////		 UserDao dingLei = (UserDao) context.getBean("userDao");
////	        
//		 System.out.println( userService.toString() + " " + userService.userDao );  
//		 
//			launch(args);
			
		
//		execute();
			
		
		
			
	}
	
	public static void execute() {
		

		Task task = getTask();
		

		//进行一下强转
		if(task instanceof MyObservable) {
			MyObservable myTask = (MyObservable)task;
			
			beforeLoadData();

			myTask.subscribe(new Listener() {
				
				@Override
				public void cc() {
					//taskHelper.exectur
					
				}
			});
			
		}else {
			
			//....
		}
		
	
		
	}
	
	public static void callback() {
		
	}
	
	public static void beforeLoadData() {
		
	}
	
	public static MyObservable<Integer> getTask() {
		
		return new MyObservable<>(Observable.just(1));
	}
	
	public static abstract class Task {
		
		public abstract Object exectue();
	}
	public  interface Listener {
		
		void cc();
	}
	
	
	public static class MyObservable<T> extends Task {

		Observable<T> obserable;
		
		Object object;
		
		public MyObservable(Observable<T> obserable) {
			super();
			
			this.obserable = obserable;
			
			
		}

		public void subscribe(Listener cc) {
			
			this.obserable.subscribe(new Consumer<Object>() {

				@Override
				public void accept(Object t) throws Exception {
					// TODO Auto-generated method stub
					object = t;
					
					cc.cc();
				}
			});
		}

		public Observable<T> getObserable() {
			return obserable;
		}


		public void setObserable(Observable<T> obserable) {
			this.obserable = obserable;
		}


		@Override
		public Object exectue() {
			// TODO Auto-generated method stub
			
			return object;
		}

		
	}
	

}
