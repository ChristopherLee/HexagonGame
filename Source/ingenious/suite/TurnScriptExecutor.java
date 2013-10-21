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
package ingenious.suite;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import ingenious.board.Color;
import ingenious.board.NullTile;
import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.Tile;
import ingenious.board.util.Constants;

import ingenious.suite.PlayerResult;
import ingenious.suite.PlayerResultType;
import ingenious.suite.network.BoardXML;

/**
 * XML parser for the Turn module
 */
public class TurnScriptExecutor {

    private final XStream xstream;
    
    private final static TurnScriptExecutor instance = new TurnScriptExecutor();
    
    private TurnScriptExecutor() {
        xstream = new XStream();
        xstream.alias(Constants.TAG_TURN, TurnXML.class);
        xstream.alias(Constants.TAG_ACTIONS, ArrayList.class);
        xstream.alias(Constants.TAG_BOARD, BoardXML.class);
        xstream.alias(Constants.TAG_TILE, Tile.class);
        xstream.alias(Constants.TAG_PLACEMENT, Placement.class);
        xstream.alias(Constants.TAG_RESULTS, BoardResult.class);
        xstream.alias(Constants.TAG_RESULTS, TurnResult.class);
        xstream.alias(Constants.TAG_RESULTS, PlayerResult.class);
        xstream.alias(Constants.TAG_SCORE, Score.class);
        xstream.registerConverter(new TurnActionConverter());
        xstream.registerConverter(new BoardXMLConverter());
        xstream.registerConverter(new ActionsConverter());
        xstream.registerConverter(new PlacementConverter());
        xstream.registerConverter(new TileConverter());
        xstream.registerConverter(new BoardResultConverter());
        xstream.registerConverter(new ScoreConverter());
        xstream.registerConverter(new TurnResultConverter());
        xstream.registerConverter(new PlayerResultConverter());
    }
    
    public static TurnScriptExecutor getInstance() {
        return instance;
    }
        
    public Object readXml (InputStream input) {
        return xstream.fromXML(input);
    }
    
    public String writeXml(Object output) {
        return xstream.toXML(output);
    }
    
//    public String writeErrorXml(TurnException exception) {
//        return xstream.toXML(exception);
//    }

    class TurnActionConverter implements Converter {

        @Override
        public void marshal(Object source, HierarchicalStreamWriter writer,
                    MarshallingContext context) {
            
        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader,
                    UnmarshallingContext context) {
                        
            reader.moveDown();
            BoardXML boardXML = (BoardXML) context.convertAnother(null, BoardXML.class);
            reader.moveUp();

            reader.moveDown();
            reader.moveDown();
            Score score = (Score) context.convertAnother(null, Score.class);
            reader.moveUp();
            List<Tile> playerHand = parsePlayerHand(reader, context);
            reader.moveUp();
            
            reader.moveDown();
            List<Tile> tileBag = parseAdmin(reader, context);
            reader.moveUp();
            
            reader.moveDown();
            List<Placement> actions = (List<Placement>) context.convertAnother(null, List.class);
            reader.moveUp();
                   
            TurnXML obj = new TurnXML(boardXML.getNumberOfPlayers(), 
                                            boardXML.getCurrentPlacements(), 
                                            playerHand, score, tileBag, actions);
            
            return obj; 
        }
        
        private List<Tile> parsePlayerHand (HierarchicalStreamReader reader, 
        		UnmarshallingContext context) {            
            List<Tile> tiles = new ArrayList<Tile>();
            while(reader.hasMoreChildren()) {
                reader.moveDown();
                Tile tile = (Tile) context.convertAnother(null, Tile.class);
                tiles.add(tile);
                reader.moveUp();
            }
            return tiles;
        }
        
        private List<Tile> parseAdmin (HierarchicalStreamReader reader, 
        		UnmarshallingContext context) {
        	List<Tile> tiles = new ArrayList<Tile>();
            while(reader.hasMoreChildren()) {
                reader.moveDown();
                Tile tile = (Tile) context.convertAnother(null, Tile.class);
                tiles.add(tile);
                reader.moveUp();
            }
            return tiles;
        }

        @Override
        public boolean canConvert(Class type) {
            return TurnXML.class.equals(type);
        }
        
    }
       
    class ActionsConverter implements Converter {

        @Override
        public void marshal(Object source, HierarchicalStreamWriter writer,
                    MarshallingContext context) {
            
        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader,
                    UnmarshallingContext context) {
            
            List<Placement> actions = new ArrayList<Placement>();
            while(reader.hasMoreChildren()) {
                reader.moveDown();
                Placement placement = (Placement) context.convertAnother(null, Placement.class);
                actions.add(placement);
                reader.moveUp();
            }
            return actions;
        }

        @Override
        public boolean canConvert(Class type) {
            return ArrayList.class.equals(type);
        }
        
    }
    
    class BoardXMLConverter implements Converter {

        @Override
        public void marshal(Object source, HierarchicalStreamWriter writer,
                    MarshallingContext context) {
            // TODO: marshal Board to XML
        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader,
                    UnmarshallingContext context) {
            
            String players = reader.getAttribute(Constants.ATTRIBUTE_BOARD_PLAYERS);
            int numberOfPlayers = new Integer(players).intValue();
            
            List<Placement> currentPlacements = new ArrayList<Placement>();
            while(reader.hasMoreChildren()) {
                reader.moveDown();
                Placement placement = (Placement) context.convertAnother(null, Placement.class);
                currentPlacements.add(placement);
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
        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader,
                    UnmarshallingContext context) {
            
            String c0 = reader.getAttribute("c0");
            String d0 = reader.getAttribute("d0");
            String a0 = reader.getAttribute("a0");
            String c1 = reader.getAttribute("c1");
            String d1 = reader.getAttribute("d1");
            String a1 = reader.getAttribute("a1");
            
            Placement placement = new Placement(Color.valueOf(c0), 
                                 Integer.valueOf(d0), 
                                 Integer.valueOf(a0),
                                 Color.valueOf(c1), 
                                 Integer.valueOf(d1), 
                                 Integer.valueOf(a1));
            return placement;
        }

        @Override
        public boolean canConvert(Class type) {
            return Placement.class.equals(type);
        }
        
    }
    
    class TileConverter implements Converter {

        @Override
        public void marshal(Object source, HierarchicalStreamWriter writer,
                    MarshallingContext context) {
        
            Tile tile = (Tile) source;
            if (tile.isNull()) {
                writer.startNode(Constants.TAG_NULL_TILE);
                writer.endNode();
            }
            else {
                writer.startNode(Constants.TAG_TILE);
                writer.addAttribute("c0", tile.getColor0().name());
                writer.addAttribute("c1", tile.getColor1().name());
                writer.endNode();
            }
        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader,
                    UnmarshallingContext context) {
            
            if (Constants.TAG_NULL_TILE.equals(reader.getNodeName())) {
                Tile tile = new NullTile();
                return tile;
            } else {
                String c0 = reader.getAttribute("c0");
                String c1 = reader.getAttribute("c1");
                
                Tile tile = new Tile(Color.valueOf(c0), Color.valueOf(c1));
                return tile;
            }
        }

        @Override
        public boolean canConvert(Class type) {
            return Tile.class.isAssignableFrom(type);
        }
        
    }
    
    class BoardResultConverter implements Converter {

        @Override
        public void marshal(Object source, HierarchicalStreamWriter writer,
                    MarshallingContext context) {
            BoardResult scoreResult = (BoardResult) source;
            Score[] scores = scoreResult.getScores();
            for(Score score: scores) {
                context.convertAnother(score);
            }
            if(scoreResult.isBad()) {
                writer.startNode(Constants.TAG_BAD);
                writer.addAttribute(Constants.TAG_REASON, scoreResult.getReason());
                writer.endNode();
            }
        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader,
                    UnmarshallingContext context) {
            return null;
        }

        @Override
        public boolean canConvert(Class type) {
            return BoardResult.class.equals(type);
        }
        
    }
    
    class ScoreConverter implements Converter {

        @Override
        public void marshal(Object source, HierarchicalStreamWriter writer,
                    MarshallingContext context) {
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
    
    class TurnResultConverter implements Converter {

        @Override
        public void marshal(Object source, HierarchicalStreamWriter writer,
                    MarshallingContext context) {
            TurnResult result = (TurnResult) source;
            
            List<Tile> tiles = result.getTiles();
            for(Tile tile: tiles) {
                //context.convertAnother(tile);
            	if (tile.isNull()) {
                    writer.startNode(Constants.TAG_NULL_TILE);
                    writer.endNode();
                }
                else {
                    writer.startNode(Constants.TAG_TILE);
                    writer.addAttribute("c0", tile.getColor0().name());
                    writer.addAttribute("c1", tile.getColor1().name());
                    writer.endNode();
                }
            }
            
            if (result.isBad()) {
                writer.startNode(Constants.TAG_BAD);
                writer.addAttribute(Constants.TAG_REASON, result.getReason());
                writer.endNode();
            }
        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader,
                    UnmarshallingContext context) {
            
            List<Tile> tiles = new ArrayList<Tile>();
            String reason = null;
            
            while(reader.hasMoreChildren()) {
                reader.moveDown();
                if (Constants.TAG_BAD.equals(reader.getNodeName())) {
                    reason = reader.getAttribute(Constants.TAG_REASON);
                } else {
                    Tile tile = (Tile) context.convertAnother(null, Tile.class);
                    tiles.add(tile);
                }
                reader.moveUp();  
            }
            
            TurnResult result = new TurnResult(tiles, reason);
            return result;
        }

        @Override
        public boolean canConvert(Class type) {
            return TurnResult.class.equals(type);
        }
        
    }
    
    class PlayerResultConverter implements Converter {

        @Override
        public void marshal(Object source, HierarchicalStreamWriter writer,
                    MarshallingContext context) {
            PlayerResult result = (PlayerResult) source;
            
            if (result.getType() == PlayerResultType.SCORE) {
            	context.convertAnother(result.getScore());
            }
            else if (result.getType() == PlayerResultType.BAD) {
            	writer.startNode(Constants.TAG_BAD);
            	writer.addAttribute(Constants.TAG_REASON, result.getError());
            	writer.endNode();
            }
            else {
            	writer.startNode(Constants.TAG_STRATEGY);
            	writer.endNode();
            }
        }
        
        public Object unmarshal(HierarchicalStreamReader reader,
                UnmarshallingContext context) {
        	return null;
        }
        
        @Override
        public boolean canConvert(Class type) {
            return PlayerResult.class.equals(type);
        }
        
    }
    
}
