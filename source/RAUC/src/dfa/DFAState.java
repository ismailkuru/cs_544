package dfa;
/* =============================================================================
* CS544 - Computer Networks - Drexel University, Spring 2017
* Protocol Implementation: Remote Automobile Utility Control
* Group 2:
* - Ismail Kuru
* - Max Mattes
* - Lewis Cannalongo
***************************************************
* File name: DFAState.java
* **************************************************
* Definition: This file includes enumeration of states in DFAs.
* *******************************************************
* Requirements:
* - STATEFUL : Entire file names the states used in Client/Server DFAs.
* 
* ==============================================================================
*/      
public enum DFAState {
	CLOSED, INIT, AUTH, ESTABLISHED, WAITCMD, WAITQRY
}