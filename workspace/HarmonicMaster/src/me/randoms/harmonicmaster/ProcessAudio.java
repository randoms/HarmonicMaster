package me.randoms.harmonicmaster;

abstract class ProcessAudioHandler {
	
	abstract public void onProcess(short[] buffer);
	abstract public void onStop();
	abstract public void onError(Throwable e);
}
