package com.wy.store.common.finger.zk;

import java.util.ArrayList;
import java.util.List;

import com.wy.store.common.finger.WFingerService;
import com.wy.store.common.finger.WFingerServiceListener;
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
	private boolean isFakeFunOn = true;

	private int enroll_idx = 0;

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


	private List<WFingerServiceListener> listeners = new ArrayList<>();

	@Override
	public int openDevice() {
		// TODO Auto-generated method stub
		int ret = FingerprintSensorErrorCode.ZKFP_ERR_OK;
		// 初始化设备
		if (FingerprintSensorErrorCode.ZKFP_ERR_OK != FingerprintSensorEx.Init()) {

			// 失败

		}
		// 获取当前连接设备的个数
		ret = getDeviceCount();

		if (ret < 0) {// 没有设备连接

			freeSensor();

			// ---- 需要一个提示
		}

		// 连接到第一个设备
		mhDevice = FingerprintSensorEx.OpenDevice(0);

		if (!isDeviceConnected()) {// 打开设备失败

			freeSensor();
		}

		mhDB = FingerprintSensorEx.DBInit();

		if (!isDBConnected()) {

			freeSensor();
		}
		// For ISO 1 /Ansi 0
		int nFmt = 1;
		// 设置编码，默认使用iso
		FingerprintSensorEx.DBSetParameter(mhDB, 5010, nFmt);

		// 获取一些图片的参数，到这一步就是启动成功了
		mbStop = false;
		workThread = new WorkThread();
	    workThread.start();// 线程启动


		return FingerprintSensorErrorCode.ZKFP_ERR_OK;

	}

	@Override
	public void registerFingerId(int fid, String base64) {

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
		return false;
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

	}

	@Override
	public void enrollFinger() {
		// TODO Auto-generated method stub

		//还是需要进行一些判断，譬如是否连接了，是否安装了驱动之类的
		enroll_idx = 0;
		this.status = ZKDeviceStatus.ENROLL;
	}

	/**
	 * 关闭所有的device
	 */
	private void freeSensor() {
		mbStop = true;
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
//			textArea.setText("Identify succ, fid=" + fid[0] + ",score=" + score[0]);
		} else {
			//验证失败
//			textArea.setText("Identify fail, errcode=" + ret);
		}
	}

	private void enrollExecute(byte[] template) {
		int[] fid = new int[1];
		int[] score = new int[1];
		//检查是否已经登记，也就是已经存在了
		int ret = FingerprintSensorEx.DBIdentify(mhDB, template, fid, score);
		if (ret == 0) {

//				指纹已经存在--需要进行一些操作
			
			this.status = ZKDeviceStatus.OPEN;
			enroll_idx = 0;
			return;
		}
		if (enroll_idx > 0 && FingerprintSensorEx.DBMatch(mhDB, regtemparray[enroll_idx - 1], template) <= 0) {
//				textArea.setText("please press the same finger 3 times for the enrollment");
			//如果已经录入了，对比一下指纹是否一致，如果不一致，就需要进行一些提示了
			
			return;
		}
		System.arraycopy(template, 0, regtemparray[enroll_idx], 0, 2048);
		enroll_idx++;
		if (enroll_idx == 3) {
			int[] _retLen = new int[1];
			_retLen[0] = 2048;
			byte[] regTemp = new byte[_retLen[0]];

			if (0 == (ret = FingerprintSensorEx.DBMerge(mhDB, regtemparray[0], regtemparray[1], regtemparray[2],
					regTemp, _retLen)) && 0 == (ret = FingerprintSensorEx.DBAdd(mhDB, iFid, regTemp))) {
				iFid++;
				cbRegTemp = _retLen[0];
				System.arraycopy(regTemp, 0, lastRegTemp, 0, cbRegTemp);
				// Base64 Template
//					录入成功
//					textArea.setText("enroll succ");
				
				for(WFingerServiceListener listener : listeners) {
					listener.enrollFingerReceived(iFid, enroll_idx);
				}
			} else {
				
				//录入失败
//					textArea.setText("enroll fail, error code=" + ret);
			}
			
			//重置状态
			this.status = ZKDeviceStatus.OPEN;
		} else {
			//继续录入，提示用户还剩余几次
			for(WFingerServiceListener listener : listeners) {
				listener.enrollFingerReceived(-1, enroll_idx);
			}
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
					OnExtractOK(template, templateLen[0]);
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
}
