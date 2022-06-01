import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
	public process schedule(OS interpreter, int timeSlice, Queue<process> readyQueue,
			Queue<process> blockedQueue, ArrayList<process> pendingList, process executingProcess) {

		for (int i = 0; i < pendingList.size(); i++) {
			if (pendingList.get(i).getTimeOfArrival() == interpreter.getClock()) {
				readyQueue.add(pendingList.remove(i));
			}
		}

		if (executingProcess == null) {
			if (!readyQueue.isEmpty()) {
				executingProcess = readyQueue.remove();
				executingProcess.setTimeToLive(timeSlice);

				// printing
				System.out.println("new executing process has been chosen");
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
		} else if (executingProcess.getPc() == executingProcess.getInstructions().size()) {
			// print the queues as the process is finished
			if (!readyQueue.isEmpty()) {
				executingProcess = readyQueue.remove();
				executingProcess.setTimeToLive(timeSlice);
			} else {
				executingProcess = null;
			}
		} else if (executingProcess.getTimeToLive() == 0) {
			
			readyQueue.add(executingProcess);
			executingProcess = readyQueue.remove();
			executingProcess.setTimeToLive(timeSlice);

			//printing
			System.out.println("new executing process has been chosen");
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

		return executingProcess;
	}
}
