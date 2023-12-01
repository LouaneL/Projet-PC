package prodcons.v6;

import BaseProdConso.IProdConsBuffer;
import BaseProdConso.Message;

public interface IProdConsBufferMulti extends IProdConsBuffer{
	public void put(int k, Message m) throws InterruptedException;
}
