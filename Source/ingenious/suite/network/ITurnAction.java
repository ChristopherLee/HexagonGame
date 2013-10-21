package ingenious.suite.network;

import ingenious.distributed.ProxyTurn;

public interface ITurnAction {
	public IAdminResponse perform(ProxyTurn turn);
}
