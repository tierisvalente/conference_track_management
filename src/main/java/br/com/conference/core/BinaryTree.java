package br.com.conference.core;

import br.com.conference.entity.Talk;

public class BinaryTree {

	private TalkNode root;

	public void add(Talk value) {
		root = addRecursive(root, value);
	}

	public boolean containsNode(Talk value) {
		return containsNodeRecusive(root, value);
	}

	public void delete(Talk value) {
		root = deleteRecursive(root, value);
	}

	public Talk getRoot() {
		if (root != null)
			return root.getValue();

		return null;
	}

	public Talk getSmallest() {
		return findSmallestValue(root);
	}

	public Talk getBiggest() {
		return findBiggestValue(root);
	}

	public Talk findWithDurationLessOrEqualThan(int duration) {
		return findWithDurationLessOrEqualThan(root, duration);
	}
	
	public Talk findWithDurationLessAndNotEqualThan(int duration, int diff) {
		return findWithDurationLessAndNotEqualThan(root, duration, diff);
	}

	public int getSize() {
		return getSizeRecursive(root);
	}

	private int getSizeRecursive(TalkNode current) {
		return current == null ? 0 : getSizeRecursive(current.getLeft()) + 1 + getSizeRecursive(current.getRight());
	}

	private TalkNode addRecursive(TalkNode current, Talk value) {
		if (current == null) {
			return new TalkNode(value);
		}

		if (value.compareTo(current.getValue()) < 0) {
			current.setLeft(addRecursive(current.getLeft(), value));
		} else if (value.compareTo(current.getValue()) > 0) {
			current.setRight(addRecursive(current.getRight(), value));
		}

		return current;
	}

	private boolean containsNodeRecusive(TalkNode current, Talk value) {
		if (current == null) {
			return false;
		}

		if (value.equals(current.getValue())) {
			return true;
		}

		return value.compareTo(current.getValue()) < 0 ? containsNodeRecusive(current.getLeft(), value)
				: containsNodeRecusive(current.getRight(), value);
	}

	private TalkNode deleteRecursive(TalkNode current, Talk value) {
		if (current == null) {
			return null;
		}

		if (value.equals(current.getValue())) {
			if (current.getLeft() == null && current.getRight() == null) {
				return null;
			}

			if (current.getRight() == null) {
				return current.getLeft();
			}

			if (current.getLeft() == null) {
				return current.getRight();
			}

			Talk smallestValue = findSmallestValue(current.getRight());
			current.setValue(smallestValue);
			current.setRight(deleteRecursive(current.getRight(), smallestValue));
			return current;
		}

		if (value.compareTo(current.getValue()) < 0) {
			current.setLeft(deleteRecursive(current.getLeft(), value));
			return current;
		}

		current.setRight(deleteRecursive(current.getRight(), value));
		return current;
	}

	private Talk findSmallestValue(TalkNode node) {
		return node.getLeft() == null ? node.getValue() : findSmallestValue(node.getLeft());
	}

	private Talk findBiggestValue(TalkNode node) {
		return node.getRight() == null ? node.getValue() : findBiggestValue(node.getRight());
	}

	private Talk findWithDurationLessOrEqualThan(TalkNode node, int duration) {
		if (node == null) {
			return null;
		}

		if (node.getValue().getDuration() > duration) {
			return findWithDurationLessOrEqualThan(node.getLeft(), duration);
		}

		return node.getValue();
	}
	
	private Talk findWithDurationLessAndNotEqualThan(TalkNode node, int duration, int diff) {
		if(node == null) {
			return null;
		}
		
		if(node.getValue().getDuration() > duration) {
			return findWithDurationLessAndNotEqualThan(node.getLeft(), duration, diff);
		}
		
		if(node.getValue().getDuration() == diff) {
			if(node.getLeft() != null)
				return findWithDurationLessAndNotEqualThan(node.getLeft(), duration, diff);
			if(node.getRight() != null && node.getValue().getDuration() < duration)
				return findWithDurationLessAndNotEqualThan(node.getRight(), duration, diff);
		}
		
		return node.getValue();
	}
}
