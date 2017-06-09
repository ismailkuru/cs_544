package pdu;
/* =============================================================================
* CS544 - Computer Networks - Drexel University, Spring 2017
* Protocol Implementation: Remote Automobile Utility Control
* Group 2:
* - Ismail Kuru
* - Max Mattes
* - Lewis Cannalongo
***************************************************
* File name: Chunk.java
* **************************************************
* Definition: .This file includes abstract definitions 
* for the most basic unit of Message which is core components of
* protocol’s service.
* *******************************************************
* Requirements:
* - SERVICE : Abstraction of core components of the Messages which are 
* backbone of service of protocol.
* 
* ==============================================================================
*/    
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
