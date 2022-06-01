import java.io.*;
import java.util.Scanner;

public class SystemCallHandler {
	private Scanner sc = new Scanner(System.in);

	public void handle(String[] instruction, process executingProcess) {
		switch (instruction[0]) {
		case "print":
			print(executingProcess.getVariables().get(instruction[1]));
			break;

		case "assign": // assign a b
			if (instruction[2].equals("input")) {
				System.out.println("please enter a value");
				instruction[2] = sc.nextLine();
				executingProcess.decrementPc();
			}else if (instruction[2].equals("readFile")) {
				
				instruction[2] = readFile(executingProcess.getVariables().get(instruction[3]));
				executingProcess.decrementPc();
			}else {
				assign(instruction[1],instruction[2], executingProcess);
			}
			break;

		case "writeFile":
			writeFile(executingProcess.getVariables().get(instruction[1]), executingProcess.getVariables().get(instruction[2]));
			break;

		case "readFile":
			instruction[1] = readFile(instruction[1]);
			break;
		}
	}

	
	
	
	
	public void print(String text) {
		
		System.out.println(text);
	}
	
	public void intPrint(int num) {
		System.out.println(num);
	}

	public void assign(String key, String value, process executingProcess) {
		executingProcess.addToVariables(key, value);
	}

	public void writeFile(String fileName, String data) {
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(data);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readFile(String fileName) {
		String text = "";
		try {
			File f = new File(fileName);
			Scanner myReader = new Scanner(f);
			while (myReader.hasNextLine()) {
        		text += myReader.nextLine();
     	 	}
			myReader.close();

		} catch (FileNotFoundException  e) {
			e.printStackTrace();
		}
		return text;
	}

	
}
