import java.util.ArrayList;
import java.util.HashMap;

public class process {
	private int pid;
	private int timeOfArrival;
	private int timeToLive;
	private String name;
	private ArrayList<String[]> instructions = new ArrayList<>();
	private int pc = 0;
	private HashMap<String, String> variables = new HashMap<String, String>();

	
	
	public process(int pid,int timeOfArrival,int timeToLive,String name) {
		this.pid = pid;
		this.timeOfArrival = timeOfArrival;
		this.timeToLive = timeToLive;
		this.name = name;
	}

	public HashMap<String, String> getVariables() {
		return variables;
	}

	public void setVariables(HashMap<String, String> variables) {
		this.variables = variables;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getPc() {
		return pc;
	}

	public void setPc(int pc) {
		this.pc = pc;
	}

	public ArrayList<String[]> getInstructions() {
		return instructions;
	}

	public void setInstructions(ArrayList<String[]> instructions) {
		this.instructions = instructions;
	}

	public int getTimeOfArrival() {
		return timeOfArrival;
	}

	public void setTimeOfArrival(int timeOfArrival) {
		this.timeOfArrival = timeOfArrival;
	}
	
	
	
	public int getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(int timeToLive) {
		this.timeToLive = timeToLive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addToVariables(String key,String value) {
		this.variables.put(key, value);
	}
	
	public void addInstruction(String[] instruction) {
		this.instructions.add(instruction);
	}
	
	public void incrementPc() {
		this.pc++;
	}
	
	public void decrementPc() {
		this.pc--;
	}
	
	public void decay() {
		this.setTimeToLive(this.getTimeToLive() - 1);
	}

}
