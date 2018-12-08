package com.wy.store.common.eventbus;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

//事件总线
///单例----使用背压
@Service
public class RxEventBus {

	private Map<String, CompositeDisposable> mSubscriptionMap;

	private static volatile RxEventBus mRxBus;

	private final Subject<Object> mSubject;

	public static RxEventBus getDefault() {

		if (mRxBus == null) {

			synchronized (RxEventBus.class) {

				mRxBus = new RxEventBus();
			}

		}
		return mRxBus;
	}

	private RxEventBus() {

		mSubject = PublishSubject.create().toSerialized();

	}

	/**
	 * 
	 * @param event
	 */
	public void post(final Object event) {

		mSubject.onNext(event);
	}

	/**
	 * 返回flowable
	 * 
	 * @param type
	 * @return
	 */
	public <T> Flowable<T> getObservable(Class<T> type) {
		return mSubject.toFlowable(BackpressureStrategy.BUFFER).ofType(type);
	}

	public <T> Flowable<T> register(Class<T> type) {

		return getObservable(type);
	}
	
	 /**
     * 保存订阅后的disposable
     * @param o
     * @param disposable
     */
    public void addSubscription(Object o, Disposable disposable) {
        if (mSubscriptionMap == null) {
            mSubscriptionMap = new HashMap<>();
        }
        String key = o.getClass().getName();
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).add(disposable);
        } else {
            //一次性容器,可以持有多个并提供 添加和移除。
            CompositeDisposable disposables = new CompositeDisposable();
            disposables.add(disposable);
            mSubscriptionMap.put(key, disposables);
        }
    }

	/**
	 * 取消订阅
	 * 
	 * @param o
	 */
	public void unSubscribe(Object o) {
		if (mSubscriptionMap == null) {
			return;
		}

		String key = o.getClass().getName();
		if (!mSubscriptionMap.containsKey(key)) {
			return;
		}
		if (mSubscriptionMap.get(key) != null) {
			mSubscriptionMap.get(key).dispose();
		}

		mSubscriptionMap.remove(key);
	}



	/**
	 * 
	 * @return
	 */
	public boolean hasSubscribers() {

		return mSubject.hasObservers();
	}

}
