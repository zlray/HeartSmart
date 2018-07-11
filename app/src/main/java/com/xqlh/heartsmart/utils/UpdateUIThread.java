package com.xqlh.heartsmart.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xqlh.heartsmart.receiver.PlayerManagerReceiver;
import com.xqlh.heartsmart.ui.mine.ui.PlayBarFragment;


//此线程只是用于循环发送广播，通知更改歌曲播放进度。
public class UpdateUIThread extends Thread {

	private static final String TAG = UpdateUIThread.class.getName();
	private int threadNumber;
	private Context context;
	private PlayerManagerReceiver playerManagerReceiver;
	private int duration;
	private int curPosition;
	
	public UpdateUIThread(PlayerManagerReceiver playerManagerReceiver, Context context, int threadNumber) {
		Log.i(TAG, "UpdateUIThread: " );
		this.playerManagerReceiver = playerManagerReceiver;
		this.context = context;
		this.threadNumber = threadNumber;
	}

	@Override
	public void run() {
		try {
			while (playerManagerReceiver.getThreadNumber() == this.threadNumber) {
				if (playerManagerReceiver.status == Constants.STATUS_STOP) {
					Log.e(TAG, "run: Constant.STATUS_STOP");
					break;
				}
				if (playerManagerReceiver.status == Constants.STATUS_PLAY ||
						playerManagerReceiver.status == Constants.STATUS_PAUSE) {
					if (!playerManagerReceiver.getMediaPlayer().isPlaying()) {
						Log.i(TAG, "run: getMediaPlayer().isPlaying() = " + playerManagerReceiver.getMediaPlayer().isPlaying());
						break;
					}
					duration = playerManagerReceiver.getMediaPlayer().getDuration();
					curPosition = playerManagerReceiver.getMediaPlayer().getCurrentPosition();
//				Log.d(TAG, "duration = "+duration);
//				Log.d(TAG, "current = "+curPosition);
					Intent intent = new Intent(PlayBarFragment.ACTION_UPDATE_UI_PlayBar);
					intent.putExtra(Constants.STATUS, Constants.STATUS_RUN);
//				intent.putExtra("status2", playerManagerReceiver.status);
					intent.putExtra(Constants.KEY_DURATION, duration);
					intent.putExtra(Constants.KEY_CURRENT, curPosition);
					context.sendBroadcast(intent);
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}
}

