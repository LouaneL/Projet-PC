package prodcons.v5;

import BaseProdConso.Message;

public class ProdConsBuffer extends prodcons.v1.ProdConsBuffer implements IProdConsBufferMulti{

	public ProdConsBuffer(int bufSz) {
		super(bufSz);
	}

	@Override
	public Message[] get(int k) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
