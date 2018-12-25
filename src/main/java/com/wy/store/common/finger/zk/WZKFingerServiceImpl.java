package com.wy.store.common.finger.zk;

import java.util.ArrayList;
import java.util.List;

import com.wy.store.common.finger.WFingerService;
import com.wy.store.common.finger.WFingerServiceEnrollListener;
import com.wy.store.common.finger.WFingerServiceListener;
import com.wy.store.common.finger.WFingerServiceLoadListener;
import com.wy.store.db.dao.UserFingerDao;
import com.wy.store.db.dao.impl.UserFingerDaoImpl;
import com.wy.store.domain.UserFinger;
import com.zkteco.biometric.FingerprintSensorErrorCode;
import com.zkteco.biometric.FingerprintSensorEx;

public class WZKFingerServiceImpl implements WFingerService {

	public static final int ENROLL_COUNT = 3;

	public ZKDeviceStatus status;

	// 控制线程
	private boolean mbStop = true;
	private long mhDevice = 0;
	private long mhDB = 0;
	// 检查假指纹

	private boolean isFakeFunOn = false;
	private int enroll_idx = 0;

	//the width of fingerprint image
	int fpWidth = 0;
	//the height of fingerprint image
	int fpHeight = 0;
	//for verify test
	private byte[] lastRegTemp = new byte[2048];
	// the length of lastRegTemp
	private int cbRegTemp = 0;
	// pre-register template
	private byte[][] regtemparray = new byte[3][2048];

	private byte[] imgbuf = null;
	private byte[] template = new byte[2048];
	private int[] templateLen = new int[1];

	//工作线程
	 private WorkThread workThread = null;
	//这里可以设置一下
	private int iFid = 1;


	private List<WFingerServiceEnrollListener> enrollListeners = new ArrayList<>();
	private List<WFingerServiceLoadListener> loadListeners = new ArrayList<>();


	private List<WFingerServiceListener> listeners = new ArrayList<>();
	@Override
	public int openDevice() {
		// TODO Auto-generated method stub
		int ret = FingerprintSensorErrorCode.ZKFP_ERR_OK;
		
		
		// 初始化设备
		try {
			if (FingerprintSensorErrorCode.ZKFP_ERR_OK != FingerprintSensorEx.Init()) {

				// 失败
				connectDeviceError("连接失败");

			}
			
		} catch (UnsatisfiedLinkError e) {
			// TODO: handle exception
			
			connectDeviceError("没有安装对应的驱动");
			
			return -1;
		}
		
		
		// 获取当前连接设备的个数
		ret = getDeviceCount();

		if (ret < 0) {// 没有设备连接

			freeSensor();

			// ---- 需要一个提示
			connectDeviceError("未发现设备，请将指纹仪连接到电脑");

			return -1;
		}

		// 连接到第一个设备
		mhDevice = FingerprintSensorEx.OpenDevice(0);

		if (!isDeviceConnected()) {// 打开设备失败

			connectDeviceError("打开设备失败");

			freeSensor();
			return -1;
		}

		mhDB = FingerprintSensorEx.DBInit();

		if (!isDBConnected()) {
			connectDeviceError("打开设备失败");

			freeSensor();
			
			return -1;
		}
		// For ISO 1 /Ansi 0
		int nFmt = 1;
		// 设置编码，默认使用iso
		FingerprintSensorEx.DBSetParameter(mhDB, 5010, nFmt);

		byte[] paramValue = new byte[4];
		int[] size = new int[1];
		//GetFakeOn
		//size[0] = 4;
		//FingerprintSensorEx.GetParameters(mhDevice, 2002, paramValue, size);
		//nFakeFunOn = byteArrayToInt(paramValue);
		size[0] = 4;
		FingerprintSensorEx.GetParameters(mhDevice, 1, paramValue, size);
		fpWidth = byteArrayToInt(paramValue);
		size[0] = 4;
		FingerprintSensorEx.GetParameters(mhDevice, 2, paramValue, size);
		fpHeight = byteArrayToInt(paramValue);
		//width = fingerprintSensor.getImageWidth();
		//height = fingerprintSensor.getImageHeight();
		imgbuf = new byte[fpWidth*fpHeight];
		//sy
		System.out.println("open success");
		// 获取一些图片的参数，到这一步就是启动成功了
		mbStop = false;
		workThread = new WorkThread();
	    workThread.start();// 线程启动


	    connectDeviceSuccess("已连接");
	    
		return FingerprintSensorErrorCode.ZKFP_ERR_OK;

	}

	@Override
	public void addFingerDataToDevice() {
		// TODO Auto-generated method stub
		
		long time = System.currentTimeMillis();
		clearAllFingers();

		UserFingerDao dao = new UserFingerDaoImpl();
		
		List<UserFinger> list = dao.getAllUserFingers();
		
		for(UserFinger finger : list) {
			
			addFinger(finger);
		}
		
//		System.out.println("add finger spend time = " + (System.currentTimeMillis() - time));
	
	}
	
	private void addFinger(UserFinger finger) {
		
//		byte[] fpTemplate = new byte[2048];
//		int[] sizeFPTemp = new int[1];
//		sizeFPTemp[0] = 2048;
		
	
		int	ret = FingerprintSensorEx.DBAdd( mhDB, (int)finger.getFingerId(), finger.getFingerBlob());
		
		if(0 == ret) {
			System.out.println("finger " + finger.getFingerId()  + " " + finger.getFingerBlob().length + " success");
			
		}else {
			System.out.println("finger " + finger.getFingerId() + " error");

		}
		
	}
	
	private void clearAllFingers() {
		
		FingerprintSensorEx.DBClear(mhDB);

	}

	@Override
	public void closeDevice() {
		freeSensor();
	}

	@Override
	public int getDeviceCount() {
		return FingerprintSensorEx.GetDeviceCount();
	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return isDeviceConnected() && isDBConnected();
	}

	@Override
	public boolean isDeviceConnected() {
		// TODO Auto-generated method stub
		return mhDevice != 0;
	}

	@Override
	public boolean isDBConnected() {
		// TODO Auto-generated method stub
		return mhDB != 0;
	}

	@Override
	public String identifyFinger() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(WFingerServiceListener listener) {

		listeners.add(listener);
	}

	@Override
	public void unregister(WFingerServiceListener listener) {

		listeners.remove(listener);
	}

	@Override
	public void receivedFinger() {
		// TODO Auto-generated method stub
		this.status = ZKDeviceStatus.IDENTIFY;
	}

	@Override
	public void enrollFinger() {
		// TODO Auto-generated method stub

		//还是需要进行一些判断，譬如是否连接了，是否安装了驱动之类的
		enroll_idx = 0;
		this.status = ZKDeviceStatus.ENROLL;
		
		System.out.println("-----------------------------enroll finger-----------------------");
	}

	/**
	 * 关闭所有的device
	 */
	private void freeSensor() {
		
		mbStop = true;
		
		System.err.println("free sensor = " + mbStop);

		try { // wait for thread stopping
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (0 != mhDB) {

			FingerprintSensorEx.DBFree(mhDB);
		}

		if (0 != mhDevice) {
			FingerprintSensorEx.CloseDevice(mhDevice);
		}

		FingerprintSensorEx.Terminate();
	}

	public void resetDeviceStatus() {
		
	}
	
	private void OnExtractOK(byte[] template, int len) {
		if (this.status == ZKDeviceStatus.ENROLL) {
			enrollExecute(template);
		} else if(this.status == ZKDeviceStatus.IDENTIFY){
			
			identifyExecute(template);
			
		}
	}
	
	private void identifyExecute(byte[] template) {
		int[] fid = new int[1];
		int[] score = new int[1];
		int ret = FingerprintSensorEx.DBIdentify(mhDB, template, fid, score);
		if (ret == 0) {
			//验证成功了
			for(WFingerServiceListener listener : listeners) {
				
				listener.onFingerReceived(fid[0], score[0]);
			}
			
			System.out.println("Identify succ, fid=" + fid[0] + ",score=" + score[0]);
//			textArea.setText("Identify succ, fid=" + fid[0] + ",score=" + score[0]);
			
		} else {
			//验证失败
			System.out.println("Identify fail, errcode=" + ret);

//			textArea.setText("Identify fail, errcode=" + ret);
		}
	}

	private void enrollExecute(byte[] template) {
		int[] fid = new int[1];
		int[] score = new int[1];
		//检查是否已经登记，也就是已经存在了
		System.out.println("enroll exectute");
		int ret = FingerprintSensorEx.DBIdentify(mhDB, template, fid, score);
		if (ret == 0) {

//				指纹已经存在--需要进行一些操作
			for(WFingerServiceEnrollListener listener : enrollListeners) {
				listener.onEnrollReceivedError("当前指纹已经存在");
			}
			
			this.status = ZKDeviceStatus.ENROLL;
			enroll_idx = 0;
			return;
		}
		if (enroll_idx > 0 && FingerprintSensorEx.DBMatch(mhDB, regtemparray[enroll_idx - 1], template) <= 0) {
//				textArea.setText("please press the same finger 3 times for the enrollment");
			//如果已经录入了，对比一下指纹是否一致，如果不一致，就需要进行一些提示了
			for(WFingerServiceEnrollListener listener : enrollListeners) {
				listener.onEnrollReceivedError("请录入相同的指纹");
			}
			return;
		}
		System.arraycopy(template, 0, regtemparray[enroll_idx], 0, 2048);
		enroll_idx++;
		if (enroll_idx == 3) {
			int[] _retLen = new int[1];
			_retLen[0] = 2048;
			byte[] regTemp = new byte[_retLen[0]];

			UserFingerDao fingerDao = new UserFingerDaoImpl();
			
			long nextID = fingerDao.getNextId();
			iFid = (int)nextID;
			//获取相应的fid，从数据库中获取，
			//有时间可以处理一下业务，自己处理id
			if (0 == (ret = FingerprintSensorEx.DBMerge(mhDB, regtemparray[0], regtemparray[1], regtemparray[2],
					regTemp, _retLen)) && 0 == (ret = FingerprintSensorEx.DBAdd(mhDB, iFid, regTemp))) {
				cbRegTemp = _retLen[0];
				System.arraycopy(regTemp, 0, lastRegTemp, 0, cbRegTemp);
				// Base64 Template
//					录入成功
//					textArea.setText("enroll succ");
				System.out.println("enroll succ");
				for(WFingerServiceEnrollListener listener : enrollListeners) {
					listener.onEnrollSuccess(iFid, regTemp);
				}
			} else {
				
				//录入失败
//					textArea.setText("enroll fail, error code=" + ret);
				System.out.println("enroll fail = " + ret);
				for(WFingerServiceEnrollListener listener : enrollListeners) {
					listener.onEnrollReceivedError("指纹录入失败,请重置后重新录入");;
				}
				
			}
			
			//重置状态
			this.status = ZKDeviceStatus.OPEN;
		} else {
			//继续录入，提示用户还剩余几次
			for(WFingerServiceEnrollListener listener : enrollListeners) {
				listener.onEnrollReceived( enroll_idx);;
			}
			System.out.println("You need to press the " + (3 - enroll_idx) + " times fingerprint");

//				textArea.setText("You need to press the " + (3 - enroll_idx) + " times fingerprint");
		}
	}

	private class WorkThread extends Thread {
		@Override
		public void run() {
			super.run();
			int ret = 0;
			while (!mbStop) {
				templateLen[0] = 2048;
//				System.out.println("--------------1--------------/-WorkThread run " + status);

				if (0 == (ret = FingerprintSensorEx.AcquireFingerprint(mhDevice, imgbuf, template, templateLen))) {
					if (isFakeFunOn) {
						byte[] paramValue = new byte[4];
						int[] size = new int[1];
						size[0] = 4;
						int nFakeStatus = 0;
						// GetFakeStatus
						ret = FingerprintSensorEx.GetParameters(mhDevice, 2004, paramValue, size);
						nFakeStatus = byteArrayToInt(paramValue);
						System.out.println("ret = " + ret + ",nFakeStatus=" + nFakeStatus);
						if (0 == ret && (byte) (nFakeStatus & 31) != 31) {
							return;
						}
					}
//					System.out.println("-----------2------------------WorkThread run " + status);

					OnExtractOK(template, templateLen[0]);
				}else {
//					System.out.println("-----------2------------------WorkThread run no finger");

				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}

		private void runOnUiThread(Runnable runnable) {
			// TODO Auto-generated method stub

		}
	}

	public static byte[] changeByte(int data) {
		return intToByteArray(data);
	}

	public static byte[] intToByteArray(final int number) {
		byte[] abyte = new byte[4];
		// "&" 与（AND），对两个整型操作数中对应位执行布尔代数，两个位都为1时输出1，否则0。
		abyte[0] = (byte) (0xff & number);
		// ">>"右移位，若为正数则高位补0，若为负数则高位补1
		abyte[1] = (byte) ((0xff00 & number) >> 8);
		abyte[2] = (byte) ((0xff0000 & number) >> 16);
		abyte[3] = (byte) ((0xff000000 & number) >> 24);
		return abyte;
	}

	public static int byteArrayToInt(byte[] bytes) {
		int number = bytes[0] & 0xFF;
		// "|="按位或赋值。
		number |= ((bytes[1] << 8) & 0xFF00);
		number |= ((bytes[2] << 16) & 0xFF0000);
		number |= ((bytes[3] << 24) & 0xFF000000);
		return number;
	}

	@Override
	public void register(WFingerServiceEnrollListener listener) {
		// TODO Auto-generated method stub
	
		enrollListeners.add(listener);
	}

	@Override
	public void unregister(WFingerServiceEnrollListener listener) {
		// TODO Auto-generated method stub
		enrollListeners.remove(listener);
	}
	
	
	@Override
	public void registerConnectListener(WFingerServiceLoadListener listener) {
		// TODO Auto-generated method stub
		loadListeners.add(listener);
	}
	
	@Override
	public void unregisterConnectListener(WFingerServiceLoadListener listener) {
		loadListeners.remove(listener);
		
	}
//
	public void connectDeviceError(String error) {
		
		for(WFingerServiceLoadListener listener : loadListeners) {
			
			listener.onDeviceConnectFailed(error);
		}
	}
	
	public void connectDeviceSuccess(String msg) {
		
		for(WFingerServiceLoadListener listener : loadListeners) {
			
			listener.onDeviceConnectFailed(msg);
		}
	}
}
