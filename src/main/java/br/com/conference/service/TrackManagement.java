package br.com.conference.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Minutes;

import br.com.conference.core.BinaryTree;
import br.com.conference.entity.Talk;

public class TrackManagement {

	private static final int morningStart = 9;
	private static final int morningEnd = 12;
	private static final int afternoonStart = 13;
	private static final int afternoonEnd = 17;

	public HashMap<String, List<Talk>> organizeTalks(BinaryTree binaryTree) {
		Calendar now = Calendar.getInstance();
		DateTime time = new DateTime(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1,
				now.get(Calendar.DAY_OF_MONTH), morningStart, 0);
		DateTime lunchTime = new DateTime(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1,
				now.get(Calendar.DAY_OF_MONTH), morningEnd, 0);
		DateTime networkingTime = new DateTime(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1,
				now.get(Calendar.DAY_OF_MONTH), afternoonEnd, 0);
		HashMap<String, List<Talk>> tracks = new HashMap<>();
		List<Talk> track = new ArrayList<>();
		int tracksCount = 0;

		while (binaryTree.getRoot() != null) {
			if (time.isBefore(lunchTime)) {
				if (putNextTalk(binaryTree, time, lunchTime, track, null)) {
					time = time.plusMinutes(track.get(track.size() - 1).getDuration());
				} else if (binaryTree.getSize() > 1) {
					int lastDuration = track.get(track.size() - 1).getDuration();

					if (!rollBack(binaryTree, track, time, lunchTime)) {
						putLunch(time, track);
						time = time.plusMinutes(track.get(track.size() - 1).getDuration());
					} else {
						time = time.minusMinutes(lastDuration).plusMinutes(track.get(track.size() - 1).getDuration());
					}
				}
			} else if (time.isEqual(lunchTime)) {
				putLunch(time, track);
				time = time.plusMinutes(track.get(track.size() - 1).getDuration());
			} else {
				boolean result = false;

				if (time.isBefore(networkingTime)) {
					result = putNextTalk(binaryTree, time, networkingTime, track, null);
				}

				if (result) {
					time = time.plusMinutes(track.get(track.size() - 1).getDuration());
				} else {
					if (time.isAfter(networkingTime.minusHours(1))) {
						putNetworking(time, track);
						tracks.put("Track " + ++tracksCount, track);
						track = new ArrayList<>();
						time = time.plusDays(1).withField(DateTimeFieldType.hourOfDay(), 9)
								.withField(DateTimeFieldType.minuteOfHour(), 0);
						lunchTime = lunchTime.plusDays(1);
						networkingTime = networkingTime.plusDays(1);
						// } else {
						// rollBack();
					}
				}
			}
		}

		if (!tracks.containsKey("Track " + ++tracksCount)) {
			putNetworking(time, track);
			tracks.put("Track " + tracksCount, track);
		}

		return tracks;
	}

	private boolean rollBack(BinaryTree binaryTree, List<Talk> track, DateTime currentTime, DateTime limitTime) {
		Talk lastTalk = track.remove(track.size() - 1);
		binaryTree.add(lastTalk);
		Talk newTalk = findNextTalk(binaryTree, currentTime, limitTime.plusMinutes(lastTalk.getDuration()),
				lastTalk.getDuration());

		if (newTalk != null)
			return putNextTalk(binaryTree, currentTime, limitTime, track, newTalk);

		// revertendo rollback :(
		track.add(lastTalk);
		binaryTree.delete(lastTalk);
		return false;
	}

	private void putNetworking(DateTime time, List<Talk> track) {
		Talk networking = new Talk("Networking Event", 0);
		networking.setStartTime(time);
		track.add(networking);
	}

	private void putLunch(DateTime time, List<Talk> track) {
		Talk lunch = new Talk("Lunch",
				Minutes.minutesBetween(time,
						new DateTime(time.getYear(), time.getMonthOfYear(), time.getDayOfMonth(), afternoonStart, 0))
						.getMinutes());
		lunch.setStartTime(time);
		track.add(lunch);
	}

	private boolean putNextTalk(BinaryTree binaryTree, DateTime currentTime, DateTime limitTime, List<Talk> track,
			Talk talk) {
		if (talk == null)
			talk = findNextTalk(binaryTree, currentTime, limitTime);

		if (talk != null) {
			talk.setStartTime(currentTime);
			track.add(talk);
			binaryTree.delete(talk);
			return true;
		}

		return false;
	}

	private Talk findNextTalk(BinaryTree binaryTree, DateTime currentTime, DateTime endTime) {
		int minutesLeft = Minutes.minutesBetween(currentTime, endTime).getMinutes();
		return binaryTree.findWithDurationLessOrEqualThan(minutesLeft);
	}

	private Talk findNextTalk(BinaryTree binaryTree, DateTime currentTime, DateTime endTime, int diff) {
		int minutesLeft = Minutes.minutesBetween(currentTime, endTime).getMinutes();
		return binaryTree.findWithDurationLessAndNotEqualThan(minutesLeft, diff);
	}
}
