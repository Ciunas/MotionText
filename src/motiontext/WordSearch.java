package motiontext;

import java.awt.Color;
import java.util.ArrayList; 
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class WordSearch {

	ArrayList<Integer> location = new ArrayList<Integer>();
	Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.red);
	Object highlightTag = null;
	JTextPane jta;
	String word;
	String document;
	int position;

	public WordSearch(JTextPane jta, JTextField word) {
		this.jta = jta;
		this.word = word.getText().trim();
	}

	@SuppressWarnings("unused")
	public int setFirst() throws BadLocationException {

		document = jta.getText();
		int i = document.indexOf(word);
		while (i >= 0) {
			location.add(i);
			i = document.indexOf(word, i + 1);
		}

		for (int j = 0; j < location.size(); j++) {
			if (location.get(j) >= jta.getCaretPosition()) {
				try {
					highlightTag = jta.getHighlighter().addHighlight(location.get(j),
							location.get(j) + word.length(), painter);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				position = j;
				return 0;
			}else {
				try {
					highlightTag = jta.getHighlighter().addHighlight(location.get(0),
							location.get(0) + word.length(), painter);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				position = 0;
				return 0;
			}
		}
		return -1;
	}

	public void forward() throws BadLocationException {
		if (position < location.size() - 1) {
			jta.getHighlighter().removeHighlight(highlightTag);
			highlightTag = jta.getHighlighter().addHighlight(location.get(++position),
					location.get(position) + word.length(), painter);
		} else { 
			jta.getHighlighter().removeHighlight(highlightTag);
			highlightTag = jta.getHighlighter().addHighlight(location.get(position = 0),
					location.get(position) + word.length(), painter);
		}
	}

	public void backwards() throws BadLocationException {
		if (position > 0) {
			jta.getHighlighter().removeHighlight(highlightTag);
			highlightTag = jta.getHighlighter().addHighlight(location.get(--position),
					location.get(position) + word.length(), painter);
		} else { 
			jta.getHighlighter().removeHighlight(highlightTag);
			highlightTag = jta.getHighlighter().addHighlight(location.get(position = location.size() - 1),
					location.get(position) + word.length(), painter);
		}
	}
	
	public void removeHighlight() {
		System.out.println("Here");
		if(!(highlightTag == null)) {
			jta.getHighlighter().removeHighlight(highlightTag);
		} 
	}
	
	public String getWord() {
		return word;
	}

}