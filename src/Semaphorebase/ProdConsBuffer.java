package Semaphorebase;

import java.util.concurrent.Semaphore;

import BaseProdConso.IProdConsBuffer;
import BaseProdConso.Message;

public class ProdConsBuffer implements IProdConsBuffer{
	
	private int sizeBuff;
	private Semaphore plein;
	private Semaphore vide;
	private Semaphore mutex;
	private Message[] buffer;
	int in = 0;
	int out = 0;
	
	int totalM = 0;
	
	public ProdConsBuffer(int sizeBuff) {
		this.sizeBuff = sizeBuff;
		buffer = new Message[sizeBuff];
		vide = new Semaphore(sizeBuff);
		plein = new Semaphore(0);
		mutex = new Semaphore(1);
	}

	@Override
	public void put(Message m) throws InterruptedException {
		vide.acquire();
		mutex.acquire();
		buffer[in] = m;
		in = (in + 1)% sizeBuff;
		totalM++;
		mutex.release();
		// one more not empty entry
		plein.release();
	}
	
	@Override
	public Message get() throws InterruptedException {
		plein.acquire();
		mutex.acquire();
		Message m = buffer[out];
		out = (out + 1)% sizeBuff;
		mutex.release();
		// one more not empty entry
		vide.release();
		
		return m;
	}

	@Override
	public int nmsg() {
		return plein.availablePermits();
	}

	@Override
	public int totmsg() {
		return totalM;
	}



}
