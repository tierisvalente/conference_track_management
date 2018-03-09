package br.com.conference;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import br.com.conference.core.BinaryTree;
import br.com.conference.core.InputReader;
import br.com.conference.entity.Talk;
import br.com.conference.service.TrackManagement;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        InputReader inputReader = new InputReader();
        BinaryTree binaryTree = new BinaryTree();
        
        for (Talk talk : inputReader.readTalks()) {
			binaryTree.add(talk);
//			System.out.println(binaryTree.containsNode(talk));
		}
        TrackManagement management = new TrackManagement();
        HashMap<String, List<Talk>> talks = management.organizeTalks(binaryTree);
        DateTimeFormatter formatter = DateTimeFormat.forPattern("hh:mm aa");
        
        for (Entry<String, List<Talk>> entry : talks.entrySet()) {
			System.out.println(entry.getKey());
			for (Talk talk : entry.getValue()) {
				System.out.println(formatter.print(talk.getStartTime()) + " " + talk.getName() + (talk.getDuration() > 0 ? " " + talk.getDuration() : ""));
			}
		}
        
//        System.out.println(binaryTree);
    }
}
