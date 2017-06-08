package components;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import pdu.Message;
import pdu.MessageType;

public class QueryExecutor {

	private QueryExecutor(){}
	
	public static String executeQuery(Message msg,Map<String, ArrayList<Component>> cmap) throws Exception{
		if(msg.getMessageType() != MessageType.OP_QUERY ){
			throw new Exception("Invalid message type : " + msg.getMessageType());	
		}
		else{
			StringBuilder builder = new StringBuilder();
			ArrayList<Component> lcomps = null ;
			String autoId=null;
			Component cmp = null;
			ComponentType cmpt = null;
			String attrib = null;
			String result = null;
			switch (msg.getContent().size()) {
				case 0: // List all automobiles registered
					Set<String> autos = cmap.keySet();
					String[] aautos = autos.toArray(new String[autos.size()]);
					for(String s : aautos) {
					    builder.append(s);
					}
					result = builder.toString();
					return result; 
				case 1: // List internal state of all components of the automobile
					autoId = msg.getContent().get(0).getContent(); // auto id
					lcomps = cmap.get(autoId);
					
					for(Component c : lcomps) {
					    builder.append(c.toString());
					}
					result = builder.toString();
					return result; 
				case 2: // List internal state of of all states of the components
					autoId = msg.getContent().get(0).getContent();
					lcomps = cmap.get(autoId);
					
					cmpt = ComponentType.typeFromString(msg.getContent().get(1).getContent());
					
					for(Component c : lcomps){
						if(c.getComponentCode().equals(cmpt))
							cmp = c;
					}
					result = cmp.toString();
					return result;
				case 3: // List internal state of the attribute
					autoId = msg.getContent().get(0).getContent();
					lcomps = cmap.get(autoId);
					
					cmpt = ComponentType.typeFromString(msg.getContent().get(1).getContent());
					attrib = msg.getContent().get(2).getContent();
					for(Component c : lcomps){
						if(c.getComponentCode().equals(cmpt))
							cmp = c;
					}
					result = cmp.attribStateToString(attrib);
					return result; 
				default: throw new Exception("Invalid Query");
			}
		}	
	}
}
