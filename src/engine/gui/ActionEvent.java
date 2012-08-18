package engine.gui;
import java.util.EventObject;

//should be immutable
@SuppressWarnings("serial")
public class ActionEvent extends EventObject {

	public ActionEvent(Object source) {
		super(source);
	}
}
