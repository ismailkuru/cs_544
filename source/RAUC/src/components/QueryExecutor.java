package components;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import pdu.Message;
import pdu.MessageType;
import pdu.ChunkImpl.ContentChunk;
/* =============================================================================
* CS544 - Computer Networks - Drexel University, Spring 2017
* Protocol Implementation: Remote Automobile Utility Control
* Group 2:
* - Ismail Kuru
* - Max Mattes
* - Lewis Cannalongo
***************************************************
* File name: QueryExecutor.java
* **************************************************
* Definition: 
* *******************************************************
* Requirements:
* - STATEFUL : 
* - SERVICE : 
* 
* ==============================================================================
*/ 
public class QueryExecutor {

	private QueryExecutor(){}
	
	public static String executeQuery(Message msg,Map<String, ArrayList<Component>> cmap) throws Exception{
		if(msg.getMessageType() != MessageType.OP_QUERY ){
			throw new Exception("Invalid message type : " + msg.getMessageType());	
		}
		else{
			StringBuilder builder = new StringBuilder();
			ArrayList<Component> lcomps = null ;
			ArrayList<Attribute> lattrs = null ;
			ArrayList<String> vals = null;
			String autoId=null;
			String ccCmp;
			String ccAtr;
			Component cmp = null;
			ComponentType cmpt = null;
			String attrib = null;
			String result = null;
													System.out.println("Beginning query execution");
													System.out.println("On message: " + msg.toString());
			switch (msg.getContent().size()) {
				case 0: // List all automobiles registered
					Set<String> autos = cmap.keySet();
					String[] aautos = autos.toArray(new String[autos.size()]);
					for(String s : aautos) {
					    builder.append(s).append(" - ");
					}
					result = builder.toString();
					return result; 
					
					
				case 1: // List all components in the component list of the given automobile
					autoId = msg.getContent().get(0).getContent(); // auto id
					// the list of components on this auto
					lcomps = cmap.get(autoId);
					
					for(Component c : lcomps) {
					    builder.append(c.toString());
					    builder.append(" - ");
					}
					result = builder.toString();										
					return result; 
					
					
				case 2: // list the attributes of the given component on the given auto
					autoId = msg.getContent().get(0).getContent(); // get the car 
					lcomps = cmap.get(autoId); // get the components on the car
					
					// search the list of components on the car for the queried component
					ccCmp = msg.getContent().get(1).getContent();
					for (Component c : lcomps) {
						if (ccCmp.equals(c.getComponentName())) {
							// capture the attributes of that component
							lattrs = (ArrayList<Attribute>) c.getAttrs();
							break;
						}
					}
					
					// build a string of those attributes & return it
					for(Attribute a : lattrs) {
						builder.append(a.attribToString());
						builder.append(" - ");
					}
					result = builder.toString();
					return result;
					
				case 3: // List the possible values of an attribute of a component
					autoId = msg.getContent().get(0).getContent(); // get the car
					lcomps = cmap.get(autoId); // get the components
					
					// search the list of components on the car for the queried component
					ccCmp = msg.getContent().get(1).getContent();
					for (Component c : lcomps) {
						if (ccCmp.equals(c.getComponentName())) {
							// capture the attributes of that component
							lattrs = (ArrayList<Attribute>) c.getAttrs();
							break;
						}
					}
					
					// search the list of attributes for the queried component
					ccAtr = msg.getContent().get(2).getContent();
					for (Attribute at : lattrs) {
						if (ccCmp.equals(at.attribToString())) {
							// capture the values of that attribute
							vals = (ArrayList<String>) at.getValues();
							break;
						}
					}
					
					
					
					for (Attribute at : lattrs) {
						if (ccAtr.equals(at.toString())) {
							vals = (ArrayList<String>) at.getValues();
						}
						
					// build a string of those values
					for (String s : vals) {
						builder.append(s);
						builder.append(" - ");
						}
					result = builder.toString();
					return result;
					}
					
				case 4:
					// List the value an attribute on the given component of a given auto
					autoId = msg.getContent().get(0).getContent(); // get the car
					lcomps = cmap.get(autoId); // get the components
					
					// search the list of components on the car for the queried component
					ccCmp = msg.getContent().get(1).getContent();
					for (Component c : lcomps) {
						if (ccCmp.equals(c.getComponentName())) {
							// capture the attributes of that component
							lattrs = (ArrayList<Attribute>) c.getAttrs();
							break;
						}
					}
					
					// search for the attribute
					ccAtr = msg.getContent().get(2).getContent();
					for (Attribute at : lattrs) {
						if (at.getName().equals(ccAtr)) {
							// get the value of that attribute
							result = at.stateToString();
						}
					}
					return result; 
				default: throw new Exception("Invalid Query");
			}
		}	
	}
}

