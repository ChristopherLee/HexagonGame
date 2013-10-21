package ingenious.suite.network;

public abstract class BooleanXML implements IAdminResponse{
	private boolean bool;
	public BooleanXML(boolean bool) {
		this.bool = bool;
	}
	
	public boolean getBoolean() {
		return bool;
	}
	
	@Override
    public String toString() {
    	return XMLConverter.getInstance().writeXml(this);
    }
}
