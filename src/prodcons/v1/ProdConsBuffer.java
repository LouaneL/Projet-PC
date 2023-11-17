package prodcons.v1;

import BaseProdConso.IProdConsBuffer;
import BaseProdConso.Message;

/* Variables :
 * 		-
 * 		-
 * 
 * 
 * 
 * Tableau de garde-actions:
 * 
 *     Opérations    | Pré-action |  Garde   |    Post-action
 * --------------------------------------------------------------
 * Produce(Message m)|            | nempty>0 | nfull++; nempty--;
 * --------------------------------------------------------------
 * Message Consume   |            | nfull>0  | nfull--; nempty++;
*/

public class ProdConsBuffer implements IProdConsBuffer {
	int nfull;
	int nempty;
	Message[] buffer;
	int bufSz;
	int in;
	int out;

	public ProdConsBuffer(int bufSz) {
		this.nfull = 0;
		this.nempty = bufSz;
		buffer = new Message[bufSz];
		this.bufSz = bufSz;
		in = 0;
		out = 0;
	}

	@Override
	public synchronized void put(Message m) throws InterruptedException {
		while (nempty <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		buffer[in] = m;
		in = (in+1)%bufSz; 
		nfull++;
		nempty--;
	}

	@Override
	public synchronized Message get() throws InterruptedException {
		while (nfull <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		Message tmp = buffer[out];
		out = (out+1)%bufSz;
		nfull--;
		nempty++;
		return tmp;
	}

	@Override
	public int nmsg() {
		return nempty;
	}

	@Override
	public int totmsg() {
		return nfull;
	}

}
