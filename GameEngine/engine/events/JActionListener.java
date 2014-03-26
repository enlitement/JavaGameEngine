package engine.events;

import engine.interfaces.ListenerTag;

public interface JActionListener extends ListenerTag {

	public void actionPerformed(JActionEvent event);
}