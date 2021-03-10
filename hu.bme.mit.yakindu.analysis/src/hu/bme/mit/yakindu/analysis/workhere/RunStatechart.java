package hu.bme.mit.yakindu.analysis.workhere;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;



public class RunStatechart {
	public static void main(String[] args) throws IOException {
		ExampleStatemachine s = new ExampleStatemachine();
		s.setTimer(new TimerService());
		RuntimeService.getInstance().registerStatemachine(s, 200);
		s.init();
		s.enter();
		s.runCycle();
		print(s);
		readConsole(s);
	}

	public static void print(IExampleStatemachine s) {

		System.out.println(s.getSCInterface().getFeherIdo());

		System.out.println(s.getSCInterface().getFeketeIdo());

}
	public static void readConsole(ExampleStatemachine s) throws IOException {
		while(true) {
			BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
			String input = reader.readLine();
			if(input.equals("indul")){
				s.raiseIndul();
				s.runCycle();
				print(s);
			}else if(input.equals("feher")){
				s.raiseFeher();
				s.runCycle();
				print(s);
			}else if(input.equals("fekete")){
				s.raiseFekete();
				s.runCycle();
				print(s);
			}else if(input.equals("exit")){
				System.exit(0);
			}else {	
			}
		}
	}
}


//	public static void main(String[] args) throws IOException {
//		ExampleStatemachine s = new ExampleStatemachine();
//		s.setTimer(new TimerService());
//		RuntimeService.getInstance().registerStatemachine(s, 200);
//		s.init();
//		s.enter();
//		s.runCycle();
//		print(s);
//		readConsole(s);
//		s.raiseStart();
//		s.runCycle();
//		System.in.read();
//		s.raiseWhite();
//		s.runCycle();
//		print(s);
//		System.exit(0);
//	}
	
//	public static void readConsole(ExampleStatemachine s) throws IOException {
//		while(true) {
//			BufferedReader reader = new BufferedReader( new InputStreamReader(System.in)); 
//		  
//		    // Reading data using readLine 
//		    String input = reader.readLine(); 
//			
//			if(input.equals("start")){
//				s.raiseStart();
//				s.runCycle();
//				print(s);
//			}else if(input.equals("white")){
//				s.raiseWhite();
//				s.runCycle();
//				print(s);
//			}else if(input.equals("black")){
//				s.raiseBlack();
//				s.runCycle();
//				print(s);
//			}else if(input.equals("exit")){
//				System.exit(0);
//			}else {
//				
//			}
//		}
//	}
	
//	public static void print(IExampleStatemachine s) {
//		System.out.println("W = " + s.getSCInterface().getWhiteTime());
//		System.out.println("B = " + s.getSCInterface().getBlackTime());
//	}
//}
