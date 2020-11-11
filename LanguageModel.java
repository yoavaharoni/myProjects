/*
Assignment number  :  9
File Name  :  LanguageModel.java
Name  :  Yoav Aharoni
Student ID : 000802947
Email : yoav.aharoni@post.idc.ac.il 
*/
import java.util.HashMap;
		import java.util.LinkedList;
		import java.util.Random;
		import std.StdIn;

public class LanguageModel {

	// The length of the moving window
	private int windowLength;
	private Random randomGen;
	// The map where we manage the (window, LinkedList) mappings
	private HashMap<String, LinkedList<CharProb>> probabilities;

	/**
	 * Creates a new language model, using the given window length.
	 * @param windowLength
	 */
	public LanguageModel(int windowLength) {
		this.windowLength = windowLength;
		randomGen = new Random();
		probabilities = new HashMap<String, LinkedList<CharProb>>();
	}

	public LanguageModel(int windowLength, int seed) {
		this.windowLength = windowLength;
		randomGen = new Random(seed);
		probabilities = new HashMap<String, LinkedList<CharProb>>();
	}


	/**
	 * Builds a language model from the text in standard input (the corpus).
	 */
	public void train() {
		String window = "";
		char c;
		for (int i = 0; i < windowLength; i++) {
			window += StdIn.readChar();
		}
		while (!StdIn.isEmpty()) {
			// Gets the next character
			c = StdIn.readChar();
			// Checks if the window is already in the map
			if (probabilities.get(window) != null) {
				LinkedList<CharProb> probs = probabilities.get(window);
				calculateCounts(probs, c);
			}
			else {
				LinkedList<CharProb> probs = new LinkedList<CharProb>();
				probs.add(new CharProb(c));
				probabilities.put(window, probs);
			}
			String temporary = window.substring(1);
			window = temporary + c;
		}
		for (LinkedList<CharProb> probs : probabilities.values()) {
			calculateProbabilities(probs);
		}
	}

	// If the given character is found in the given list, increments its count;
	// Otherwise, constructs a new CharProb object and adds it to the given list.
	private void calculateCounts(LinkedList<CharProb> probs, char c) {
		int indexOf = indexOf(probs, c);
		if (indexOf == -1) {
			probs.add(new CharProb(c));
		}
		else {
			probs.get(indexOf).count++;
		}
	}
	private static int indexOf(LinkedList<CharProb> probs, char c) {
		// Replace the following statement with your code.
		int size = probs.size();
		for (int i = 0; i < size; i++) {
			if (probs.get(i).chr == c) {
				return i;
			}
		}
		return -1;
	}

	// Calculates and sets the probabilities (p and cp fields) of all the
	// characters in the given list.
	private void calculateProbabilities(LinkedList<CharProb> probs) {
		double cp = 0;
		double totalCount = 0;
		for (int i = 0; i < probs.size(); i++) {
			totalCount += probs.get(i).count;
		}
		for (int i = 0; i < probs.size(); i++) {
			probs.get(i).p = probs.get(i).count/totalCount;
			cp += probs.get(i).p;
			probs.get(i).cp = cp;
		}
	}

	/**
	 * Returns a string representing the probabilities map.
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (String key : probabilities.keySet()) {
			LinkedList<CharProb> keyProbs = probabilities.get(key);
			str.append(key + ": ").append(Tools.toString(keyProbs)).append("\n");
		}
		return str.toString();
	}

	public String generate(String initialText, int textLength) {
		if (initialText.length() < windowLength) {
			return initialText;
		}
		String str = initialText;
		String window = initialText.substring(initialText.length() - windowLength);
		for (int i = 0; i < textLength - initialText.length(); i++) {
			if (probabilities.get(window) != null) {
				str += getRandomChar(probabilities.get(window));
				window = window.substring(1);
				window += str.charAt(str.length()-1);
			}
			else {
				return str;
			}

        }
		return str;
	}

	private char getRandomChar(LinkedList<CharProb> probs) {
        double randomS = randomGen.nextDouble();
        for (int i = 0; i < probs.size(); i++) {
            if (probs.get(i).cp > randomS) {
                return probs.get(i).chr;
            }
        }
		return 0;
	}

	// Learns the text that comes from standard input,
	// using the window length given in args[0],
	// and prints the resulting map.
	public static void main(String[] args) {
		int windowLength = Integer.parseInt(args[0]);
		String intialText = args[1];
		int textLength = Integer.parseInt(args[2]);
		boolean random = (args[3].equals("random") ? true : false);

		// Constructs a learning model
		LanguageModel lm;

		if (random) {
			lm = new LanguageModel(windowLength);
		}
		else {
			lm = new LanguageModel(windowLength, 20);
		}
		// Builds the language model
		lm.train();
		// Prints the resulting map
		System.out.println(lm.generate(intialText, textLength));
	}

}