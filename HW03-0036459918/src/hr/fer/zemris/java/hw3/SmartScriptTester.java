package hr.fer.zemris.java.hw3;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.parser.*;
import hr.fer.zemris.java.tecaj.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.elems.*;


@SuppressWarnings("javadoc")
public class SmartScriptTester {

	public static void main(String[] args) throws IOException {

		String docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);

		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody);
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		System.out.println("-------------------------------");
		String parsedDocumentBody = createOriginalDocumentBody(document2);
		System.out.println(parsedDocumentBody);
	}
	
	//*Demonstracijski ispis početnog zadanog stabla i stabla koje se dobije ponovnim parsiranjem
	
/*		System.out.printf("-------------------------------\n----------------\n------------\n");
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		
		printDocumentTree(document);
		System.out.printf("-------------------------------\n----------------\n------------\n");
		printDocumentTree(document2);
	}
		
	
	
	private static void printDocumentTree(DocumentNode document) {
		int i = 0;
		Node pomNode;
		Node pomNode2;
		System.out.printf("%s%n",document.getClass().getSimpleName());
		for (int tr = document.numberOfChildren(); i < tr; i++) {
			int br=0;
			pomNode = document.getChild(i);
			if (pomNode instanceof ForLoopNode) {
				System.out.printf("---%s%n",pomNode.getClass().getSimpleName());
				for (int nr = pomNode.numberOfChildren(); br < nr; br++) {
					pomNode2 = pomNode.getChild(br);
					System.out.printf("------%s%n",pomNode2.getClass().getSimpleName());
				}
			}else{
				System.out.printf("---%s%n",pomNode.getClass().getSimpleName());
			}
			
		}

	}*/
	
	
	private static String createOriginalDocumentBody(DocumentNode document) {
		Node pomNode;
		StringBuilder string = new StringBuilder();
		int children_number = document.numberOfChildren();
		for (int i = 0; i < children_number; i++) {
			pomNode = document.getChild(i);
			if(pomNode instanceof TextNode){
				string.append(((TextNode) pomNode).getText());
				continue;
			}
			if(pomNode instanceof ForLoopNode){
				string.append(childrenElements((ForLoopNode)pomNode));
				string.append(createOriginalDocumentBody(pomNode));
				string.append("{$END$}");
				continue;
			}
			if(pomNode instanceof EchoNode){
				string.append(childrenElements((EchoNode)pomNode));
				continue;
			}
		}
		return string.toString();
	}
	
	private static String createOriginalDocumentBody(Node node) {
		Node pomNode;
		StringBuilder string = new StringBuilder();
		int children_number = node.numberOfChildren();
		for (int i = 0; i < children_number; i++) {
			pomNode = node.getChild(i);
			if (pomNode instanceof TextNode) {
				string.append(((TextNode) pomNode).getText());
				continue;
			}
			if (pomNode instanceof ForLoopNode) {
				string.append(childrenElements((ForLoopNode) pomNode));
				string.append(createOriginalDocumentBody(pomNode));
				continue;
			}
			if (pomNode instanceof EchoNode) {
				string.append(childrenElements((EchoNode) pomNode));
				continue;
			}
		}
		return string.toString();
	}
	
	
	
	private static String childrenElements(ForLoopNode node){
		StringBuilder string = new StringBuilder();
		string.append("{$FOR ");
		string.append(node.getVariable().asText() + " ");
		
		string.append(node.getStartExpression().asText()+" ");
		string.append(node.getEndExpression().asText()+" ");
		if(node.getStepExpression() != null){
			string.append(node.getStepExpression().asText()+" ");
		}
		string.append("$}");
		return string.toString();
	}
	
	private static String childrenElements(EchoNode node) {
		StringBuilder string = new StringBuilder();
		string.append("{$");
		string.append(node.getElements()[0].asText());
		int br = node.getElements().length;
		for (int i = 1; i < br; i++) {
			if (node.getElements()[i] != null) {
				if (node.getElements()[i] instanceof ElementFunction) {
					string.append("@" + node.getElements()[i].asText() + " ");
					continue;
				}
				if (node.getElements()[i] instanceof ElementString) {
					string.append("\"" + node.getElements()[i].asText() + "\"" + " ");
					continue;
				}
				string.append(node.getElements()[i].asText() + " ");
			}

		}
		string.append("$}");
		return string.toString();
	}

}
