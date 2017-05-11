package pdu;

public abstract class Chunk <S,C> {

	 	private S _first;
	    private C _second;
	    public Chunk(S sz, C cnt){
	        this._first = sz;
	        this._second = cnt;
	    }
	    public S getFirst(){ return _first; }
	    public C getSecond(){ return _second; }
	    public void setFirst(S sz){ this._first = sz; }
	    public void setSecond(C cnt){ this._second = cnt; }
	
}
