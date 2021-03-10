package hu.bme.mit.yakindu.analysis.workhere;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.stext.stext.EventDefinition;
import org.yakindu.sct.model.stext.stext.VariableDefinition;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		
		System.out.println(
				"public static void main(String[] args) throws IOException {\n"+
				"	ExampleStatemachine s = new ExampleStatemachine();\n"+
				"	s.setTimer(new TimerService());\n"+
				"	RuntimeService.getInstance().registerStatemachine(s, 200);\n"+
				"	s.init();\n"+
				"	s.enter();\n"+
				"	s.runCycle();\n"+
				"	print(s);\n"+
				"	readConsole(s);\n"+
				"}\n"
		);
			

		ArrayList<VariableDefinition> variables = new ArrayList<VariableDefinition>();
		
		ArrayList<EventDefinition> events = new ArrayList<EventDefinition>();
		
		System.out.println("public static void print(IExampleStatemachine s) {\r\n");
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			
			if(content instanceof VariableDefinition) {
				VariableDefinition vardef = (VariableDefinition) content;
				variables.add(vardef);
				System.out.println(
						"	System.out.println(s.getSCInterface().get" + vardef.getName() + "());\r\n");
			}
			
			if(content instanceof EventDefinition) {
				EventDefinition eventdef = (EventDefinition) content;
				events.add(eventdef);
			}
			
		}
		System.out.println("}");
		
		
		System.out.println(
				"public static void readConsole(ExampleStatemachine s) throws IOException {\n" +
				"	while(true) {\n"+
				"		BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));\n"+ 
			    "		String input = reader.readLine();\n"+
				"		if(input.equals(\"" +events.get(0).getName()+"\")){\n"+
				"			s.raise"+events.get(0).getName()+"();\n"+
				"			s.runCycle();\n"+
				"			print(s);\n"+
				"		}else if(input.equals(\""+events.get(1).getName()+"\")){\n"+
				"			s.raise"+events.get(1).getId()+"();\n"+
				"			s.runCycle();\n"+
				"			print(s);\n"+
				"		}else if(input.equals(\""+events.get(2).getName() +"\")){\n"+
				"			s.raiseBlack();\n"+
				"			s.runCycle();\n"+
				"			print(s);\n"+
				"		}else if(input.equals(\"exit\")){\n"+
				"			System.exit(0);\n"+
				"		}else {	\n"+
				"		}\n"+
				"}\n"
			);
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}

//if(content instanceof State) {
//	State state = (State) content;
//	
//	if (state.getName().isEmpty()) {
//		EList<Transition> transitions = state.getIncomingTransitions();
//		
//		System.out.println("Nameless state, recommended name: " 
//							+ transitions.get(0).getSource().getName() 
//							+ "-" + transitions.get(0).getSpecification()
//							+ "->" + transitions.get(0).getTarget().getName());
//		
//	}
//	if(state.getOutgoingTransitions().isEmpty()) {
//		System.out.println(state.getName() +  "This state is a trap state.");
//	}
//	System.out.println(state.getName());
//}
//if(content instanceof Transition) {
//	Transition transition = (Transition) content;
//	System.out.println(transition.getSource().getName() + "->" + transition.getTarget().getName());
//}
