import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class OS {
	private Scanner sc = new Scanner(System.in);
	private FileReader fileReader;
	private BufferedReader bufferedReader;

	private int timeSlice = 2;
	private int processCount = 3;
	private int clock = 0;
	private pair[] memory = new pair[40];
	
	private Mutex userInput = new Mutex();
	private Mutex userOutput = new Mutex();
	private Mutex file = new Mutex();

	private Queue<process> readyQueue = new LinkedList<>();
	private Queue<process> blockedQueue = new LinkedList<>();
	private ArrayList<process> pendingList = new ArrayList<>();
	private process executingProcess;
	private SystemCallHandler systemCallHandler = new SystemCallHandler();
	private Scheduler scheduler = new Scheduler();


	public OS() {
		initialize();

		run();
	}

	public static void main(String[] args) {

		new OS();

	}

	public void initialize() {

		String choice;

		System.out.println("the default for number of programs is 3,do you want to change it? (y/n) ");
		choice = sc.nextLine();

		if (choice.equals("y")) {
			System.out.println("please enter your number of programs");
			processCount = sc.nextInt();
			sc.nextLine();
		}

		System.out.println("the default time slice is 2,do you want to change it? (y/n) ");
		choice = sc.nextLine();
		if (choice.equals("y")) {
			System.out.println("please enter your time slice");
			timeSlice = sc.nextInt();
			sc.nextLine();
		}

		String path;
		int timeOfArrival;
		for (int i = 1; i <= processCount; i++) {
			System.out.println("please enter the file path of process " + i + " and its time of arrival");
			System.out.println("path :- ");
			path = sc.nextLine();
			System.out.println();
			System.out.println("time of arrival :- ");
			timeOfArrival = sc.nextInt();
			sc.nextLine();
			System.out.println();

			createProcess(path, timeOfArrival, i, timeSlice);
		}
	}

	public void run() {
		while (!(readyQueue.isEmpty() && blockedQueue.isEmpty() && pendingList.isEmpty() && executingProcess == null)) {
			System.out.println("clock :- " + clock);
			executingProcess = scheduler.schedule(this, timeSlice, readyQueue, blockedQueue, pendingList,
					executingProcess);
			if (executingProcess != null) {

				StringBuffer sb = new StringBuffer(executingProcess.getName());
				sb.deleteCharAt(sb.length() - 1);
				sb.deleteCharAt(sb.length() - 1);
				sb.deleteCharAt(sb.length() - 1);
				sb.deleteCharAt(sb.length() - 1);

				System.out.println("currently executing process :-  " + sb);
				System.out.println("currently executing instruction :-  "
						+ String.join(" ", executingProcess.getInstructions().get(executingProcess.getPc())));
				executeInstruction(executingProcess.getInstructions().get(executingProcess.getPc()));
				if (executingProcess != null && executingProcess.getPc() == executingProcess.getInstructions().size()) {
					System.out.println("process finished");
					System.out.print("ready queue :- ");
					for (process p : readyQueue) {
						System.out.print(p.getName() + ", ");
					}
					System.out.println();
					System.out.print("blocked queue :- ");
					for (process p : blockedQueue) {
						System.out.print(p.getName() + ", ");
					}
					System.out.println();
					System.out.println("pending list  :- ");
					for (process p : pendingList) {
						System.out.println(p.getName() + " ");
					}
					System.out.println();
				}
			} else {
				System.out.println("No process currently executing");
			}
			clock++;
			System.out.println("--------------------------------------------------");
		}
	}

	public void createProcess(String path, int timeOfArrival, int pid, int timeToLive) {
		try {
			process tempProcess = new process(pid, timeOfArrival, timeToLive, path);
			fileReader = new FileReader(path);
			bufferedReader = new BufferedReader(fileReader);
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				tempProcess.addInstruction(line.split(" "));
			}
			if (timeOfArrival == 0) {
				readyQueue.add(tempProcess);
			} else {
				pendingList.add(tempProcess);
			}

			bufferedReader.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}


	public void printFromTo(int start, int end) {
		for (int i = start; i <= end; i++) {
			systemCallHandler.intPrint(i);
		}
	}

	void semWaitB(Mutex m) {
		if (m.getValue() == true) {
			m.setOwnerID(executingProcess.getPid());
			m.setValue(false);
		} else {
			/* place this process in m.queue */
			m.addToWaiting(executingProcess);
			/* block this process */
			blockedQueue.add(executingProcess);
			
			//printing
			System.out.println("process blocked");
			System.out.print("ready queue :- ");
			for (process p : readyQueue) {
				System.out.print(p.getName() + ", ");
			}
			System.out.println();
			System.out.print("blocked queue :- ");
			for (process p : blockedQueue) {
				System.out.print(p.getName() + ", ");
			}
			System.out.println();
			System.out.println("pending list  :- ");
			for (process p : pendingList) {
				System.out.println(p.getName() + " ");
			}
			System.out.println();

			executingProcess = null;
		}
	}

	void semSignalB(Mutex m) {
		/* check if this process is the owner */
		if (m.getOwnerID() == executingProcess.getPid()) {
			if (m.getWaiting().isEmpty()) {
				m.setValue(true);
			} else {
				/* update ownerID to be equal to Process P�s ID */
				m.setOwnerID(m.getWaiting().peek().getPid());
				/* remove a process P from m.queue */
				/* place process P on ready list */
				blockedQueue.remove(m.getWaiting().peek());
				readyQueue.add(m.removeFromWaiting());

			}
		}
	}

	public void executeInstruction(String[] instruction) {
		
		
		
		
		
//		executingProcess.incrementPc();
//		executingProcess.decay();
//		switch (instruction[0]) {
//		case "semWait":
//			switch (instruction[1]) {
//			case "userInput":
//				semWaitB(userInput);
//				break;
//			case "userOutput":
//				semWaitB(userOutput);
//				break;
//			case "file":
//				semWaitB(file);
//				break;
//
//			}
//			break;
//		case "semSignal":
//			switch (instruction[1]) {
//			case "userInput":
//				semSignalB(userInput);
//				break;
//			case "userOutput":
//				semSignalB(userOutput);
//				break;
//			case "file":
//				semSignalB(file);
//				break;
//
//			}
//			break;
//		case "printFromTo":
//			int from = Integer.parseInt(executingProcess.getVariables().get(instruction[1]));
//			int to = Integer.parseInt(executingProcess.getVariables().get(instruction[2]));
//			printFromTo(from, to);
//		default:
//			systemCallHandler.handle(instruction, executingProcess);
//
//		}

	}

	public int getTimeSlice() {
		return timeSlice;
	}

	public void setTimeSlice(int timeSlice) {
		this.timeSlice = timeSlice;
	}

	public int getClock() {
		return clock;
	}

	public void setClock(int clock) {
		this.clock = clock;
	}

	public Queue<process> getReadyQueue() {
		return readyQueue;
	}

	public void setReadyQueue(Queue<process> readyQueue) {
		this.readyQueue = readyQueue;
	}

//	public Queue<process> getBlockedQueue() {
//		return blockedQueue;
//	}
//
//	public void setBlockedQueue(Queue<process> blockedQueue) {
//		this.blockedQueue = blockedQueue;
//	}
//
//	public ArrayList<process> getPendingList() {
//		return pendingList;
//	}
//
//	public void setPendingList(ArrayList<process> pendingList) {
//		this.pendingList = pendingList;
//	}
//
//	public process getExecutingProcess() {
//		return executingProcess;
//	}
//
//	public void setExecutingProcess(process executingProcess) {
//		this.executingProcess = executingProcess;
//	}

}
