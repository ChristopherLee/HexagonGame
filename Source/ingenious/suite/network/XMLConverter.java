/** 
 * ScriptExecutor.java
 *
 * This source file can be used for educational purpose only.
 * Originally developed by Chunzhao Zheng.
 * 
 * Copyright (c) 2011 Chunzhao, College of Computer and Information Science,
 * Northeastern University, Boston, MA 02115. All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met: 
 *     -  Redistributions of source code must retain the above copyright notice.
 *     -  Redistributions in binary form must reproduce the above copyright notice.
 *
 * Revision History
 *       Date             Programmer            Notes
 * ------------------ ------------------ -------------------
 *  Feb 2, 2011        Chunzhao ZHENG         Initial
 *********************************************************************
 */
package ingenious.suite.network;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.CompactWriter;

import ingenious.board.Color;
import ingenious.board.NullTile;
import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.Tile;
import ingenious.board.util.Constants;
import ingenious.suite.ResultsXML;
import ingenious.suite.TurnTestXML;
import ingenious.suite.network.TurnXML;

/**
 * XML parser for the Turn module
 */
public class XMLConverter {

	private final XStream xstream;

	private final static XMLConverter instance = new XMLConverter();

	private XMLConverter() {
		xstream = new XStream();
		xstream.alias(Constants.TAG_REGISTER, RegisterXML.class);
		xstream.alias(Constants.TAG_ACCEPT, AcceptXML.class);
		xstream.alias(Constants.TAG_PLAYERS, PlayersXML.class);
		xstream.alias(Constants.TAG_BOARD, BoardXML.class);
		xstream.alias(Constants.TAG_TILE, TileXML.class);
		xstream.alias(Constants.TAG_PLACEMENT, PlacementXML.class);
		xstream.alias(Constants.TAG_SCORE, Score.class);
		xstream.alias(Constants.TAG_TURN, TurnXML.class);
		xstream.alias(Constants.TAG_RERACKABLE, RerackableXML.class);
		xstream.alias(Constants.TAG_TRUE, TrueXML.class);
		xstream.alias(Constants.TAG_FALSE, FalseXML.class);
		xstream.alias(Constants.TAG_RERACK, RerackXML.class);
		xstream.alias(Constants.TAG_RESULT, ResultXML.class);
		xstream.alias(Constants.TAG_END, EndXML.class);
		xstream.alias(Constants.TAG_RESULTS, ResultsXML.class);

		xstream.alias(Constants.TAG_TEST_TURN, TurnTestXML.class);
		xstream.registerConverter(new RegisterConverter());
		xstream.registerConverter(new AcceptConverter());
		xstream.registerConverter(new TileConverter());
		xstream.registerConverter(new PlayersConverter());
		xstream.registerConverter(new ScoreConverter());
		xstream.registerConverter(new BoardConverter());
		xstream.registerConverter(new PlacementConverter());
		xstream.registerConverter(new TurnConverter());
		xstream.registerConverter(new RerackableConverter());
		xstream.registerConverter(new BooleanConverter());
		xstream.registerConverter(new RerackConverter());
		xstream.registerConverter(new ResultConverter());		
		xstream.registerConverter(new EndConverter());
		
		xstream.registerConverter(new TurnTestConverter());
		xstream.registerConverter(new ResultsConverter());
	}

	public static XMLConverter getInstance() {
		return instance;
	}

	public Object readXml (InputStream input) {
		return xstream.fromXML(input);
	} 

	public Object readXml (String input) {
		return xstream.fromXML(input);
	} 

	public String writeXml(Object output) {
		StringWriter sw = new StringWriter();
		xstream.marshal(output, new CompactWriter(sw));
		return sw.toString();
	}

	class RegisterConverter implements Converter {

		@Override
		public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
			RegisterXML registerPojo = (RegisterXML)source;
			writer.addAttribute(Constants.ATTRIBUTE_REGISTER_NAME, registerPojo.getName());
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader reader,
		                        UnmarshallingContext context) {
			String name = reader.getAttribute(Constants.ATTRIBUTE_REGISTER_NAME);
			return new RegisterXML(name);
		}

		@Override
		public boolean canConvert(Class type) {
			return RegisterXML.class.equals(type);
		}

	}

	class AcceptConverter implements Converter {

		@Override
		public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
			AcceptXML accept = (AcceptXML)source;
			writer.addAttribute(Constants.ATTRIBUTE_ACCEPT_PLAYER, "" + accept.getNumberOfPlayers());
			for (Tile tile : accept.getTiles()) {
				writer.startNode(Constants.TAG_TILE);
				context.convertAnother(new TileXML(tile));
				writer.endNode();
			}
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader reader,
		                        UnmarshallingContext context) {

			String numberOfPlayersStr = reader.getAttribute(Constants.ATTRIBUTE_ACCEPT_PLAYER);
			int numberOfPlayers;
			try {
				numberOfPlayers = Integer.parseInt(numberOfPlayersStr);
			}
			catch (NumberFormatException e) {
				return null;
			}
			List<Tile> tiles = new ArrayList<Tile>();
			while(reader.hasMoreChildren()) {
				reader.moveDown();
				TileXML tileXML = (TileXML) context.convertAnother(null, TileXML.class);
				tiles.add(tileXML.getTile());
				reader.moveUp();
			}
			return new AcceptXML(numberOfPlayers, tiles);
		}

		@Override
		public boolean canConvert(Class type) {
			return AcceptXML.class.equals(type);
		}
	}

	class TileConverter implements Converter {

		@Override
		public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
			TileXML tileXML = (TileXML) source;
			Tile tile = tileXML.getTile();
			if(!tile.isNull()){
				writer.addAttribute(Constants.ATTRIBUTE_TILE_C0, tile.getColor0().name());
				writer.addAttribute(Constants.ATTRIBUTE_TILE_C1, tile.getColor1().name());
			}
			else
				new RuntimeException("Can't Marshal a null tile");
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader reader,
		                        UnmarshallingContext context) {

			if (Constants.TAG_NULL_TILE.equals(reader.getNodeName())) {
				Tile tile = new NullTile();
				return tile;
			} else {
				String c0 = reader.getAttribute(Constants.ATTRIBUTE_TILE_C0);
				String c1 = reader.getAttribute(Constants.ATTRIBUTE_TILE_C1);

				Tile tile = new Tile(Color.valueOf(c0), Color.valueOf(c1));
				return new TileXML(tile);
			}
		}

		@Override
		public boolean canConvert(Class type) {
			return TileXML.class.equals(type);
		}

	}

	class PlayersConverter implements Converter {    	
		@Override
		public void marshal(Object source, HierarchicalStreamWriter writer,MarshallingContext context) {
			PlayersXML players = (PlayersXML)source;
			for (String name : players.getNames()) {
				writer.startNode(Constants.TAG_PLAYER);
				writer.addAttribute(Constants.ATTRIBUTE_PLAYER_NAME, name);
				writer.endNode();
			}
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
			List<String> names = new ArrayList<String> ();
			while(reader.hasMoreChildren()) {
				reader.moveDown();
				names.add(reader.getAttribute(Constants.ATTRIBUTE_PLAYER_NAME));
				reader.moveUp();
			}
			return new PlayersXML(names);
		}

		@Override
		public boolean canConvert(Class type) {
			return PlayersXML.class.equals(type);
		}
	}

	class ScoreConverter implements Converter {

		@Override
		public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
			Score score = (Score) source;

			writer.startNode(Constants.TAG_SCORE);

			writer.addAttribute(Color.ORANGE.toString().toLowerCase(), 
			                    String.valueOf(score.get(Color.ORANGE)));
			writer.addAttribute(Color.RED.toString().toLowerCase(), 
			                    String.valueOf(score.get(Color.RED)));
			writer.addAttribute(Color.GREEN.toString().toLowerCase(), 
			                    String.valueOf(score.get(Color.GREEN)));
			writer.addAttribute(Color.YELLOW.toString().toLowerCase(), 
			                    String.valueOf(score.get(Color.YELLOW)));
			writer.addAttribute(Color.PURPLE.toString().toLowerCase(), 
			                    String.valueOf(score.get(Color.PURPLE)));
			writer.addAttribute(Color.BLUE.toString().toLowerCase(), 
			                    String.valueOf(score.get(Color.BLUE)));

			writer.endNode();
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader reader,
		                        UnmarshallingContext context) {

			String c0 = reader.getAttribute("orange");
			String d0 = reader.getAttribute("red");
			String a0 = reader.getAttribute("green");
			String c1 = reader.getAttribute("yellow");
			String d1 = reader.getAttribute("purple");
			String a1 = reader.getAttribute("blue");

			Score score = new Score();
			score.setValue(Color.ORANGE, Integer.valueOf(c0));
			score.setValue(Color.RED, Integer.valueOf(d0)); 
			score.setValue(Color.GREEN, Integer.valueOf(a0)); 
			score.setValue(Color.YELLOW, Integer.valueOf(c1)); 
			score.setValue(Color.PURPLE, Integer.valueOf(d1)); 
			score.setValue(Color.BLUE, Integer.valueOf(a1)); 

			return score;
		}

		@Override
		public boolean canConvert(Class type) {
			return Score.class.equals(type);
		}
	}

	class BoardConverter implements Converter {

		@Override
		public void marshal(Object source, HierarchicalStreamWriter writer,
		                    MarshallingContext context) {
			BoardXML boardXML = (BoardXML)source;
			writer.startNode(Constants.TAG_BOARD);
			writer.addAttribute(Constants.ATTRIBUTE_BOARD_PLAYERS, "" + boardXML.getNumberOfPlayers());
			for (Placement placement : boardXML.getCurrentPlacements()) {
				writer.startNode(Constants.TAG_PLACEMENT);
				context.convertAnother(new PlacementXML(placement));
				writer.endNode();
			}
			writer.endNode();
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader reader,
		                        UnmarshallingContext context) {

			String players = reader.getAttribute(Constants.ATTRIBUTE_BOARD_PLAYERS);
			int numberOfPlayers = new Integer(players).intValue();

			List<Placement> currentPlacements = new ArrayList<Placement>();
			while(reader.hasMoreChildren()) {
				reader.moveDown();
				PlacementXML placementXML = (PlacementXML) context.convertAnother(null, PlacementXML.class);
				currentPlacements.add(placementXML.getPlacement());
				reader.moveUp();   
			}

			return new BoardXML(numberOfPlayers, currentPlacements);
		}

		@Override
		public boolean canConvert(Class type) {
			return BoardXML.class.equals(type);
		}
	}

	class PlacementConverter implements Converter {

		@Override
		public void marshal(Object source, HierarchicalStreamWriter writer,
		                    MarshallingContext context) {
			PlacementXML placementXML = (PlacementXML)source;
			Placement placement = placementXML.getPlacement();
			writer.addAttribute(Constants.ATTRIBUTE_PLACEMENT_C0, 
			                    placement.getTile().getColor0().toString());
			writer.addAttribute(Constants.ATTRIBUTE_PLACEMENT_D0, 
			                    "" + placement.getCoordinate0().getDistance());
			writer.addAttribute(Constants.ATTRIBUTE_PLACEMENT_A0, 
			                    "" + placement.getCoordinate0().getAngle());
			writer.addAttribute(Constants.ATTRIBUTE_PLACEMENT_C1, 
			                    placement.getTile().getColor1().toString());
			writer.addAttribute(Constants.ATTRIBUTE_PLACEMENT_D1, 
			                    "" + placement.getCoordinate1().getDistance());
			writer.addAttribute(Constants.ATTRIBUTE_PLACEMENT_A1, 
			                    "" + placement.getCoordinate1().getAngle());
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader reader,
		                        UnmarshallingContext context) {

			String c0 = reader.getAttribute(Constants.ATTRIBUTE_PLACEMENT_C0);
			String d0 = reader.getAttribute(Constants.ATTRIBUTE_PLACEMENT_D0);
			String a0 = reader.getAttribute(Constants.ATTRIBUTE_PLACEMENT_A0);
			String c1 = reader.getAttribute(Constants.ATTRIBUTE_PLACEMENT_C1);
			String d1 = reader.getAttribute(Constants.ATTRIBUTE_PLACEMENT_D1);
			String a1 = reader.getAttribute(Constants.ATTRIBUTE_PLACEMENT_A1);

			Placement placement = new Placement(Color.valueOf(c0), 
			                                    Integer.valueOf(d0), 
			                                    Integer.valueOf(a0),
			                                    Color.valueOf(c1), 
			                                    Integer.valueOf(d1), 
			                                    Integer.valueOf(a1));
			return new PlacementXML(placement);
		}

		@Override
		public boolean canConvert(Class type) {
			return PlacementXML.class.equals(type);
		}
	}

	class TurnConverter implements Converter {

		@Override
		public void marshal(Object source, HierarchicalStreamWriter writer,
		                    MarshallingContext context) {
			TurnXML turnXML = (TurnXML)source;
			context.convertAnother(new BoardXML(turnXML.getNumberOfPlayers(), turnXML.getCurrentPlacements()));

			context.convertAnother(turnXML.getPlayerScore());

			for (Tile tile : turnXML.getPlayerHand()) {
				writer.startNode(Constants.TAG_TILE);
				context.convertAnother(new TileXML(tile));
				writer.endNode();
			}
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader reader,
		                        UnmarshallingContext context) {

			reader.moveDown();
			BoardXML boardXML = (BoardXML) context.convertAnother(null, BoardXML.class);
			reader.moveUp();

			reader.moveDown();
			Score score = (Score) context.convertAnother(null, Score.class);
			reader.moveUp();

			List<Tile> playerHand = parsePlayerHand(reader, context);

			TurnXML obj = new TurnXML(boardXML.getNumberOfPlayers(), 
			                          boardXML.getCurrentPlacements(), 
			                          playerHand, score);

			return obj; 
		}

		private List<Tile> parsePlayerHand (HierarchicalStreamReader reader, 
		                                    UnmarshallingContext context) {            
			List<Tile> tiles = new ArrayList<Tile>();
			while(reader.hasMoreChildren()) {
				reader.moveDown();
				TileXML tileXML = (TileXML) context.convertAnother(null, TileXML.class);
				tiles.add(tileXML.getTile());
				reader.moveUp();
			}
			return tiles;
		}

		@Override
		public boolean canConvert(Class type) {
			return TurnXML.class.equals(type);
		}

	}

	class RerackableConverter implements Converter {

		@Override
		public void marshal(Object source, HierarchicalStreamWriter writer,
		                    MarshallingContext context) {
			// do nothing
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader reader,
		                        UnmarshallingContext context) {
			return new RerackableXML(); 
		}

		@Override
		public boolean canConvert(Class type) {
			return RerackableXML.class.equals(type);
		}
	}

	class BooleanConverter implements Converter {

		@Override
		public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
			// do nothing
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader reader,
		                        UnmarshallingContext context) {
			if (reader.getNodeName().equals(Constants.TAG_TRUE))
				return new TrueXML();
			else if (reader.getNodeName().equals(Constants.TAG_FALSE))
				return new FalseXML();
			else{
				System.out.println("Boolean Unmarshal unexpectd result");
				return null; 
			}
		}

		@Override
		public boolean canConvert(Class type) {
			return TrueXML.class.equals(type) || FalseXML.class.equals(type);
		}
	}

	class RerackConverter implements Converter {

		@Override
		public void marshal(Object source, HierarchicalStreamWriter writer,
		                    MarshallingContext context) {
			RerackXML rerackXML = (RerackXML)source;

			for (Tile tile : rerackXML.getTiles()) {
				writer.startNode(Constants.TAG_TILE);
				context.convertAnother(new TileXML(tile));
				writer.endNode();
			}
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader reader,
		                        UnmarshallingContext context) {
			List<Tile> tiles = new ArrayList<Tile>();
			while(reader.hasMoreChildren()) {
				reader.moveDown();
				TileXML tileXML = (TileXML) context.convertAnother(null, TileXML.class);
				tiles.add(tileXML.getTile());
				reader.moveUp();
			}
			return new RerackXML(tiles);
		}

		@Override
		public boolean canConvert(Class type) {
			return RerackXML.class.equals(type);
		}
	}

	class ResultConverter implements Converter {

		@Override
		public void marshal(Object source, HierarchicalStreamWriter writer,
		                    MarshallingContext context) {
			ResultXML resultXML = (ResultXML)source;
			writer.addAttribute(Constants.ATTRIBUTE_RESULT_NAME, resultXML.getName());

			for (Placement placement : resultXML.getPlacements()) {
				writer.startNode(Constants.TAG_PLACEMENT);
				context.convertAnother(new PlacementXML(placement));
				writer.endNode();
			}
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader reader,
		                        UnmarshallingContext context) {
			String name = reader.getAttribute(Constants.ATTRIBUTE_RESULT_NAME);
			List<Placement> placements = new ArrayList<Placement>();
			while(reader.hasMoreChildren()) {
				reader.moveDown();
				PlacementXML placementXML = (PlacementXML) context.convertAnother(null, PlacementXML.class);
				placements.add(placementXML.getPlacement());
				reader.moveUp();
			}
			return new ResultXML(name, placements);
		}

		@Override
		public boolean canConvert(Class type) {
			return ResultXML.class.equals(type);
		}
	}

	class EndConverter implements Converter {

		@Override
		public void marshal(Object source, 
		                    HierarchicalStreamWriter writer, 
		                    MarshallingContext context) {
			// do nothing
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader reader,UnmarshallingContext context) {
			return new EndXML(); 
		}

		@Override
		public boolean canConvert(Class type) {
			return EndXML.class.equals(type);
		}
	}

	class TurnTestConverter implements Converter {

		@Override
		public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
			// don't need to create turn test xml
			throw new RuntimeException("Should never Marsahl a TurnTest");
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
			reader.moveDown();
			TurnXML turn = (TurnXML)context.convertAnother(null, TurnXML.class);

			reader.moveUp();

			reader.moveDown();
			List<IAdminResponse> responses = new ArrayList<IAdminResponse>();
			while (reader.hasMoreChildren()) {
				reader.moveDown();
				String tag = reader.getNodeName();
				/*AdminResponse	is:	
					 -- Tile of false 
					 -- Boolean 
					 -- Tiles */				
				IAdminResponse response = null;
				if (tag.equals(Constants.TAG_TILE))
					response = (TileXML)context.convertAnother(null, TileXML.class);
				else if (tag.equals(Constants.TAG_FALSE))
					response = (FalseXML)context.convertAnother(null, FalseXML.class);
				else if (tag.equals(Constants.TAG_TRUE))
					response = (TrueXML)context.convertAnother(null, TrueXML.class);
				else if (tag.equals(Constants.TAG_RERACK))
					response = (RerackXML)context.convertAnother(null, RerackXML.class);
				responses.add(response);
				reader.moveUp();
			}
			reader.moveUp();

			reader.moveDown();
			List<ITurnAction> actions = new ArrayList<ITurnAction>();
			while (reader.hasMoreChildren()) {
				reader.moveDown();

				String tag = reader.getNodeName();
				ITurnAction action = null;
				if (tag.equals(Constants.TAG_PLACEMENT))
					action = (PlacementXML)context.convertAnother(null, PlacementXML.class);
				else if (tag.equals(Constants.TAG_RERACKABLE))
					action = (RerackableXML)context.convertAnother(null, RerackableXML.class);
				else if (tag.equals(Constants.TAG_RERACK))
					action = (RerackXML)context.convertAnother(null, RerackXML.class);

				actions.add(action);       
				reader.moveUp();
			}
			reader.moveUp();

			TurnTestXML pojo = new TurnTestXML(turn, actions, responses);
			return pojo;
		}

		@Override
		public boolean canConvert(Class type) {
			return TurnTestXML.class.equals(type);
		}
	}
	
	class ResultsConverter implements Converter{

		@Override
		public boolean canConvert(Class type) {
			return ResultsXML.class.equals(type);
		}

		@Override
		public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context){
			ResultsXML results = (ResultsXML)source;
			
			for (IAdminResponse response : results.getResponses()) {
				if (response.getClass().equals(TileXML.class)) {
					Tile tile = ((TileXML)response).getTile();
					if(tile.isNull()){
						throw new RuntimeException("Should not enter into this case");
					}
					else {
						writer.startNode(Constants.TAG_TILE);
						writer.addAttribute(Constants.ATTRIBUTE_TILE_C0, tile.getColor0().name());
						writer.addAttribute(Constants.ATTRIBUTE_TILE_C1, tile.getColor1().name());
					}
					writer.endNode();
				}
				else if (response.getClass().equals(FalseXML.class)) {
					writer.startNode(Constants.TAG_FALSE);
					writer.endNode();
				}
				else if (response.getClass().equals(RerackXML.class)) {
					writer.startNode(Constants.TAG_RERACK);	
					context.convertAnother(response);
					writer.endNode();
				}
				
			}
			
			if(results.isBad()) {
				writer.startNode(Constants.TAG_BAD);
				writer.addAttribute(Constants.TAG_REASON, results.getBad());
				writer.endNode();
			}
		}
		
		/**
		 * Should never unmarshal this type
		 */
		@Override
		public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
			throw new RuntimeException("Not supposed to unmarshal ResultsXML");
		}
		
	}
}
