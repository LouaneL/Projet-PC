package prodcons.v5;

import BaseProdConso.IProdConsBuffer;
import BaseProdConso.Message;

public interface IProdConsBufferMulti extends IProdConsBuffer{
	/**
	* Retrieve n consecutive messages from the prodcons buffer
	**/
	public Message[] get(int k) throws InterruptedException;
}
