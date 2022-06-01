import java.util.LinkedList;
import java.util.Queue;

public class Mutex {
    private boolean value ;
    private Queue<process> waiting = new LinkedList<process>();
    private int ownerID;
    
    
    
    
    public int getOwnerID() {
        return ownerID;
    }
    public boolean getValue() {
        return value;
    }
    public Queue<process> getWaiting() {
        return waiting;
    }
	public void setValue(boolean value) {
		this.value = value;
	}
	public void setWaiting(Queue<process> waiting) {
		this.waiting = waiting;
	}
	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}
	public void addToWaiting(process executingProcess) {
		this.waiting.add(executingProcess);
	}
	
	public process removeFromWaiting() {
		return this.waiting.remove();
	}
    

}

    