import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
	//returns executingPid
	public int schedule(OS os) {
		int oldExecutingPid = os.getExecutingPid();
		for (int i = 0; i < os.getPendingList().size(); i++) {
			if (os.getPendingList().get(i).getTimeOfArrival() == os.getClock()) {
				os.getReadyQueue().add(os.getPendingList().get(i).getPid());
				os.createInMemory(os.getPendingList().get(i));
			}
		}

		if (!os.getReadyQueue().isEmpty()) {
			if (os.getExecutingPid() != -1) {
				os.getReadyQueue().add(os.getExecutingPid());
			}
			os.setExecutingPid(os.getReadyQueue().remove());
		}
		
		if (os.getExecutingPid() == (int)os.getMemory()[0].getValue()) {
			return 0;
		}else if (os.getExecutingPid() == (int)os.getMemory()[20].getValue()) {
			return 20;
		}else {
			if(oldExecutingPid == (int)os.getMemory()[0].getValue()) {
				os.moveBlockFromToDisk(true, 0);
				return 0;
			}
			else{
				os.moveBlockFromToDisk(true, 20);
				return 20;
			}
		}
		
		// for (int i = 0; i < os.getPendingList().size(); i++) {
//			if (os.getPendingList().get(i).getTimeOfArrival() == interpreter.getClock()) {
//				readyQueue.add(os.getPendingList().remove(i));
//			}
//		}
//
//		if (executingProcess == null) {
//			if (!readyQueue.isEmpty()) {
//				executingProcess = readyQueue.remove();
//				executingProcess.setTimeToLive(timeSlice);
//
//				// printing
//				System.out.println("new executing process has been chosen");
//				System.out.print("ready queue :- ");
//				for (process p : readyQueue) {
//					System.out.print(p.getName() + ", ");
//				}
//				System.out.println();
//				System.out.print("blocked queue :- ");
//				for (process p : blockedQueue) {
//					System.out.print(p.getName() + ", ");
//				}
//				System.out.println();
//				System.out.println("pending list  :- ");
//				for (process p : os.getPendingList()) {
//					System.out.println(p.getName() + " ");
//				}
//				System.out.println();
//			}
//		} else if (executingProcess.getPc() == executingProcess.getInstructions().size()) {
//			// print the queues as the process is finished
//			if (!readyQueue.isEmpty()) {
//				executingProcess = readyQueue.remove();
//				executingProcess.setTimeToLive(timeSlice);
//			} else {
//				executingProcess = null;
//			}
//		} else if (executingProcess.getTimeToLive() == 0) {
//			
//			readyQueue.add(executingProcess);
//			executingProcess = readyQueue.remove();
//			executingProcess.setTimeToLive(timeSlice);
//
//			//printing
//			System.out.println("new executing process has been chosen");
//			System.out.print("ready queue :- ");
//			for (process p : readyQueue) {
//				System.out.print(p.getName() + ", ");
//			}
//			System.out.println();
//			System.out.print("blocked queue :- ");
//			for (process p : blockedQueue) {
//				System.out.print(p.getName() + ", ");
//			}
//			System.out.println();
//			System.out.println("pending list  :- ");
//			for (process p : os.getPendingList()) {
//				System.out.println(p.getName() + " ");
//			}
//			System.out.println();
//
//		}
	}
}
