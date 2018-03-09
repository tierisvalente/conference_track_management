package br.com.conference.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.conference.entity.Talk;

public class InputReader {

	private static final String fileName = "input.txt";

	public List<Talk> readTalks() {
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		List<Talk> result = new ArrayList<Talk>();

		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			String currentLine;

			while ((currentLine = bufferedReader.readLine()) != null) {
				Talk talk = new Talk();

				if (currentLine.endsWith("lightning")) {
					talk.setName(currentLine.replace("lightning", "").trim());
					talk.setDuration(5);
				} else {
					String pattern = "(\\d+min)";
					Pattern r = Pattern.compile(pattern);
					Matcher m = r.matcher(currentLine);
					m.find();
					talk.setName(currentLine.replace(m.group(0), "").trim());
					talk.setDuration(new Integer(m.group(0).replace("min", "")));
				}

				result.add(talk);
			}

		} catch (FileNotFoundException e) {
			System.err.println("File not found.");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null)
					bufferedReader.close();
				if (fileReader != null)
					fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
}
