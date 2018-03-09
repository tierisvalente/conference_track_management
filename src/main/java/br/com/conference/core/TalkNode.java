package br.com.conference.core;

import br.com.conference.entity.Talk;

public class TalkNode {

	private Talk value;
	
	private TalkNode left;
	
	private TalkNode right;
	
	public TalkNode(Talk value) {
		this.value = value;
		this.left = null;
		this.right = null;
	}

	public Talk getValue() {
		return value;
	}

	public void setValue(Talk value) {
		this.value = value;
	}

	public TalkNode getLeft() {
		return left;
	}

	public void setLeft(TalkNode left) {
		this.left = left;
	}

	public TalkNode getRight() {
		return right;
	}

	public void setRight(TalkNode right) {
		this.right = right;
	}
}
