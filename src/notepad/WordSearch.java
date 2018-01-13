package notepad;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class WordSearch {
	
	ArrayList<Integer> location = new ArrayList<Integer>();
	Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.red);
	Object highlightTag;
	DataNode dn;
	String word;
	String document;
	int position;
	

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
		if (position < location.size()-1) {
			dn.getJta().getHighlighter().removeHighlight(highlightTag);
			highlightTag = dn.getJta().getHighlighter().addHighlight(location.get(++position),
					location.get(position) + word.length(), painter);
		}
	}

	public void backwards() throws BadLocationException {
		if (position > 0) {
			dn.getJta().getHighlighter().removeHighlight(highlightTag);
			highlightTag = dn.getJta().getHighlighter().addHighlight(location.get(--position),
					location.get(position) + word.length(), painter);
		}
	}
}