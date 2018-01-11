package notepad;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class WordSearch {
	
	Object highlightTag;
	ArrayList<Integer> location = new ArrayList<Integer>();
	Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.cyan);
	DataNode dn;
	String word;
	String document;
	int position = 0;

	public WordSearch(DataNode dn, JTextField word) { 
		this.dn = dn;
		this.word = word.getText().trim();
	}
	
	public void setFirst() throws BadLocationException {
		
		document = dn.getJta().getText();
		int i = document.indexOf(word);
		while (i >= 0) {
			location.add(i);
			i = document.indexOf(word, i + 1);
		}  
		
		for (int j = 0; j < location.size(); j++) {
			if (location.get(j) >= dn.getJta().getCaretPosition()) {
				try {
					highlightTag = dn.getJta().getHighlighter().addHighlight(location.get(j), location.get(j) + word.length(),
							painter);
					
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				position = j;
				return;
			}
		}
	}
	
	public void forward() throws BadLocationException {
		dn.getJta().getHighlighter().removeHighlight(highlightTag);
		highlightTag = dn.getJta().getHighlighter().addHighlight(location.get(++position), location.get(position) + word.length(),
				painter);
	}

	public void backwards() throws BadLocationException {
		dn.getJta().getHighlighter().removeHighlight(highlightTag);
		highlightTag = dn.getJta().getHighlighter().addHighlight(location.get(--position), location.get(position) + word.length(),
				painter);
	}
}