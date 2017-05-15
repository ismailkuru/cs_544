package components.TestComponentImpl;
import org.junit.Test;
import pdu.MessageImpl.UtilityControlReqMessage;
import com.google.gson.Gson;
import components.Command;
import components.ComponentImpl.AC;

public class ACTest {

	@Test
	public void test() throws Exception {
	
		
		UtilityControlReqMessage umcr = new UtilityControlReqMessage("1", "0" , "0", "4");
		Gson gson = new Gson();
		String json = gson.toJson(umcr);
		System.out.println(json);
		System.out.println();
		System.out.println();
		System.out.println();
		
		UtilityControlReqMessage as = gson.fromJson(json, UtilityControlReqMessage.class);
		Command cmd = Command.createCommand(as);
		System.out.println("Command: autoID "  + cmd.getAutoId() + " - attrb " + cmd.getAttribute() );
		//[TODO] component type should be passed as String ?
		AC ac = new AC("1",cmd.getComponentType(), "0");
		
		ac.applyCommand(cmd);
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("Value of attrib 0 is now " +  ac.getValueOfAttrb("0"));
		
		
	}

}
